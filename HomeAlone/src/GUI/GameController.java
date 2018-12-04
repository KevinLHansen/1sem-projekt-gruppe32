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
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author skoti
 */
public class GameController implements Initializable {

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

    private Stage gameStage;
    private Game game;
    private ObservableList<String> inventoryList = FXCollections.observableArrayList();
    private Timer timer = new Timer();
   // private int delay = 1000;
    private int countDown = 1;
    //private int period = countDown * 60 * 60 * 1000; // 10min in milliseconds
    private int startTimeSec = 0;
    private Timeline timeline = new Timeline();
    //private boolean isRunning;
    private int min = countDown;

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
    @FXML
    private TextField txtTimeLeft;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new Game();
        lvAvailableExits.setItems(game.getExitsObservableList()); // show available exits at currentRoom (foyer)
        txtTimeLeft.setText(String.format("%d:%02d", countDown, startTimeSec));
        startTimer();
    }

    private void startTimer() {
        KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                startTimeSec--;
                boolean isSecondsZero = startTimeSec == 0;
                boolean timeToChangeBackground = startTimeSec == 0 && countDown == 0;

                if (isSecondsZero) {
                    countDown--;
                    startTimeSec = 60;
                }
                if (timeToChangeBackground) {
                    timeline.stop();
                    countDown = 0;
                    startTimeSec = 0;
                    txtTimeLeft.setText(String.valueOf(countDown));
                    // Start next phase here

                }

                txtTimeLeft.setText(String.format("%d:%02d", countDown, startTimeSec));

            }
        });
        startTimeSec = 60; // Change to 60!
        countDown = min - 1;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyframe);
        timeline.playFromStart();
        //isRunning = true;
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
            if (t.equalsIgnoreCase("")) {
                outputText += returnString + "\"";
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
            Stage primaryStage = (Stage) ((Node) menuBar).getScene().getWindow();
            primaryStage.close();

            game = null;
            game = new Game();

            Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("HOME ALONE™");
            stage.getIcons().add(new Image("file:img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleListItemClicked(MouseEvent event) {

        String nextRoom = lvAvailableExits.getSelectionModel().getSelectedItem(); // save selected item in String
        if (event.getClickCount() == 2) {
            game.goRoom(nextRoom);
            txtCurrentLocation.setText("Current location: " + game.getCurrentRoomShortDescription()); // update Current location label with using the nextRoom String
            Tooltip loc = new Tooltip();
            loc.setText(game.getCurrentRoomShortDescription());
            txtCurrentLocation.setTooltip(loc);
            lvAvailableExits.setItems(game.getExitsObservableList()); // update available exits at new currentRoom
            txtOutput.setText(""); // clear output box
        }
    }
}
