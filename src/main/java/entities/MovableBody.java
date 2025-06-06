package entities;

import entities.Component;
import entities.Entity;
import graphics.GenericAnimator;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import game_parts.direction;

public class MovableBody extends JPanel implements Component {
    private Entity owner;

    public MovableBody(Entity entity) {
        this.owner = entity;
        setOpaque(false);  
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; 

        GenericAnimator<?> animator = owner.getComponent(GenericAnimator.class);
        if (animator != null) {
            BufferedImage frame = animator.getCurrentFrame();
            if (frame != null) {
                direction dir = owner.getComponent(EnemyPhysics.class) != null
                        ? owner.getComponent(EnemyPhysics.class).getDirection()
                        : direction.right; 

                if (dir == direction.left) {
                    frame = flipHorizontally(frame);
                }

                g2d.drawImage(frame, (int)owner.getXpos(),(int)owner.getYpos(), null); 
            }
        }
    }

    @Override
    public void update() {
        repaint();  
    }
        private BufferedImage flipHorizontally(BufferedImage original) {  //specchia l'immagine se guarda a sinistra
        int w = original.getWidth();
        int h = original.getHeight();
        AffineTransform flip = new AffineTransform(-1, 0, 0, 1, w, 0);
        AffineTransformOp transform = new AffineTransformOp(flip, AffineTransformOp.TYPE_BICUBIC);
        return transform.filter(original, null);
    }

}
