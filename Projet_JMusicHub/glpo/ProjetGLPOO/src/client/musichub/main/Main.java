package client.musichub.main;

import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;

import client.musichub.network.ServerConnexion;
import client.musichub.sound.MusicLoader;

/**
 * Classe principale du client qui g�re toute les requ�tes
 * @author XXXX
 */
public class Main {
	
	ServerConnexion sc;
	MusicLoader ml;
	Scanner scan;
	
	/**
	 * Constructeur classe principale client
	 * <p>
	 * Initialise un scanner pour permettre au client d'envoyer ensuite des messages au serveur
	 * </p>
	 */
	public Main() {
		scan = new Scanner(System.in);
	}
	
	/**
	 * M�thode qui lance toute la logique et g�re la communication client -> serveur
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		sc = new ServerConnexion("localhost", 5213);
		ml = new MusicLoader();
		sc.sendMessage("OK");
		while(true) {
			
			String msg = sc.getMessage();
			System.out.println("msg = " + msg);
			String outMsg = scan.nextLine();
			sc.sendMessage(outMsg);
			int lines;
			
			switch(outMsg) {
				case "h":
					for(int i = 0;i<13;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					break;
				case "t":
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					System.out.println(lines);
					for(int i = 0;i<=lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					break;
				case "d":
				case "g":
					msg = sc.getMessage();
					System.out.println(msg);
					
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					for(int i = 0;i<=lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					for(int i = 0;i<lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					break;
				case "u":
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					for(int i = 0;i<=lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					break;
				case "c":
					msg = sc.getMessage();
					System.out.println(msg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					break;
				case "a":
					msg = sc.getMessage();
					System.out.println(msg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					msg = sc.getMessage();
					System.out.println(msg);
					break;
				case "+":
					msg = sc.getMessage();
					System.out.println(msg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					for(int i = 0;i<=lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					for(int i = 0;i<=lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					
					break;
				case "l":
					msg = sc.getMessage();
					System.out.println(msg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					
					break;
				case "p":
					
					msg = sc.getMessage();
					System.out.println(msg);
					msg = sc.getMessage();
					System.out.println(msg);
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					for(int i = 0;i<=lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					
					boolean stillAdd = true;
					do{
						msg = sc.getMessage();
						System.out.println(msg);
						
						outMsg = scan.nextLine();
						sc.sendMessage(outMsg);
						
						if(outMsg.equals("n")) stillAdd = false;
					}while(stillAdd);
					
					msg = sc.getMessage();
					System.out.println(msg);
					
					break;
				case "-":
					msg = sc.getMessage();
					System.out.println(msg);
					msg = sc.getMessage();
					lines = Integer.parseInt(msg);
					for(int i = 0;i<=lines;i++) {
						msg = sc.getMessage();
						System.out.println(msg);
					}
					
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					
					msg = sc.getMessage();
					System.out.println(msg);
					
					break;
				case "s":
					msg = sc.getMessage();
					System.out.println(msg);
					break;
				case "m":
					
					msg = sc.getMessage();
					System.out.println(msg);
					outMsg = scan.nextLine();
					sc.sendMessage(outMsg);
					AudioInputStream music = sc.getAudio();
					ml.listenMusicAIS(music);
					
					sc.sendMessage("ok");
					//System
					break;
			}
		}
	}
	
	/**
	 * M�thode de lancement de l'application client
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Main mc = new Main();
		try {
			mc.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
