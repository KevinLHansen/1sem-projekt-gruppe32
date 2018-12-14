package HomeAlone.business;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Gruppe 32
 */
public class AudioFile {
    private Clip clip;
    private String filePath;
    private InputStream inputStream;
    private AudioInputStream audioStream;
    
    public AudioFile(String url) {
        filePath = url;
        inputStream = null;
        audioStream = null;
    }
    
    public void playFile() {
        
        try {
            URL url = getClass().getResource(filePath);
            audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AudioFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(audioStream != null) {
                    audioStream.close();
                } else {
                    System.out.println("FILE NOT FOUND?");
                }
            } catch (IOException ex) {
                Logger.getLogger(AudioFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void loopFile(int loops) {
        if(clip != null) {
            clip.loop(loops);
        }
    }
    
    public void stopFile() {
        if(clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }
    
    public void pauseFile() {
        if(clip != null) {
            if(clip.isRunning()) {
                clip.stop();
            }
        }
    }
    
    public void restartFile() {
        if(clip != null) {
            clip.start();
        }
    }
}
