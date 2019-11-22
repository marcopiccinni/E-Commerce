import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe per l'implementazione del bottone per rendere visibile o nascondere il carrello
 * se l'utente attivo e' l'aquirente.
 * 
 * @author Marco Piccinni
 */
public class ListenerShowCart implements ActionListener {
	private WindowTable windowTable;


	/**
	 * Costruttore per inizializzare le istanze della classe.
	 * @param windowTable
	 */
	public ListenerShowCart(WindowTable windowTable) {
		this.windowTable= windowTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Mostra carrello" ) 
			windowTable.setShowCart(true);

		if (e.getActionCommand() == "Nascondi carrello") 
			windowTable.setShowCart(false);
	}
}