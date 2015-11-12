package game;

import display.Display;
import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    private static final int BACKGROUND_WIDTH = 1024;
    public static final int SPEED_FACTOR = 15;

    private String title;
    private int width, height;
    private String scoreCount = "0";

    public Thread thread;
    private boolean isRunning;

    private Display display;
    private BufferStrategy bs;
    private Graphics g;

    private SpriteSheet sh;
    private InputHandler ih;

    private Player player;
    private Rectangle bottomFloor;
    private Stamp firstStamp, secondStamp, thirdStamp;
    private Fire firstFire, secondFirst;
    private double backgroundX = 0;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isRunning = false;
    }
    //initializing all objects and variables
    private void init() {
        this.display = new Display(title, width, height);
        this.ih = new InputHandler(this.display);
        this.sh = new SpriteSheet(ImageLoader.load("/images/player.png"));

        this.player = new Player(100, 260, 125, 150, "Stamat");
        this.firstStamp = new Stamp(2000, 340, 80, 100, Assets.enemy);
        this.secondStamp = new Stamp(2500, 340, 80, 100, Assets.enemy);
        this.thirdStamp = new Stamp(2570, 340, 80, 100, Assets.enemy);
        this.firstFire = new Fire(3000, 300, 80, 120, Assets.treeEnemy);
        this.secondFirst = new Fire(3500, 300, 80, 120, Assets.treeEnemy);

        this.bottomFloor = new Rectangle(0, 420, this.width, 100);
        Assets.init();

    }

    private void tick(){
        //update the player and the objects
        this.player.tick();
        this.firstStamp.tick();
        this.firstFire.tick();
        this.secondStamp.tick();
        this.thirdStamp.tick();
        this.secondFirst.tick();


        //check all intersections
        if (this.player.intersectsWithFloor(bottomFloor)) {
            this.player.setGravity(0);
        }
        if (this.player.getBoundingBox().intersects(firstStamp.getEnemy())){
            isRunning = false;
        }
        if (this.player.getBoundingBox().intersects(secondStamp.getEnemy())){
            isRunning = false;
        }
        if (this.player.getBoundingBox().intersects(thirdStamp.getEnemy())){
            isRunning = false;
        }
        if (this.player.getBoundingBox().intersects(firstFire.getEnemy())){
            isRunning = false;
        }
        if (this.player.getBoundingBox().intersects(secondFirst.getEnemy())){
            isRunning = false;
        }

        //resetting the background when it run out of screen
        if (backgroundX <= -BACKGROUND_WIDTH){
            backgroundX += BACKGROUND_WIDTH;
        } else {
            backgroundX -= SPEED_FACTOR;
        }

        //calculating the score and updating it
        int scoreNumber = Integer.parseInt(scoreCount) + SPEED_FACTOR;
        scoreCount = ""+scoreNumber;
    }

    private void render() {
        this.bs = this.display.getCanvas().getBufferStrategy();

        if(this.bs == null){
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }
        this.g = this.bs.getDrawGraphics();
        this.g.clearRect(0, 0, this.width, this.height); //clearig thhe canvas
        //DRAWING

        //drawing the background and repainting when it run out of screen
        this.g.drawImage(Assets.background, (int) backgroundX, 0, null);
        if (this.backgroundX <= Launcher.WINDOW_WIDTH - BACKGROUND_WIDTH) {
            this.g.drawImage(Assets.background, (int) (BACKGROUND_WIDTH + backgroundX), 0, null);
        }

        //draw the player and the objects
        this.firstStamp.render(g);
        this.secondStamp.render(g);
        this.thirdStamp.render(g);
        this.firstFire.render(g);
        this.secondFirst.render(g);
        this.player.render(g);

        //show floor bounding box
//        this.g.drawRect(this.bottomFloor.x,
//                        this.bottomFloor.y,
//                        this.bottomFloor.width,
//                        this.bottomFloor.height);

        //show player bounding box
//        this.g.drawRect(this.player.getBoundingBox().x,
//                        this.player.getBoundingBox().y,
//                        this.player.getBoundingBox().width,
//                        this.player.getBoundingBox().height);

        //set the font and font size of the score meter
        g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        //updating the score
        if (isRunning){
            g.drawString("Score: ", 30, 30);
            g.drawString(scoreCount, 150, 30);;
        } else {
            g.drawString("Game Over! Your score is: ", 30, 30);
            g.drawString(scoreCount, 450, 30);;
        }
        //END OF DRAWING
        this.bs.show();
        this.g.dispose();
    }

    @Override
    public void run() {
        init();

        //calculating the fps
        int fps = 35;
        double ticksPerFrame = 1000000000.0 / fps;
        double delta = 0;
        long now;
        long lastTimeTicked = System.nanoTime();

        while (isRunning){
            now = System.nanoTime();
            delta+= (now - lastTimeTicked) / ticksPerFrame;
            lastTimeTicked = now;

            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        this.stop();
    }

    public synchronized void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public synchronized void stop() {
        if (this.isRunning) {
            try {
                this.isRunning = false;
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}