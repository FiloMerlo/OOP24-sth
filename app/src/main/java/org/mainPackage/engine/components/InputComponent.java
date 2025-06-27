package org.mainPackage.engine.components;

import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.impl.InputEvent;
import org.mainPackage.engine.systems.InputManager;
import org.mainPackage.enums.direction;
import java.awt.event.KeyEvent;

/**
 * {@link Component} responsible for handling {@link InputEvent}s, must added to the {@link Entity} controlled by the player(s)
 */

public class InputComponent implements Component, Observer{
    private Entity owner;
    private boolean pause = false;
    private PlayerPhysics playerPhysics;
        public InputComponent(Entity owner){
        this.owner = owner;
        this.playerPhysics = owner.getComponent(PlayerPhysics.class);
        InputManager.getInstance().addObserver(this);
        
        if (playerPhysics == null) {
            System.err.println("ERRORE: PlayerPhysics non trovato per InputComponent di " + owner.getClass().getSimpleName());
        }
    }

    @Override
    public void update(float deltaTime) {
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_RIGHT) || InputManager.getInstance().isKeyDown(KeyEvent.VK_LEFT)){
        }
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_RIGHT)){
            playerPhysics.setWill(direction.right, true);
            System.out.println("DEBUG: InputComponent - Update, Movimento destra.");
        } else {
            playerPhysics.setWill(direction.right, false);
        }
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_LEFT)){
            playerPhysics.setWill(direction.left, true);
            System.out.println("DEBUG: InputComponent - Update, Movimento sinistra.");
        } else {
            playerPhysics.setWill(direction.left, false);
        }
    }
    /**
     * {@link InputManager} fired the {@link InputEvent} , the key is pressed and released within a certain fraction of time
     */
    
  /* L'input del salto rimane alla versione vecchia, riadattamenti per il tasto pausa */        
    @Override
    public void onNotify(Event event) {
        if (event instanceof InputEvent){
            InputEvent i = (InputEvent) event;
            
            switch(i.getKeyEvent().getKeyCode()){
                /* Valutare questo metodo per il jump */
                case(KeyEvent.VK_SPACE):
                    if (i.getType() == EventType.KEY_DOWN) {
                        System.out.println("DEBUG: InputComponent - Salto KEY_DOWN");
                        playerPhysics.jump();
                    } else {
                        System.out.println("DEBUG: InputComponent - Salto " + i.getType());
                    }
                    break;
                
                case(KeyEvent.VK_P): 
                    
                    if (InputManager.getInstance().isKeyDown(KeyEvent.VK_P)){
                        pause = !pause;
                        System.out.println("PEPINO  - Tasto P premuto, stato pausa: " + pause);
                        EventType pauseEvent = pause ? EventType.PAUSE : EventType.RESUME;
                        GameEvent e = new GameEvent(pauseEvent, owner);
                        ((EntityImpl) owner).notifyObservers(e);
                        System.out.println("DEBUG: InputComponent - Evento PAUSE/RESUME notificato: " + pauseEvent);
                    }
                    break;
                
                default:
                    break;
            }
        }
    }
}