package org.mainPackage.engine.systems;

import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.impl.InputEvent;
import org.mainPackage.engine.events.impl.SubjectImpl;
import org.mainPackage.enums.input;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* {@link InputManager} which is a {@link SubjectImpl}
*/
public class InputManager extends SubjectImpl implements KeyListener{
    /** It uses a Singleton pattern
    * {@link mapActionKeys} is a map containing the keys used in the game with his corrispettive action
    * {@link keysDown} keysDown is a list containing the keysCode of the keys pressed and not released yet
    */
    private static InputManager instance = null;
    private Map<Integer, input> mapActionKeys;
    private List<Integer> keysDown;

    private InputManager(){
        mapActionKeys = new HashMap<>();
        keysDown = new ArrayList<>();
        setActionKeys();
    }

    public static InputManager getInstance(){
        if (instance == null){
            instance = new InputManager();
        }
        return instance;
    }

    private void setActionKeys() {
        mapActionKeys.put(KeyEvent.VK_LEFT, input.LEFT);   
        mapActionKeys.put(KeyEvent.VK_RIGHT, input.RIGHT);
        mapActionKeys.put(KeyEvent.VK_SPACE, input.JUMP);
        mapActionKeys.put(KeyEvent.VK_DOWN, input.DOWN);
        mapActionKeys.put(KeyEvent.VK_P, input.PAUSE);
    }
    /**
     * @param KeyEvent , it created a new {@link InputEvent}, adds the KeyCode to {@link keysDown} and {@link NotifyObsevers} of it
     * if checks if the command is already present in the polling
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("DEBUG: InputManager - keyPressed: " + KeyEvent.getKeyText(key));
        if (!keysDown.contains(key)) {
            keysDown.add(key);
            InputEvent i = new InputEvent(EventType.KEY_DOWN, e);
            notifyObservers(i);
        }
    }
    /**
     * @param KeyEvent , it created a new {@link InputEvent}, removes the KeyCode from {@link keysDown} and {@link NotifyObsevers} of it
     */
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("DEBUG: InputManager - keyReleased: " + KeyEvent.getKeyText(e.getKeyCode()));
        InputEvent i = new InputEvent(EventType.KEY_RELEASED, e);
        keysDown.remove(Integer.valueOf(e.getKeyCode()));
        notifyObservers(i);
    }
    /**
     * Given a @param keyEvent is a method regard higher-level language such as writing on a textBox, then must be left empty
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    /**
     * 
     * @param keyCode
     * @return Boolean if a given key is down
     */
    public Boolean isKeyDown(int keyCode){
        return keysDown.contains(keyCode);
    }
}
