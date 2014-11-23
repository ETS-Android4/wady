package com.mpv.game.world;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class MapManager {

	private static int keyX, keyY;

	private static TiledMapTile getTile(String tilename) {
		TiledMapTileSet tileSet = Assets.map.getTileSets().getTileSet("obtacles");
		for (TiledMapTile tile : tileSet) {
			if (tile.getProperties().containsKey(tilename)) {
				return tile;
			}
		}
		return tileSet.getTile(0);
	}

	public static void removeKey() {
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) Assets.map.getLayers().get("obtacles");
		tileLayer.setCell(keyX, keyY, null);
	}

	private static void setExit(int x, int y) {
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) Assets.map.getLayers().get("obtacles");
		Cell cell = new Cell();
		cell.setTile(getTile("exit"));
		tileLayer.setCell(x, y, cell);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.5f);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Body body = GVars.world.createBody(bd);
		body.createFixture(circleShape, 1);
		body.setTransform(x + 0.5f, y + 0.5f, 0f);
		body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
		GameObject.exit = body;
	}

	private static void setStart(int x, int y) {
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) Assets.map.getLayers().get("obtacles");
		Cell cell = new Cell();
		cell.setTile(getTile("start"));
		tileLayer.setCell(x, y, cell);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.5f);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Body body = GVars.world.createBody(bd);
		body.createFixture(circleShape, 1);
		body.setTransform(x + 0.5f, y + 0.5f, 0f);
		body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
		body.setActive(false);
		GameObject.start = body;
	}

	private static void setKey(int x, int y) {
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) Assets.map.getLayers().get("obtacles");
		Cell cell = new Cell();
		cell.setTile(getTile("key"));
		keyX = x;
		keyY = y;
		tileLayer.setCell(x, y, cell);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.5f);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Body body = GVars.world.createBody(bd);
		body.createFixture(circleShape, 0f);
		body.setTransform(x + 0.5f, y + 0.5f, 0f);
		body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
		GameObject.key = body;
		body.setUserData(cell);
	}

	private static void clearLayer(TiledMapTileLayer layer) {
		for (int y = 0; y < layer.getHeight(); y++) {
			for (int x = 0; x < layer.getWidth(); x++) {
				layer.setCell(x, y, null);
			}
		}
	}

	public static void Generate() {
		setWorldBounds();
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) Assets.map.getLayers().get("obtacles");
		clearLayer(tileLayer);
		Cell cell = new Cell();
		TiledMapTile tile = Assets.map.getTileSets().getTileSet("obtacles").iterator().next();
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
		setStart(0, 0);
		setExit(1, 1);
		setKey(tileLayer.getWidth() / 2, tileLayer.getHeight() / 2);
	}

	private static void setWorldBounds() {

		Vector2 lowerLeftCorner = new Vector2(Const.startpointX, Const.startpointY);
		Vector2 lowerRightCorner = new Vector2(GVars.widthInMeters - Const.startpointX, Const.startpointY);
		Vector2 upperLeftCorner = new Vector2(Const.startpointX, GVars.heightInMeters - Const.startpointY);
		Vector2 upperRightCorner = new Vector2(GVars.widthInMeters - Const.startpointX, GVars.heightInMeters
				- Const.startpointY);
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
