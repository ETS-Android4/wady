package com.mpv.game.players;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Players {
	public static Player activePlayer = null;
	private static ArrayList<Player> playerList = null;
	
	public static void add(Stage stage) {
		if (playerList != null) {
			return;
		}
		playerList = new ArrayList<Player>(16);
		Player player;
		player = new Player();
		//player.setBounds(0, 0, Const.BLOCK_SIZE*GVars.WORLD_TO_BOX, Const.BLOCK_SIZE*GVars.WORLD_TO_BOX);
		playerList.add(player);
		stage.addActor(player);
		activePlayer = playerList.get(0);
	}
	
	public static void positionSync() {
		for (Player player : playerList) player.positionSync();
	}
}
