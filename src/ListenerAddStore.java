import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Ascoltatore che implementa i bottoni Aggiungi ed Elimina per il magazzino,
 * e i bottoni Aggiungi e Annulla associati alla finestra di inserimento.
 * @author marco
 *
 */
public class ListenerAddStore implements ActionListener{
	private WindowInsert windowInsert;
	private WindowTable windowTable;
	private Store<Product> store; 
	private TableModelStore sDataModel;

	/**
	 * Costruttore per i pulsanti presenti sulla finestra di inserimento.
	 * @see WindowInsert
	 * @param windowInsert
	 * @param windowTable
	 * @param store
	 * @param sDataModel
	 */
	public ListenerAddStore(WindowInsert windowInsert, WindowTable windowTable, Store<Product> store, TableModelStore sDataModel) {
		this.windowInsert = windowInsert;
		this.windowTable = windowTable;
		this.store = store;
		this.sDataModel = sDataModel;
	}

	/**
	 * Costruttore per i pulsanti presenti sulla finestra principale {@link WindowTable}.
	 * @param windowTable
	 * @param store
	 * @param sDataModel
	 */
	public ListenerAddStore( WindowTable windowTable, Store<Product> store, TableModelStore sDataModel) {
		this.windowTable = windowTable;
		this.store = store;
		this.sDataModel = sDataModel;
	}

