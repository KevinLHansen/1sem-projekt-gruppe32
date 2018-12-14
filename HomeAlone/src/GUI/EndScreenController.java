/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import HomeAlone.business.Game;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Gruppe 32
 */
public class EndScreenController implements Initializable {
    @FXML
    private Label txtResult;
    @FXML
    private Button btnYes;
    @FXML
    private Button btnNo;
    @FXML
    private Label txtResultReason;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtResult.setText(Game.getInstance().getResult());
        txtResultReason.setText(Game.getInstance().getResultReason());
    }    

    @FXML
    private void handleBtnYes(ActionEvent event) {
        try {
            Game.getInstance().restart();
            
            Stage primaryStage = (Stage)btnYes.getScene().getWindow();
            primaryStage.close();
            
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

        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleBtnNo(ActionEvent event) {
        // close the window
        Stage primaryStage = (Stage)btnYes.getScene().getWindow();
        primaryStage.close();
    }
    
}
