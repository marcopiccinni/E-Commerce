import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Classe che implementa l'ascoltatore per l'immissione e controllo delle credenziali.
 * Chiamata dalla finestra {@link WindowUserLogin}
 * @author Marco Piccinni
 *
 */
public class ListenerLogin implements ActionListener {
	
	private WindowUserLogin windowLogin;
	private int AccessId;
	private String Account;
	private String AdminAccount= "Admin";
	private char[] AdminPassword = { 'A', 'd', 'm', 'i', 'n' };
	private WindowTable windowTable;


	/**
	 * Costruttore che inizializza le istanze della classe per il primo login.
	 * Richiede che la finestra di Login sia già stata creata.
	 * L'Id corrispondente al tipo di utente e' impostato a 2 (Utente non inserito o non valido).
	 * 
	 * @param windowLogin
	 * @param windowTable
	 */
	public ListenerLogin(WindowUserLogin windowLogin, WindowTable windowTable) {
		this.windowLogin = windowLogin;
		this.windowTable = windowTable;
		this.AccessId = 2;
	}
	
	/**
	 * Costruttore per la creazione della finestra di login.
	 * Impiegata per la modalita' di cambio utente.
	 * In questo caso, se l'immissione risultasse invalida l'account valido rimane quello attivo.
	 * @param windowTable
	 */
	public ListenerLogin(WindowTable windowTable) {
		this.windowTable= windowTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Comando richiamato alla pressione del pulsante di conferma per il login
		if (e.getActionCommand() == "Login") {
			Account= windowLogin.UsernameGetText();
			AccessControl(windowLogin.getPassword());
		} 
		
		//Comando posto nella finestra principale per richiamare la finestra di login
		if(e.getActionCommand()=="<html>Cambia<br> utente</html>"){
			windowLogin= new WindowUserLogin(windowTable);
			windowLogin.setFrameVisible(true);
			windowTable.setVisibiltySelect();
		}
	}
	
	/**
	 * Metodo interno per il controllo dei dati richiesti.
	 * @param Password 
	 */
	private void AccessControl(char[] Password) {

		if (Account.equals(AdminAccount)) {
			windowLogin.setVisiblePassword(true);
			windowLogin.frameRepaint();

			if (Password.length>0) {
				if(isPasswordCorrect(Password)){
					AccessId = 0;
					access();
				}
				else {
					Notify notifica = new Notify();
					notifica.msgError("Accesso negato \nCredenziali errate");
				}	
			}
			windowLogin.resetPassword();
		}
		else if (!Account.equals("")) {
			AccessId = 1;
			access();
		}
	}

	/**
	 * Metodo interno per impostare il frame principale dopo la validazione dell'accesso. 
	 */
	private void access() {
		setAscClean();
		if (!windowTable.getUsername().equals(Account)) {

			windowTable.setUsername(Account);
			windowTable.setAId(AccessId);	
			windowTable.cleanCart();
		}
		
		windowLogin.setFrameVisible(false);
		windowTable.setVisible(true);
	}

	/**
	 * Metodo interno per la verifica della validita' del campo password.
	 * @param input password immessa dall'utente.
		 * @return true se la password e' corretta, false altrimenti.
	 */
	private boolean isPasswordCorrect(char[] input) {
		boolean isCorrect = true;

		if (input.length != AdminPassword.length)
			isCorrect = false;
		else
			isCorrect = Arrays.equals(input, AdminPassword);

		// Zero out the password.
		Arrays.fill(input, '0');
		return isCorrect;
	}

	/**
	 * Metodo interno per il reset dei campi richiesti all'utente.
	 */
	private void setAscClean() {
		windowLogin.resetPassword();
		windowLogin.resetUsername();
		windowLogin.setVisiblePassword(false);
	}
}