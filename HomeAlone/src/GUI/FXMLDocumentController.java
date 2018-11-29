/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import HomeAlone.business.AudioFile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import HomeAlone.business.Game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

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
    ObservableList<String> inventoryList = FXCollections.observableArrayList();
    @FXML
    private MenuItem menuItemRestart;
    @FXML
    private MenuItem menuItemShowMap;
    @FXML
    private MenuItem menuItemExit;
    @FXML
    private MenuItem menuItemHTP;
    @FXML
    private MenuItem menuItemAbout;
    @FXML
    private MenuBar menuBar;

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

        game.goRoom(nextRoom);
        txtCurrentLocation.setText("Current location: " + game.getCurrentRoomShortDescription()); // update Current location label with using the nextRoom String
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

        String itemName = lvItemsNearby.getSelectionModel().getSelectedItem();
        if (itemName != null) {
            game.pickupItem(itemName);

            //if (inventoryList.size() < 3) {
            if (game.getError("pickup").equals("")) {
                inventoryList.add(itemName);
                lvInventory.setItems(inventoryList);
                lvItemsNearby.setItems(game.getItemsObservableList());
                //lvItemsNearby.
                        
                AudioFile pickupSound = null;
                pickupSound = new AudioFile("sfx/pickup.wav");
                pickupSound.playFile();
            } else {
                txtOutput.setText(game.getError("pickup"));
            }
        }
    }

    @FXML
    private void handleBtnSetup(ActionEvent event) {

        String itemName = lvInventory.getSelectionModel().getSelectedItem();
        game.setTrap(itemName);
        inventoryList.remove(itemName);

    }

    @FXML
    private void handleBtnDrop(ActionEvent event) {

        String itemName = lvInventory.getSelectionModel().getSelectedItem();
        game.dropItem(itemName);
        inventoryList.remove(itemName);
        lvItemsNearby.setItems(game.getItemsObservableList());
        
        AudioFile dropSound = null;
        dropSound = new AudioFile("sfx/drop.wav");
        dropSound.playFile();
    }

    @FXML
    private void handleMenuItemRestart(ActionEvent event) {
        try {
            Stage primaryStage = (Stage)((Node)menuBar).getScene().getWindow();
            primaryStage.close();
            
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("HOME ALONE™");
            stage.getIcons().add(new Image("file:img/icon.png"));
            
            stage.setResizable(false);
            stage.setScene(scene);       
            stage.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleMenuItemShowMap(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ShowMap.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("HOME ALONE™ - Map");
            stage.getIcons().add(new Image("file:img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleMenuItemExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleMenuItemHTP(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("HOME ALONE™ - How to play");
            stage.getIcons().add(new Image("file:img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleMenuItemAbout(ActionEvent event) {
        try {
            Parent root = root = FXMLLoader.load(getClass().getResource("About.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("HOME ALONE™ - About");
            stage.getIcons().add(new Image("file:img/icon.png"));
            
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
