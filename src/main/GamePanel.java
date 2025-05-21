package main;


import java.awt.*;
import java.awt.image.BufferStrategy;

public class GamePanel extends Canvas implements Runnable {
    public static final int SCREEN_W = 800;  //implementare lo schermo responsive DEX
    public static final int SCREEN_H = 600;

    private Thread thread;
    private boolean running = false;

    public GameState state = GameState.HOME;

    private final Rectangle exitButton = new Rectangle(SCREEN_W/2 - 75, SCREEN_H/2 + 50, 150, 40); //se la finestra viene ridimensionata va calcolato il render in base ai parametri dinamici

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_W, SCREEN_H));
        addKeyListener(new InputHandler(this));
        addMouseListener(new ExitHandler(this));
        setFocusable(true);
    }

    public Rectangle getExitButton() {
        return exitButton;
    }

    public synchronized void startGameLoop() {
        if (running) return;
        running = true;
        thread = new Thread(this, "GameLoop");
        thread.start();
    }

    public synchronized void stopGameLoop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void run() {
        final long nsPerFrame = 1_000_000_000L / 60;   // 60 fps medi 
        long last = System.nanoTime();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - last) / (double) nsPerFrame;
            last = now;

            if (delta >= 1) {
                render();
                delta--;
            }
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics(); // triple buffer implementato tramite la classe buffer strategy  
             
        switch (state) {
            case HOME -> {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, SCREEN_W, SCREEN_H);
                g.setColor(Color.CYAN);
                g.setFont(new Font("Verdana", Font.BOLD, 36));
                TextPosition.drawCentered(g, "SONIC GAME", SCREEN_W, SCREEN_H/2 - 50);
                g.setFont(new Font("Verdana", Font.PLAIN, 24));
                TextPosition.drawCentered(g, "Press ENTER to Start", SCREEN_W, SCREEN_H/2 + 10);
            }
            case PLAYING -> {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, SCREEN_W, SCREEN_H);
                g.setColor(Color.GREEN);
                g.setFont(new Font("Verdana", Font.BOLD, 24));
                TextPosition.drawCentered(g, "GAME PLAYING…", SCREEN_W, SCREEN_H/2);
            }
            case PAUSED -> {
                g.setColor(new Color(0,0,0,150));
                g.fillRect(0, 0, SCREEN_W, SCREEN_H);
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Verdana", Font.BOLD, 36));
                TextPosition.drawCentered(g, "PAUSED", SCREEN_W, SCREEN_H/2 - 20);
                g.setFont(new Font("Verdana", Font.PLAIN, 24));
                TextPosition.drawCentered(g, "Press P to Resume", SCREEN_W, SCREEN_H/2 + 20);
                g.setColor(Color.RED);
                g.fill(exitButton);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Verdana", Font.BOLD, 20));
                TextPosition.drawCenteredRectText(g, "Exit", exitButton);
            }
        }

        g.dispose();
        bs.show();
    }
}
