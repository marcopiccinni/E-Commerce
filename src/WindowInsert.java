import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;

/**
 * Classe per la finestra per l'inserimento di nuovi prodotti nel magazzino.
 * 
 * @see Store
 * @see Product
 * @see TableModelStore
 * 
 * @author Marco Piccinni
 */
public class WindowInsert {

	private JFrame frmWindowInsert;
	private JTextField textFieldName;
	private JTextField textFieldBrand;
	private JTextField textFieldCode;
	private JTextField textFieldCategory;
	private JTextField textFieldPrice;
	private JTextField textFieldPromo;
	private JTextField textFieldDiscount;
	private JButton btnAdd;
	private JButton btnCancel;

	/**
	 * Costruttore vuoto.
	 * In attesa che venga chiamato il metodo @link initialize.
	 */
	public WindowInsert(){
	}
	
	/**
	 * Costruttore della classe. Chiama @link initialize per inizializzare gli elementi contenuti.
	 * @param windowTable finestra principale
	 * @param store magazzino 
	 * @param sDataModel modello del magazzino
	 */
	public WindowInsert(WindowTable windowTable, Store<Product> store, TableModelStore sDataModel) {
		initialize(windowTable,store,sDataModel);
	}

	/**
	 * Metodo per l'inizializzazione degli elementi appartenti alla classe.
	 * @param windowTable finestra principale
	 * @param store magazzino 
	 * @param sDataModel modello del magazzino
	 */
	public void initialize(WindowTable windowTable, Store <Product> store, TableModelStore sDataModel) {
		windowTable.setEnabled(false);
		
		//frame della finestra
		frmWindowInsert = new JFrame();
		frmWindowInsert.setResizable(false);
		frmWindowInsert.setTitle("Nuovo Elemento");
		frmWindowInsert.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmWindowInsert.setAlwaysOnTop(true);
		frmWindowInsert.setBounds(100, 100, 600, 150);
		frmWindowInsert.getContentPane().setLayout(null);

		//Inizializzazione delle label
		JLabel lblName = new JLabel("Nome");
		lblName.setBounds(13, 15, 98, 25);
		frmWindowInsert.getContentPane().add(lblName);

		JLabel lblBrand = new JLabel("Marca");
		lblBrand.setBounds(116, 15, 86, 25);
		frmWindowInsert.getContentPane().add(lblBrand);

		JLabel lblCode = new JLabel("Codice");
		lblCode.setBounds(207, 15, 66, 25);
		frmWindowInsert.getContentPane().add(lblCode);

		JLabel lblCategory = new JLabel("Categoria");
		lblCategory.setBounds(278, 15, 80, 25);
		frmWindowInsert.getContentPane().add(lblCategory);

		JLabel lblPrice = new JLabel("Prezzo");
		lblPrice.setBounds(363, 15, 70, 25);
		frmWindowInsert.getContentPane().add(lblPrice);

		JLabel lblPromo= new JLabel("Promo");
		lblPromo.setBounds(438, 15, 73, 25);
		frmWindowInsert.getContentPane().add(lblPromo);

		JLabel lblDiscount = new JLabel("Sconto");
		lblDiscount.setBounds(516, 15, 67, 25);
		frmWindowInsert.getContentPane().add(lblDiscount);

		//Inizializzazione dei campi per l'inserimento delle propriet� del prodotto.
		textFieldName = new JTextField("");
		textFieldName.setBounds(13, 45, 98, 25);
		frmWindowInsert.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);

		textFieldBrand = new JTextField("");
		textFieldBrand.setBounds(116, 45, 86, 25);
		frmWindowInsert.getContentPane().add(textFieldBrand);
		textFieldBrand.setColumns(10);

		textFieldCode = new JTextField("");
		textFieldCode.setBounds(207, 45, 66, 25);
		frmWindowInsert.getContentPane().add(textFieldCode);
		textFieldCode.setColumns(10);

		textFieldCategory = new JTextField("");
		textFieldCategory.setBounds(278, 45, 80, 25);
		frmWindowInsert.getContentPane().add(textFieldCategory);
		textFieldCategory.setColumns(10);

		textFieldPrice = new JTextField("");
		textFieldPrice.setBounds(363, 45, 70, 25);
		frmWindowInsert.getContentPane().add(textFieldPrice);
		textFieldPrice.setColumns(10);

