package HomeAlone;

import HomeAlone.business.Game;
import HomeAlone.textUI.PresentationControl;

public class HomeAlone {

    public static void main(String[] args) {

        //Game game = new Game();

        //game.play();
        
        PresentationControl pc = new PresentationControl();
        pc.startPlaying();

    }

}
