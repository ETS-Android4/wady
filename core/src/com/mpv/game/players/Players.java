package com.mpv.game.players;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class Players {
	public static Player activePlayer = null;
	private static ArrayList<Player> playerList = null;
	
	public static void add(Stage stage) {
		if (playerList != null) {
			return;
		}
		playerList = new ArrayList<Player>(16);
		Player player;
		for (int i = 0; i < 16; i++) {
			player = new Player();
			player.setBounds(0, 0, Const.BLOCK_SIZE*GVars.WORLD_TO_BOX, Const.BLOCK_SIZE*GVars.WORLD_TO_BOX);
			playerList.add(player);
			stage.addActor(player);
		}
		activePlayer = playerList.get(0);
	}
	public static void draw (SpriteBatch spriteBatch, float parentAlpha) {
		for (Player player : playerList) {
			player.draw(spriteBatch, parentAlpha);
		}
	}
}
