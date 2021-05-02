package client.musichub.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * 
 * @author XXXX
 *
 */
public class MusicLoader {

	/**
	 * M�thode utilis�e pour lire une source AudioInputStream (donc une musique ou livre audio).
	 * @param ais source de la musique sous la forme d'AudioInputStream
	 */
	public void listenMusicAIS(AudioInputStream ais) {
		try {
			AudioFormat audioFormat = ais.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();
			byte[] samples = new byte[4096];
			int count = 0;
			while((count = ais.read(samples,0,4096)) != -1) {
				line.write(samples, 0, count);
				//System.out.println("PPP");
			}
			System.out.println("�coute finie");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
