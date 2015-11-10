package gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage player, enemy, treeEnemy;
    public static BufferedImage background;

    public static void init() {
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.load("/images/player.png"));
        background = ImageLoader.load("/images/bg.png");
        player = spriteSheet.crop(0, 0, 125, 150);
        enemy = ImageLoader.load("/images/grass.png");
        treeEnemy = ImageLoader.load("/images/tree-test.png");
    }
}
