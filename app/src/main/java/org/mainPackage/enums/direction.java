package org.mainPackage.enums;
public enum direction{
    left(-1), 
    up(1), 
    right(1), 
    down(-1);

    private int value;
    direction (int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
};
