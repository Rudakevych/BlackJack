package com.logos.blackJack.terminalVersion;

public class Player {

    private String playersName;
    private int playerCash;

    public Player() { }

    public Player(String playersName, int playerMoney) {
        this.playersName = playersName;
    }

    public String getPlayersName() {
        return playersName;
    }

    public void setPlayersName(String playersName) {
        this.playersName = playersName;
    }

    public double getPlayerCash() {
        return playerCash;
    }

    public void setPlayerCash(int playerCash) {
        this.playerCash = playerCash;
    }
}
