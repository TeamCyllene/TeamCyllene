package game;

import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;

public class Player {
    private int x, y, width, height, health, velocity;
    private double gravity, jumpingVelocity;



    private SpriteSheet sh;
    private String name;
    private Rectangle boundingBox;

    private final int w = 125;
    private final int h = 150;
    private int i = 0;
    private int j = 0;


    public static boolean
            isMovingUp = false,
            isMovingDown = false,
            isMovingLeft = false,
            isMovingRight = false,
            hasJumped = false,
            isIdle = true;


    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public Player(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;


        this.gravity = 5;
        this.velocity = 10;
        this.jumpingVelocity = 20;
        this.health = 100;
        this.sh = new SpriteSheet(ImageLoader.load("/images/player.png"));

        this.boundingBox = new Rectangle(this.x + 20,this.y + 10,this.width - 20, this.height - 20);

        this.hasJumped = false;
    }

    public void tick() {

        if(hasJumped){
            this.y -= this.jumpingVelocity;
            this.jumpingVelocity -= 1.5;
        }

        if (this.jumpingVelocity <= -20){
            hasJumped = false;
            this.jumpingVelocity = 20;
            gravity = 5;
            System.out.println("Landed");
        }

//        if (isMovingDown) {
//            this.y += this.velocity;
//        } else if (isMovingUp) {
//            this.y -= this.velocity;
//        }

        if (isMovingRight) {
            this.x += this.velocity;
        } else if (isMovingLeft) {
            this.x -= this.velocity;
        }
            i++;
            if (i >= 7) {
                i = 0;
                j++;
            }
            if (j >= 4) {
                j = 0;
            }

        this.boundingBox.setBounds(this.x+20,
                this.y+10,
                this.width-20,
                this.height-20);
        this.y+=this.gravity;
    }

    public void render(Graphics g) {
        g.drawImage(this.sh.crop(0 + this.i * this.w, 0 + this.j  *this.h, this.w, this.h), 100, this.y, null);//static player

// if(isIdle){
//            g.drawImage(this.sh.crop(0 + 2 * this.w, 0 + 1 * this.h, this.w, this.h), this.x, this.y, null);
//        } else {
//            g.drawImage(this.sh.crop(0 + this.i * this.w, 0 + this.j * this.h, this.w, this.h), this.x, this.y, null);
//        }
        //allowing the player to be controlled with keyboard
    }

    public boolean intersects (Rectangle rect){
        //return this.boundingBox.contains(rect) || rect.contains(this.boundingBox);
        return rect.x >= this.boundingBox.x &&
                rect.x <= this.boundingBox.x + this.boundingBox.width ||
                rect.y >= this.boundingBox.y &&
                rect.y <= this.boundingBox.y + this.boundingBox.height;
    }
}
