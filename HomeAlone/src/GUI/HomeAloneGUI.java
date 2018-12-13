package GUI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Gruppe 32 
 */
public class HomeAloneGUI extends Application {
    
    /* GAME WINDOW
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("HOME ALONE™");
        stage.getIcons().add(new Image("file:img/icon.png"));
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    */
    // WELCOME WINDOW
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
            
            Scene scene = new Scene(root);
            
            stage.setTitle("HOME ALONE™");
            stage.getIcons().add(new Image("file:img/icon.png"));
            
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeAloneGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
