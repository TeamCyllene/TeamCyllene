package game;

import display.Display;
import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    private static final int BACKGROUND_WIDTH = 1024;
    private static final int SPEED_FACTOR = 15;

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
        Assets.init();

        this.player = new Player(100, 260, 125, 150, "Stamat");
        this.bottomFloor = new Rectangle(0, 420, this.width, 100);

    }

    private void tick(){
        this.player.tick();

        if (this.player.intersects(bottomFloor)) {
            this.player.setGravity(0);
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

        this.g.drawImage(ImageLoader.load("/images/bg.png"), (int) backgroundX, 0, null);
        if (this.backgroundX <= Launcher.WINDOW_WIDTH - BACKGROUND_WIDTH) {
            this.g.drawImage(ImageLoader.load("/images/bg.png"), (int) (BACKGROUND_WIDTH + backgroundX), 0, null);
        }
        this.player.render(g);
        this.g.drawRect(this.bottomFloor.x,
                        this.bottomFloor.y,
                        this.bottomFloor.width,
                        this.bottomFloor.height);


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

        int fps = 500000000;
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
