package org.mainPackage.engine.components;

/**
 * Component pattern: each {@link Entity} have a list of {@link Component}s, very specfic-purpose datas, 
 * methods that are updated throughout {@link GameLoop}
 */
public interface Component {
    /**
     * @param deltaTime is the elapsed time from the beginning and end of a cycle in the {@link GameLoop}
     */
    void update(float deltaTime);
}
