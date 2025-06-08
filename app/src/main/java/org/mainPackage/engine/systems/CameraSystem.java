package sth.engine.systems;


import sth.engine.entities.impl.EntityImpl;

import java.awt.geom.Point2D;

import sth.engine.components.TransformComponent;

public class CameraSystem {

    private static CameraSystem instance;

    private EntityImpl target;

    private Point2D pos;
    private int screenSizeWidth;
    private int screenSizeHeight;
    private int levelWidth, levelHeight;

    private CameraSystem(int screenSizeWidth, int screenSizeHeight, int levelWidth, int levelHeight) {
        this.screenSizeWidth = screenSizeWidth;
        this.screenSizeHeight = screenSizeHeight;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.pos = new Point2D.Float(0,0);
    }

    public static void init(int screenSizeWidth, int screenSizeHeight, int levelWidth, int levelHeight) {
        if (instance == null) {
            instance = new CameraSystem(screenSizeWidth, screenSizeHeight, levelWidth, levelHeight);
        }
    }

    public static CameraSystem getInstance() {
        return instance;
    }

    public void setTarget(EntityImpl entity) {
        this.target = entity;
    }

    public void update(float deltaTime) {
        if (target == null || !target.hasComponent(TransformComponent.class)) {
            return;
        }

        TransformComponent transform = target.getComponent(TransformComponent.class);
    }

    public Point2D getCameraOffset() {
        return this.pos;
    }
}
