package com.mpv.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class Const {
		//Animation
		public static final float ANIMATION_SPEED = 0.05f;
		//Physics parameters
		public static final float BLOCK_SIZE = 0.5f; //meters
		public static final float MAX_SPEED = BLOCK_SIZE*6;
		public static final float MIN_SPEED = BLOCK_SIZE;
		public static final float GRAVITY_MUL = -3f*BLOCK_SIZE;
		public static final float BLOCK_HALF = BLOCK_SIZE/2;
		public static final float BOX_STEP=1/60f;
		public static final int BOX_VELOCITY_ITERATIONS=15;  
		public static final int BOX_POSITION_ITERATIONS=8;
		public static final float BLOCK_DENSITY = 0.1f;
		public static final float BLOCK_FRICTION = 0f;
		public static final float BLOCK_RESTITUTION = 0f;
		public static final float BODY_LINEAR_DAMPING = 0.9f;
		public static final boolean FIXED_ROTATION = false;
		//Physics world bounds
		public static final float startpointX = 0;
		public static final float startpointY = 0;
		public static final float widthInMeters = 10f;
		public static final float heightInMeters = 10f;
		public static final float widthInPixels = 1600f;
		public static final float heightInPixels = 1600f;
		public static final float CAM_BORDER = 100f;
		//Scale
		public static final float BOX_TO_WORLD = widthInPixels/widthInMeters;
		public static final float WORLD_TO_BOX = 1/BOX_TO_WORLD;
		//Scaled sizes
		public static final float BLOCK_HALF_PIX = Const.BLOCK_HALF*Const.BOX_TO_WORLD;
		public static final float BLOCK_SIZE_PIX = Const.BLOCK_SIZE*Const.BOX_TO_WORLD;
		//Collision categories
		public static final short CATEGORY_BLOCK = 0x0001;  // 0000000000000001 in binary
		public static final short CATEGORY_SCENERY = 0x0002; // 0000000000000100 in binary
		//Collision masks
		public static final short MASK_BLOCK = -1;  
		public static final short MASK_SCENERY = -1;
		//Strings
		public static final String TIME_FORMAT = "%s:%s";
		//Camera bounds
		public static final BoundingBox 
		maxBoundingBox = new BoundingBox(
			new Vector3(-CAM_BORDER, -CAM_BORDER, 0), 
			new Vector3(widthInPixels+CAM_BORDER, widthInPixels+CAM_BORDER, 0)),
		minBoundingBox = new BoundingBox(
					new Vector3(CAM_BORDER, CAM_BORDER, 0), 
					new Vector3(widthInPixels-CAM_BORDER, widthInPixels-CAM_BORDER, 0));
		public static final Rectangle 
		minZoom = new Rectangle(-CAM_BORDER, -CAM_BORDER, widthInPixels+CAM_BORDER, heightInPixels+CAM_BORDER), 
		maxZoom = new Rectangle(CAM_BORDER, CAM_BORDER, widthInPixels-CAM_BORDER, heightInPixels-CAM_BORDER);
		
}
