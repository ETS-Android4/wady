package com.mpv.data;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/*
 * Sound effects library
 */
public class Sounds {

	public enum ID {
		START, WIN, WING, FAIL, STAR, GET_DIAMOND, KEY, LOCK, FINISH, GET_BATTERY, TIMER, COUNT_DIAMOND, COUNTER, BUTTON, HIT
	};

	private static HashMap<ID, Sound> sounds = new HashMap<ID, Sound>();

	public static void load(AssetManager am) {
		String FILENAME = "sounds/%s.mp3";
		ID[] nms = ID.values();
		for (ID name : nms) {
			am.load(String.format(FILENAME, name.name().toLowerCase()), Sound.class);
		}
		am.finishLoading();
		for (ID name : nms) {
			sounds.put(name, am.get(String.format(FILENAME, name.name().toLowerCase()), Sound.class));
		}
	}

	public static void play(ID id) {
		play(id, 1, 1);
	}

	public static void play(ID id, float volume) {
		play(id, volume, 1);
	}

	public static void play(ID id, float volume, float pitch) {
		if (Settings.soundEnabled)
			sounds.get(id).play(volume, pitch, 0);
	}

	public static void loop(ID id) {
		loop(id, 1, 1);
	}

	public static void loop(ID id, float volume) {
		loop(id, volume, 1);
	}

	public static void loop(ID id, float volume, float pitch) {
		if (Settings.soundEnabled)
			sounds.get(id).loop(volume, pitch, 0);
	}

	public static void stop(ID id) {
		sounds.get(id).stop();
	}
}
