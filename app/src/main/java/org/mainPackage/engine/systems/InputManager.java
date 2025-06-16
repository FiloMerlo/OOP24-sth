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

public class InputManager extends SubjectImpl implements KeyListener{
    private static InputManager instance = null;
    private Map<Integer, input> mapActionKeys = new HashMap<>();
    private List<Integer> keysDown = new ArrayList<>();

    private InputManager(){
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
        InputEvent i = new InputEvent(EventType.KEY_DOWN, e);
        keysDown.add(e.getKeyCode());
        notifyObservers(i);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        InputEvent i = new InputEvent(EventType.KEY_RELEASED, e);
        keysDown.remove(e.getKeyCode());
        notifyObservers(i);
    }
    // keyTyped(KeyEvent e) regards about higher-level machine language, must be left empty
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    public boolean isKeyDown(int keyCode){
        return keysDown.contains(keyCode);
    }
}
