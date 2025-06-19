package org.mainPackage.enums;
public enum direction{
    left(-1), 
    up(-1), 
    right(1), 
    down(1);

    private int value;
    direction (int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public direction opposite(){
        direction opposite;
        if (this == direction.left){
            opposite = direction.right;
        } else if (this == direction.right){
            opposite = direction.left;
        } else if (this == direction.up){
            opposite = direction.down;
        } else {
            opposite = direction.up;
        }
        return opposite;
    }
};
