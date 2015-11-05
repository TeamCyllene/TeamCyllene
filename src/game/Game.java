package game;

import display.Display;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{
    private  String title;
    private int width, height;

    public Thread thread;
    private boolean isRunning;

    private Display display;
    private BufferStrategy bs;
    private Graphics g;
    private SpriteSheet sh;

    private final int w = 125;
    private final int h = 150;
    private int i = 0;
    private int j = 0;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isRunning = false;

    }

    private void init() {
        this.display = new Display(title, width, height);
        this.sh = new SpriteSheet(ImageLoader.load("/images/player.png"));
    }

    private void tick(){
            i++;
            if (i >= 7) {
                i = 0;
                j++;
            }
            if (j >= 4) {
                j = 0;
            }
    }

    private void render() {
        this.bs = this.display.getCanvas().getBufferStrategy();

        if(this.bs == null){
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }
        this.g = this.bs.getDrawGraphics();
        this.g.clearRect(0,0,this.width,this.height);
        //DRAWING
        this.g.drawImage(ImageLoader.load("/images/bg.png"), 0, 0, null);
        this.g.drawImage(this.sh.crop(0+this.i*this.w, 0+this.j*this.h, this.w, this.h), 100, 300, null);

        //END OF DRAWING
        this.bs.show();
        this.g.dispose();
    }

    @Override
    public void run() {
        init();

        while (isRunning){
            tick();
            render();
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
