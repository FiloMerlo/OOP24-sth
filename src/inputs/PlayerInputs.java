package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInputs implements KeyListener{
    private Character character;
    public PlayerInputs(Character character){
        this.character = character;  
    }
    /**
     * When a key is pressed, the character will move in the respective
     * direction. If the space key is pressed, the character will jump.
     * @param e the key event.
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                character.getEntity().setLeft(true);
                character.moveX(character);
                break;
            case KeyEvent.VK_D:
                character.getEntity().setRight(true);
                character.moveX(+3);
                break;
            case KeyEvent.VK_SPACE: //azione di salto
                int jumpH;
                character.moveY(+1);
                character.playerAction = falling;
                for (jumpH = 1; jumpH < 10; jumpH++){
                    character.moveY(+1);
                }
                while (jumpH > 0){
                    character.moveY(-1);
                    jumpH--;
                }
                character.sonic.updateAction();
            case KeyEvent.VK_S:
                //sonic si abbassa, pronto per fare uno spindash
                character.getEntity().setDown(true);
            default:
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                character.getEntity().setLeft(false);
                break;
            case KeyEvent.VK_D:
                character.getEntity().setRight(false);
                break;
            case KeyEvent.VK_S:
                character.getEntity().setDown(false);
            default:
                break;
        }
    }
}
