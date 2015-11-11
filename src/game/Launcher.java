package game;

import display.Display;

public class Launcher {
    public static final int WINDOW_WIDTH = 950;
    public static final int WINDOW_HEIGHT = 512;
    public static void main(String[] args) {

        Game game = new Game("The Runner", WINDOW_WIDTH, WINDOW_HEIGHT);
        game.start();
    }
}
