package org.mainPackage.engine.components;

import java.awt.geom.Rectangle2D;

import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.impl.SubjectImpl;

    
/** This {@link Component }represent the objective to finish the level. 
 * It can be anything, from an enemy to kill to simply a point the player has to get to
 * Has to be a {@link SubjectImpl} , since it has to notify {@link org.mainPackage.state_management.GameStateManager}
*/

public class GoalComponent extends SubjectImpl implements Component {
    private  Rectangle2D.Float finishLine;
    private EntityImpl player;

    public GoalComponent(EntityImpl owner, EntityImpl player) {
        TransformComponent transform = owner.getComponent(TransformComponent.class);
        finishLine = new Rectangle2D.Float (transform.getX(), transform.getY(), transform.getWidth(), transform.getHeight());
        this.player = player;
    }

    public void update(float deltaTime){
        TransformComponent transform = player.getComponent(TransformComponent.class);
        if (finishLine.intersects(new Rectangle2D.Float(transform.getX(), transform.getY(), transform.getWidth(), transform.getHeight()))) {
            System.out.println("Goal reached!");
            GameEvent e = new GameEvent(EventType.STAGE_CLEARED, player);
            notifyObservers(e);
        }
    }
}
