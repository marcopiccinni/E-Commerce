import java.awt.event.* ;
import java.util.Vector ;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter ;
import javax.swing.JFileChooser ;

/**
 *	Classe che implementa l'ascoltatore per i bottoni Salva e Carica.
 *	Svolge le funzioni di salvataggio-caricamento
 *
 * @author Marco Piccinni
 */
public class ListenerSave implements ActionListener {

	private Store<Product> store ;
	private TableModelStore sDataModel ;

	/**
	 * Costruttore che permette di avere i riferimenti necessari per gli oggetti da utilizzare.
	 * @param store magazzino
	 * @param sDataModel modello della vista magazzino
	 */
	public ListenerSave (Store<Product> store, TableModelStore sDataModel) {
		this.store 	= store;
		this.sDataModel = sDataModel;
	}

	@Override 
	public void actionPerformed(ActionEvent e) {
		Notify ntf = new Notify();
		
		//Opzione Salva
		if (e.getActionCommand() == "Salva")
		{
			JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));

			//estensione suggerita
			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("File .dat", "dat");
			chooser.setFileFilter(extFilter);
			//file salvato solo con estensione .dat
			chooser.setAcceptAllFileFilterUsed(false);

			File fileName = null ;

			int valid = chooser.showSaveDialog(null);
			//se procedo col salvataggio controllo estensione
			if (valid == JFileChooser.APPROVE_OPTION) {
				fileName = chooser.getSelectedFile(); 	

				//se l'utente non ha specificato una estensione la aggiunge in automatico
				if (fileName.getName().indexOf('.') == -1)
					fileName = new File(fileName.getPath() + ".dat");
			}
			else if(valid == JFileChooser.CANCEL_OPTION)
				return;

			//se e' gia presente un file con lo stesso nome chiede la sovrascrittura
			if (fileName.exists()) {
				boolean oWrite = ntf.overwrite() ;
				// se la scelta risulta falsa vuol dire che non voglio sovrascrivere. 
				if (!oWrite)
					return ; //torno indietro, non voglio procedere
			}

			//procedura per scrivere su file
			FileOutputStream fos = null ;

			try {
				fos = new FileOutputStream(fileName);
			}
			catch(IOException ioe) {
			}

			ObjectOutputStream oos = null ;

			try {
				oos = new ObjectOutputStream(fos) ;
				Vector<Product> temp = store.getVect() ;
				//scorre il vettore che contiene i prodotti e scrive gli oggetti in esso contenuti
				oos.writeObject(temp) ;
				oos.close() ;
				ntf.msgOk("File Dat salvato correttamente!");
			}
			catch(IOException ioe) {
				ntf.msgError("Salvataggio fallito. " + ioe);
			}

			return;
		}

		//Opzione Carica
		if (e.getActionCommand() == "Carica") {	
			
			JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")) ;
			FileNameExtensionFilter filtrodat = new FileNameExtensionFilter("File dat" , "dat");
			
			chooser.setFileFilter(filtrodat);

			//permette di selezionare solo file con estensione specificata
			chooser.setAcceptAllFileFilterUsed(false);

			//selezione singola
			chooser.setMultiSelectionEnabled(false);

			File toRead = null;

			int valid = chooser.showDialog(null, null);

			if (valid == JFileChooser.APPROVE_OPTION)
				toRead = chooser.getSelectedFile();
			else if(valid == JFileChooser.CANCEL_OPTION)
				return;

			FileInputStream fis = null ;
			ObjectInputStream ois = null ;

			try {
				fis = new FileInputStream(toRead) ;
				ois = new ObjectInputStream(fis) ;	
			} 
			catch(IOException ioe) {
				ntf.msgError("Caricamento fallito.");
			}

			try {
				//dal file ottengo un vettore di prodotti
				@SuppressWarnings("unchecked")
				Vector<Product> temp = (Vector<Product>)(ois.readObject()) ;
				//inserisco nel magazzino i prodotti caricati da file
				store.setVect(temp);
				ntf.msgOk("File dat caricato correttamente!");
			}
			catch(IOException ioe) {
				ntf.msgError("Caricamento fallito.");
			} 
			catch(ClassNotFoundException cnf) {
				ntf.msgError("Caricamento fallito.");
			}

			sDataModel.update();
		} 	
	}
}