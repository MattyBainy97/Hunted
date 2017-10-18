package com.hunted;

public enum GameState {

    IN_LOBBY, IN_GAME, RESTART;

    private static GameState currentState;

    public static void setState(GameState state){

        GameState.currentState = state;

    }

    public static boolean isState(GameState state){

        return GameState.currentState == state;

    }

    public static GameState getState(){

        return currentState;

    }

}
