package org.mainPackage.util;

/**
* The {@code SizeView} interface defines methods to retrieve
* the width and height of the game panel.
* <p>
* This interface provides an abstraction for size-related functionality,
* allowing different application components to adapt to the panel's dimensions.
* </p>
*/

public interface SizeView {
    
    /**
     * @return the width of the view
     */
    
    int getSizeWidth();
    
    /**
     * 
     * @return the height of the view
     */
    
    int getSizeHeight();
}
