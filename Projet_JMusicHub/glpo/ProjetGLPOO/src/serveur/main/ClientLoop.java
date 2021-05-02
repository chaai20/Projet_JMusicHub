package serveur.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import serveur.musichub.business.Album;
import serveur.musichub.business.AudioBook;
import serveur.musichub.business.AudioElement;
import serveur.musichub.business.MusicHub;
import serveur.musichub.business.NoAlbumFoundException;
import serveur.musichub.business.NoElementFoundException;
import serveur.musichub.business.NoPlayListFoundException;
import serveur.musichub.business.PlayList;
import serveur.musichub.business.Song;

/**
 *
 * Classe utilis�e par les thread g�rant les clients
 * @author YYYY
 *
 */
public class ClientLoop implements Runnable {
	
	Socket socket;
	
	OutputStream out;
	BufferedReader in;
	PrintWriter outPrint;
	MusicHub theHub;
	
	String DIR = System.getProperty("user.dir");
	
	/**
	 * Class qui g�re un client
	 * <p>
	 * Contient tout le n�cessaire pour l'application musichub
	 * </p>
	 * @param socket correspondant au client souhaitant utiliser l'application
	 * @param main simple r�f�rence au main
	 */
	public ClientLoop(Socket socket, Main main) {
		this.socket = socket;
		try {
			out = socket.getOutputStream();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outPrint = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		theHub = new MusicHub ();
	}
	
	
	/**
	 * Method qui permet d'envoyer la liste des commandes vers le client
	 * 
	 * @param out
	 *		Le PrintWriter qui envoie vers le client
	 */
	public void sendCommands(PrintWriter out) {
		sendMessage("h: display command list", out);
		sendMessage("t: display the album titles, ordered by date",out);
		sendMessage("g: display songs of an album, ordered by genre",out);
		sendMessage("d: display songs of an album",out);
		sendMessage("u: display audiobooks ordered by author",out);
		sendMessage("c: add a new song",out);
		sendMessage("a: add a new album",out);
		sendMessage("+: add a song to an album",out);
		sendMessage("l: add a new audiobook",out);
		sendMessage("p: create a new playlist from existing songs and audio books",out);
		sendMessage("-: delete an existing playlist",out);
		sendMessage("s: save elements, albums, playlists",out);
		sendMessage("q: quit program",out);
	}
	
	public void sendMessage(String msg, PrintWriter out) {
		out.println(msg);
		out.flush();
	}
	
	public String getMessage() {
		
		String msg = null;
		
		try {
			msg = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return msg;
		
	}
	
	public void sendMusic(String fileName, OutputStream out) {
		InputStream is;
		try {
			is = new FileInputStream(DIR+"/Music/"+fileName);
			int count;
			byte[] buffer = new byte[4096];
			while((count = is.read(buffer)) != -1) {
				socket.getOutputStream().write(buffer, 0, count);
			}
			//socket.getOutputStream().flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int countLines(String str){
	   String[] lines = str.split("\r\n|\r|\n");
	   return  lines.length;
	}

	@Override
	public void run() {
		
		System.out.println("Enter client loop");
		String msg = getMessage();
		if(msg.equals("OK")) {
			System.out.println("Connexion done");
			while(true) {
				sendMessage("Select an action : (h for help)", outPrint);
				
				msg = getMessage();
				System.out.println("from Client : "+msg);
				String listAlbum;
				switch(msg) {
					case "h":
						sendCommands(outPrint);
						break;
					case "t":
						listAlbum = theHub.getAlbumsTitlesSortedByDate();
						System.out.println(listAlbum);
						sendMessage(""+countLines(listAlbum), outPrint);
						sendMessage(listAlbum, outPrint);
						break;
					case "g":
						sendMessage("Songs of an album sorted by genre will be displayed; enter the album name, available albums are:",outPrint);
						listAlbum = theHub.getAlbumsTitlesSortedByDate();
						sendMessage(""+countLines(listAlbum), outPrint);
						sendMessage(listAlbum, outPrint);
						msg = getMessage();
						try {
							//System.out.println(theHub.getAlbumSongsSortedByGenre(msg));
							String listSongs = theHub.getAlbumSongsSortedByGenre(msg).toString();
							sendMessage(""+countLines(listSongs),outPrint);
							sendMessage(listSongs,outPrint);
						} catch (NoAlbumFoundException ex) {
							sendMessage(""+1,outPrint);
							sendMessage("No album found with the requested title " + ex.getMessage(), outPrint);
						}
						break;
					case "d":
						sendMessage("Songs of an album will be displayed; enter the album name, available albums are:",outPrint);
						listAlbum = theHub.getAlbumsTitlesSortedByDate();
						sendMessage(""+countLines(listAlbum), outPrint);
						sendMessage(listAlbum, outPrint);
						msg = getMessage();
						try {
							//System.out.println(theHub.getAlbumSongsSortedByGenre(msg));
							String listSongs = theHub.getAlbumSongs(msg).toString();
							sendMessage(""+countLines(listSongs),outPrint);
							sendMessage(listSongs,outPrint);
						} catch (NoAlbumFoundException ex) {
							sendMessage(""+1,outPrint);
							sendMessage("No album found with the requested title " + ex.getMessage(), outPrint);
						}
						break;
					case "u":
						String listAudioB = theHub.getAudiobooksTitlesSortedByAuthor();
						sendMessage(""+countLines(listAudioB), outPrint);
						sendMessage(listAudioB, outPrint);
						break;
					case "c":
						sendMessage("Enter a new song: ",outPrint);
						sendMessage("Song title: ",outPrint);
						String ctitle = getMessage();
						sendMessage("Song genre (jazz, classic, hiphop, rock, pop, rap):",outPrint);
						String cgenre = getMessage();
						sendMessage("Song artist: ",outPrint);
						String cartist = getMessage();
						sendMessage("Song length in seconds: ",outPrint);
						String cstrlength = getMessage();
						int clength = Integer.parseInt(cstrlength);
						sendMessage("Song content: ",outPrint);
						String ccontent = getMessage();
						Song s = new Song(ctitle,cartist,clength,ccontent,cgenre);
						theHub.addElement(s);
						System.out.println("New element list: ");
	                    Iterator<AudioElement> it = theHub.elements();
	                    while (it.hasNext()) System.out.println(it.next().getTitle());
	                    sendMessage("Song created!",outPrint);
	                    break;
					case "a":
						sendMessage("Enter a new album: ",outPrint);
						sendMessage("Album title: ",outPrint);
						String atitle = getMessage();
						sendMessage("Album artist: ",outPrint);
						String aartist = getMessage();
						sendMessage("Album length in seconds: ",outPrint);
						String astrlength = getMessage();
						int alength = Integer.parseInt(astrlength);
						sendMessage("Album date as YYYY-DD-MM: ",outPrint);
						String adate = getMessage();
						Album a = new Album(atitle,aartist,alength,adate);
						theHub.addAlbum(a);
						System.out.println("New list of albums: ");
	                    Iterator<Album> ita = theHub.albums();
	                    while (ita.hasNext()) System.out.println(ita.next().getTitle());
	                    sendMessage("Album Created!",outPrint);
						break;
					case "+":
						sendMessage("Add an existing song to an existing album",outPrint);
						sendMessage("Type the name of the song you wish to add. Available songs: ",outPrint);
						Iterator<AudioElement> itae = theHub.elements();
						String listsSongs = "";
						while (itae.hasNext()) {
							AudioElement ae = itae.next();
							if ( ae instanceof Song) listsSongs+=(ae.getTitle()+"\n");
						}
						sendMessage(""+countLines(listsSongs),outPrint);
						sendMessage(listsSongs,outPrint);
						String songtitle = getMessage();
						
						sendMessage("Type the name of the album you wish to enrich. Available albums: ",outPrint);
						Iterator<Album> ait = theHub.albums();
						String listsalbums = "";
						while (ait.hasNext()) {
							Album al = ait.next();
							listsalbums += (al.getTitle() + "\n");
						}
						sendMessage(""+countLines(listsalbums),outPrint);
						sendMessage(listsalbums,outPrint);
						String albumtitle = getMessage();
						
						try {
							theHub.addElementToAlbum(songtitle, albumtitle);
						} catch (NoAlbumFoundException ex){
							System.out.println (ex.getMessage());
						} catch (NoElementFoundException ex){
							System.out.println (ex.getMessage());
						}
						
						sendMessage("Song added to the album!",outPrint);
						break;
					case "l":
						sendMessage("Enter a new audiobook: ",outPrint);
						sendMessage("AudioBook title: ",outPrint);
						String btitle = getMessage();
						sendMessage("AudioBook category (youth, novel, theater, documentary, speech)",outPrint);
						String bcategory = getMessage();
						sendMessage("AudioBook artist: ",outPrint);
						String bartist = getMessage();
						sendMessage("AudioBook length in seconds: ",outPrint);
						String blengthtemp = getMessage();
						int blength = Integer.parseInt(blengthtemp);
						sendMessage("AudioBook content: ",outPrint);
						String bcontent = getMessage();
						sendMessage("AudioBook language (french, english, italian, spanish, german)",outPrint);
						String blanguage = getMessage();
						AudioBook b = new AudioBook (btitle, bartist, blength, bcontent, blanguage, bcategory);
						theHub.addElement(b);
						System.out.println("Audiobook created! New element list: ");
						Iterator<AudioElement> itl = theHub.elements();
	                    while (itl.hasNext()) System.out.println(itl.next().getTitle());
						sendMessage("Audiobook created!",outPrint);
						break;
					case "p":
						
						sendMessage("Add an existing song or audiobook to a new playlist",outPrint);
						sendMessage("Type the name of the playlist you wish to create:",outPrint);
						String pName = getMessage();
						PlayList pl = new PlayList(pName);
						theHub.addPlaylist(pl);
						sendMessage("Available elements: ",outPrint);
						String listelems = "";
						Iterator<AudioElement> itael = theHub.elements();
						while (itael.hasNext()) {
							AudioElement ae = itael.next();
							listelems += ae.getTitle() + "\n";
						}
						sendMessage(""+countLines(listelems),outPrint);
						sendMessage(listelems,outPrint);
						boolean stillAdd = true;
						do {
							sendMessage("Type the name of the audio element you wish to add or 'n' to exit:",outPrint);
							String elementTitle = getMessage();
							if(!elementTitle.equals("n")) {
								try {
									theHub.addElementToPlayList(elementTitle, pName);
								} catch (NoPlayListFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (NoElementFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}else {
								stillAdd = false;
							}
						}while(stillAdd);
						sendMessage("Playlist created!",outPrint);
						break;
					case "-":
						sendMessage("Delete an existing playlist. Available playlists:",outPrint);
						String listPlaylists = "";
						Iterator<PlayList> itp = theHub.playlists();
						while (itp.hasNext()) {
							PlayList p = itp.next();
							listPlaylists += p.getTitle() + "\n";
						}
						sendMessage(""+countLines(listPlaylists),outPrint);
						sendMessage(listPlaylists,outPrint);
						
						String pltitle = getMessage();
						try {
							theHub.deletePlayList(pltitle);
						}	catch (NoPlayListFoundException ex) {
							System.out.println (ex.getMessage());
						}
						sendMessage("Playlist deleted!",outPrint);
						
						break;
					case "s":
						theHub.saveElements();
						theHub.saveAlbums();
						theHub.savePlayLists();
						System.out.println("Elements, albums and playlists saved!");
						sendMessage("Elements, albums and playlists saved!",outPrint);
						break;
					case "m":
						
						sendMessage("Enter a song you want to listen :", outPrint);
						
						String st = getMessage();
						
						Iterator<AudioElement> itel = theHub.elements();
						
						String pathtosong = "error.wav";
						
						while (itel.hasNext()) {
							AudioElement ae = itel.next();
							if(ae.getTitle().equals(st)) pathtosong = ae.getContent();
						}
						
						sendMusic(pathtosong,out);
						getMessage();
						break;
				}
			}
		}
	}

}
