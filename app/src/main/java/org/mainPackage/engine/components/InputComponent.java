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
        System.out.println("DEBUG: InputComponent inizializzato per " + owner.getClass().getSimpleName() + ".");
        if (playerPhysics == null) {
            System.err.println("ERRORE: PlayerPhysics non trovato per InputComponent di " + owner.getClass().getSimpleName());
        }
    }

    @Override
    public void update(float deltaTime) {
        if(InputManager.getInstance().isKeyDown(KeyEvent.VK_RIGHT)){
            playerPhysics.setWill(direction.right, true);
        } else {
            playerPhysics.setWill(direction.right, false);
        }
        if(InputManager.getInstance().isKeyDown(KeyEvent.VK_LEFT)){
            playerPhysics.setWill(direction.left, true);;
        } else {
            playerPhysics.setWill(direction.left, false);
        }
    }
    /**
     * {@link InputManager} fired the {@link InputEvent} , the key is pressed and released within a certain fraction of time
     */
    
     @Override
    public void onNotify(Event event) {
        System.out.println("DEBUG: InputComponent - onNotify ricevuto evento di tipo: " + event.getType());
        if (event instanceof InputEvent){
            InputEvent i = (InputEvent) event;
            System.out.println("DEBUG: InputComponent - InputEvent key: " + KeyEvent.getKeyText(i.getKeyEvent().getKeyCode()));
            switch(i.getKeyEvent().getKeyCode()){
                case(KeyEvent.VK_SPACE):
                    if (playerPhysics != null) {
                        playerPhysics.jump();
                        System.out.println("DEBUG: InputComponent - Jump attivato.");
                    }
                    break;
                case(KeyEvent.VK_ESCAPE):
                    pause = !pause;
                    EventType pauseEvent = pause ? EventType.PAUSE : EventType.RESUME;
                    GameEvent e = new GameEvent(pauseEvent, owner);
                    ((EntityImpl) owner).notifyObservers(e);
                    System.out.println("DEBUG: InputComponent - Evento PAUSE/RESUME notificato: " + pauseEvent);
                    break;
                default:
                    break;
            }
        }
    }
}