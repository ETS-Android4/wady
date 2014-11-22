package com.mpv.game.world;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class MapManager {

	public static void Generate() {
		setWorldBounds();

		TiledMapTileLayer tileLayer = (TiledMapTileLayer) Assets.map
				.getLayers().get("obtacles");
		Cell cell = new Cell();
		TiledMapTile tile = Assets.map.getTileSets().getTileSet("obtacles")
				.iterator().next();
		cell.setTile(tile);
		Random random = new Random();
		for (int y = 0; y < tileLayer.getHeight(); y = y + 3) {
			if (random.nextBoolean()) {
				continue;
			}
			PolygonShape shape;
			for (int x = 0; x < tileLayer.getWidth(); x++) {
				if (random.nextBoolean()) {
					tileLayer.setCell(x, y, cell);
					shape = new PolygonShape();
					shape.setAsBox(0.5f, 0.5f);
					BodyDef bd = new BodyDef();
					bd.type = BodyType.StaticBody;
					Body body = GVars.world.createBody(bd);
					body.createFixture(shape, 1);
					body.setTransform(x + 0.5f, y + 0.5f, 0f);
					body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
				}
			}
		}
	}

	private static void setWorldBounds() {

		Vector2 lowerLeftCorner = new Vector2(Const.startpointX,
				Const.startpointY);
		Vector2 lowerRightCorner = new Vector2(GVars.widthInMeters
				- Const.startpointX, Const.startpointY);
		Vector2 upperLeftCorner = new Vector2(Const.startpointX,
				GVars.heightInMeters - Const.startpointY);
		Vector2 upperRightCorner = new Vector2(GVars.widthInMeters
				- Const.startpointX, GVars.heightInMeters - Const.startpointY);
		EdgeShape edgeBoxShape = new EdgeShape();
		Body groundBody;
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		FixtureDef groundFixtureDef = new FixtureDef();
		groundFixtureDef.shape = edgeBoxShape;
		groundFixtureDef.density = 0f;
		groundFixtureDef.filter.categoryBits = Const.CATEGORY_SCENERY;
		groundFixtureDef.restitution = 0f;
		groundBody = GVars.world.createBody(groundBodyDef);
		edgeBoxShape.set(lowerLeftCorner, lowerRightCorner);
		groundBody.createFixture(groundFixtureDef);
		edgeBoxShape.set(lowerLeftCorner, upperLeftCorner);
		groundBody.createFixture(groundFixtureDef);
		edgeBoxShape.set(upperLeftCorner, upperRightCorner);
		groundBody.createFixture(groundFixtureDef);
		edgeBoxShape.set(lowerRightCorner, upperRightCorner);
		groundBody.createFixture(groundFixtureDef);
		// Dispose
		edgeBoxShape.dispose();
	}
}
