package com.mpv.data;

import com.badlogic.gdx.audio.Sound;

/*
 * Sound effects library
 */
public class Effect {
	public static final String START = "sounds/start.mp3";
	public static final String WIN = "sounds/win.mp3";
	public static final String WING = "sounds/wing.mp3";
	public static final String FAIL = "sounds/fail.mp3";
	public static final String STAR = "sounds/star.mp3";
	public static final String DIAMOND = "sounds/get_diamond.mp3";
	public static final String KEY = "sounds/key.mp3";
	public static final String LOCK = "sounds/lock.mp3";
	public static final String FINISH = "sounds/finish.mp3";
	public static final String GET_BATTERY = "sounds/get_battery.mp3";
	public static final String TIMER = "sounds/timer.mp3";
	public static final String COUNT_DIAMOND = "sounds/count_diamond.mp3";
	public static final String COUNTER = "sounds/counter.mp3";
	public static final String BUTTON = "sounds/button.mp3";
	public static final String HIT = "sounds/hit.mp3";

	public static void start() {
		playSnd(START);
	}

	public static void button() {
		playSnd(BUTTON);
	}

	public static void playSnd(String name) {
		playSnd(name, 1, 1);
	}

	public static void playSnd(String name, float volume) {
		playSnd(name, volume, 1);
	}

	public static void playSnd(String name, float volume, float pitch) {
		if (Settings.soundEnabled)
			((Sound) Assets.am.get(name)).play(volume, pitch, 0);
	}

	public static void loopSnd(String name) {
		loopSnd(name, 1, 1);
	}

	public static void loopSnd(String name, float volume) {
		loopSnd(name, volume, 1);
	}

	public static void loopSnd(String name, float volume, float pitch) {
		if (Settings.soundEnabled)
			((Sound) Assets.am.get(name)).loop(volume, pitch, 0);
	}

	public static void wing() {
		playSnd(WING);
	}

	public static void key() {
		playSnd(KEY);
	}

	public static void get_battery() {
		playSnd(GET_BATTERY);
	}

	public static void diamond() {
		playSnd(DIAMOND);
	}

	public static void lock() {
		playSnd(LOCK);
	}

	public static void hit() {
		playSnd(HIT);
	}

	public static void star() {
		playSnd(STAR);
	}

	public static void counter() {
		playSnd(COUNTER);
	}

	public static void stopSnd(String name) {
		((Sound) Assets.am.get(name)).stop();
	}

	public static void count_diamond() {
		playSnd(COUNT_DIAMOND);
	}

	public static void fail() {
		playSnd(FAIL);
	}

	public static void win() {
		playSnd(WIN);
	}

	public static void finish() {
		playSnd(FINISH);
	}
}
