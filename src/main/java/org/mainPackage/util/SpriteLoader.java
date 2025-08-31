package org.mainPackage.util;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.util.stream.IntStream;
/**
 * Utility class to load and divide spritesheets into individual frames.
 */
public class SpriteLoader {

    /** The loaded spritesheet image */
    private final BufferedImage spriteSheet;

    /**
     * Builds the SpriteLoader and loads the spritesheet from the given resource path.
     *
     * @param path the path to the sprite sheet resource
     * @throws Exception if the resource cannot be loaded or is invalid
     */
    public SpriteLoader(String path) throws Exception {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IllegalArgumentException("Spritesheet not found: " + path);
        }
        spriteSheet = ImageIO.read(is);
    }

/**
 * Extracts a series of frames from the loaded sprite sheet
 *
 * @param startX the starting X pixel coordinate
 * @param startY the starting Y pixel coordinate
 * @param count the number of frames to extract
 * @param frameWidth the width of each frame in pixels
 * @param frameHeight the height of each frame in pixels
 * @return an array of images
 */
    public BufferedImage[] getFramesByPixels(int startX, int startY, int count, int frameWidth, int frameHeight) {
        return IntStream.range(0, count)
                .mapToObj(i -> spriteSheet.getSubimage(
                    startX + i * frameWidth,
                    startY,
                    frameWidth,
                    frameHeight
                ))
                .toArray(BufferedImage[]::new);
    }

}
