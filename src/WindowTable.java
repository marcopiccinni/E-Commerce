import java.awt.GraphicsDevice;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Classe che rappresenta l'interfaccia grafica visualizzata dagli utenti.
 * @author Marco Piccinni
 */
public class WindowTable {

	private JFrame frmWindowTable;
	private int AId;
	private Store<Product> store;
	private Cart<ProductSaved>  cart;
	private JScrollPane scrollPaneStore;
	private JScrollPane scrollPaneCart;
	private JButton btnShowCart;
	private JButton btnAddItem;
	private JButton btnDelItem;
	private JLabel lblWelcome;
	private String username;
	private JTextField txtFieldSearch;
	private JButton btnSearch;
	private JButton btnSearchName;
	private JButton btnSearchCategory;
	private JButton btnEndSearch;
	private JButton btnAddCart;
	private JButton btnDelCart;
	private JButton btnEmptyCart;
	private JButton btnBuy;
	private JLabel lblTotalPrice;
	private JLabel lblTotalText;
	private JButton btnLoad;
	private JButton btnSave;
	private JTable tableStore;
	private JTable tableCart;
	private TableModelStore sDataModel;
	private TableModelCart cDataModel;

	/**
	 * Create the application.
	 */
	public WindowTable() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		

		store = new Store<Product>();		
		cart = new Cart<ProductSaved>();	
		AId = 2;
		username="";

		frmWindowTable = new JFrame();
		GraphicsDevice gd = frmWindowTable.getGraphicsConfiguration().getDevice();
		frmWindowTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindowTable.setTitle("JV MARKET");
		frmWindowTable.setResizable(false);
		frmWindowTable.setSize(new Dimension(1280,720));
		frmWindowTable.setLocation((gd.getDisplayMode().getWidth() - frmWindowTable.getWidth())/2, (gd.getDisplayMode().getHeight() - frmWindowTable.getHeight())/2);
		frmWindowTable.getContentPane().setLayout(null);

		lblTotalPrice = new JLabel("");
		lblTotalPrice.setOpaque(true);
		lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTotalPrice.setBounds(1124, 494, 108, 37);
		frmWindowTable.getContentPane().add(lblTotalPrice);

		sDataModel = new TableModelStore(store,this);
		tableStore = new JTable(sDataModel);
		tableStore.setPreferredScrollableViewportSize(new Dimension(600,300));
		tableStore.setFillsViewportHeight(true);
		tableStore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableStore.setRowHeight(30);
		tableStore.setRowSelectionAllowed(true);
		scrollPaneStore = new JScrollPane(tableStore);
		scrollPaneStore.setLocation(350, 150);
		scrollPaneStore.setSize(600, 300);
		scrollPaneStore.createVerticalScrollBar();
		scrollPaneStore.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		frmWindowTable.getContentPane().add(scrollPaneStore);

		cDataModel = new TableModelCart(cart,this);
		tableCart = new JTable(cDataModel);
		tableCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableCart.setRowSelectionAllowed(true);
		tableCart.setRowHeight(30);
		scrollPaneCart = new JScrollPane(tableCart);
		scrollPaneCart.setVisible(false);
		scrollPaneCart.setLocation(650, 150);
		scrollPaneCart.setSize(600, 300);
		scrollPaneCart.createVerticalScrollBar();
		scrollPaneCart.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		frmWindowTable.getContentPane().add(scrollPaneCart);

		lblWelcome = new JLabel("Benvenuto!");
		lblWelcome.setAlignmentY(Component.TOP_ALIGNMENT);
		lblWelcome.setFont(new Font("Modern No. 20", Font.PLAIN, 47));
		lblWelcome.setBounds(10, 11, 1014, 62);
		frmWindowTable.getContentPane().add(lblWelcome);

		btnShowCart = new JButton("<html>Mostra<br>carrello</html>");
		btnShowCart.setActionCommand("Mostra carrello");
		btnShowCart.setVisible(false);
		btnShowCart.setBounds(1124, 36, 108, 51);
		frmWindowTable.getContentPane().add(btnShowCart);
		ListenerShowCart LMostraCarrello = new ListenerShowCart(this);
		btnShowCart.addActionListener(LMostraCarrello);

		btnAddItem = new JButton("<html>Inserisci <br> prodotto</html>");
		btnAddItem.setActionCommand("AddItem");
		btnAddItem.setVisible(false);
		btnAddItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddItem.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddItem.setBounds(162, 578, 89, 51);
		frmWindowTable.getContentPane().add(btnAddItem);
		ListenerAddStore LAddItem =new ListenerAddStore(this, store, sDataModel);
		btnAddItem.addActionListener(LAddItem);

		btnDelItem = new JButton("<html>Cancella <br> prodotto</html>");
		btnDelItem.setActionCommand("DelItem");
		btnDelItem.setBounds(272, 578, 89, 51);
		frmWindowTable.getContentPane().add(btnDelItem);
		btnDelItem.addActionListener(LAddItem);

