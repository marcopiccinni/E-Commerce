import javax.swing.ImageIcon;

/**
 * Classe che descrive l'entita' ProductSaved.
 * Utilizzata per i prodotti contenuti nel carrello dell'acquirente.
 * 
 * La classe estende {@link Product}.
 * 
 * @see Cart
 * @see TableModelCart
 * @author Marco Piccinni
 */
public class ProductSaved extends Product {

	private static final long serialVersionUID = 1L;

	private double singlePrice;	//Mantiene in memoria il prezzo originario del singolo prodotto

	/**
	 * Costruttore che inizializza le istanze di {@link Product} con i valori forniti
	 * @param name nome del prodottp
	 * @param brand marca del prodotto
	 * @param code codice del prodotto
	 * @param category categoria del prodotto
	 * @param price prezzo del prodotto
	 * @param promo promozione del prodotto
	 * @param discount sconto del prodotto
	 * @param qt quantita' del prodotto
	 * @param image immagine del prodotto
	 */
	public ProductSaved(String name, String brand, String code, String category, double price, String promo,
			int discount, int qt, ImageIcon image) {
		super(name, brand, code, category, price, promo, discount, qt, image);
		singlePrice = price; // Viene impostato il prezzo del singlo prodotto a partire dal suo valore iniziale.
	}

	/** Imposta il prezzo.
	 * Ridefinita dalla classe estesa.
	 * @see Product#setPrice(double)
	 */
	@Override
	public void setPrice(double price) {

		// In caso di sconto attivo
		if(getDiscount()!=0){
			super.setPrice( (singlePrice- ((singlePrice/100)* (double) getDiscount()) )*getQt()  );
			return;
		}

		// In caso di promozione attiva.
		if (!getPromo().equals(" ")) {

			// Numero di prodotti effettivi da pagare.
			int nOgg = getQt()/Character.getNumericValue(getPromo().charAt(0)) * Character.getNumericValue(getPromo().charAt(2));

			// Numero di prodotti fuori dalla promozione. Non multipli dell'offerta
			int nPlus = getQt()% Character.getNumericValue(getPromo().charAt(0));
			super.setPrice(singlePrice* (nOgg + nPlus));
			return;
		}

		// Prodotto senza sconti o promozioni attive
		super.setPrice(getQt()*singlePrice);
		return;
	}

	/**
	 * Restituisce il prezzo del singolo prodotto, senza sconti e promozioni.
	 * @return singlePrice
	 */
	public double getSinglePrice() {
		return singlePrice;
	}
}
