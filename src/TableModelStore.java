import java.io.File;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

/**
 * Classe che rappresenta il modello magazzino da cui la JTable recupera i dati da visualizzare.
 * Estende la classe astratta AbstactTableModel che implementa la maggior parte dei metodi di
 * TableModel tranne: RowCount() , getColumnCount() e getValueAt(). 
 *
 * La classe e' estesa da  @link TableModelCart.
 *
 * @see Store
 * @see TableModelStore
 * 
 * @author Marco Piccinni
 */
public class TableModelStore extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Store<Product> store;
	private final String[] colName ={"Nome", "Marca", "Codice", "Categoria", "Prezzo", "Promo", "Sconto", "Icona", "Quantita'" };
	private int currentUser;
	private WindowTable windowTable;

	/**
	 * Costruttore che prende in ingresso magazzino e finestra principale
	 * inizializzando le istanze della classe.
	 * @param store vettore magazzino
	 * @param windowTable 
	 */
	public TableModelStore(Store<Product> store, WindowTable windowTable) {
		this.store = store;
		this.windowTable=windowTable;
	}

	public TableModelStore(){
	}

	/** 
	 * Restituisce il numero di righe, ovvero il numero di elementi contenuti.
	 * Ridefinizione del metodo definito dallo standard.
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return store.length();
	}

	/** 
	 * Prende in ingresso il numero della colonna e ne restituisce il nome.
	 * @return nome della colonna
	 */
	@Override
	public String getColumnName(int col) {
		return colName[col];
	}

	/** 
	 * Restituisce il numero di colonne per il magazzino.
	 * La colonna Quantita' e' nascosta e non modificabile.
	 * @see javax.swing.table.TableModel#getColumnCount()
	 *
	 * @return length
	 */
	@Override
	public int getColumnCount() {
		return colName.length-1;
	}

	/**
	 * Restituisce il contenuto della cella di indice row e colonna col
	 *
	 * Il prezzo e' rappresentato in valore assoluto, il dato resta col segno 
	 * @param row indica la riga della cella selezionata
	 * @param col indica la colonna della cella selezionata
	 *
	 * @return elemento corrispondente della cella selezionata
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		//determino quale prodotto appartenente al magazzino
		//si trova nella riga considerata
		Product prod = (Product)store.getElement(row);

		switch (col) {
		case 0:	
			return prod.getName();
		case 1:
			return prod.getBrand();
		case 2:
			return prod.getCode();
		case 3:
			return prod.getCategory();
		case 4:
			if (prod.getPrice() <= 0)
				return Math.abs(prod.getPrice());
			return prod.getPrice();
		case 5:
			return prod.getPromo();
		case 6:
			if(prod.getDiscount() == 0)
				return "";
			return prod.getDiscount();
		case 7:
			return prod.getImage();
		case 8:
			return prod.getQt();

		default: return "";
		}
	}

	/**
	 * Specifica se la cella di indice row e colonna col e' editabile
	 * @return flag che indica se la cella risulta editabile o meno
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		currentUser= windowTable.getAId();
		//0 = admin
		if(currentUser==0) {
			//admin non puo' modificare la quantita' di default 1
			if(col == 8)
				return false;
			return true;
		}

		//1= utente
		if(currentUser==1) {
			//utente puo' modificare solo la quantita'
			if(col == 8)
				return true;
			return false;
		}
		return false;
	}

	/**
	 * Sovrascrive il metodo di ritorno della classe della cella.
	 * @param column colonna della Jtable
	 * ritorna la classe della colonna desiderata, in particolare
	 * imposta la ImageIcon class che permette la visualizzazione delle icone,
	 * per le altre non era necessario sovrascrivere il seguente metodo
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		switch(column) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 8:
			return String.class;
		case 7:
			return ImageIcon.class;
		default:
			return Object.class;
		}
	}

	/**
	 * Controlla e modifica il contenuto della cella selezionata.
	 * 
	 * @param value nuovo valore impostato per la cella selezionata
	 * @param row riga della cella selezionata
	 * @param col colonna della cella selezionata
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override	
	public void setValueAt(Object value, int row, int col) {

		Notify ntf = new Notify();
		Product prod = (Product)store.getElement(row);

		switch (col) {
		case 0:
			prod.setName(value.toString());
			break;
		case 1:
			prod.setBrand(value.toString());
			break;
		case 2:
			prod.setCode(value.toString());
			break;
		case 3:
			prod.setCategory(value.toString());
			break;
		case 4:
			if(Pattern.matches("\\d+([.]\\d{1,2})?",value.toString()) ) {
				try {
					double price = Double.parseDouble((String)value);
					prod.setPrice(price);
				}
				catch(NumberFormatException nfe)  {
					ntf.msgError("Inserire un valore numerico");
				}
			}
			else 
				ntf.msgError("Inserire un numero positivo con al massimo due decimali");
			break;	

		case 5:
			//se sconto attivo non posso modificare promozione
			if(prod.getDiscount()!=0) {
				ntf.msgError("Un prodotto non puo' essere sia in sconto che in promozione");
				break;
			}

			String promo = value.toString().replaceAll(" ", "");
			if(Pattern.matches("\\d[x]\\d",promo) && promo.charAt(0) > promo.charAt(2) )
					prod.setPromo(promo);
			else if (promo.equals(""))
				prod.setPromo(" ");
			else
				ntf.msgError("Promozione non valida");
			break;
		case 6:
			//se promozione attiva non posso modificare sconto
			if(!prod.getPromo().equals(" ")) {
				ntf.msgError("Un prodotto non puo' essere sia in sconto che in promozione");
				fireTableDataChanged();
				break;
			}
			try {
				int sconto = Integer.parseInt((String)value);
				if (sconto < 0 || sconto > 100) {
					ntf.msgError("Inserire valore compreso tra 0 e 100");
					break;
				}
				prod.setDiscount(sconto);
			}
			catch(NumberFormatException nfe)  {
				ntf.msgError("Inserire un valore numerico");
			}
			break;
		case 7:
			JFileChooser chooser = new JFileChooser();
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
				prod.setImage(immagine);
				break;
			}
			if(risultato == JFileChooser.CANCEL_OPTION)
				break;
		case 8:
			prod.setQt(Integer.parseInt((String)value));
			break;

		default:
			return;
		}
		fireTableDataChanged();
	}

	/**
	 * Refresh della visualizzazione della tabella
	 */
	public void update () {
		fireTableDataChanged();
	}

	/**
	 * Restituisce l'utente corrente
	 * @return intero corrispondente l'utente corrente. 0 admin, 1 guest, 2 not set.
	 */
	public int getCurrentUser() {
		return currentUser;
	}

	/**
	 * Imposta l'utente per abilitare le diverse interfacce.
	 * @param currentUser 0 admin, 1 guest, 2 not set
	 */
	public void setCurrentUser(int currentUser) {
		this.currentUser = currentUser;
	}
}