/**
 * Classe che descrive l'entita' Cart, carrello contenente i prodotti da acquistare.
 * La classe estende {@link Store}
 * 
 * @see Cart
 * @see TableModelCart
 * @author Marco Piccinni
 */
public class Cart<E> extends Store<E> {

	private double total;

	/**
	 * Costruttore vuoto che inizializza le istanze di Cart.
	 */
	public Cart() {
		super();
		total = 0;
	}

	/**
	 *Costruttore che inizializza il carrello con il numero di elementi passato.
	 * @param numObj
	 */
	public Cart(int numObj) {
		super(numObj);
		total = 0;
	}

	/**
	 * Aggiorna il totale in seguito all'aggiunta di un prodotto o al
	 * cambiamento dei filtri di visualizzazione in {@link ListenerFilterCategory}.
	 *
	 */
	public String updateTotal (TableModelCart dataModel) {
		//resetto totale
		total = 0.0;

		try {
			//ritorno l'elemento Prodotto alla riga i-esima
			for(int i = 0; i < dataModel.getRowCount(); i++) {
				//analizzo gli elementi alle varie colonne
				String singoloPrezzo = (String.valueOf(dataModel.getValueAt(i,4)));
				total += Double.parseDouble(singoloPrezzo);
			}
			//arrotondamento a due cifre decimali
			total = (Math.floor(total*100)) /100;

			return String.valueOf(total);
		}
		catch(ArrayIndexOutOfBoundsException aoe) {			
		}
		return "";
	}
}