package HomeAlone.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

    public class AudioFile
    {
        // attributes
        private String filePath; // string for use in constructor
        private AudioStream audioStream;
        private InputStream inputStream;
        
        // constructors
        public AudioFile(String filePath) {
            this.filePath = filePath; // declare file path of sound file
        }
        
        // methods
        public void playFile() {
            
            try {
                inputStream = new FileInputStream(filePath); // declare file path
                audioStream = new AudioStream(inputStream); // instantiate AudioStream
                AudioPlayer.player.start(audioStream); // play the sound file
            }
            catch (FileNotFoundException e) {
                System.out.println("!FILE NOT FOUND!");
            }
            catch (IOException e) {
                System.out.println("!IOException!");
            }
        }
        
        public void stopFile() {
            AudioPlayer.player.stop(audioStream);
        }
    }