package client.musichub.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * Classe utilis�e pour effectuer une connexion au serveur.
 * @author XXXX
 */
public class ServerConnexion {
	
	public PrintWriter out;
	public BufferedReader in;
	public Socket socket;
	
	/**
	 * Constructeur de la classe de connexion au serveur
	 * <p>
	 * Lance une connexion au serveur et initialise les diff�rent composants
	 * </p>
	 * 
	 * @param ip l'adresse ipv4 du serveur
	 * @param port port ouvert par le serveur
	 */
	public ServerConnexion(String ip, int port) {
		try {
			socket = new Socket(ip,port);
			System.out.println("socket set");
			/*InputStream in = new BufferedInputStream(socket.getInputStream());
			AudioInputStream musica = AudioSystem.getAudioInputStream(in);*/
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction utilis�e pour attendre et recevoir un message du serveur.
	 * @return retourne le message attendu.
	 */
	public String getMessage() {
		
		String msg = null;
		
		try {
			msg = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return msg;
		
	}
	
	/**
	 * Fonction utilis�e pour recevoir une musique de la part du serveur.
	 * @return retourne un audioInputStream qui sera ensuite lu.
	 */
	public AudioInputStream getAudio() {
		
		AudioInputStream sound = null;
		try {
			InputStream in = new BufferedInputStream(socket.getInputStream());
			sound = AudioSystem.getAudioInputStream(in);
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		
		return sound;
	}
	
	/**
	 * M�thode utilis�e pour envoyer un message au serveur.
	 * @param msg message que l'on souhaite envoyer.
	 */
	public void sendMessage(String msg) {
		out.println(msg);
		out.flush();
	}

}
