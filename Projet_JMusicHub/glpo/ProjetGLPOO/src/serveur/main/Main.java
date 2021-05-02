package serveur.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import serveur.main.ClientLoop;

/**
 * Class principale du serveur 
 * @author YYYY
 *
 */
public class Main {
	
	static ServerSocket ss;

	
	/**
	 * Constructeur classe Main
	 */
	public Main() {
		
	}
	
	/**
	 * M�thode start()
	 * 
	 * <p>
	 * Utils�e pour lancer le serveur, r�cup�rer les clients et lancer un thread pour chaque client permettant de communiquer avec plusieurs en m�me temps
	 * </p>
	 */
	public void start() {
		Socket clientSocket;

		try {
			ss = new ServerSocket(5213);

			while (true) {
				clientSocket = ss.accept();
				Thread newThread = new Thread(new ClientLoop(clientSocket, this));
				newThread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�thode de base
	 * @param args non utilis� ici
	 */
	public static void main(String[] args) {
		Main mc = new Main();
		mc.start();
	}
	
}
