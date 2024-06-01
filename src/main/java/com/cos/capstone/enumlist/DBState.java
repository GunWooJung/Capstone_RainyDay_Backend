package com.cos.capstone.enumlist;

public enum DBState {

    NOT_USE(0),	USE(1);

    private final int value;

    DBState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
