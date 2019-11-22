import javax.swing.JOptionPane;

/**
 * Questa classe e' utilizzata per creare messaggi di avviso, conferma, errore.
 * @author Marco Piccinni
 */
public class Notify extends JOptionPane{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto.
	 */
	public Notify() {
	}

	/**
	 * Richiesta di sovrascrittura del salvataggio.
	 * @return true o false a seconda della scelta 
	 */
	public boolean overwrite() {
		if (JOptionPane.showConfirmDialog(null, "Sovrascrivere il salvataggio?", "Attenzione!", JOptionPane.YES_NO_OPTION) == 0)
			return true;
		return false;
	}

	/**
	 * Messaggio di errore
	 * @param error messaggio d'errore da mostrare
	 */

	public void msgError(String error) {
		JOptionPane.showMessageDialog(null, error, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Messaggio di avviso
	 * @param warning messaggio di avviso da mostrare
	 */
	public void msgWarning(String warning) {
		JOptionPane.showMessageDialog(null,warning,"Attenzione", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Messaggio di conferma
	 * @param notice messaggio di conferma da mostrare
	 */
	public void msgOk(String notice) {
		JOptionPane.showMessageDialog(null,notice);
	}
}