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

	private static MapManager instance;

	public static MapManager getInst() {
		if (instance == null) {
			instance = new MapManager();
		}
		return instance;
	}

	private Position start, exit, key;

	private TiledMapTile getTile(String tilename) {
		TiledMapTileSet tileSet = Assets.map.getTileSets().getTileSet(Const.Map.TILESET_OBTACLES);
		for (TiledMapTile tile : tileSet) {
			if (tile.getProperties().containsKey(tilename)) {
				return tile;
			}
		}
		return tileSet.getTile(0);
	}

	public TiledMapTileLayer getLayerItems() {
		return getLayer(Const.Map.LAYER_ITEMS);
	}

	private TiledMapTileLayer getLayer(String layerName) {
		return (TiledMapTileLayer) Assets.map.getLayers().get(layerName);
	}

	private void setExit() {
		Cell cell = new Cell();
		cell.setTile(getTile("exit"));
		exit = setRandomEmptyCell(cell, key);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.5f);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Body body = GVars.world.createBody(bd);
		body.createFixture(circleShape, 1);
		body.setTransform(exit.x + 0.5f, exit.y + 0.5f, 0f);
		body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
		GameObject.exit = body;
	}

	private void addCoins() {
		for (int i = 0; i < GameObject.getInstance().getTotalCoins(); i++) {
			Cell cell = new Cell();
			cell.setTile(getTile("diamond"));
			Position pos = setRandomEmptyCell(cell);
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(0.5f);
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			Body body = GVars.world.createBody(bd);
			body.createFixture(circleShape, 0f);
			body.setTransform(pos.x + 0.5f, pos.y + 0.5f, 0f);
			body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
			body.setUserData(new Coin(pos.x, pos.y));
		}
	}

	private void addTimeBonuses() {
		for (int i = 0; i < GameObject.getInstance().getBonusCount(); i++) {
			Cell cell = new Cell();
			cell.setTile(getTile("timeBonus"));
			Position pos = setRandomEmptyCell(cell);
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(0.5f);
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			Body body = GVars.world.createBody(bd);
			body.createFixture(circleShape, 0f);
			body.setTransform(pos.x + 0.5f, pos.y + 0.5f, 0f);
			body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
			body.setUserData(new TimeBonus(pos.x, pos.y));
		}
	}

	private void setStart() {
		Cell cell = new Cell();
		cell.setTile(getTile("enter"));
		start = setRandomEmptyCell(cell);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.5f);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Body body = GVars.world.createBody(bd);
		body.createFixture(circleShape, 1);
		body.setTransform(start.x + 0.5f, start.y + 0.5f, 0f);
		body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
		body.setActive(false);
		GameObject.start = body;
	}

	private Position setRandomEmptyCell(Cell cell) {
		return setRandomEmptyCell(cell, null);
	}

	private Position setRandomEmptyCell(Cell cell, Position pos) {
		TiledMapTileLayer itemsLayer = getLayerItems();
		Random random = new Random();
		int x = 0, y = 0;
		for (int i = 0; i < 1000; i++) {
			x = random.nextInt(itemsLayer.getWidth());
			y = random.nextInt(itemsLayer.getHeight());
			if (null == itemsLayer.getCell(x, y) && null == getLayerObtacles().getCell(x, y)) {
				if (null == pos) {
					itemsLayer.setCell(x, y, cell);
					break;
				} else {
					Vector2 vec = new Vector2(pos.x, pos.y);
					if (vec.dst(x, y) > itemsLayer.getWidth() / 2.1f) {
						itemsLayer.setCell(x, y, cell);
						break;
					}
				}
			}
		}
		return new Position(x, y);
	}

	public TiledMapTileLayer getLayerObtacles() {
		return getLayer(Const.Map.LAYER_OBTACLES);
	}

	private void setKey() {
		Cell cell = new Cell();
		cell.setTile(getTile("key"));
		key = setRandomEmptyCell(cell, start);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.5f);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Body body = GVars.world.createBody(bd);
		body.createFixture(circleShape, 0f);
		body.setTransform(key.x + 0.5f, key.y + 0.5f, 0f);
		body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
		GameObject.key = body;
		body.setUserData(key);
	}

	private void clearLayer(TiledMapTileLayer layer) {
		for (int y = 0; y < layer.getHeight(); y++) {
			for (int x = 0; x < layer.getWidth(); x++) {
				layer.setCell(x, y, null);
			}
		}
	}

	public void generate() {
		setWorldBounds();
		TiledMapTileLayer tileLayer = getLayerObtacles();
		clearLayer(tileLayer);
		clearLayer(getLayerItems());
		Cell cell = new Cell();
		cell.setTile(getTile("brick"));
		Random random = new Random();
		for (int y = 0; y < tileLayer.getHeight(); y += 4) {
			PolygonShape shape;
			for (int x = 0; x < tileLayer.getWidth(); x += 4) {
				if (random.nextBoolean()) {
					// Drawing pattern
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							if (Const.pattern[j][i] == 1) {
								tileLayer.setCell(x + i, y + j, cell);
								shape = new PolygonShape();
								shape.setAsBox(0.5f, 0.5f);
								BodyDef bd = new BodyDef();
								bd.type = BodyType.StaticBody;
								Body body = GVars.world.createBody(bd);
								body.createFixture(shape, 1);
								body.setTransform(x + i + 0.5f, y + j + 0.5f, 0f);
								body.getFixtureList().first().getFilterData().categoryBits = Const.CATEGORY_SCENERY;
							}
						}
					}
				}
			}
		}
		setStart();
		setKey();
		setExit();
		addCoins();
		addTimeBonuses();
	}

	private void setWorldBounds() {
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

	public void removeItem(Position pos) {
		getLayerItems().setCell(pos.x, pos.y, null);
	}
}
