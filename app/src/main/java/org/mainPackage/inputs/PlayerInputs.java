package org.mainPackage.inputs;
import org.mainPackage.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.game_parts.ComponentType;
import org.mainPackage.game_parts.Entity;
import org.mainPackage.game_parts.direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInputs implements KeyListener{
    private PlayerPhysics character;
    public PlayerInputs(PlayerPhysics character){
        this.character = character;  
    }
    /**
     * When a key is pressed, the character will move in the respective
     * direction. If the space key is pressed, the character will jump.
     * @param e the key event.
     */
    public void keyPressed(KeyEvent e) {
        /*TODO FIX*/
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                character.;
                break;
            case KeyEvent.VK_D:
                character.moveX(right);
                break;
            case KeyEvent.VK_SPACE: 
                character.jump();
            default:
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                character.brake();
                break;
            case KeyEvent.VK_D:
                character.brake();
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
}
