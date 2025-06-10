package org.mainPackage.engine.components;

import org.mainPackage.engine.events.impl.SubjectImpl;

public class HealthComponent extends SubjectImpl implements Component{
    private int health;
    public HealthComponent(int health){
        this.health = health;
    }
    public void update(float deltaTime){

    }
    public int getHealth() {
        return health;
    }
}
