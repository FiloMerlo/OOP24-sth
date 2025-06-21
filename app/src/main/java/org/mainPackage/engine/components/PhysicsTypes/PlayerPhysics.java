package org.mainPackage.engine.components.PhysicsTypes;

import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.WalletComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;

import org.mainPackage.state_management.GameStateManager;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.components.WalletComponent;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EnumMap;

public class PlayerPhysics extends PhysicsComponent {
    private direction playerDir = direction.right;
    private action playerAction = action.idle;
    private float accelMod = 0.01f, maxSpeed = 1.2f, minSpeed = 0.1f, initFallSpeed = 0.1f, fallMod = 0.1f, maxFallSpeed = 1;
    private int jumpFrames = 0, maxJumpFrames = 100, jSpeed = -1, brakeForce = 1, iFrames = 0;
    protected HashMap<direction, Boolean> tryToMove = new HashMap<>();
    private boolean hit;

    public PlayerPhysics(EntityImpl o, ArrayList<Rectangle2D.Float> tList){
        super(o, tList);
        ySpeed = initFallSpeed;
        tryToMove.put(direction.left, false);
        tryToMove.put(direction.up, false);
        tryToMove.put(direction.right, false);
        addObserver(o.getComponent(WalletComponent.class));
        addObserver(GameStateManager.getInstance());
        /*tryToMove per direction.down è sempre opposto a tryToMove per direction.up*/
    }

    public void update(float deltaTime) {
        if (hit) takeDamage();
        if (iFrames > 0) iFrames--;

        handleHorizontal();
        handleVertical();
        determineAction();
    }

    private void handleHorizontal() {
        boolean onGround = !canGoThere(direction.down, 0.05f);
        float accel = onGround ? ACCEL : AIR_ACCEL;

        if (input.get(direction.left)) {
            playerDir = direction.left;
            xSpeed = Math.max(xSpeed - accel, -MAX_SPEED);
        } else if (input.get(direction.right)) {
            playerDir = direction.right;
            xSpeed = Math.min(xSpeed + accel, MAX_SPEED);
        } else if (onGround) {
            // Apply friction when not moving and on ground
            if (Math.abs(xSpeed) < FRICTION) xSpeed = 0;
            else xSpeed -= Math.signum(xSpeed) * FRICTION;
        }

        if (canGoThere(playerDir, xSpeed)) {
            owner.getComponent(TransformComponent.class).moveX(xSpeed);
        }
    }

    private void handleVertical() {
        if (jumpFrames > 0) {
            ySpeed = JUMP_SPEED;
            jumpFrames--;
        } else {
            ySpeed = Math.min(ySpeed + GRAVITY, MAX_FALL_SPEED);
        }

        if (!canGoThere(direction.down, ySpeed) && ySpeed > 0) {
            landing();
            ySpeed = 0;
        }

        owner.getComponent(TransformComponent.class).moveY(ySpeed);
    }

    private void determineAction() {
        if (iFrames > 0) return;

        if (ySpeed < 0) playerAction = action.jumping;
        else if (ySpeed > 0) playerAction = action.falling;
        else if (Math.abs(xSpeed) > 1.0f) playerAction = action.dashing;
        else if (Math.abs(xSpeed) > 0.5f) playerAction = action.running;
        else if (xSpeed != 0) playerAction = action.walking;
        else playerAction = action.idle;
        System.out.println(playerAction);
    }

    public void jump() {
        boolean onGround = !canGoThere(direction.down, 0.05f);
        if (onGround) {
            jumpFrames = MAX_JUMP_FRAMES;
        }
    }

    public void hit() {
        if (iFrames == 0) hit = true;
    }

    public void takeDamage(){
        System.out.println("Sonic took damage!");
        playerAction = action.hurt;
        hit = false;
        iFrames = 240;
        GameEvent e;
        if (owner.getComponent(WalletComponent.class).getAmount() > 0){
            e = new GameEvent(EventType.PLAYER_HIT, owner);
        }
        else {
            System.out.println("GAME OVER!!!!!");
            e = new GameEvent(EventType.GAME_OVER, owner);
        }
        notifyObservers(e);
        xSpeed = 0.2f * playerDir.opposite().getValue();
    }

    public void hit() { 
        if (iFrames == 0) {
            hit = true;
        }
    }

    public action getAction() { return playerAction; }
    public direction getDirection() { return playerDir; }
    public void setWill(direction dir, boolean bool){
        tryToMove.replace(dir, bool);
    }
}
