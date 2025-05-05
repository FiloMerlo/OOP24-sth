package util;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SpriteLoader {
    private BufferedImage spriteSheet;

    public SpriteLoader(String path) throws IOException {
        spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
    }

    public BufferedImage getFrame(int col, int row, int frameWidth, int frameHeight) {
        return spriteSheet.getSubimage(col * frameWidth, row * frameHeight, frameWidth, frameHeight);
    }

    public BufferedImage[] getFrames(int row, int totalFrames, int frameWidth, int frameHeight) {
        BufferedImage[] frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = getFrame(i, row, frameWidth, frameHeight);
        }
        return frames;
    }
}
