package org.mainPackage.engine.entities.api;

import org.mainPackage.engine.components.Component;

/*
 * Entities are the game objects of the game, primarily the player(s), enemies and collectibles
 */

public interface Entity {
    /**
     * @param deltaTime , each {@link Component} associated to the Entity
     *  is updated each time is called {@link update} on {@link GameLoop}
     */
    void update(float deltaTime);
    /**
     * @param <T> , it @return Boolean
     */
    Boolean hasComponent(Class<? extends Component> componentClass);
    /**
     * @param <T> , it @return <T extends {@link Component}>
     */
    <T extends Component> T getComponent(Class<T> componentClass);

    void addComponent(Component c);

}