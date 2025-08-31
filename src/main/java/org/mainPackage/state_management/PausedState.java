package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent; 

import org.mainPackage.util.SizeView;
import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.renderer.PausedRenderer;

/**
 * PausedState represents the state of the game when it is paused.
 * <p>
 * It displays a pause menu with an exit button and background.
 * </p>
 * 
 * <p>
 * The {@link PausedState} extends {@link GameState}, reusing 
 * the input-handling structure and ensuring consistency with 
 * other states in the system. Rendering tasks are delegated 
 * to {@link PausedRenderer}, which draws the background and 
 * the button text.
 * </p>
 * 
 */

public class PausedState extends GameState {
    
    private PausedRenderer pausedRenderer;

    /** Bounding box for the {@code EXIT} button. */
    private Rectangle exitButtonBounds;
    
    // --- Constants for the EXIT button ---
    private static final Color BUTTON_COLOR_DEFAULT = Color.YELLOW;
    private static final Color BUTTON_COLOR_HOVER = Color.RED;    
    private static final Color BUTTON_TEXT_COLOR = Color.BLACK;
    
    /** Tracks whether the mouse is hovering over the {@code EXIT} button */
    private boolean hoveringExit = false; 
    
    /**
     * Constructs a paused state.
     *
     * @param gameStateManager the manager responsible for controlling state transitions
     * @param sizeView utility for handling screen dimensions and scaling
     */

    public PausedState(GameStateManager gameStateManager, SizeView sizeView) {
        super(gameStateManager, sizeView);
        pausedRenderer = new PausedRenderer();
    }
    
    /**
     * Updates the paused state logic.
     * <p>
     * Currently, no dynamic updates are required for the pause screen,
     * but the method is left for consistency and possible future use.
     * </p>
     */
    
    @Override
    public void update() {}
    
    /**
     * Draws the paused screen and the {@code EXIT} button.
     *
     * @param g the {@link Graphics} context used for rendering
     */

    @Override
    public void draw(Graphics g) { 
        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = sizeView.getSizeWidth(); 
        int panelHeight = sizeView.getSizeHeight();
        pausedRenderer.render(g2d, panelWidth, panelHeight);
        drawExitButton(g2d, panelWidth, panelHeight);
    }

    /**
     * Draws the {@code EXIT} button in the center of the screen.
     * <p>
     * The button changes color when hovered and displays centered text
     * using a font provided by {@link PausedRenderer}.
     * </p>
     *
     * @param g2d the {@link Graphics2D} context
     * @param panelWidth current width of the screen
     * @param panelHeight current height of the screen
     */
    
    private void drawExitButton(Graphics2D g2d, int panelWidth, int panelHeight) {
        // --- Design responsible for the EXIT button ---
        int buttonWidth = (int) (panelWidth * 0.2);
        int buttonHeight = (int) (panelHeight * 0.1);
        int buttonX = (panelWidth - buttonWidth) / 2;
        int buttonY = (int) (panelHeight * 0.7); 
       
        exitButtonBounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight); 

        // --- Fill the EXIT button with color ---
        g2d.setColor(hoveringExit ? BUTTON_COLOR_HOVER : BUTTON_COLOR_DEFAULT);
        g2d.fillRect(exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);
        
        // --- Draw the border of the EXIT button ---
        g2d.setColor(BUTTON_TEXT_COLOR);
        g2d.drawRect(exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);

        // --- Draw centered "EXIT" text ---
        Font exitFont = pausedRenderer.getExitButtonFont(panelWidth, panelHeight);
        pausedRenderer.drawCenteredText(g2d, "EXIT", exitFont, BUTTON_TEXT_COLOR, exitButtonBounds); 
    }   
    
    /**
     * Handles keyboard key press events.
     * <p>
     * Currently unused, but kept for consistency with other states.
     * </p>
     */

    @Override
    public void keyPressed(KeyEvent e) { 
    }

    /**
     * Handles keyboard key release events.
     * <p>
     * Currently unused, but kept for consistency with other states.
     * </p>
     */

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Handles mouse movement events to detect when the cursor is hovering
     * over the {@code EXIT} button. Updates the {@link #hoveringExit} flag.
     *
     * @param e the {@link MouseEvent} representing the mouse movement
     */

    @Override
    public void mouseMoved(MouseEvent e){
        if (exitButtonBounds != null) {
            hoveringExit = exitButtonBounds.contains(e.getPoint());
        }
    }

    /**
     * Handles mouse press events to detect clicks on the {@code EXIT} button.
     * <p>
     * If the button is clicked, the game will call 
     * {@link GameStateManager#gameShutdown()} to exit gracefully.
     * </p>
     *
     * @param e the {@link MouseEvent} representing the mouse click
     */

    @Override
    public void mousePressed(MouseEvent e) {
        if (exitButtonBounds != null && exitButtonBounds.contains(e.getPoint())) {
            gameStateManager.gameShutdown();
        }
    } 
}
