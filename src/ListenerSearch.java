import java.awt.event.*;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *	Classe che implementa l'ascoltatore dei bottoni di ricerca.
 *
 *	Permette la ricerca di testo nelle celle della tabella evidenziato
 *  la corrispondente nel caso di risultato positivo.
 *
 *	@author Daniele Tortoli
 */
public class ListenerSearch implements ActionListener {

	private JTextField txtFieldSearch;
	private JTable 	tableStore;
	private boolean firstTime;
	private int 	indRow;
	private int 	indCol;
	private TableModelStore sDataModel;
	private String toSearch;

	/**
	 * Costruttore che permette di avere i riferimenti necessari per gli oggetti da utilizzare.
	 * @param txtFieldSearch campo ricerca
	 * @param sDataModel modello della vista magazzino
	 * @param firstTime indica se e la prima ricerca effettuata dall'avvio del programma
	 * @param indRow indice di riga
	 * @param indCol indice di colonna
	 * @param tableStore contenitore tabella visualizzata 
	 */
	public ListenerSearch (JTextField txtFieldSearch, TableModelStore sDataModel,
			boolean firstTime, int indRow, int indCol, JTable tableStore) {

		this.txtFieldSearch 	= txtFieldSearch;
		this.sDataModel 		= sDataModel;
		this.firstTime 			= firstTime;
		this.indRow 			= indRow;
		this.indCol 			= indCol;
		this.tableStore 		= tableStore;
	}

	@Override 
	public void actionPerformed(ActionEvent e) {
		Notify ntf = new Notify();

		//Opzione Cerca
		if (e.getActionCommand() == "Cerca") {				
			if (txtFieldSearch.getText().equals("")) {
				ntf.msgWarning("Inserisci la parola da cercare!");	
				return;
			}

			//si disabilita la selezione delle righe per evidenziare la singola cella.
			//se e' la prima ricerca setto gli indici a zero
			if (firstTime) {
				firstTime = false;
				toSearch = txtFieldSearch.getText();
				indRow = 0;
				indCol = 0;
				tableStore.setRowSelectionAllowed(false);
			}
			else {
				//controlla se la ricerca i-esima che sto effettuando e uguale alla ricerca i-1
				//o ho cambiato testo
				if(!toSearch.equals(txtFieldSearch.getText())) {
					//nuova ricerca, riazzero gli indici
					indRow = 0;
					indCol = 0;
					toSearch =  txtFieldSearch.getText();
				}
				else {
					//aumento l'indice colonna per evitare che il ciclo continui dal risultato
					//positivo precedente, altrimenti resto in loop
					indCol += 1;
					tableStore.setRowSelectionAllowed(false);
				}
			}

			try {
				//ritorno l'elemento Prodotto alla riga i-esima
				for ( ; indRow < sDataModel.getRowCount(); indRow++) {

					//analizzo gli elementi alle varie colonne tranne quella delle immagini
					for (; indCol < sDataModel.getColumnCount() -2; indCol++ ) { 
						String temp = String.valueOf(sDataModel.getValueAt(indRow,indCol)); 
						//se la stringa da cercare non e contenuta in temp torno -1
						if (temp.indexOf(toSearch) != -1) {
							updateFocusTable(indRow,indCol);
							return;
						}
					}
					//ho terminato di scorrere la riga, se proseguo nella ricerca riazzero
					//l'indice colonna per la riga successiva o cado fuori dall'array
					indCol = 0;			
				}
			}
			catch(ArrayIndexOutOfBoundsException aoe) {
			}
			catch(NullPointerException npe) {
			}

			//se arrivo qui non ho ottenuto risultati o sono terminate le corrispondenza
			ntf.msgWarning("Ricerca finita\nFai una nuova ricerca");
			tableStore.setRowSelectionAllowed(true);

			indRow = 0;
			indCol = 0; 
			firstTime = true;
		}

		//Comando Cerca Nome
		if (e.getActionCommand() == "Cerca nome") {				
			if (txtFieldSearch.getText().equals("")) {
				ntf.msgWarning("Inserisci la parola da cercare!");	
				return;
			}

			//se e la prima ricerca setto gli indici a zero e disabilito la selezione
			//delle righe per evidenziare la singola cella.
			if (firstTime) {
				firstTime = false;
				toSearch = txtFieldSearch.getText();
				indRow = 0;
				indCol = 0;
				tableStore.setRowSelectionAllowed(false);
			}
			else
				//controlla se la ricerca iesima che sto effettuando e uguale alla ricerca i-1
				//o ho cambiato testo
			{
				//se ricerca modificata resetto gli indici e la stringa da cercare
				if(!toSearch.equals(txtFieldSearch.getText()))	{
					indRow = 0;
					indCol = 0;
					toSearch =  txtFieldSearch.getText();
				}
				else {
					//aumento l'indice riga per evitare che il ciclo continui dal risultato
					//positivo precedente, altrimenti resto in loop
					indRow += 1;
					tableStore.setRowSelectionAllowed(false);
				}
			}

			try {
				//ritorno l'elemento Prodotto alla riga i-esima
				for( ; indRow < sDataModel.getRowCount(); indRow++) {
					//analizzo gli elementi alle varie colonne
					String temp = String.valueOf(sDataModel.getValueAt(indRow,0));

					//se la stringa da cercare non e contenuta in temp torno -1
					if (temp.indexOf(toSearch) != -1) {
						updateFocusTable(indRow,0); //indCol
						return;
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException aoe) {
			}
			catch(NullPointerException npe) {
			}

			//se arrivo qui non ho ottenuto risultati o sono terminate le corrispondenza
			ntf.msgWarning("Ricerca finita\nFai una nuova ricerca");
			tableStore.setRowSelectionAllowed(true);

			indRow = 0;
			indCol = 0; 
			firstTime = true;
		}

		//Comando Termina
		if (e.getActionCommand() == "Termina") {
			txtFieldSearch.setText("");
			tableStore.setRowSelectionAllowed(true);
			indRow = 0;
			indCol = 0; 
			firstTime=true;
			ntf.msgWarning("Ricerca terminata");
		}
	}

	/*
	 * Permette di evidenziare la cella selezionata.
	 */
	private void updateFocusTable(final int indRow, final int indCol) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				tableStore.requestFocusInWindow();
				tableStore.changeSelection(indRow, indCol, false, false);
			}
		});
	}
}