package org.mainPackage.engine.components.PhysicsTypes;

import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.WalletComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EnumMap;

public class PlayerPhysics extends PhysicsComponent {
    private direction playerDir = direction.right;
    private action playerAction = action.idle;

    private static final float ACCEL = 0.1f;
    private static final float MAX_SPEED = 1.5f;
    private static final float AIR_ACCEL = 0.05f;
    private static final float FRICTION = 0.1f;
    private static final float GRAVITY = 0.1f;
    private static final float MAX_FALL_SPEED = 2.0f;
    private static final float JUMP_SPEED = -2.0f;
    private static final int INVINCIBLE_FRAMES = 240;
    private static final int MAX_JUMP_FRAMES = 20;

    private int jumpFrames = 0;
    private int iFrames = 0;
    private boolean hit = false;

    private EnumMap<direction, Boolean> input = new EnumMap<>(direction.class);

    public PlayerPhysics(EntityImpl owner, ArrayList<Rectangle2D.Float> tiles) {
        super(owner, tiles);
        for (direction dir : direction.values()) input.put(dir, false);
        ySpeed = 0.01f;
        addObserver(owner.getComponent(WalletComponent.class));
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

    private void takeDamage() {
        playerAction = action.hurt;
        hit = false;
        iFrames = INVINCIBLE_FRAMES;

        GameEvent e = owner.getComponent(WalletComponent.class).getAmount() > 0
            ? new GameEvent(EventType.PLAYER_HIT, owner)
            : new GameEvent(EventType.GAME_OVER, owner);

        notifyObservers(e);
        xSpeed = 0.5f * playerDir.opposite().getValue();
    }

    public void setWill(direction dir, boolean willMove) {
        input.put(dir, willMove);
    }

    public action getAction() { return playerAction; }
    public direction getDirection() { return playerDir; }
}