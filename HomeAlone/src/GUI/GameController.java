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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author gruppe 32
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

    private int startTimeMin = 1;
    private int startTimeSec = 0;
    private Timeline timeline = new Timeline();
    //private boolean isRunning;
    private int min = startTimeMin;
    private int phase = 1;
    
    AudioFile gameTheme = null;

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
    @FXML
    private Label lblTimeLeft;
    @FXML
    private Pane panePopup;
    @FXML
    private ImageView imgviewPopup;
    @FXML
    private TextArea txtPopup;
    @FXML
    private Button btnPopupOk;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvAvailableExits.setItems(Game.getInstance().getExitsObservableList()); // show available exits at currentRoom (foyer)
        txtTimeLeft.setText(String.format("%d:%02d", startTimeMin, startTimeSec));
        txtCurrentLocation.setText("Current location: " + Game.getInstance().getCurrentRoomShortDescription());
        txtObjective.setText(Game.getInstance().getObjective());
        startTimer();
        
        gameTheme = new AudioFile("sfx/gameTheme.wav");
        gameTheme.playFile();
    }

    private void startTimer() {
        KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                startTimeSec--;
                boolean isSecondsZero = startTimeSec == 0;
                boolean isSecondsLessThanZero = startTimeSec < 0;
                boolean timeToChangePhase = startTimeSec == 0 && startTimeMin == 0;
                if (Game.getInstance().inKitchenWithGun() && phase == 2) {
                    timeToChangePhase = true;
                }

                if (!timeToChangePhase) {
                    if (isSecondsZero) {
                        startTimeMin--;
                        startTimeSec = 60;
                    }
                    if (isSecondsLessThanZero) {
                        startTimeMin--;
                        startTimeSec = 59;
                    }
                }
                if (timeToChangePhase) {
                    timeline.stop();
                    // Start next phase here
                    phase = Game.getInstance().changePhase();
                    if (!Game.getInstance().checkStatus()) {
                        // YOU LOSE
                        endGame();
                    } else {
                        AudioFile popupSound = null;
                        popupSound = new AudioFile("sfx/popup.wav");
                        if (phase == 2) {
                            imgviewPopup.setImage(new Image("file:img/phase2transition.gif"));
                            txtPopup.setText("The Wet Bandits have arrived and are roaming the outside area! \nIf you exit the house, you will most certainly get caught.");
                            panePopup.setVisible(true);
                            lvAvailableExits.setDisable(true); // disable room list UI element to avoid dirty cheating
                            txtObjective.setText(Game.getInstance().getObjective()); // update objective UI element with new objective
                            popupSound.playFile();
                            gameTheme.stopFile();
                            gameTheme.playFile();
                        }
                        if (phase == 3) {
                            imgviewPopup.setImage(new Image("file:img/phase3transition.gif"));
                            txtPopup.setText("The Wet Bandits have entered the house! \nIf you run into them, you will get caught.");
                            panePopup.setVisible(true);
                            lvAvailableExits.setDisable(true);
                            txtObjective.setText(Game.getInstance().getObjective());
                            popupSound.playFile();
                        }
                    }
                }
                txtTimeLeft.setText(String.format("%d:%02d", startTimeMin, startTimeSec));
            }
        });

        startTimeSec = 60; // Change to 60!
        startTimeMin = min - 1;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyframe);
        timeline.playFromStart();
        //isRunning = true;
    }

    @FXML
    private void handleBtnMove(ActionEvent event) {
        String nextRoom = lvAvailableExits.getSelectionModel().getSelectedItem(); // save selected item in String
        moveRoom(nextRoom);
    }

    private void moveRoom(String nextRoom) {
        Game.getInstance().goRoom(nextRoom);

        if (phase > 1) {
            if (!Game.getInstance().checkStatus()) {
                // LOSE
                txtOutput.setText("YOU LOSE!!");
                endGame();
                return;
            }
        }
        txtCurrentLocation.setText("Current location: " + Game.getInstance().getCurrentRoomShortDescription()); // update Current location label with using the nextRoom String
        lvAvailableExits.setItems(Game.getInstance().getExitsObservableList()); // update available exits at new currentRoom
        txtOutput.setText(""); // clear output box
        lvItemsNearby.setItems(Game.getInstance().getItemsObservableList()); // update nearby item list upon moving
        if (phase == 3) {
            String s = Game.getInstance().checkNeighbourRoom();
            txtOutput.appendText(s);
            if (Game.getInstance().getFinished()) {
                endGame();
            }
        }
    }

    @FXML
    private void handleBtnExamine(ActionEvent event) {
        if (phase > 1) {
            if (!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
                endGame();
                return;
            }
        }
        String roomInfo = Game.getInstance().getCurrentRoomInfo();
        String outputText = "";
        if ("".equals(roomInfo)) { // if RoomInfo is empty
            txtOutput.setText("Kevin doesn't think that there's anything he can do here. Maybe try something elsewhere.");
        } else {
            outputText += "Kevin's thoughts: \n\"";
            String t = Game.getInstance().getTrapInfo();
            if (t.equalsIgnoreCase("")) {
                outputText += roomInfo + "\"";
            } else {
                outputText += "I already set up a trap in this room. Better look somewhere else.\"";
                outputText += "\nTrap: ";
                outputText += Game.getInstance().getTrapString();
            }
            txtOutput.setText(outputText); // paste outputText to output box
        }
        if (phase == 3) {
            String s = Game.getInstance().checkNeighbourRoom();
            txtOutput.appendText(s);
            if (Game.getInstance().isPhoneHere()) {
                txtOutput.appendText("\nPick up the phone, to call the police.");
            }
        }
        lvItemsNearby.setItems(Game.getInstance().getItemsObservableList()); // update nearby items list with nearby items

    }

    @FXML
    private void handleBtnPickup(ActionEvent event) {
        if (phase > 1) {
            if (!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
                endGame();
                return;
            }
        }
        String itemName = lvItemsNearby.getSelectionModel().getSelectedItem();
        if (itemName != null) {
            if (phase == 3 && itemName == "Phone") {
                txtOutput.setText("You called the police. Escape through the attic window!");
            } else {
                Game.getInstance().pickupItem(itemName);
                //if (inventoryList.size() < 3) {
                if (Game.getInstance().getError("pickup").equals("")) {
                    lvInventory.setItems(Game.getInstance().getInventoryObservableList());
                    /*inventoryList.add(itemName);
                     lvInventory.setItems(inventoryList);*/
                    lvItemsNearby.setItems(Game.getInstance().getItemsObservableList());
                    //lvItemsNearby.

                    AudioFile pickupSound = null;
                    pickupSound = new AudioFile("sfx/pickup.wav");
                    pickupSound.playFile();
                } else {
                    txtOutput.setText(Game.getInstance().getError("pickup"));
                }
            }
        }
    }

    @FXML
    private void handleBtnSetup(ActionEvent event) {
        if (phase > 1) {
            if (!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
                endGame();
                return;
            }
        }
        String itemName = lvInventory.getSelectionModel().getSelectedItem();
        if (Game.getInstance().setZipline(itemName)) {
            txtOutput.setText("Swoosh!\nYou assembled a zipline to the treehouse and used it. You should probably get inside before the burglars arrive.");
            txtCurrentLocation.setText("Current location: " + Game.getInstance().getCurrentRoomShortDescription()); // update Current location label with using the nextRoom String
            lvAvailableExits.setItems(Game.getInstance().getExitsObservableList()); // update available exits at new currentRoom
            lvItemsNearby.getItems().clear();// Clear listview items nearby
            
        }
        else {
            Game.getInstance().setTrap(itemName);
            //inventoryList.remove(itemName);
            lvItemsNearby.setItems(Game.getInstance().getItemsObservableList()); // update nearby items list with nearby items
        }
        lvInventory.setItems(Game.getInstance().getInventoryObservableList()); // update inventory UI element with items
        
    }

    @FXML
    private void handleBtnDrop(ActionEvent event) {
        if (phase > 1) {
            if (!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
                endGame();
                return;
            }
        }
        String itemName = lvInventory.getSelectionModel().getSelectedItem();
        Game.getInstance().dropItem(itemName);
        //inventoryList.remove(itemName);
        lvInventory.setItems(Game.getInstance().getInventoryObservableList());
        lvItemsNearby.setItems(Game.getInstance().getItemsObservableList());
        
        AudioFile dropSound = null;
        dropSound = new AudioFile("sfx/drop.wav");
        dropSound.playFile();
    }

    @FXML
    private void handleMenuItemRestart(ActionEvent event) {
        try {
            gameTheme.stopFile();
            
            Game.getInstance().restart();

            timeline.stop();
            
            Stage primaryStage = (Stage) ((Node) menuBar).getScene().getWindow();
            primaryStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });

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
            moveRoom(nextRoom);
        }
    }

    private void endGame() {
        try {
            gameTheme.stopFile();
            
            // close current window
            Stage primaryStage = (Stage) btnMove.getScene().getWindow();
            primaryStage.close();

            // open EndScreen window
            Parent root = FXMLLoader.load(getClass().getResource("EndScreen.fxml"));

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
    private void handleBtnPopupOk(ActionEvent event) {
        panePopup.setVisible(false);
        lvAvailableExits.setDisable(false);

        // start timer back up after clicking OK
        if (phase == 2) {
            startTimeMin = 1;
            startTimeSec = 0;
            timeline.playFromStart();
        } else if (phase == 3) {
            startTimeMin = 0;
            startTimeSec = 0;
            txtTimeLeft.setVisible(false);
            lblTimeLeft.setVisible(false);
        }

    }
}
