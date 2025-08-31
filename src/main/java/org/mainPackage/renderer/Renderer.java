package org.mainPackage.renderer;


import java.awt.Graphics2D; 

/**
 * Defines a generic rendering interface for game components.
 * Implementations of this interface are responsible for drawing 
 * their contents onto the given {@link Graphics2D} context.
 *
 * <p>The {@code width} and {@code height} parameters allow 
 * renderers to adapt their drawing to the current viewport or 
 * panel dimensions.</p>
 */

public interface Renderer {

    /**
     * Renders the component using the specified graphics context.
     *
     * @param g2d    the graphics context to draw onto
     * @param width  the width of the rendering area in pixels
     * @param height the height of the rendering area in pixels
     */
  
    void render(Graphics2D g2d, int width, int height);
}