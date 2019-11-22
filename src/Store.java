import java.util.Vector;

/**
 * Classe che descrive l'entita' Store, magazzino dei prodotti.
 * La classe e' estesa da {@link Cart}
 *
 * @see Product
 * @see TableModelStore
 * @author Marco Piccinni
 */
public class Store <E> {
	private Vector<E> vect;
	private int numObj;

	/**
	 * Costruttore vuoto che inizializza le istanze del magazzino.	
	 * Il numero di elementi e' impostato a dieci elementi.
	 */
	public Store() {
		numObj=10;
		vect= new Vector<E>(numObj);
	}

	/**
	 * Costruttore che inizializza le istanze del magazzino con
	 * un numero definito di elementi.
	 * @param numObj
	 */
	public Store(int numObj) {
		this.numObj=numObj;
		vect= new Vector<E>(numObj);
	}

	/**
	 * Restituisce il vettore dei prodotti.
	 * @return vect
	 */
	public Vector<E> getVect() {
		return vect;
	}

	/**
	 * Imposta il vettore dei prodotti.
	 * @param vect
	 */
	public void setVect(Vector<E> vect) {
		this.vect = vect;
	}

	/**
	 * Restituisce il numero della lunghezza impostata al vettore.
	 * @return numObj
	 */
	public int getNumObj() {
		return numObj;
	}

	/**
	 * Imposta il numero di oggetti massimi e aggiorna la dimensione del vettore.
	 * @param numObj
	 */
	public void setNumObj(int numObj) {
		this.numObj = numObj;
		vect= new Vector<E>(numObj);
	}

	/**
	 * Aggiunge un elemento al vettore.
	 * @param elem  Elmento di tipo prodotto che viene aggiunto al vettore
	 */
	public void addElem (E elem) {
		if (!vect.add(elem) ) {
			changeSize();
			if (!vect.add(elem) ) {
				Notify notifica= new Notify();
				notifica.msgWarning("Impossibile aggiungere l'elemento");
			}
		}
	}

	/** 
	 * Rimuove un elemento dal magazzino tramite indice.
	 * @param index Indice dell'elemento da rimuovere dall'array
	 */
	public void remove (int index) {
		vect.remove(index);
	}

	/**
	 * Rimuove tutti gli elementi contenuti nel magazzino, pulendo il vettore.
	 */
	public void removeAll () {
		vect.clear();
	}

	/**
	 * Ritorna elemento alla posizione indicata.
	 *
	 * @param index indice di cui voglio ritornare l'elemento
	 * @return E Elemento appartenente al tipo @link Product
	 */
	public E getElement (int index) {
		if (index < vect.size())
			return vect.elementAt(index);
		else
			return null;
	}

	/**
	 * Ritorna dimensione del vettore 
	 * @return dimensione vettore
	 */	
	public int length() {
		return vect.size();
	}	

	/**
	 * Modifica la dimensione del vettore di un elemento.
	 * Se si supera la dimensione di 10 elementi e' possibile aggiungerne altri.
	 */
	private void changeSize() {
		numObj++;
		vect.setSize(numObj);
	}
}