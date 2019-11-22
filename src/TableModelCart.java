
/**
 * Classe che rappresenta il modello da cui la JTable recupera i dati da visualizzare.
 * Estende la classe @link TableModelStore.
 * 
 * @see ProductSaved
 * @see Cart
 * 
 * @author Marco Piccinni
 */
public class TableModelCart extends TableModelStore {

	private static final long serialVersionUID = 1L;
	private Cart<ProductSaved> cart;
	private WindowTable windowTable;

	/**
	 * Costruttore che prende in ingresso un magazzino e inizializza le istanze.
	 * @param cart 
	 * @param windowTable
	 */
	public TableModelCart(Cart<ProductSaved> cart, WindowTable windowTable) {
		this.windowTable=windowTable;
		this.cart=cart;
	}

	/** 
	 * Restituisce il numero di righe dell'elenco del carrello
	 * @return numero di righe della tabella
	 */
	@Override
	public int getRowCount() {
		return cart.length();
	}

	/** 
	 * Restituisce il numero di colonne della tabella del carrello.
	 * La tabella mostra anche il valore Quantita', nascosto nello store.
	 * @return numero di colonne della tabella
	 */
	@Override
	public int getColumnCount() {
		return super.getColumnCount()+1;
	}

	/**
	 * Restituisce il contenuto della cella di indice row e colonna col
	 * L'importo e rappresentato in valore assoluto, il dato resta col segno.
	 * 
	 *  @return valore contenuto nella cella selezionata.
	 */
	@Override
	public Object getValueAt (int row, int col) {

		//determino quale prodotto appartenente al magazzino si trova nella riga considerata
		ProductSaved prod = (ProductSaved)cart.getElement(row);

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
			return prod.getDiscount();
		case 7:
			return prod.getImage();
		case 8:
			return prod.getQt();
		default: return "";
		}
	}

	/**
	 * Specifica se la cella di indice row e colonna col e' modificabile.
	 * @see TableModelStore#isCellEditable(int, int)
	 * 
	 * @return flag che indica se la cella risulta modificabile
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		if(col ==8)
			return true;
		return false;
	}

	/**
	 * Controlla e modifica il contenuto della cella selezionata.
	 * 
	 * @param value nuovo valore impostato per la cella selezionata
	 * @param row riga della cella selezionata
	 * @param col colonna della cella selezionata
	 * 
	 * @see TableModelStore#setValueAt(Object, int, int)
	 */
	@Override	
	public void setValueAt(Object value, int row, int col) {
		double total = 0.0;
		if(col == 8) {
			try {
			ProductSaved prod = (ProductSaved)cart.getElement(row);
			if(Integer.parseInt((String)value)==0)
				prod.setQt(1);
			else
				prod.setQt(Math.abs(Integer.parseInt((String)value)));
			prod.setPrice(Double.parseDouble((String) value));
			} catch (NumberFormatException e1) {
			}
			fireTableDataChanged();

			try {
				//ritorno l'elemento Prodotto alla riga i-esima
				for(int i = 0; i < getRowCount(); i++) {
					//analizzo gli elementi alle varie colonne

					String singoloPrezzo = (String.valueOf(getValueAt(i,4)));
					total += Double.parseDouble(singoloPrezzo);//* Double.parseDouble(String.valueOf(getValueAt(i,8)));
				}
				//arrotondamento a due cifre decimali
				total = (Math.floor(total*100)) /100;

				windowTable.lblTotalSetText(String.valueOf(total));
			}
			catch(ArrayIndexOutOfBoundsException aoe) {			
			}	
		}
		fireTableDataChanged();
		return;
	}
}