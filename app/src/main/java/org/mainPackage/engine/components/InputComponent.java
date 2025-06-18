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
    // Polling through @{link InputManager} when a key is down (Character running to right or left)
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

    // @{link InputManager} fired the @{link Event}, key is pressed, released within a certain fraction of time
    @Override
    public void onNotify(Event event) {
        if (event instanceof InputEvent){
            InputEvent i = (InputEvent) event;
            switch(i.getKeyEvent().getKeyCode()){
                case(KeyEvent.VK_SPACE):
                    playerPhysics.jump();
                    break;
                case(KeyEvent.VK_ESCAPE):
                    pause = !pause;
                    EventType pauseEvent = pause ? EventType.PAUSE : EventType.RESUME;
                    GameEvent e = new GameEvent(pauseEvent, owner);
                    ((EntityImpl) owner).notifyObservers(e);
                    break;
                default:
                    break;
            }
        }
    }
}
