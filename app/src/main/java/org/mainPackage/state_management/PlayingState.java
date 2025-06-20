package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;

import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import org.mainPackage.engine.events.impl.SubjectImpl;
import org.mainPackage.engine.components.GoalComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.SonicAnimator;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.renderer.PlayingRenderer;
import org.mainPackage.util.SizeView;

/* 
 * il render dello stato è delegato al PlayingRenderer
 */

public class PlayingState extends GameState {

    private final PlayingRenderer playingRenderer;
    private final EntityManagerImpl entityManager; 
    private final Entity sonicPlayer;
    private final int[][] levelGrid;
    private final int tileWorldSize;
    private GoalComponent goal;
    private long lastUpdateTime = System.currentTimeMillis();
    
    
    public PlayingState(GameStateManager gameStateManager, SizeView sizeView, Entity sonic, int[][] grid, int tileSize, GoalComponent goal) {
        super(gameStateManager, sizeView);
        System.out.println("PlayingState inizializzato.");
        this.entityManager = EntityManagerImpl.getInstance();
        this.sonicPlayer = sonic;
        this.levelGrid = grid;
        this.tileWorldSize = tileSize;
        this.goal = goal;
        this.playingRenderer = new PlayingRenderer(entityManager, levelGrid, tileWorldSize);

        if (this.sonicPlayer == null) {
            System.err.println("Sonic non è stato trovato");
        }
        ((EntityImpl) sonicPlayer).addObserver(this.gameStateManager);
        ((GoalComponent) goal).addObserver(this.gameStateManager);
    }
    
    
    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastUpdateTime) / 1000.0f; 
        lastUpdateTime = currentTime;

        entityManager.updateEntities(deltaTime);
    if (sonicPlayer.hasComponent(SonicAnimator.class) && sonicPlayer.hasComponent(PlayerPhysics.class)) {
        SonicAnimator anim = sonicPlayer.getComponent(SonicAnimator.class);
        PlayerPhysics physics = sonicPlayer.getComponent(PlayerPhysics.class);
        anim.setState(physics.getAction());
    }

        if (sonicPlayer != null && sonicPlayer.hasComponent(TransformComponent.class)) {
            TransformComponent sonicTransform = sonicPlayer.getComponent(TransformComponent.class);
            if (sonicTransform != null) {
               playingRenderer.updateCamera((int) sonicTransform.getX(), (int) sonicTransform.getY());
            } else if (sonicPlayer == null) {
                    System.err.println("Errore: sonicPlayer è null durante l'update del PlayingState.");
            }
        }
    }
    
    @Override
    public void draw(Graphics g) { 
        Graphics2D g2d = (Graphics2D) g;
        int currentWidth = sizeView.getSizeWidth();
        int currentHeight = sizeView.getSizeHeight();

        playingRenderer.updateViewPort(currentWidth,currentHeight);
        playingRenderer.render(g2d, currentWidth, currentHeight);
    }
    
  
}