		textFieldPromo= new JTextField("");
		textFieldPromo.setBounds(438, 45, 73, 25);
		frmWindowInsert.getContentPane().add(textFieldPromo);
		textFieldPromo.setColumns(10);

		textFieldDiscount = new JTextField("");
		textFieldDiscount.setBounds(516, 45, 67, 25);
		frmWindowInsert.getContentPane().add(textFieldDiscount);
		textFieldDiscount.setColumns(10);

		//Inizializzazione dei pulsanti
		btnAdd = new JButton("Aggiungi");
		btnAdd.setBounds(438, 85, 110, 25);
		frmWindowInsert.getContentPane().add(btnAdd);
		ListenerAddStore LAddWindowInsert = new ListenerAddStore(this, windowTable, store, sDataModel);
		btnAdd.addActionListener(LAddWindowInsert);
		
		btnCancel = new JButton("Annulla");
		btnCancel.setBounds(317, 85, 116, 25);
		frmWindowInsert.getContentPane().add(btnCancel);
		btnCancel.addActionListener(LAddWindowInsert);
		frmWindowInsert.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblName, textFieldName, lblBrand, textFieldBrand, lblCode, 
				textFieldCode, lblCategory, textFieldCategory, lblPrice, textFieldPrice, lblPromo, textFieldPromo, lblDiscount, textFieldDiscount, btnCancel, btnAdd}));

		//Label per lo sfondo
		JLabel lblBackground = new JLabel();
		lblBackground.setBounds(0, 0, 600, 200);
		lblBackground.setIcon(new ImageIcon(WindowTable.class.getResource("/images/PaperBackground.jpg")));
		frmWindowInsert.getContentPane().add(lblBackground);

		frmWindowInsert.setVisible(true);
	}

	/**
	 * Permette la distuzione del frame da parte di altre classi.
	 */
	public void dispose() {
		frmWindowInsert.dispose();
	}

	/**
	 * Permette di impostare la visibilita' della finestra.
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		frmWindowInsert.setVisible(visible);
	}

	/**
	 * Restituisce il contenuto del campo del nome.
	 * @return name
	 */
	public String getTextFieldName() {
		return textFieldName.getText();
	}

	/**
	 * Imposta il nome del campo.
	 * @param text
	 */
	public void setTextFieldName(String text) {
		textFieldName.setText(text); ;
	}

	/**
	 * Restituisce il contenuto del campo del modello
	 * @return brand
	 */
	public String getTextFieldBrand() {
		return textFieldBrand.getText();
	}

	/**
	 * Imposta il modello del campo
	 * @param text
	 */
	public void setTextFieldBrand(String text) {
		textFieldBrand.setText(text);
	}

	/**
	 * Restituisce il contenuto del campo del codice
	 * @return code
	 */
	public String getTextFieldCode() {
		return textFieldCode.getText();
	}

	/**
	 * Imposta il codice del campo
	 * @param text
	 */
	public void setTextFieldCode(String text) {
		textFieldCode.setText(text);
	}

	/**
	 * Restituisce il contenuto del campo della categoria
	 * @return category
	 */
	public String getTextFieldCategory() {
		return textFieldCategory.getText();
	}

	/**
	 * Imposta la categoria del campo
	 * @param text
	 */
	public void setTextFieldCategory(String text) {
		textFieldCategory.setText(text);
	}

	/**
	 * Restituisce il contenuto del campo del prezzo
	 * @return price
	 */
	public String getTextFieldPrice() {
		return textFieldPrice.getText();
	}

	/**
	 * Imposta il prezzo del campo
	 * @param text
	 */
	public void setTextFieldPrice(String text) {
		textFieldPrice.setText(text);
	}

	/**
	 * Restituisce il contenuto del campo dell'offerta
	 * @return promo
	 */
	public String getTextFieldPromo() {
		return textFieldPromo.getText();
	}

	/**
	 * Imposta la promozione del campo
	 * @param text
	 */
	public void setTextFieldPromo(String text) {
		textFieldPromo.setText(text);
	}

	/**
	 * Restituisce il contenuto del campo dello sconto
	 * @return discount
	 */
	public String getTextFieldDiscount() {
		return textFieldDiscount.getText();
	}

	/**
	 * Imposta lo sconto del campo
	 * @param text
	 */
	public void setTextFieldDiscount(String text) {
		textFieldDiscount.setText(text);
	}	
}