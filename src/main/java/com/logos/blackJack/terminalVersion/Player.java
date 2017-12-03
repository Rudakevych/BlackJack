package com.logos.blackJack.terminalVersion;

public class Player {

    private String playersName;
    private double playerCash;

    public Player(String playersName, double playerMoney) {
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

    public void setPlayerCash(double playerCash) {
        this.playerCash = playerCash;
    }
}
