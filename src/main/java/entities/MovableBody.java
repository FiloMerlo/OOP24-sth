package entities;

import entities.Component;
import entities.Entity;
import graphics.GenericAnimator;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MovableBody extends JPanel implements Component {
    private Entity owner;

    public MovableBody(Entity entity) {
        this.owner = entity;
        setOpaque(false);  
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GenericAnimator<?> animator = owner.getComponent(GenericAnimator.class); // entity manager deve avere getcomponent
        if (animator != null) {
            BufferedImage frame = animator.getCurrentFrame();
            if (frame != null) {
             
                g.drawImage(frame, 0, 0, null); // qui vanno coordinate entity
            }
        }
    }

    @Override
    public void update() {
        repaint();  
    }
}
