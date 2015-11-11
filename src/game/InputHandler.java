package game;

import display.Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public InputHandler(Display display) {
        display.getCanvas().addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyCode();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP){
            Player.hasJumped = true;
        }

        if (keyCode == KeyEvent.VK_DOWN){
//            if (Player.hasJumped) {
//                Player.hasDropped = true;
//            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
