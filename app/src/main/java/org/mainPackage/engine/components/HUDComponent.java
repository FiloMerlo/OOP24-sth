package org.mainPackage.engine.components;

import java.awt.Graphics2D;

import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.renderer.Renderer;

public class HUDComponent implements Component, Renderer {
    private EntityImpl owner;
    private WalletComponent wallet;
    public HUDComponent(EntityImpl owner){
        this.owner = owner;
        this.wallet = this.owner.getComponent(WalletComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        
    }
    @Override
    public void render(Graphics2D g2d, int width, int height) {
        g2d.drawString("RINGS: " + wallet.getAmount(), 10, 20);
    }
}
