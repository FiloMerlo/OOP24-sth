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

public class InputComponent implements Component, Observer{

    private Entity owner;
    private boolean pause = false;
    private PlayerPhysics playerPhysics;
    public InputComponent(Entity owner){
        this.owner = owner;
        owner.getComponent(PlayerPhysics.class);
        InputManager.getInstance().addObserver(this);
         
    }
    // Polling through input manager when a key is down (Character running to right or left)
    @Override
    public void update(float deltaTime) {
        if(InputManager.getInstance().isKeyDown(KeyEvent.VK_RIGHT)){
            playerPhysics.moveX(direction.right);
        }
        if(InputManager.getInstance().isKeyDown(KeyEvent.VK_LEFT)){
            playerPhysics.moveX(direction.left);
        }
    }

    // Input manager fired the event, key is pressed, released within a fraction of a 'second'
    @Override
    public void onNotify(Event event) {
        if (event instanceof InputEvent){
            InputEvent i = (InputEvent) event;
            if (playerPhysics == null){
                return;
            }
            if (i.getKeyEvent().getKeyCode() == KeyEvent.VK_SPACE){
                playerPhysics.jump();
            }
            if (i.getKeyEvent().getKeyCode() == KeyEvent.VK_ESCAPE){
               pause = !pause;
                EventType pauseEvent = pause ? EventType.PAUSE : EventType.RESUME;
                ((EntityImpl) owner).notifyObservers(new GameEvent(pauseEvent, owner));
            }
        }
    }
    
}
