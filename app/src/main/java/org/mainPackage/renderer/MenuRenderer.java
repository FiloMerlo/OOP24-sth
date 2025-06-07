package renderer;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class MenuRenderer implements Renderer {

    private Font titleFont;
    private Font instructionFont;
    private Font logoFont;
    private BufferedImage background;

    private int bgX = 0;
    private boolean showPressEnter = true;
    private long startTime;
    private long lastBlinkTime = 0;

    private static final int TITLE_FONT_SIZE = 72;
    private static final int INSTRUCTION_FONT_SIZE = 24;
    private static final int LOGO_FONT_SIZE = 80;
    private static final float LOGO_BREATH_AMPLITUDE = 0.05f;
    private static final float LOGO_BREATH_SPEED = 2f * (float) Math.PI;
    private static final long BLINK_INTERVAL_MS = 500;
    private static final String FONT_PATH = "/res/fonts/NiseSegaSonic.TTF";
    private static final String BACKGROUND_PATH = "/res/images/background.jpg";

    public MenuRenderer() {
        try (InputStream is = getClass().getResourceAsStream(FONT_PATH)) {
            if (is == null) throw new IOException("Font file not found at " + FONT_PATH);

            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            titleFont = customFont.deriveFont(Font.BOLD, (float) TITLE_FONT_SIZE);
            instructionFont = customFont.deriveFont(Font.PLAIN, (float) INSTRUCTION_FONT_SIZE);
            logoFont = customFont.deriveFont(Font.BOLD, (float) LOGO_FONT_SIZE);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
            titleFont = new Font("Arial", Font.BOLD, TITLE_FONT_SIZE);
            instructionFont = new Font("Arial", Font.PLAIN, INSTRUCTION_FONT_SIZE);
            logoFont = new Font("Arial", Font.BOLD, LOGO_FONT_SIZE);
        }

        try (InputStream is = getClass().getResourceAsStream(BACKGROUND_PATH)) {
            if (is == null) throw new IOException("Background image not found at " + BACKGROUND_PATH);
            background = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            
        }

        startTime = System.nanoTime();
    }

    public void updateAnimation(long currentTimeMillis, long deltaTimeNanos) {
        bgX -= 1;
        if (background != null && bgX <= -background.getWidth()) {
            bgX = 0;
        } else if (background == null && bgX <= -800) { 
            bgX = 0;
        }

        if (currentTimeMillis - lastBlinkTime > BLINK_INTERVAL_MS) {
            showPressEnter = !showPressEnter;
            lastBlinkTime = currentTimeMillis;
        }
    }

    @Override
    public void render(Graphics2D g2d, int width, int height) { // Modifica qui
        if (background != null) {
            g2d.drawImage(background, bgX, 0, width, height, null);
            g2d.drawImage(background, bgX + width, 0, width, height, null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);
        }

        g2d.setFont(titleFont);
        g2d.setColor(Color.YELLOW);
        String title = "SONIC";
        FontMetrics fmTitle = g2d.getFontMetrics();
        int titleWidth = fmTitle.stringWidth(title);
        int titleX = (width - titleWidth) / 2;
        int titleY = height / 5 + fmTitle.getAscent();
        g2d.drawString(title, titleX, titleY);

        String logoText = "the Hedgehog";
        g2d.setFont(logoFont);
        FontMetrics fmLogo = g2d.getFontMetrics();

        int logoCenterX = width / 2;
        int logoCenterY = titleY + 75;

        double seconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
        float scale = 1.0f + LOGO_BREATH_AMPLITUDE * (float) Math.sin(seconds * LOGO_BREATH_SPEED);

        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(logoCenterX, logoCenterY);
        g2d.scale(scale, scale);
        g2d.setColor(Color.WHITE);
        g2d.drawString(logoText, -fmLogo.stringWidth(logoText) / 2, fmLogo.getAscent() / 2 - fmLogo.getDescent());
        g2d.setTransform(oldTransform);

        if (showPressEnter) {
            g2d.setFont(instructionFont);
            g2d.setColor(Color.BLACK);
            String msg = "Press ENTER to start";
            FontMetrics fmInst = g2d.getFontMetrics();
            int msgWidth = fmInst.stringWidth(msg);
            g2d.drawString(msg, (width - msgWidth) / 2, (2 * height) / 3 + fmInst.getAscent());
        }
    }
}