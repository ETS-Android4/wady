package com.mpv.game.world;

import static com.mpv.data.Const.STEP;
import static com.mpv.data.Sounds.ID.*;

import java.util.HashSet;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Sounds;
import com.mpv.data.GVars;
import com.mpv.game.Collisions;
import com.mpv.game.actors.Player;
import com.mpv.ui.stages.GameUIStage;

public class GameObj {

    public static final int ACTIVE = 0;
    public static final int PAUSE = 1;
    public static final int OVER = 2;
    public static final int FINISH = 3;

    public static int state = FINISH;

    // Delta time accumulator
    private float accumulator = 0;
    // Map data
    MapProperties mapProps;
    private float startTime;
    private float timeBonus;
    private int bonusCount;
    private int totalCoins;
    private final int[] points = new int[3];
    //
    private int collectedCoins;
    public static int mapIndex = -1;
    private static GameObj instance;
    public static Body start, key, lock;
    private static final HashSet<Body> bodyTrash = new HashSet<>();

    public static GameObj get() {
        if (instance == null) {
            instance = new GameObj();
        }
        return instance;
    }

    public void loadWorld() {
        if (GVars.world != null) {
            GVars.world.dispose();
            GVars.world = null;
        }

        GVars.world = new World(new Vector2(0, -9.8f), true);
        GVars.world.setContactListener(new Collisions());

        collectedCoins = 0;
        mapProps = Assets.map.getProperties();
        startTime = Float.parseFloat((String) mapProps.get("startTime"));
        timeBonus = Float.parseFloat((String) mapProps.get("timeBonus"));
        bonusCount = Integer.parseInt((String) mapProps.get("bonusCount"));
        totalCoins = Integer.parseInt((String) mapProps.get("coinsCount"));
        points[0] = Integer.parseInt((String) mapProps.get("points1"));
        points[1] = Integer.parseInt((String) mapProps.get("points2"));
        points[2] = Integer.parseInt((String) mapProps.get("points3"));

        MapManager.get().generate();

        Player.get().createBody();
        // Light
        if (GVars.rayHandler != null)
            GVars.rayHandler.dispose();

        GVars.rayHandler = new RayHandler(GVars.world);
        GVars.sceneryLight = new ConeLight(GVars.rayHandler, 24, new Color(0.72f, 0.72f, 0.0f, 1f),
                Const.VIEWPORT_METERS / 3.2f, Const.BLOCK_SIZE, Const.BLOCK_SIZE, 0f, 180f);
        GVars.sceneryLight.attachToBody(Player.get().body, 0f, 0f);
        GVars.playerLight = new ConeLight(GVars.rayHandler, 24, new Color(0.72f, 0.72f, 0.72f, 1f),
                Const.VIEWPORT_METERS, Const.BLOCK_SIZE, Const.BLOCK_SIZE, 90f, 30f);
        GVars.playerLight.setSoft(true);
        // GVars.playerLight.setStaticLight(true);
    }

    public float getTimeBonus() {
        return timeBonus;
    }

    public int getBonusCount() {
        return bonusCount;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void gameStart() {
        GameTimer.get().setTimer(startTime);
        Player.get().resetGame();
        state = ACTIVE;
        Player.state = Player.S_IDLE;
        Sounds.play(START);
    }

    public void gamePause() {
        state = PAUSE;
        Player.state = Player.S_FALL;
    }

    public void gameResume() {
        if (state != PAUSE) {
            loadWorld();
            gameStart();
        } else {
            state = ACTIVE;
        }
    }

    public void gameFinish() {
        state = FINISH;
        Player.state = Player.S_INVISIBLE;
        GameUIStage.get().gameFinish();
    }

    public void gameOver() {
        state = OVER;
        Player.state = Player.S_INVISIBLE;
        GameUIStage.get().gameOver();
    }

    public void gameUpdate(float delta) {
        if (state != ACTIVE) {
            return;
        }
        GameTimer.get().update(delta);
        worldUpdate(delta);
    }

    public float getMapLimit() {
        return startTime;
    }

    public void worldUpdate(float delta) {
        // No interpolation
        accumulator += delta;
        while (accumulator >= STEP) {
            update(STEP);
            accumulator -= STEP;
        }
        /*
         * if (accumulator >= STEP / 2f) { Player.get().interpolate(accumulator); }
         */
    }

    private void update(float value) {
        Player.get().applyForces();
        GVars.world.step(value, Const.BOX_VELOCITY_ITERATIONS, Const.BOX_POSITION_ITERATIONS);
        Player.get().update();
    }

    public static void captureKey() {
        MapManager.get().removeItem((Position) key.getUserData());
        bodyTrash.add(key);
        key = null;
        Sounds.play(KEY);
    }

    public void clearBodies() {
        for (Body body : bodyTrash) {
            // Unlocking door here. Not the best place
            if (body == lock) {
                lock = null;
                start.setActive(true);
            }
            GVars.world.destroyBody(body);
        }
        bodyTrash.clear();
    }

    public void collectTime(Body body) {
        MapManager.get().removeItem((Position) body.getUserData());
        bodyTrash.add(body);
        Sounds.play(GET_BATTERY);
        GameTimer.get().addSeconds(10);
    }

    public void collectDiamond(Body body) {
        MapManager.get().removeItem((Position) body.getUserData());
        bodyTrash.add(body);
        Sounds.play(GET_DIAMOND);
        collectedCoins++;
    }

    public int getPoints(int index) {
        return points[index];
    }

    public int getCoinCount() {
        return collectedCoins;
    }

    public static void captureLock() {
        MapManager.get().removeItem((Position) lock.getUserData());
        MapManager.get().unlockExit();
        bodyTrash.add(lock);
        Sounds.play(LOCK);
    }
}
