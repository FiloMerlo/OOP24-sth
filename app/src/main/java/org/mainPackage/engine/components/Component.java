package org.mainPackage.engine.components;

/*
 * Component pattern: each {@link Entity} have a list of Components, very specfic-purpose datas, 
 * methods that are updated throughout the GameLoop
 */
public interface Component {
    /*
     * @param deltaTime is the elapsed time from the beginning and end of a cycle in the {@link GameLoop}
     */
    void update(float deltaTime);
}
