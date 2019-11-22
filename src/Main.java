/**
 * Classe che cambia il look and feel se il sistema in uso e' Windows
 * e avvia l'interfaccia grafica {@link WindowTable} e {@link WindowUserLogin} 
 * per il primo login nell'applicazione.
 * 
 * @author Marco Piccinni
 */
public class Main {
	
	public static void main(String[] args) {
	
		if (OSValidator.isWindows()) {
			try {
				javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
			} 
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
				System.err.println(ex.toString());
			}
		}
		
		WindowTable windowTable = new WindowTable();
		windowTable.setVisible(false);
		
		WindowUserLogin login = new WindowUserLogin(windowTable);
		login.setFrameVisible(true);
	}
}