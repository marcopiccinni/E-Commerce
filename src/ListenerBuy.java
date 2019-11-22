
import java.awt.event.* ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JTextField ;
import javax.swing.JOptionPane ;
import javax.swing.Box ;

/**
 *	Classe che implementa il pagamento dei prodotti nel carrello.
 *
 * @author Marco Piccinni
 */
public class ListenerBuy extends JOptionPane implements ActionListener  {

	private static final long serialVersionUID = 1L;

	private Cart<ProductSaved> cart;
	private TableModelCart cDataModel;	
	private WindowTable windowTable;

	/**
	 * Costruttore che inizializza gli oggetti della classe.
	 * 
	 * @param cart carello
	 * @param cDdataModel modello della vista carrello
	 * @param windowTable finestra principale
	 */
	public ListenerBuy (Cart<ProductSaved> cart, TableModelCart cDdataModel, WindowTable windowTable) {

		this.cart 		= cart;
		this.cDataModel = cDdataModel;
		this.windowTable= windowTable;
	}	

	@Override 
	public void actionPerformed(ActionEvent e) {

		Notify ntf = new Notify() ;

		//Comando Acquista -> Nuova finestra
		if (e.getActionCommand() == "Acquista") {

			JPanel panelPay  = new JPanel() ;
			//creazione oggetti pannello pagamento
			JTextField street = new JTextField(10);
			JTextField mod = new JTextField(10);
			panelPay.add(new JLabel("Indirizzo:"));
			panelPay.add(street);
			panelPay.add(Box.createHorizontalStrut(5));
			panelPay.add(new JLabel("Tipo Pagamento:"));
			panelPay.add(mod);
			
			int result = JOptionPane.showConfirmDialog(null, panelPay,"Inserisci informazioni richieste", JOptionPane.OK_CANCEL_OPTION);

			if (street.equals("")) {
				ntf.msgWarning("Inserire indirizzo");
				return ;
			}

			if (mod.equals("")) {
				ntf.msgWarning("Inserire tipo pagamento");
				return;
			}

			if (result == JOptionPane.OK_OPTION) {
				//Svuota Carrello
				cart.removeAll() ;	
				windowTable.lblTotalSetText(cart.updateTotal(cDataModel));
				cDataModel.update();
			}
		}
		
		//Comando Svuota carrello
		if (e.getActionCommand() == "Svuota carrello") {
			try {
				cart.removeAll();
				cDataModel.update() ;
				windowTable.lblTotalSetText(cart.updateTotal(cDataModel));
				ntf.msgOk("Carello svuotato!");
				return ;
			}
			catch(ArrayIndexOutOfBoundsException aoe) {
				ntf.msgError("Attenzione, il carrello non e' stato svuotato");
			}
		}
	}
}