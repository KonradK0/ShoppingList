package com.example.kuba.firebasetutorial.database;

/**
 * Created by Kuba on 19/01/2018.
 */

public class State {
    String name = null;
    State currentState = null;

    public String getName() {
        return name;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        this.name = currentState.name;
    }
}
