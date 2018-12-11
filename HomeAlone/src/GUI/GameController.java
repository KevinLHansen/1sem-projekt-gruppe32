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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvAvailableExits.setItems(Game.getInstance().getExitsObservableList()); // show available exits at currentRoom (foyer)
        txtTimeLeft.setText(String.format("%d:%02d", startTimeMin, startTimeSec));
        txtCurrentLocation.setText("Current location: " + Game.getInstance().getCurrentRoomShortDescription());
        startTimer();
    }

    private void startTimer() {
        KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                startTimeSec--;
                boolean isSecondsZero = startTimeSec == 0;
                boolean isSecondsLessThanZero = startTimeSec < 0;
                boolean timeToChangePhase = startTimeSec == 0 && startTimeMin == 0;

                if (isSecondsZero) {
                    startTimeMin--;
                    startTimeSec = 60;
                }
                if (isSecondsLessThanZero) {
                    startTimeMin--;
                    startTimeSec = 59;
                }
                if (timeToChangePhase) {
                    timeline.stop();
                    // Start next phase here
                    phase = Game.getInstance().changePhase();
                    if(!Game.getInstance().checkStatus()) {
                        // YOU LOSE
                        txtOutput.setText("YOU LOSE!!");
                    } else {
                        if(phase == 2) {
                            startTimeMin = 1;
                            startTimeSec = 0;
                            timeline.playFromStart();
                        }
                        if(phase == 3) {
                            startTimeMin = 0;
                            startTimeSec = 0;
                            txtTimeLeft.setVisible(false);
                            lblTimeLeft.setVisible(false);
                            txtOutput.appendText("Phase 3: Escape the house. The game is now turn based instead of timed, enjoy the variety.");
                        }
                        txtObjective.setText(Game.getInstance().getObjective());
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

        Game.getInstance().goRoom(nextRoom);

        if(phase > 1) {
            if(!Game.getInstance().checkStatus()){
                // LOSE
                txtOutput.setText("YOU LOSE!!");
                return;
            }
        }
        txtCurrentLocation.setText("Current location: " + Game.getInstance().getCurrentRoomShortDescription()); // update Current location label with using the nextRoom String
        lvAvailableExits.setItems(Game.getInstance().getExitsObservableList()); // update available exits at new currentRoom
        txtOutput.setText(""); // clear output box
        if(phase == 3) {
            String s = Game.getInstance().checkNeighbourRoom();
            txtOutput.appendText(s);
        }

    }

    @FXML
    private void handleBtnExamine(ActionEvent event) {
        if(phase > 1) {
            if(!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
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
        if(phase == 3) {
            String s = Game.getInstance().checkNeighbourRoom();
            txtOutput.appendText(s);
        }
        lvItemsNearby.setItems(Game.getInstance().getItemsObservableList()); // update nearby items list with nearby items


    }

    @FXML
    private void handleBtnPickup(ActionEvent event) {
        if(phase > 1) {
            if(!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
                return;
            }
        }
        String itemName = lvItemsNearby.getSelectionModel().getSelectedItem();
        if (itemName != null) {
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

    @FXML
    private void handleBtnSetup(ActionEvent event) {
        if(phase > 1) {
            if(!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
                return;
            }
        }
        String itemName = lvInventory.getSelectionModel().getSelectedItem();
        Game.getInstance().setTrap(itemName);
        //inventoryList.remove(itemName);
        lvInventory.setItems(Game.getInstance().getInventoryObservableList());
    }

    @FXML
    private void handleBtnDrop(ActionEvent event) {
        if(phase > 1) {
            if(!Game.getInstance().checkStatus()) {
                txtOutput.setText("YOU LOSE!!");
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
            Game.getInstance().restart();
            
            Stage primaryStage = (Stage) ((Node) menuBar).getScene().getWindow();
            primaryStage.close();

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
            Game.getInstance().goRoom(nextRoom);
            if(phase > 1) {
                if(!Game.getInstance().checkStatus()){
                    // LOSE
                    txtOutput.setText("YOU LOSE!!");
                    return;
                }
            }
            txtCurrentLocation.setText("Current location: " + Game.getInstance().getCurrentRoomShortDescription()); // update Current location label with using the nextRoom String
            Tooltip loc = new Tooltip();
            loc.setText(Game.getInstance().getCurrentRoomShortDescription());
            txtCurrentLocation.setTooltip(loc);
            lvAvailableExits.setItems(Game.getInstance().getExitsObservableList()); // update available exits at new currentRoom
            txtOutput.setText(""); // clear output box
            if(phase == 3) {
                String s = Game.getInstance().checkNeighbourRoom();
                txtOutput.appendText(s);
            }
        }
    }
    private void endGame() {
        try {
            // close current window
            Stage primaryStage = (Stage)btnMove.getScene().getWindow();
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
} 
