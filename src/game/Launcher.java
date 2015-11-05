package game;

import display.Display;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("ASD", 800, 512);
        game.start();
    }
}
