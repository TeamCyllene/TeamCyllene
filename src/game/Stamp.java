package game;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Stamp {
    private int x, y, width, height;

    private Rectangle enemyBounds;
    private BufferedImage enemyImage;

    public Stamp(int x, int y, int width, int height, BufferedImage enemyImage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enemyImage = enemyImage;

        this.enemyBounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getEnemy() {
        return enemyBounds;
    }

    public void tick(){
        //moving the stamp objects based on the speed of the background
        this.x-=Game.SPEED_FACTOR;
        this.enemyBounds.setBounds(
                this.x,
                this.y,
                this.width,
                this.height);

        //set the stamp on starting position when he run out of screen
        if (this.x+this.width <= 0){
            this.x += 3000;
        }

        //setting the bounding box of the stamp
        this.enemyBounds.setBounds(
                this.x + 25,
                this.y + 20,
                this.width - 35,
                this.height - 20
        );
    }
    public void render(Graphics g){
        //drawing the stamp
        g.drawImage(Assets.enemy, this.x, this.y, null);
        //Showing the bounding box (test purposes)
        //g.drawRect(enemyBounds.x, enemyBounds.y, enemyBounds.width, enemyBounds.height);
    }
}
