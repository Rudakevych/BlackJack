package com.logos.blackJack.terminalVersion;

public class Player {

    private String playersName;
    private int playerCash;

    public Player() { }

    public Player(String playersName, int playerCash) {
        this.playersName = playersName;
        this.playerCash = playerCash;
    }

    public String getPlayersName() {
        return playersName;
    }

    public void setPlayersName(String playersName) {
        this.playersName = playersName;
    }

    public int getPlayerCash() {
        return playerCash;
    }

    public void setPlayerCash(int playerCash) {
        this.playerCash = playerCash;
    }
}
