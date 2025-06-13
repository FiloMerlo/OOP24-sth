package org.mainPackage.engine.components;

import java.awt.geom.Rectangle2D;

import org.mainPackage.engine.entities.impl.EntityImpl;


public class GoalComponent implements Component {
    /*This component represent the objective to finish the level. It can be anything, from an enemy to kill to simply a point the player has to get to*/
    private  Rectangle2D.Float finishLine;
    private EntityImpl player;

    public GoalComponent(EntityImpl owner) {
        TransformComponent transform = owner.getComponent(TransformComponent.class);
        finishLine = new Rectangle2D.Float (transform.getX(), transform.getY(), transform.getWidth(), transform.getHeight());
    }

    public void update(float deltaTime){
        if (finishLine.intersects(player.getComponent(PhysicsComponent.class).getHitbox())) {
            System.out.println("Goal reached!");
            /*TODO fermare la partita*/
        }
    }
}
