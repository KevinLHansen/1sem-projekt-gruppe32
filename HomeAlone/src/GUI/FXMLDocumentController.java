/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import HomeAlone.business.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author skoti
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private ListView<String> lvAvailableExits;
    @FXML
    private TextArea txtOutput;
    @FXML
    private Button btnMove;
    @FXML
    private Button btnExamine;
    @FXML
    private ListView<String> lvInventory;
    @FXML
    private TextArea txtObjective;
    @FXML
    private Button btnPickup;
    @FXML
    private Button btnSetup;
    @FXML
    private Button btnDrop;
    @FXML
    private ListView<String> lvItemsNearby;
    @FXML
    private Label txtCurrentLocation;
    
    Game game = new Game();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvAvailableExits.setItems(game.getExitsObservableList()); // show available exits at currentRoom (foyer)
    }    

    @FXML
    private void handleBtnMove(ActionEvent event) {
        String nextRoom = lvAvailableExits.getSelectionModel().getSelectedItem(); // save selected item in String
        txtCurrentLocation.setText("Current location: " + nextRoom); // update Current location label with using the nextRoom String
        
        game.goRoom(nextRoom); // move to nextRoom
        lvAvailableExits.setItems(game.getExitsObservableList()); // update available exits at new currentRoom
        txtOutput.setText(""); // clear output box
        
    }

    @FXML
    private void handleBtnExamine(ActionEvent event) {
        String returnString = game.getCurrentRoomInfo();
        String outputText = "";
        if ("".equals(returnString)) { // if RoomInfo is empty
            txtOutput.setText("Kevin doesn't think that there's anything he can do here. Maybe try something elsewhere.");
        } else {
            outputText += "Kevin's thoughts: \n\"";
            String t = game.getTrapInfo();
            if(t.equalsIgnoreCase("")) { 
                outputText +=  returnString + "\"";
            } else {
                outputText += "I already set up a trap in this room. Better look somewhere else.\"";
                outputText += "\nTrap: ";
                outputText += game.getTrapString();
            }
            txtOutput.setText(outputText); // paste outputText to output box
        }
        lvItemsNearby.setItems(game.getItemsObservableList()); // update nearby items list with nearby items
            
    }

    @FXML
    private void handleBtnPickup(ActionEvent event) {
    }

    @FXML
    private void handleBtnSetup(ActionEvent event) {
    }

    @FXML
    private void handleBtnDrop(ActionEvent event) {
    }
    
}
