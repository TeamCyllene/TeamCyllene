package gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage player, enemy;
    public static BufferedImage background;

    public static void init() {
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.load("/images/player.png"));
        background = ImageLoader.load("/images/bg.png");
        player = spriteSheet.crop(0, 0, 125, 150);
    }
}
