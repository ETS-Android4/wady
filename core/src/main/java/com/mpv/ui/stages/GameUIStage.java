package com.mpv.ui.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mpv.data.Assets;
import com.mpv.data.Sounds;
import com.mpv.data.Sounds.ID;
import com.mpv.data.GVars;
import com.mpv.game.actors.Player;
import com.mpv.game.world.GameObj;
import com.mpv.game.world.GameTimer;
import com.mpv.ui.dialogs.FailedDialog;
import com.mpv.ui.dialogs.FinishDialog;
import com.mpv.ui.dialogs.PauseDialog;
import com.mpv.ui.dialogs.StartDialog;

import java.util.Locale;

public class GameUIStage extends Stage {

    public static Label labelFPS;
    public static Label labelDebug;
    private final Label labelTime;
    private final Label labelCoins;
    public static PauseDialog pauseDialog = new PauseDialog("", Assets.skin, "dialog");
    public static FinishDialog finishDialog = new FinishDialog("", Assets.skin, "default");
    public static FailedDialog failedDialog = new FailedDialog("", Assets.skin, "dialog");
    public static StartDialog startDialog = new StartDialog("", Assets.skin, "default");
    private static GameUIStage instance;
    private final Image radar = new Image(Assets.skin, "radar");

    public static GameUIStage get() {
        return instance;
    }

    public GameUIStage(Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);
        instance = this;
        final Button bPause = new Button(Assets.skin, "pause");
        //
        bPause.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // super.touchDown(event, x, y, pointer, button);
                Sounds.play(ID.BUTTON);
                if (GameObj.state == GameObj.ACTIVE) {
                    GameObj.get().gamePause();
                    gamePause();
                }
            }
        });

        radar.setSize(GVars.scrWidth / 4.3f, GVars.scrWidth / 4.3f);
        radar.setPosition(GVars.scrWidth / 2f - radar.getWidth() / 2f, GVars.scrHeight - radar.getHeight());
        radar.setOrigin(Align.center);
        radar.setRotation(360);
        this.addActor(radar);
        labelFPS = new Label("", Assets.skin, "normal-text");
        labelFPS.setPosition(getWidth() / 3, getHeight() / 3);
        this.addActor(labelFPS);
        labelDebug = new Label("", Assets.skin, "game-text");
        labelTime = new Label("", Assets.skin, "normal-text");
        labelCoins = new Label("", Assets.skin, "normal-text");
        labelCoins.setAlignment(Align.right);
        Button leftJump = new Button(Assets.skin, "leftJump");
        Button rightJump = new Button(Assets.skin, "rightJump");
        Widget empty = new Widget();
        Table controlPanel = new Table();
        controlPanel.setFillParent(true);
        controlPanel.top();
        controlPanel.add(new Image(Assets.skin.getDrawable("battery"))).size(GVars.scrWidth / 12f);
        controlPanel.add(labelTime).expand(true, false).height(getWidth() / 13).width(getWidth() / 4.3f).left();
        controlPanel.add(labelCoins).expand(true, false).height(getWidth() / 13).width(getWidth() / 8.6f).right();
        controlPanel.add(new Image(Assets.skin.getDrawable("diamond"))).size(GVars.scrWidth / 12f).right();

        // controlPanel.add(labelDebug).expand(true, false).size(GVars.scrHeight/13);
        // controlPanel.add(labelFPS).expand(true, false).size(GVars.scrHeight/13);
        Table buttonPanel = new Table();
        buttonPanel.setFillParent(true);
        buttonPanel.bottom();
        float bw = GVars.scrWidth / 3.2f / 24f;
        buttonPanel.add(leftJump).size(GVars.scrWidth / 4.8f);
        buttonPanel.add(bPause).size(bw * 10f).pad(bw * 14f, bw * 7f, 0, bw * 7f);
        buttonPanel.add(rightJump).size(GVars.scrWidth / 4.8f).row();
        buttonPanel.add(empty).size(getWidth() / 25f);
        this.addActor(controlPanel);
        this.addActor(buttonPanel);

        leftJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Player.get().powerLeft(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Player.get().powerLeft(false);
            }

        });
        rightJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Player.get().powerRigth(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Player.get().powerRigth(false);
            }

        });
    }

    @Override
    public void draw() {
        labelTime.setText(GameTimer.getLeftString());
        labelCoins.setText(String.format(Locale.US, "%02d", GameObj.get().getCoinCount()));
        // Calculating radar rotation
        Vector2 player = Player.get().body.getPosition();
        Vector2 target;
        if (null != GameObj.key) {
            target = GameObj.key.getPosition();
        } else if (null != GameObj.lock) {
            target = GameObj.lock.getPosition();
        } else {
            target = GameObj.start.getPosition();
        }

        radar.setRotation(target.sub(player).angleDeg() - 90);
        super.draw();
    }

    public void gamePause() {
        Gdx.input.setInputProcessor(instance);
        GameUIStage.pauseDialog.show(instance);
    }

    public void gameFinish() {
        Gdx.input.setInputProcessor(instance);
        GameUIStage.finishDialog.show(instance);
    }

    public void gameOver() {
        Gdx.input.setInputProcessor(instance);
        GameUIStage.failedDialog.show(instance);
        Sounds.play(ID.FAIL);
    }

    public void gameStart() {
        Player.get().resetGame();
        Gdx.input.setInputProcessor(instance);
        GameUIStage.startDialog.show(instance);
    }
}