		JButton btnChangeUser = new JButton("<html>Cambia<br> utente</html>");
		btnChangeUser.setBounds(34, 78, 89, 37);
		frmWindowTable.getContentPane().add(btnChangeUser);
		ListenerLogin LChangeUser = new ListenerLogin(this);
		btnChangeUser.addActionListener(LChangeUser);

		txtFieldSearch = new JTextField();
		txtFieldSearch.setVisible(false);
		txtFieldSearch.setBounds(50, 539, 108, 20);
		frmWindowTable.getContentPane().add(txtFieldSearch);
		txtFieldSearch.setColumns(10);

		boolean firstTime = true;
		int indRow = 0;
		int indCol = 0;
		btnSearch = new JButton("Search");
		btnSearch.setText("Cerca");
		btnSearch.setVisible(false);
		btnSearch.setBounds(179, 538, 118, 23);
		frmWindowTable.getContentPane().add(btnSearch);
		ListenerSearch LSearch= new ListenerSearch(txtFieldSearch, sDataModel, firstTime, indRow, indCol, tableStore);
		btnSearch.addActionListener(LSearch);

		btnSearchName = new JButton("SearchName");
		btnSearchName.setText("Cerca nome");
		btnSearchName.setVisible(false);
		btnSearchName.setBounds(179, 572, 118, 23);
		frmWindowTable.getContentPane().add(btnSearchName);
		btnSearchName.addActionListener(LSearch);

		btnSearchCategory = new JButton("FilterCategory");
		btnSearchCategory.setText("<html>Filtra<br>categoria</html>");
		btnSearchCategory.setActionCommand("Filtra categoria");
		btnSearchCategory.setVisible(false);
		btnSearchCategory.setBounds(179, 606, 118, 37);
		frmWindowTable.getContentPane().add(btnSearchCategory);
		ListenerFilterCategory LFilterCategory= new ListenerFilterCategory(txtFieldSearch, sDataModel, tableStore);
		btnSearchCategory.addActionListener(LFilterCategory);

		btnEndSearch = new JButton("Termina");
		btnEndSearch.setBounds(322, 538, 95, 23);
		btnEndSearch.setVisible(false);
		frmWindowTable.getContentPane().add(btnEndSearch);
		btnEndSearch.addActionListener(LSearch);
		btnEndSearch.addActionListener(LFilterCategory);

		lblTotalText = new JLabel("<html>Totale <br>acquisti</html>");
		lblTotalText.setBounds(1062, 494, 62, 37);
		frmWindowTable.getContentPane().add(lblTotalText);

		btnAddCart = new JButton("Aggiungi");
		btnAddCart.setBounds(815, 494, 100, 51);
		frmWindowTable.getContentPane().add(btnAddCart);
		ListenerAddCart LAddCart = new ListenerAddCart(cDataModel, cart, store, this);
		btnAddCart.addActionListener(LAddCart);

		btnDelCart = new JButton("Elimina");
		btnDelCart.setBounds(935, 494, 89, 51);
		frmWindowTable.getContentPane().add(btnDelCart);
		btnDelCart.addActionListener(LAddCart);

		btnBuy = new JButton("Acquista");
		btnBuy.setBounds(1143, 582, 95, 47);
		frmWindowTable.getContentPane().add(btnBuy);
		ListenerBuy LBuy= new ListenerBuy(cart, cDataModel, this);
		btnBuy.addActionListener(LBuy);
	
		btnEmptyCart = new JButton("<html>Svuota<br>carrello</html>");
		btnEmptyCart.setActionCommand("Svuota carrello");
		btnEmptyCart.setBounds(1045, 582, 89, 47);
		frmWindowTable.getContentPane().add(btnEmptyCart);
		btnEmptyCart.addActionListener(LBuy);

		btnSave = new JButton("Salva");
		btnSave.setBounds(899, 585, 89, 23);
		frmWindowTable.getContentPane().add(btnSave);
		ListenerSave LSave=new ListenerSave(store, sDataModel);
		btnSave.addActionListener(LSave);

		btnLoad = new JButton("Carica");
		btnLoad.setBounds(1015, 585, 89, 23);
		frmWindowTable.getContentPane().add(btnLoad);
		btnLoad.addActionListener(LSave);

