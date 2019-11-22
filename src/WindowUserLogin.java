import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import org.eclipse.wb.swing.FocusTraversalOnArray;

/**
 * Classe per l'interfaccia grafica per la selezione degli utenti.
 * @author Marco Piccinni
 */
public class WindowUserLogin {

	private JFrame frmWindowUserLogin;
	private JTextField txtFieldUsername;
	private JLabel lblPassword;
	private JPasswordField pwdFieldPassword;
	private WindowTable windowTable;

	/**
	 * Costruttore della finestra di login e cambio utente
	 */
	public WindowUserLogin(WindowTable windowTable) {
		this.windowTable = windowTable;
		initialize();
		setClean();
	}

	/**
	 * Inizializza il contenuto del frame.
	 */
	private void initialize() {
		int frmDim=240;

		frmWindowUserLogin = new JFrame();
		GraphicsDevice gd = frmWindowUserLogin.getGraphicsConfiguration().getDevice();
		frmWindowUserLogin.setBounds(gd.getDisplayMode().getWidth()/2 - frmDim/2, gd.getDisplayMode().getHeight()/2 - frmDim/2, 400, 300);
		frmWindowUserLogin.setVisible(true);
		frmWindowUserLogin.setTitle("Login control");
		frmWindowUserLogin.setResizable(false);
		if (windowTable.getAId()==2)
			frmWindowUserLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		else {
			windowTable.setVisible(true);
			frmWindowUserLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		frmWindowUserLogin.getContentPane().setLayout(null);

		JLabel lblTextLogin = new JLabel();
		lblTextLogin.setFocusTraversalKeysEnabled(false);
		lblTextLogin.setFocusable(false);
		lblTextLogin.setAutoscrolls(true);
		lblTextLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTextLogin.setForeground(Color.DARK_GRAY);
		lblTextLogin.setBackground(UIManager.getColor("Panel.background"));
		lblTextLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTextLogin.setText("<html><p align=\"center\">Inserire nome utente e</p><p align=\"center\">password per proseguire</p></html");
		lblTextLogin.setBounds(79, 11, 246, 61);
		frmWindowUserLogin.getContentPane().add(lblTextLogin);
		lblTextLogin.setVisible(true);

		JLabel lblUserName = new JLabel();
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUserName.setFocusTraversalKeysEnabled(false);
		lblUserName.setFocusable(false);
		lblUserName.setBackground(UIManager.getColor("Panel.background"));
		lblUserName.setText("Nome utente");
		lblUserName.setBounds(49, 83, 133, 20);
		frmWindowUserLogin.getContentPane().add(lblUserName);
		lblUserName.setVisible(true);

		txtFieldUsername = new JTextField();
		txtFieldUsername.setToolTipText("");
		txtFieldUsername.setBounds(200, 83, 125, 20);
		txtFieldUsername.setColumns(10);
		frmWindowUserLogin.getContentPane().add(txtFieldUsername);
		txtFieldUsername.setVisible(true);

		lblPassword = new JLabel();
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setFocusTraversalKeysEnabled(false);
		lblPassword.setFocusable(false);
		lblPassword.setVisible(false);
		lblPassword.setBackground(UIManager.getColor("Panel.background"));
		lblPassword.setText("Password");
		lblPassword.setBounds(49, 133, 133, 20);
		frmWindowUserLogin.getContentPane().add(lblPassword);

		pwdFieldPassword = new JPasswordField();
		pwdFieldPassword.setVisible(false);
		pwdFieldPassword.setBounds(200, 133, 125, 20);
		frmWindowUserLogin.getContentPane().add(pwdFieldPassword);

		JButton btnSelectUser = new JButton("Login");
		btnSelectUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSelectUser.setBounds(200, 190, 85, 43);
		frmWindowUserLogin.getContentPane().add(btnSelectUser);
		btnSelectUser.setVisible(true);
		ListenerLogin LLogin = new ListenerLogin(this, windowTable);
		btnSelectUser.addActionListener(LLogin);

		JLabel lblBackground = new JLabel();
		lblBackground.setFocusTraversalKeysEnabled(false);
		lblBackground.setFocusable(false);
		lblBackground.setIcon(new ImageIcon(WindowUserLogin.class.getResource("/images/PaperBackground.jpg")));
		lblBackground.setBounds(0, 0, 420, 318);
		frmWindowUserLogin.getContentPane().add(lblBackground);
		frmWindowUserLogin.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtFieldUsername, pwdFieldPassword, btnSelectUser}));
		frmWindowUserLogin.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtFieldUsername, pwdFieldPassword, btnSelectUser}));

		frmWindowUserLogin.repaint();
	}

	/**
	 * Svuota il contenuto dei campi e rinizializza il frame.
	 */
	public void setClean(){
		txtFieldUsername.setText("");
		lblPassword.setVisible(false);
		pwdFieldPassword.setText("");
		pwdFieldPassword.setVisible(false);
	}

	/**
	 * Restituisce la visibilita' del frame.
	 * @return visible
	 */
	public boolean isVisible(){
		return frmWindowUserLogin.isVisible();
	}
	
	/**
	 * Imposta la visibilita' del frame.
	 * @param visible
	 */
	public void setFrameVisible(boolean visible){
		frmWindowUserLogin.setVisible(visible);
	}

	/**
	 * Ridisegna il frame per aggiornarlo.
	 */
	public void frameRepaint(){
		frmWindowUserLogin.repaint();
	}
	
	/**
	 * Imposta la visibilita' dell'inserimento della password.
	 * @param visible
	 */
	public void setVisiblePassword(boolean visible) {
		lblPassword.setVisible(visible);
		pwdFieldPassword.setVisible(visible);
	}
	
	/**
	 * Resetta il campo di inserimento della password. 
	 */
	public void resetPassword(){
	 pwdFieldPassword.setText("");
	}
	
	/**
	 * Restituisce il testo contenuto nel campo password.
	 * La funzione getPassword() restituisce un array invece che una Stringa per sicurezza.
	 * La precedente funzione getText() e' deprecata.
	 * @return password
	 */
	public char[] getPassword(){
		return pwdFieldPassword.getPassword();
	}

	/**
	 * Reimposta il campo dell'utente.
	 */
	public void resetUsername(){
		txtFieldUsername.setText("");
	}
	
	/**
	 * Restituisce il contenuto del campo dell'utente.
	 * @return username
	 */
	public String UsernameGetText(){
		return txtFieldUsername.getText();
	}
}