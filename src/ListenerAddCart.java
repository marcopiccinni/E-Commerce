import java.awt.event.*;

/**
 *	Classe che implementa l'ascoltatore di bottoni Aggiungi ed Elimina del carrello.
 *  Aggiunge il prodotto selezionato al carrello, aumentandone la quantita'.
 *  
 *  @see Cart
 *  @see TableModelCart
 *
 * @author Marco Piccinni
 */
public class ListenerAddCart implements ActionListener {

	private TableModelCart cDataModel;
	private Cart <ProductSaved> cart;
	private Store<Product> store;
	private WindowTable windowTable;

	/**
	 * Costruttore che permette di avere i riferimenti necessari per gli oggetti da utilizzare.
	 * @param cDataModel modello della vista carrello
	 * @param cart carrello
	 * @param store magazzino
	 * @param windowTable 
	 */
	public ListenerAddCart (TableModelCart cDataModel, Cart <ProductSaved>cart, Store<Product> store, WindowTable windowTable) { 
		this.cDataModel		= cDataModel;
		this.cart 			= cart;
		this.store 			= store;
		this.windowTable	= windowTable;
	}

	/**
	 * Sovrascrizione del metodo per impostare l'azione desiderata
	 */
	@Override 
	public void actionPerformed(ActionEvent e) {
		Notify ntf = new Notify();
		if (e.getActionCommand() == "Aggiungi") {
			
			 // Seleziona l'oggetto alla riga evidenziata
			try {
				//ritorna l'indice del prodotto selezionato nella tabella magazzino
				int index = windowTable.getTableStoreSelectedRow();

				//ritorna prodotto del vettore magazzino all'indice precedente
				Product prodS = (Product)store.getElement(index);

				//booleano che indichera' se nel carrello l'elemento da aggiungere e' gia' presente
				boolean check = false;

				//Analizzo gli elementi presenti nel carrello
				for(int i = 0; i < cDataModel.getRowCount(); i++) {

					//univocita' data da coppia codice e categoria
					String dataTabCod = (String)cDataModel.getValueAt(i,2);
					String dataTabCat = (String)cDataModel.getValueAt(i,3);

					//prodotti gia' presenti nel carrello
					if (dataTabCod.equals(prodS.getCode()) && dataTabCat.equals(prodS.getCategory()))	{
						//seleziona elemento all'indice trovato e ne modifica la quantita'
						ProductSaved prodC = (ProductSaved) cart.getElement(i);
						int newQt = prodC.getQt() + prodS.getQt();

						//Aggiorna Nuova Quantita'
						prodC.setQt(newQt);
						prodC.setPrice(prodS.getPrice());

						//Aggiorna Nuovo Prezzo
						cDataModel.update();
						windowTable.lblTotalSetText(cart.updateTotal(cDataModel));

						check = true;
						break;
					}
				}

				//prodotto non presente nel carrello
				if (!check) {
					//Convertire oggetto (conformita') a ProdottoAcq
					prodS = new ProductSaved(prodS.getName(),prodS.getBrand(),prodS.getCode(),prodS.getCategory(),prodS.getPrice(),prodS.getPromo(),
							prodS.getDiscount(),prodS.getQt(),prodS.getImage());
					prodS.setPrice(prodS.getPrice());

					//Aggiungere a Carrello 
					cart.addElem((ProductSaved)prodS);
					cDataModel.update();
					windowTable.lblTotalSetText(cart.updateTotal(cDataModel));
					cDataModel.update();
				}
				return;
			}
			catch(ArrayIndexOutOfBoundsException aoe) {
				ntf.msgError("L'operazione di aggiunta al carrello non e' andata a buon fine.");
			}
		}
		
		if (e.getActionCommand() == "Elimina") {
			/*
			 * Elimina l'oggetto alla riga selezionata
			 */
			try {
				//seleziona l'indice della riga selezionata
				int indice = windowTable.getTableCartSelectedRow();
				//rimuove il prodotto dal vettore carrello tramite indice
				cart.remove(indice);
				cDataModel.update();
				windowTable.lblTotalSetText(cart.updateTotal(cDataModel));
				ntf.msgOk("Prodotto rimosso!");
				return ;
			}
			catch(ArrayIndexOutOfBoundsException aoe) {
				ntf.msgError("L'operazione di rimozione non e' andata a buon fine.");
			}
		}
	}
}