	/**
	 * Ridefinizione del metodo per la cattura degli eventi.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Notify ntf = new Notify(); 

		//WINDoW INSERT ==> AGGIUNGI
		if(e.getActionCommand() == "Aggiungi") {

			//Nome Almeno di 3 caratteri
			if(windowInsert.getTextFieldName().equals("")) {
				ntf.msgWarning("Inserire Nome");
				return;
			} else if(windowInsert.getTextFieldName().length() < 3 ){
				ntf.msgWarning("Inserire almeno tre caratteri per il Nome");
				return;
			}

			//Marca	Almeno 3 caratteri
			if(windowInsert.getTextFieldBrand().equals("")) {
				ntf.msgWarning("Inserire Marca");
				return;
			} else if(windowInsert.getTextFieldBrand().length() < 3 ){
				ntf.msgWarning("Inserire almeno tre caratteri per la Marca");
				return;
			}

			//Codice Se lasciato vuoto calcolato automaticamente in base a nome e marca
			String code= "" ;
			if(windowInsert.getTextFieldCode().equals("")) {

				String onlyConsName = windowInsert.getTextFieldName().replaceAll("[aeiouAEIOU]", "");
				if (onlyConsName.length()>3)
					onlyConsName=onlyConsName.substring(0, 3);
				String onlyConsBrand = windowInsert.getTextFieldBrand().replaceAll("[aeiouAEIOU]", "");
				if (onlyConsBrand.length()>3)
					onlyConsBrand = onlyConsBrand.substring(0, 3);
				code = onlyConsName + onlyConsBrand;
				
				if (code.length() < 6) {
					String onlyVocalName = windowInsert.getTextFieldName().replaceAll("[^aeiouAEIOU]", "");
					if (onlyVocalName.length()>3)
						onlyVocalName=onlyVocalName.substring(0, 2);
					String onlyVocalBrand = windowInsert.getTextFieldBrand().replaceAll("[^aeiouAEIOU]", "");
					if (onlyVocalBrand.length()>3)
						onlyVocalBrand=	onlyVocalBrand.substring(0, 2);
					code= onlyConsName + onlyVocalName + onlyConsBrand + onlyVocalBrand + ThreadLocalRandom.current().nextInt(1, 9 + 1);
				}
				code=code.toLowerCase();
			}

			//Categoria
			if(windowInsert.getTextFieldCategory().equals("")) {
				ntf.msgWarning("Inserire Categoria");
				return;
			}
			
			//Prezzo	In formato num.num Il numero di decimali e' fissato a 2
			if(windowInsert.getTextFieldPrice().equals("")) {
				ntf.msgWarning("Inserire Prezzo");
				return;
			} else if(!Pattern.matches("\\d+([.]\\d{1,2})?", windowInsert.getTextFieldPrice()) ){
				windowInsert.setTextFieldPrice("");
				ntf.msgWarning("Inserire un numero positivo con al massimo due decimali");
				return;
			}

			//Controllo che non siano presenti entrambe le caselle
			if(!windowInsert.getTextFieldPromo().equals("") && !windowInsert.getTextFieldDiscount().equals("")) {
				ntf.msgWarning("Un prodotto non puo' essere sia in sconto che in promozione");
				windowInsert.setTextFieldPromo("");
				windowInsert.setTextFieldDiscount("");
				return;
			}

			//Controllo Promozione
			if(!windowInsert.getTextFieldPromo().equals("")) {
				if(!Pattern.matches("\\d[x]\\d", windowInsert.getTextFieldPromo()) || windowInsert.getTextFieldPromo().charAt(0) <=windowInsert.getTextFieldPromo().charAt(2) ){
					windowInsert.setTextFieldPromo("");
					ntf.msgWarning("Inserire offerta corretta NxN");
					return;
				}
			}

			//Controllo Sconto
			if(!windowInsert.getTextFieldDiscount().equals("") && windowInsert.getTextFieldPromo().equals("")) {
				try {
					int tempSconto = Integer.parseInt(windowInsert.getTextFieldDiscount());
					if(tempSconto < 0 || tempSconto > 100) {
						ntf.msgWarning("Sconto errato");
						windowInsert.setTextFieldDiscount("");
						return;
					}
				} catch (NumberFormatException e1) {
					ntf.msgWarning("Sconto errato");
					windowInsert.setTextFieldDiscount("");
					return;
				}
			}

			windowInsert.setVisible(false);
			windowTable.setEnabled(true);

			//Tentativo di creazione e aggiunta prodotto
			try {
				//Apre la finestra per selezionare l'immagine da caricare
				JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + File.separator + "media");
				FileNameExtensionFilter filtroimg = new FileNameExtensionFilter("File .png", "png");
				chooser.setFileFilter(filtroimg);
				//carica solo con estensione .png
				chooser.setAcceptAllFileFilterUsed(false);
				//selezione singola
				chooser.setMultiSelectionEnabled(false);

				int risultato = chooser.showOpenDialog(null);

				ImageIcon immagine = null;

				if (risultato == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();		
					String pathImmagine = file.getPath();

					//setta il path dell'immagine da caricare
					immagine = new ImageIcon(pathImmagine);
				}

				if(risultato == JFileChooser.CANCEL_OPTION)
					return;

				Product prod = new Product();

				//attraverso i metodi pubblici della classe prodotto
				//setta le relative variabili	
				prod.setName(windowInsert.getTextFieldName());
				prod.setBrand(windowInsert.getTextFieldBrand());
				prod.setCode(code);
				prod.setCategory(windowInsert.getTextFieldCategory());
				//conversione da stringa a double
				prod.setPrice(Double.parseDouble(windowInsert.getTextFieldPrice()));
				prod.setImage(immagine);

				//stringa nulla provoca errore
				if(windowInsert.getTextFieldPromo().equals(""))
					prod.setPromo(" ");
				else
					prod.setPromo(windowInsert.getTextFieldPromo());

				//se assente lo sconto viene settato a 0, nella visualizzazione della tabella
				//lo 0 comporta una visualizzazione del campo vuoto
				if(windowInsert.getTextFieldDiscount().equals(""))
					prod.setDiscount(0);
				else
					//conversione ad intero del contenuto della textfield
					prod.setDiscount(Integer.parseInt(windowInsert.getTextFieldDiscount()));

				//quantita' definita automaticamente ad uno
				prod.setQt(1);

				//aggiunta prodotto
				store.addElem(prod);
				ntf.msgOk("Prodotto aggiunto!");
				sDataModel.update();
			}
			catch(NumberFormatException nfe) {
				ntf.msgWarning("Inserire valore numerico");
			}
			catch(NullPointerException npe) {
				return;
			}

			//Una volta aggiunto il prodotto le textfield vengono svuotate
			windowInsert.setTextFieldName("");
			windowInsert.setTextFieldBrand("");
			windowInsert.setTextFieldCode("");
			windowInsert.setTextFieldCategory("");
			windowInsert.setTextFieldPrice("");
			windowInsert.setTextFieldPromo("");
			windowInsert.setTextFieldDiscount("");

			windowTable.setEnabled(true);
			windowInsert.dispose();
		}

		if(e.getActionCommand()=="Annulla") {
			windowTable.setEnabled(true);			
			windowInsert.dispose();
		}

		if (e.getActionCommand() == "AddItem"){

			WindowInsert insert = new WindowInsert();
			insert.initialize(windowTable,store,sDataModel);
		}

		if (e.getActionCommand() == "DelItem" ) {

			// Elimina l'oggetto alla riga selezionata
			try {	
				windowTable.delItemStore();
				ntf.msgOk("Prodotto eliminato!");
				return;
			}
			catch(ArrayIndexOutOfBoundsException aoe) {
				ntf.msgError("L'operazione di eliminazione non e' andata a buon fine.");
			}
		}
	}
}
