package util;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class SpriteLoader {
    private BufferedImage spriteSheet;

    public SpriteLoader(String path) throws Exception {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) throw new IllegalArgumentException("Sprite sheet not found: " + path);
        spriteSheet = ImageIO.read(is);
    }

    public BufferedImage[] getFramesByPixels(int startX, int startY, int count, int frameWidth, int frameHeight) {
        BufferedImage[] frames = new BufferedImage[count];
        for (int i = 0; i < count; i++) {
            frames[i] = spriteSheet.getSubimage(startX + i * frameWidth, startY, frameWidth, frameHeight);
        }
        return frames;
    }
}