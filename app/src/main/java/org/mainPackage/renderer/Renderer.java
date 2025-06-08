package org.mainPackage.renderer;


import java.awt.Graphics2D; 


public interface Renderer {
  
    void render(Graphics2D g2d, int width, int height);
}