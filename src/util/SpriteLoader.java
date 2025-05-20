package util;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SpriteLoader {
    private BufferedImage spriteSheet;

    public SpriteLoader(String path) throws IOException {
        spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
    }

    public BufferedImage[] getFramesByPixels(int startX, int startY, int totalFrames, int frameWidth, int frameHeight) {
        BufferedImage[] frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = spriteSheet.getSubimage(startX + i * frameWidth, startY, frameWidth, frameHeight);
        }
        return frames;
    }
}
