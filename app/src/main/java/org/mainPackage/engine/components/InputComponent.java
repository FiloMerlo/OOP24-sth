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
import org.mainPackage.enums.action;
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
        if (playerPhysics == null) {
            throw new IllegalStateException("PlayerPhysics non trovato per InputComponent di " + owner.getClass().getSimpleName());
        }
    }

    @Override
    public void update(float deltaTime) {
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_RIGHT) || InputManager.getInstance().isKeyDown(KeyEvent.VK_LEFT)){
        }
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_RIGHT)){
            playerPhysics.setWill(direction.right, true);
        } else {
            playerPhysics.setWill(direction.right, false);
        }
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_LEFT)){
            playerPhysics.setWill(direction.left, true);
        } else {
            playerPhysics.setWill(direction.left, false);
        }
    }
    /**
     * {@link InputManager} fired the {@link InputEvent} , the key is pressed and released within a certain fraction of time
     */
    

    /* L'opizione di accedere alla pausa tramite esc è stata rimossa, adesso solo tramite il pulsante p */
    @Override
    public void onNotify(Event event) {
        if (event instanceof InputEvent){
            InputEvent i = (InputEvent) event;
            switch (i.getKeyEvent().getKeyCode()){
                case (KeyEvent.VK_SPACE):
                    if (playerPhysics != null) {
                        if (i.getType() == EventType.KEY_DOWN) {
                            if (playerPhysics.getAction() != action.jumping){ // This if fixes a previous bug which let player 'double' jumping if they pressed multiple space multiple times
                            playerPhysics.jump();
                            }
                        }
                    }
                    break;
                case (KeyEvent.VK_P):
                    if (InputManager.getInstance().isKeyDown(KeyEvent.VK_P)){
                        pause = !pause;
                        EventType pauseEvent = pause ? EventType.PAUSE : EventType.RESUME;
                        GameEvent e = new GameEvent(pauseEvent, owner);
                        ((EntityImpl) owner).notifyObservers(e);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}