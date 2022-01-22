package com.mpv.game.world;

import java.util.Locale;

public class GameTimer {

    public static int gameTimeMin;
    public static int gameTimeSec;

    private float time = 0f;
    private float gameLimit = 0f;
    private static GameTimer instance;

    public static GameTimer get() {
        if (instance == null) {
            instance = new GameTimer();
        }
        return instance;
    }

    public float getLeft() {
        return gameLimit - time + 1;
    }

    public float getSpent() {
        return time;
    }

    public void setTimer(float limit) {
        gameLimit = limit;
        time = 0;
    }

    public void addSeconds(float seconds) {
        setTimer(getLeft() + seconds);
    }

    public void update(float deltaTime) {
        time += deltaTime;
        if (time >= gameLimit) {
            GameObj.get().gameOver();
            // Reset timer (not set to 0)
            // time -= gameLimit;
        }
        gameTimeMin = (int) ((getLeft()) / 60);
        gameTimeSec = (int) ((getLeft()) % 60);
    }

    public static String getLeftString() {
        return String.format(Locale.US, "%02d:%02d", gameTimeMin, gameTimeSec);
    }

    public String getSpentString() {
        int min = (int) (time / 60);
        int sec = (int) (time % 60);
        return String.format(Locale.US, "%02d:%02d", min, sec);
    }

    public int getLeftSec() {
        return Math.round(gameLimit - time);
    }
}
