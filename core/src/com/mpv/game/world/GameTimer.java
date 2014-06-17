package com.mpv.game.world;

public class GameTimer {
	
	public static int gameTimeMin;
	public static int gameTimeSec;
	
	private float time = 0f;
	private float gameLimit = 0f;
	private static GameTimer instance;
	
	public static GameTimer getInstance() {
		if (instance == null)  {
            instance = new GameTimer();
        }
        return instance;
    }
	
	public float getLeft() {
		return gameLimit - time; 
	}
	
	public float getSpent() {
		return time; 
	}
	
	public void setTimer(float limit) {
		gameLimit = limit;
		time = 0;
	}
	
	public void update(float deltaTime) {
	    time += deltaTime;
	    if (time >= gameLimit) {
	    	GameObject.getInstance().gameOver();
	        // Reset timer (not set to 0)
	        time -= gameLimit;
	    }
	    gameTimeMin = (int) ((gameLimit - time) / 60);
		gameTimeSec = (int) ((gameLimit - time) % 60);
	}
	public static String getLeftString() {
		return String.format("%02d:%02d", gameTimeMin, gameTimeSec);
	}
	public String getSpentString() {
		int min = (int) (time / 60);
		int sec = (int) (time % 60);
		return String.format("%02d:%02d", min, sec);
	}
	public int getLeftSec() {
		return Math.round(gameLimit - time);
	}

}
