package com.mpv.data;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mpv.game.ApplicationHandler;

public class GVars {

	//Screen parameters
	public static float scrWidth=1;
	public static float scrHeight=1;
	//Scale
	public static float BOX_TO_WORLD; //= widthInPixels/widthInMeters;
	public static float WORLD_TO_BOX; //= 1/BOX_TO_WORLD;
	//Global items	
	public static ApplicationHandler app;
	public static OrthographicCamera cam;
	public static World world = new World(new Vector2(0, 0), true);
	//Tweens
	public static TweenManager tweenManager;
	
	public static void resize(float width, float height) {
		
		scrWidth = width;
		scrHeight = height;
		if (cam!=null) {
			cam.viewportWidth = width;
			cam.viewportHeight = height;
		}
		//Scale
		BOX_TO_WORLD = scrWidth/Const.widthInMeters;
		WORLD_TO_BOX = scrHeight/BOX_TO_WORLD;
	}
	public static void dispose () {
		world.dispose();		
	}
	
}
