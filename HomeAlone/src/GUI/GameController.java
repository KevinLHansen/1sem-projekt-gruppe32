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
 * FXML Controller class for the main game window.
 *
 * @author Gruppe 32
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

    private int startTimeMin = 5;
    private int startTimeSec = 0;
    private Timeline timeline = new Timeline();
    private boolean isRunning;
    private int min = startTimeMin;
    private int phase = 1;

    private AudioFile gameTheme = null;

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
        panePopup.setVisible(true);
        imgviewPopup.setImage(new Image("/resources/img/phase1transition.gif"));
        txtPopup.setText("After having a conversation with the old man, Marley, in the church at Christmas Eve, Kevin McCallister rushes home to his house.\n"
                + "\n"
                + "He overheard earlier that day, that the two burglars, Marv and Harry, also known as \"The Wet Bandits\", are planning a burglary at his house.\n"
                + "\n"
                + "Kevin rushes through the front door, switching the lights on and locking the door behind him.\n");
        lvAvailableExits.setDisable(true);

        lvAvailableExits.setItems(Game.getInstance().getExitsObservableList()); // show available exits at currentRoom (foyer)
        txtTimeLeft.setText(String.format("%d:%02d", startTimeMin, startTimeSec));
        txtCurrentLocation.setText("Current location: " + Game.getInstance().getCurrentRoomShortDescription());
        txtObjective.setText(Game.getInstance().getObjective());

        gameTheme = new AudioFile("/resources/sfx/gameTheme.wav");
        gameTheme.playFile();
    }

    /**
     * Starts the timer functionality for phase 1 and 2.
     * This method is used to both animate the clock and to count down the timer
     * displayed on the clock.
     * It also runs the checks that is needed at the phase changes.
     */
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
                    isRunning = false;
                    // Start next phase here
                    phase = Game.getInstance().changePhase();
                    if (!Game.getInstance().checkStatus()) {
                        // YOU LOSE
                        endGame();
                    } else {
                        AudioFile popupSound = null;
                        popupSound = new AudioFile("/resources/sfx/popup.wav");
                        if (phase == 2) {
                            imgviewPopup.setImage(new Image("/resources/img/phase2transition.gif"));
                            txtPopup.setText("The Wet Bandits have arrived and are roaming the outside area! \nIf you exit the house, you will most certainly get caught.");
                            panePopup.setVisible(true);
                            lvAvailableExits.setDisable(true); // disable room list UI element to avoid dirty cheating
                            txtObjective.setText(Game.getInstance().getObjective()); // update objective UI element with new objective
                            popupSound.playFile();
                            gameTheme.pauseFile();
                            gameTheme.playFile();
                        }
                        if (phase == 3) {
                            imgviewPopup.setImage(new Image("/resources/img/phase3transition.gif"));
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
        isRunning = true;
    }

    /**
     * Event handler for button with the caption "MOVE".
     * This method handles the event that is triggered when the button is
     * clicked. It calls {@link #moveRoom(String nextRoom)} to move the player
     * to the selected room.
     * @param event 
     */
    @FXML
    private void handleBtnMove(ActionEvent event) {
        String nextRoom = lvAvailableExits.getSelectionModel().getSelectedItem(); // save selected item in String
        moveRoom(nextRoom);
    }

    /**
     * Calls the Game instance to move the player.
     * This method calls {@link HomeAlone.business.Game.goRoom(String command)} to move the player
     * to another room and updates the UI with relevant information.
     * @param nextRoom 
     */
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

    /**
     * Event handler for button with caption "EXAMINE".
     * This method updates the textarea with appropriate text, some of which is
     * from the Game object.
     * <p>
     * Uses the following methods in Game:
     * {@link HomeAlone.business.Game.getCurrentRoomInfo()}
     * {@link HomeAlone.business.Game.getTrapInfo()}
     * {@link HomeAlone.business.Game.getTrapString()}
     * {@link HomeAlone.business.Game.checkNeighbourRoom()}
     * {@link HomeAlone.business.Game.isPhoneHere()}
     * {@link HomeAlone.business.Game.getItemsObservableList()}
     * @param event 
     */
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

    /**
     * Event handler for button with caption "PICKUP".
     * This method the event that is triggered when the button is clicked. It
     * updates the listviews {@link #lvItemsNearby} and {@link #lvInventory}.
     * Uses the following methods in Game:
     * {@link HomeAlone.business.Game.pickupItem(String command)}
     * {@link HomeAlone.business.Game.getInventoryObservableList()}
     * {@link HomeAlone.business.Game.getItemsObservableList()}
     * {@link HomeAlone.business.Game.getError(String e)}
     * @param event 
     */
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
                if (Game.getInstance().getError("pickup").equals("")) {
                    lvInventory.setItems(Game.getInstance().getInventoryObservableList());

                    lvItemsNearby.setItems(Game.getInstance().getItemsObservableList());

                    AudioFile pickupSound = null;
                    pickupSound = new AudioFile("/resources/sfx/pickup.wav");
                    pickupSound.playFile();
                } else {
                    txtOutput.setText(Game.getInstance().getError("pickup"));
                }
            }
        }
    }

    /**
     * Event handler for button with caption "SETUP".
     * This method is used when setting up a trap. When a trap have been set
     * this method updates the appropriate UI elements.
     * Uses the following methods in Game:
     * {@link HomeAlone.business.Game.setZipLine(String item)}
     * {@link HomeAlone.business.Game.getCurrenRoomShortDescription()}
     * {@link HomeAlone.business.Game.getExitsObservableList()}
     * {@link HomeAlone.business.Game.getItemsObservableList()}
     * {@link HomeAlone.business.Game.setTrap(String trapName)}
     * {@link HomeAlone.business.Game.getInventoryObservableList()}
     * @param event 
     */
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

        } else {
            Game.getInstance().setTrap(itemName);
            lvItemsNearby.setItems(Game.getInstance().getItemsObservableList()); // update nearby items list with nearby items
        }
        lvInventory.setItems(Game.getInstance().getInventoryObservableList()); // update inventory UI element with items

    }

    /**
     * Event handler for button with caption "DROP".
     * This method is called when the player want to drop an items from their 
     * inventory.
     * Uses the following methods in Game:
     * {@link HomeAlone.business.Game.dropItem(String itemName)}
     * {@link HomeAlone.business.Game.getInventoryObservableList()}
     * {@link HomeAlone.business.Game.getItemsObservableList()}
     * @param event 
     */
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
        lvInventory.setItems(Game.getInstance().getInventoryObservableList());
        lvItemsNearby.setItems(Game.getInstance().getItemsObservableList());

        AudioFile dropSound = null;
        dropSound = new AudioFile("/resources/sfx/drop.wav");
        dropSound.playFile();
    }

    /**
     * Event handler for menu item with caption "Restart".
     * This method restarts the game by stopping the soundplayer, setting the 
     * Game instance to null, stopping the timer, closing the game window and
     * creating a new instance of the game window, running
     * {@link #initialize(URL url, ResourceBundle rb)} again.
     * @param event 
     */
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
            stage.getIcons().add(new Image("/resources/img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Event handler for menu item with caption "Show map".
     * This method creates a new window that shows a primitive layout of the 
     * house the player is navigating.
     * @param event 
     */
    @FXML
    private void handleMenuItemShowMap(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ShowMap.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("HOME ALONE™ - Map");
            stage.getIcons().add(new Image("/resources/img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Event handler for menu item with caption "Exit".
     * This method closes the game.
     * @param event 
     */
    @FXML
    private void handleMenuItemExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Event handler for menu item with caption "How to play".
     * This method opens a new window with a description of how the player can
     * use the graphical interface to play the game.
     * @param event 
     */
    @FXML
    private void handleMenuItemHTP(ActionEvent event) {
        try {
            if (phase < 3) {
                timeline.stop();
                isRunning = false;
            }
            Parent root = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (!isRunning && phase < 3) {
                        isRunning = true;
                        timeline.play();
                    }
                }
            });

            stage.setTitle("HOME ALONE™ - How to play");
            stage.getIcons().add(new Image("/resources/img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Event handler for menu item with caption "About".
     * This method opens a new window showing information regarding the people
     * that has developed this game.
     * @param event 
     */
    @FXML
    private void handleMenuItemAbout(ActionEvent event) {
        try {
            if (phase < 3) {
                timeline.stop();
                isRunning = false;
            }
            Parent root = root = FXMLLoader.load(getClass().getResource("About.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (!isRunning && phase < 3) {
                        isRunning = true;
                        timeline.play();
                    }
                }
            });

            stage.setTitle("HOME ALONE™ - About");
            stage.getIcons().add(new Image("/resources/img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Event handler for listview {@link #lvAvailableExits}.
     * This method is used to make it possible to double click on an exit in the
     * listview {@link #lvAvailableExits} to move to that room, instead of 
     * having to click button with caption "MOVE".
     * It uses {@link #moveRoom(String nextRoom)}.
     * @param event 
     */
    @FXML
    private void handleListItemClicked(MouseEvent event) {

        String nextRoom = lvAvailableExits.getSelectionModel().getSelectedItem(); // save selected item in String
        if (event.getClickCount() == 2) {
            moveRoom(nextRoom);
        }
    }

    /**
     * Shows the End Screen.
     * This method is called when the game is over. Either when the player wins
     * or when the player loses.
     */
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
            stage.getIcons().add(new Image("/resources/img/icon.png"));

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * event handler for button with caption "OK".
     * This method is used to close the messages that is shown everytime a phase
     * changes. It also handles the starting of the timer in phase 1 and 2.
     * @param event 
     */
    @FXML
    private void handleBtnPopupOk(ActionEvent event) {
        panePopup.setVisible(false);
        lvAvailableExits.setDisable(false);

        // start timer back up after clicking OK
        if (phase == 1) {
            startTimer();
            AudioFile startQuote = null;
            startQuote = new AudioFile("/resources/sfx/startQuote.wav");
            startQuote.playFile();
        } else if (phase == 2) {
            startTimeMin = 1;
            startTimeSec = 0;
            isRunning = true;
            timeline.playFromStart();
        } else if (phase == 3) {
            startTimeMin = 0;
            startTimeSec = 0;
            txtTimeLeft.setVisible(false);
            lblTimeLeft.setVisible(false);
        }

    }
}
