package org.mainPackage.core;

import javax.swing.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.*;
import java.awt.event.KeyAdapter;    
import java.awt.event.KeyEvent;      
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;

import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.engine.systems.InputManager;
import org.mainPackage.util.SizeView;


/**
 * The {@code GamePanel} class represents the main drawing surface of the game.
 * It extends {@link JPanel} and acts as the central component where the game 
 * is rendered and input events are captured.
 *
 * <p>Main responsibilities:
 * <ul>
 *   <li>Defines the default size of the game window (width and height).</li>
 *   <li>Delegates rendering to the {@link GameStateManager}.</li>
 *   <li>Handles input events (keyboard and mouse), forwarding them either to 
 *       the {@link GameStateManager} (e.g., for menu interactions) or to the 
 *       {@link InputManager} (for gameplay).</li>
 *   <li>Provides the current size of the panel through the {@link SizeView} interface.</li>
 * </ul>
 *
 * <p>Double buffering and rendering hints are enabled to improve drawing quality 
 * and reduce flickering during fast updates.</p>
 */

public class GamePanel extends JPanel implements SizeView {
    // --- Default dimensions for the game panel ---
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    private GameStateManager gameStateManager;
    
    /**
     * Constructs a new {@code GamePanel} with the specified {@link GameStateManager}.
     * 
     * <p>This constructor sets up the panel properties, enables double buffering,
     * and registers listeners for keyboard and mouse input.
     *
     * <p>Inputs are managed through the input manager and the gamestatemanager. 
     * The latter handles the menu, while the rest is delegated directly to the InputManager. 
     * This allows for a centralized input system that can be easily extended for different states.</p>
     * 
     * @param gameStateManager the state manager controlling the game flow
     */
    

    public GamePanel(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)); 
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameStateManager.getEnumState() == GameStateManager.State.MENU) {
                    gameStateManager.keyPressed(e); // --- GameStateManager handles the menu input ---
                } else {                    
                    InputManager.getInstance().keyPressed(e); // InputManager handles the input for gameplay
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (gameStateManager.getEnumState() == GameStateManager.State.MENU) {
                    gameStateManager.keyReleased(e);
                } else {
                    InputManager.getInstance().keyReleased(e); 
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameStateManager.mousePressed(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
        gameStateManager.mouseMoved(e);
    }
});
    }

    /**
     * Sets the {@link GameStateManager} for this panel.
     * 
     * <p>This method allows the game panel to be associated with a specific game state manager,
     * enabling it to delegate rendering and input handling to the appropriate state.
     *
     * @param gameStateManager the game state manager to set
     */

    public void setGameStateManager(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
    }

    /**
     * Returns the current width of the game panel.
     * 
     * <p>This method is part of the {@link SizeView} interface, providing a way to retrieve
     * the dimensions of the panel for layout and rendering purposes.
     * 
     * @return the width of the game panel
     */

    @Override
    public int getSizeWidth() {
        return getSize().width;
    }
    
    /**
     * Returns the current height of the game panel.
     *
     * <p>This method is part of the {@link SizeView} interface, providing a way to retrieve
     * the dimensions of the panel for layout and rendering purposes.
     *
     * @return the height of the game panel
     */

    @Override
    public int getSizeHeight() {
        return getSize().height;
    }
   

    /**
     * Handles the rendering of the game.
     * 
     * <p>This method applies anti-aliasing hints for smoother graphics
     * and delegates the drawing logic to the {@link GameStateManager}.</p>
     * 
     * <p>Uses {@code g.create()} to make a temporary Graphics2D copy. 
     * {@code g2d.dispose()} is called after drawing to free resources and avoid memory leaks.</p>
     *
     * @param g the {@link Graphics} context used for drawing
     * 
     */
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // --- Option for anti-aliasing ---
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // --- Rendering the game state ---
        gameStateManager.draw(g2d);
       
        g2d.dispose();
    }
}
