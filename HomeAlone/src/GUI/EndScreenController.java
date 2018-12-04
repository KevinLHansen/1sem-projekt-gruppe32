/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author skoti
 */
public class EndScreenController implements Initializable {
    @FXML
    private Label txtResult;
    @FXML
    private Button btnYes;
    @FXML
    private Button btnNo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBtnYes(ActionEvent event) {
    }

    @FXML
    private void handleBtnNo(ActionEvent event) {
    }
    
}
