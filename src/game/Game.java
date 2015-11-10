package game;

import display.Display;
import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.security.PrivateKey;

public class Game implements Runnable{

    private static final int BACKGROUND_WIDTH = 1024;
    public static final int SPEED_FACTOR = 15;

    private String title;
    private int width, height;

    public Thread thread;
    private boolean isRunning;

    private Display display;
    private BufferStrategy bs;
    private Graphics g;

    private SpriteSheet sh;
    private InputHandler ih;


    private Player player;
    private Rectangle bottomFloor;
    private Enemy enemy;
    private Enemy secondEnemy;
    private Enemy thirdEnemy;
    private double backgroundX = 0;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isRunning = false;
    }

    private void init() {
        this.display = new Display(title, width, height);
        this.ih = new InputHandler(this.display);
        this.sh = new SpriteSheet(ImageLoader.load("/images/player.png"));

        this.player = new Player(100, 260, 125, 150, "Stamat");
        this.enemy = new Enemy(2000, 320, 80, 100, Assets.enemy);
        this.secondEnemy = new Enemy(2500, 320, 80, 100, Assets.enemy);
        this.thirdEnemy = new Enemy(2570, 320, 80, 100, Assets.enemy);
        this.bottomFloor = new Rectangle(0, 420, this.width, 100);
        Assets.init();

    }

    private void tick(){
        this.player.tick();
        this.enemy.tick();
        this.secondEnemy.tick();
        this.thirdEnemy.tick();
        if (this.player.intersectsWithFloor(bottomFloor)) {
            this.player.setGravity(0);
        }
        if (this.player.getBoundingBox().intersects(enemy.getEnemy())){
            System.out.println("ENEMYYYYY!11!");
            isRunning = false;
        }
        if (this.player.getBoundingBox().intersects(secondEnemy.getEnemy())){
            System.out.println("ENEMYYYYY!11!");
            isRunning = false;
        }
        if (this.player.getBoundingBox().intersects(thirdEnemy.getEnemy())){
            System.out.println("ENEMYYYYY!11!");
            isRunning = false;
        }
        if (backgroundX <= -BACKGROUND_WIDTH){
            backgroundX += BACKGROUND_WIDTH;
        } else {
            backgroundX -= SPEED_FACTOR;
        }
    }

    private void render() {
        this.bs = this.display.getCanvas().getBufferStrategy();

        if(this.bs == null){
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }
        this.g = this.bs.getDrawGraphics();
        this.g.clearRect(0,0,this.width,this.height); //clearig thhe canvas
        //DRAWING

        this.g.drawImage(Assets.background, (int) backgroundX, 0, null);
        if (this.backgroundX <= Launcher.WINDOW_WIDTH - BACKGROUND_WIDTH) {
            this.g.drawImage(Assets.background, (int) (BACKGROUND_WIDTH + backgroundX), 0, null);
        }

        //floor bounding box
        this.player.render(g);
        this.enemy.render(g);
        this.secondEnemy.render(g);
        this.thirdEnemy.render(g);
        this.g.drawRect(this.bottomFloor.x,
                        this.bottomFloor.y,
                        this.bottomFloor.width,
                        this.bottomFloor.height);

        //player bounding box
        this.g.drawRect(this.player.getBoundingBox().x,
                        this.player.getBoundingBox().y,
                        this.player.getBoundingBox().width,
                        this.player.getBoundingBox().height);

        //END OF DRAWING
        this.bs.show();
        this.g.dispose();
    }

    @Override
    public void run() {
        init();

        //calculating the fps
        int fps = 40;
        double ticksPerFrame = 1000000000.0/fps;
        double delta = 0;
        long now;
        long lastTimeTicked = System.nanoTime();

        while (isRunning){
            now = System.nanoTime();
            delta+= (now - lastTimeTicked) / ticksPerFrame;
            lastTimeTicked = now;

            if (delta>=1) {
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
