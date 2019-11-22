import java.awt.event.*;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Classe che implementa l'ascoltatore del filtro di visualizzazione.
 * A seconda della categoria scelta, permette di visualizzare solo i
 * prodotti che verificano la corrispondenza ricercata.
 * @see Store
 *
 * @author Marco Piccinni
 */
public class ListenerFilterCategory implements ActionListener {

	private JTextField 		txtFieldCategory;
	private TableModelStore sDataModel;	
	private JTable			tableStore;

	/**
	 * Costruttore che permette di avere i riferimenti necessari per gli oggetti da utilizzare.
	 * @param txtFieldCategory campo categoria  
	 * @param sDataModel modello della vista magazzino
	 * @param tableStore contenitore tabella visualizzata
	 */
	public ListenerFilterCategory (JTextField txtFieldCategory, TableModelStore sDataModel, JTable tableStore) {

		this.txtFieldCategory	= txtFieldCategory;
		this.sDataModel     	= sDataModel;
		this.tableStore 		= tableStore;
	}

	@Override 
	public void actionPerformed(ActionEvent e) {
		Notify ntf = new Notify();
		
		if (e.getActionCommand() == "Filtra categoria") {
			//case sensitive
			String cat = txtFieldCategory.getText();

			if (cat.equals(""))	{
				ntf.msgWarning("Inserire campo categoria");
				return;
			}

			//interessa solo la colonna 3 della tabella che 
			//rappresenta la categoria del prodotto
			try {
				//ritorno il campo data dell'i-esimo Prodotto
				for(int i = 0; i < sDataModel.getRowCount(); i++) {

					//leggo la categoria in formato stringa dalla tabella
					String dataTabCat = (String)sDataModel.getValueAt(i,3);

					if (!dataTabCat.equals(cat)){
						// Nascondo i prodotti non relativi alla categoria cercata
						tableStore.setRowHeight(i,1); //a 0 restituisce errore, a 1 non risulta visibile
						txtFieldCategory.setText("");	
						// ALTRIMENTI altezza riga resta invariata perche' categoria trovata
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException aoe) {
			}
		}

		//Reimposta i campi visibili
		if (e.getActionCommand() == "Termina") {
			for( int i = 0; i < sDataModel.getRowCount(); i++)
				tableStore.setRowHeight(i,30);//analizzo gli elementi alle varie colonne
		} 
	}
}