		JLabel lblBackground = new JLabel();
		lblBackground.setIcon(new ImageIcon(WindowTable.class.getResource("/images/PaperBackground.jpg")));
		lblBackground.setBounds(0, 0, 1274, 691);
		frmWindowTable.getContentPane().add(lblBackground);
	}

	/**
	 * Imposta la visibilita' della finestra agendo sul frame
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		frmWindowTable.setVisible(visible);
	}

	/**
	 * Imposta l'Id della sessione. Cambia la visibilita' degli elementi.
	 * @param AId 0 per amministratore, 1 per l'acquirente, 2 per non settato
	 */
	public void setAId(int AId) {
		switch(AId){
		case 0:
			lblWelcome.setText("Benvenuto "+ username);
			this.AId=AId;
			setVisibiltySelect();
			return;
		case 1:
			lblWelcome.setText("Benvenuto "+ username + ". Buoni acquisti!");
			this.AId=AId;
			setVisibiltySelect();
			return;
		default:
			Notify not=new Notify();
			not.msgError("Utente errato");
		}
	}

	/**
	 * Restituisce il valore attuale associato all'utente.
	 * @return 0 per l'amministratore, 1 per l'acquirente, 2 se non e' impostato.
	 */
	public int getAId(){
		return AId;
	}

	/**
	 * Restituisce il nome dell'utente.
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Imposta il nome dell'utente.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Imposta la visibilita' degli oggetti interni al frame in base all'Id.
	 */
	public void setVisibiltySelect() {
		switch(AId) {
		case 0:
			setAdmin(true);
			setUser(false);
			break;
		case 1:
			setUser(true);
			setAdmin(false);
			break;
		default:
			setAId(1);
			Notify not=new Notify();
			not.msgError("Account non valido. \nImpostata modalita' normale");
		}
	}

	/**
	 * Imposta la visibilita' degli oggetti legati all'amministratore.
	 * @param visible
	 */
	private void setAdmin(boolean visible) {
		if(AId<0 || AId>1)			
			return;
		btnAddItem.setVisible(visible);
		btnDelItem.setVisible(visible);
		btnSave.setVisible(visible);
		btnLoad.setVisible(visible);
		if(visible){
			scrollPaneCart.setVisible(false);
			scrollPaneStore.setLocation(350, 150);
		}
	}

	/**
	 * Imposta la visibilita' degli oggetti legati all'acquirente.
	 * @param visible
	 */
	private void setUser(boolean visible) {
		if(AId<0 || AId>1)
			return;
		btnShowCart.setText("<html>Mostra<br>carrello</html>");
		btnShowCart.setVisible(visible);
		txtFieldSearch.setVisible(visible);
		btnSearch.setVisible(visible);
		btnSearchName.setVisible(visible);
		btnSearchCategory.setVisible(visible);
		btnEndSearch.setVisible(visible);
		btnAddCart.setVisible(visible);
		btnDelCart.setVisible(visible);
		btnEmptyCart.setVisible(visible);
		btnBuy.setVisible(visible);
		lblTotalPrice.setVisible(visible);
		lblTotalText.setVisible(visible);
	}

	/**
	 * Imposta il valore visibile nella label del prezzo totale.
	 * @param text
	 */
	public void lblTotalSetText(String text) {
		lblTotalPrice.setText(text);
	}

	/**
	 * Imposta la visibilita' del carrello.
	 * @param visible
	 */
	public void scrollPaneCartSetVisible(boolean visible) {
		scrollPaneCart.setVisible(visible);
	}

	/**
	 * Imposta la posizione del magazzino.
	 * @param x
	 * @param y
	 */
	public void scrollPaneStoreSetLocation(int x, int y) {
		scrollPaneStore.setLocation(x,y);
	}

	/**
	 * Imposta la possibilita' di interagire con il frame.
	 * @param enable
	 */
	public void setEnabled(boolean enable) {
		frmWindowTable.setEnabled(enable);
		if(enable)
			frmWindowTable.setAutoRequestFocus(true);
	}

	/**
	 * Imposta la visiblilta' del carrello e modifica il bottone associato.
	 * @param visible
	 */
	public void setShowCart(boolean visible) {
		scrollPaneCartSetVisible(visible);
		if(visible) {
			btnShowCart.setText("<html>Nascondi<br>carrello</html>");
			btnShowCart.setActionCommand("Nascondi carrello");
			scrollPaneStoreSetLocation(20, 150);
		}
		else {
			btnShowCart.setText("<html>Mostra<br>carrello</html>");
			btnShowCart.setActionCommand("Mostra carrello");
			scrollPaneStoreSetLocation(350,150);
		}
	}
	
	/**
	 * Permette la rimozione di un prodotto selezionato dal magazzino.
	 */
	public void delItemStore(){
		int index = tableStore.getSelectedRow();
		//si passa l'indice del prodotto da eliminare
		store.remove(index);
		//aggiorna la visualizzazione della tabella dopo aver cancellato il prodotto
		sDataModel.update();
	}
	
	/**
	 * Restituisce il prodotto selezionato dal carrello.
	 */
	public int getTableCartSelectedRow(){
		return tableCart.getSelectedRow();
	}
	
	/**
	 * Restituisce il prodotto selezionato dal magazzino.
	 */
	public int getTableStoreSelectedRow(){
		return tableStore.getSelectedRow();
	}
	
	/**
	 * Rimuove tutti gli elementi contenuti nel carrello.
	 */
	public void cleanCart() {
		cart.removeAll();
		cDataModel.update();
		lblTotalPrice.setText("");
	}
}