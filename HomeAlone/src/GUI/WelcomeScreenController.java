package GUI;

import HomeAlone.business.AudioFile;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class for the Welcome window
 *
 * @author Gruppe 32
 */
public class WelcomeScreenController implements Initializable {
    @FXML
    private Button btnStartGame;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Event handler for button with caption "Start Game".
     * This method opens the main game window.
     * @param event 
     */
    @FXML
    private void handleBtnStartGame(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("HOME ALONE™");
            stage.getIcons().add(new Image("/resources/img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            // Needed because of timer
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            // define current window as welcomeStage to be able to close
            Stage welcomeStage = (Stage)btnStartGame.getScene().getWindow();
            welcomeStage.close();


        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
