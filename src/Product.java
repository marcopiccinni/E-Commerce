import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Classe che descrive l'entita' prodotto.
 * Utilizzata per i prdotti contenuti nel magazzino pronti per la vendita.
 * 
 * La classe e' estesa da {@link ProductSaved}.
 * 
 * @see Store
 * @see TableModelStore
 * @author Marco Piccinni
 *
 */
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	private String brand;
	private String code;
	private String category;
	private double  price;
	private String  promo;
	private int discount;
	private int qt;
	private ImageIcon image;

	/**
	 * Costruttore vuoto
	 */
	public Product() {
	}

	/**
	 * Costruttore che inizializza le istanze di Product con i valori forniti.
	 * @param name 	nome del prodotto
	 * @param brand marca del prodotto
	 * @param code codice del prodotto
	 * @param category categoria del prodotto
	 * @param price prezzo del prodotto
	 * @param promo promozione del prodotto
	 * @param discount sconto del prodotto
	 * @param qt quantita' del prodotto
	 * @param image icona del prodotto
	 */
	public Product(String name, String brand, String code, String category, double price, String promo,
			int discount, int qt, ImageIcon image) {

		this.name 		= name;
		this.brand 		= brand;
		this.code 		= code;
		this.category 	= category;
		this.price 		= price;
		this.promo 		= promo;
		this.discount	= discount;
		this.qt			= qt;
		this.image		= image;
	}

	/**
	 * Restituisce il nome
	 * @return name 
	 */	
	public String getName() {
		return name;
	}

	/**
	 * Imposta il nome
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Restituisce la marca
	 * @return brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Imposta la marca
	 * @param brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * Restituisce il codice
	 * @return code
	 */	
	public String getCode() {
		return code;
	}

	/**
	 * Imposta il codice
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Restituisce la categoria
	 * @return category
	 */	
	public String getCategory() {
		return category;
	}

	/**
	 * Imposta la categoria
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Restituisce il prezzo 
	 * @return price
	 */	
	public double getPrice() {
		return price;
	}

	/**
	 * Imposta il prezzo
	 * @param price
	 */
	public void setPrice(double price) {
		price = (Math.floor(price*100)) /100;
		this.price = price;
	}

	/**
	 * Restituisce la promozione
	 * @return promo
	 */	
	public String getPromo() {
		return promo;
	}

	/**
	 * Imposta la promozione
	 * @param promo
	 */
	public void setPromo(String promo) {
		this.promo = promo;
	}

	/**
	 * Restituisce lo sconto. 
	 * Valutato in percentuale.
	 * @return discount
	 */	
	public int getDiscount() {
		return discount;
	}

	/**
	 * Imposta lo sconto. 
	 * La funzione accetta solo valori compresi tra 0 e 100 inclusi.
	 * @param discount
	 */
	public void setDiscount(int discount) {
		if (discount>=0 && discount<=100)
			this.discount = discount;
	}

	/**
	 * Restituisce la quantita'.
	 * @return qt
	 */	
	public int getQt() {
		return qt;
	}

	/**
	 * Imposta la quantita'
	 * @param qt
	 */
	public void setQt(int qt) {
		this.qt = qt;
	}

	/**
	 * Restituisce l'immagine.
	 * @return image
	 */	
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * Imposta l'immagine
	 * @param image
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	} 
}