package org.mainPackage.engine.entities.impl;

import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;

import org.mainPackage.engine.components.InputComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.WalletComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.SonicAnimator;
import org.mainPackage.engine.systems.InputManager;
import org.mainPackage.state_management.GameStateManager;

public class PlayerFactory {

    public static EntityImpl createPlayer(ArrayList<Float> tileList, float sonicSize){
        EntityImpl player = new EntityImpl();
        player.addComponent(new SonicAnimator());
        player.addComponent(new WalletComponent(tileList, player));
        TransformComponent sonicTransform = new TransformComponent(0, 0, sonicSize, sonicSize);
        player.addComponent(sonicTransform);
        PlayerPhysics physics = new PlayerPhysics(player, tileList);
        physics.addObserver(player.getComponent(WalletComponent.class));
        physics.addObserver(GameStateManager.getInstance());
        physics.addObserver(EntityManagerImpl.getInstance());
        player.addComponent(physics);
        player.addComponent(new InputComponent(player));
        player.addObserver(EntityManagerImpl.getInstance());
        player.addObserver(GameStateManager.getInstance());
        InputManager.getInstance().addObserver(player.getComponent(InputComponent.class));
        return player;
    }
}
