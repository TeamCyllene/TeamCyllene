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

    private int i = 0;
    private int j = 0;

    public static boolean
            hasDropped = false,
            hasJumped = false;

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
        this.jumpingVelocity = 22;
        this.health = 100;
        this.sh = new SpriteSheet(ImageLoader.load("/images/player.png"));

        this.boundingBox = new Rectangle(
                this.x + 15,
                this.y + 10,
                this.width - 35,
                this.height - 20);

        this.hasJumped = false;
        this.hasDropped = false;
    }

    public void tick() {

        //jumping algorithm
        if(hasJumped){
            this.y -= this.jumpingVelocity;
            this.jumpingVelocity -= 1.5;
        }
//        if (hasDropped && hasJumped) {
//            this.y = 260;
//            hasDropped = false;
//            this.jumpingVelocity = -50;
//        }

        //bug fix while jumping
        if (this.jumpingVelocity <= -22){
            hasJumped = false;
            this.jumpingVelocity = 22;
            gravity = 5;
        }

        //bug fix if the player drops below the ground
        if (this.y > 300){
            this.y = 260;
        }

        //drawing the spritesheet
        i++;
        if (i >= 7) {
            i = 0;
            j++;
        }
        if (j >= 4) {
            j = 0;
        }

        //setting hte bounding box of the player
        this.boundingBox.setBounds(
                this.x + 40,
                this.y + 10,
                this.width - 70,
                this.height - 25);
                this.y+=this.gravity;
    }

    //cropping the sprite sheet
    public void render(Graphics g) {
        g.drawImage(this.sh.crop(0 + this.i * this.width, 0 + this.j  *this.height, this.width, this.height), 100, this.y, null);//static player
    }

    //check if the player intersects with the floor
    public boolean intersectsWithFloor(Rectangle floor){
        //return this.boundingBox.contains(rect) || rect.contains(this.boundingBox);
        return floor.x >= this.boundingBox.x &&
                floor.x <= this.boundingBox.x + this.boundingBox.width ||
                floor.y >= this.boundingBox.y &&
                        floor.y <= this.boundingBox.y + this.boundingBox.height;
    }
}
