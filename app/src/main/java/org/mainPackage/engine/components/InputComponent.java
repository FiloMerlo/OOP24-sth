package org.mainPackage.engine.components;

import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.impl.InputEvent;
import org.mainPackage.engine.systems.InputManager;
import org.mainPackage.game_parts.direction;

import java.awt.event.KeyEvent;

public class InputComponent implements Component, Observer{

    Entity owner;
    PlayerPhysics playerPhysics = owner.getComponent(PlayerPhysics.class);
    // Polling through input manager when a key is down (Character running to right or left)
    @Override
    public void update() {
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
        }
    }
    
}
