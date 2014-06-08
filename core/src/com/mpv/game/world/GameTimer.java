package com.mpv.game.world;

import com.mpv.data.GVars;

public class GameTimer {
	
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
	    GVars.gameTimeMin = (int) ((gameLimit - time) / 60);
		GVars.gameTimeSec = (int) ((gameLimit - time) % 60);
	}
}
