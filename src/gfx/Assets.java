package gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage player, enemy;

    public static void init() {
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.load("/images/player.png"));
        player = spriteSheet.crop(0, 0, 125, 150);
    }
}
