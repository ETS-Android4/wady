package com.mpv.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
import com.mpv.game.actors.Player;
import com.mpv.game.world.GameObject;
import com.mpv.game.world.GameTimer;
import com.mpv.screens.dialogs.FailedDialog;
import com.mpv.screens.dialogs.FinishDialog;
import com.mpv.screens.dialogs.PauseDialog;
import com.mpv.screens.dialogs.StartDialog;

public class GameUIStage extends Stage {

	public static Label labelFPS;
	public static Label labelDebug;
	private final Label labelTime;
	private final Label labelCoins;
	private final Button leftJump, rightJump;
	public static PauseDialog pauseDialog = new PauseDialog("", Assets.skin, "dialog");
	public static FinishDialog finishDialog = new FinishDialog("", Assets.skin, "dialog");
	public static FailedDialog failedDialog = new FailedDialog("", Assets.skin, "dialog");
	public static StartDialog startDialog = new StartDialog("", Assets.skin, "dialog");
	private static GameUIStage instance;
	private final Image radar = new Image(Assets.skin, "radar");

	public static GameUIStage getInstance() {
		return instance;
	}

	public GameUIStage(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		instance = this;
		final TextButton bPause = new TextButton("II", Assets.skin);
		//
		bPause.addListener(new ClickListener() {
			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				if (GameObject.state == GameObject.ACTIVE) {
					GameObject.getInstance().gamePause();
					gamePause();
				}
			}
		});

		radar.setSize(GVars.scrWidth / 4.3f, GVars.scrWidth / 4.3f);
		radar.setPosition(GVars.scrWidth / 2f - radar.getWidth() / 2f, GVars.scrHeight - radar.getHeight());
		radar.setOrigin(Align.center);
		radar.setRotation(360);
		this.addActor(radar);
		labelFPS = new Label("", Assets.skin, "game-text");
		labelDebug = new Label("", Assets.skin, "game-text");
		labelTime = new Label("", Assets.skin, "normal-text");
		labelCoins = new Label("", Assets.skin, "normal-text");
		leftJump = new Button(Assets.skin, "leftJump");
		rightJump = new Button(Assets.skin, "rightJump");
		Widget empty = new Widget();
		Table controlPanel = new Table();
		controlPanel.setFillParent(true);
		controlPanel.top();
		controlPanel.add(new Image(Assets.skin.getDrawable("battery"))).size(GVars.scrWidth / 12f);
		controlPanel.add(labelTime).expand(true, false).height(getWidth() / 13).width(getWidth() / 4.3f).left();
		controlPanel.add(labelCoins).expand(false, false).height(getWidth() / 13).width(getWidth() / 8.6f).right();
		controlPanel.add(new Image(Assets.skin.getDrawable("star-gold"))).size(GVars.scrWidth / 12f).right();

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

		leftJump.addListener(new ClickListener() {
			Vector2 leftForce = new Vector2(-1f, 1f);

			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				GVars.activePlayer.applyForce(leftForce);
			}
		});
		rightJump.addListener(new ClickListener() {
			Vector2 rightForce = new Vector2(1f, 1f);

			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				GVars.activePlayer.applyForce(rightForce);
			}
		});
	}

	@Override
	public void draw() {
		labelTime.setText(GameTimer.getLeftString());
		labelCoins.setText(String.format("%02d", GameObject.getInstance().getCoinCount()));
		// Calculating radar rotation
		Vector2 player = Player.getInstance().body.getPosition();
		Vector2 target;
		if (null != GameObject.key) {
			target = GameObject.key.getPosition();
		} else {
			target = GameObject.exit.getPosition();
		}
		radar.setRotation(target.sub(player).angle() - 90);
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
		Assets.playSnd(Assets.failSnd);
	}

	public void gameStart() {
		Player.getInstance().resetGame();
		Gdx.input.setInputProcessor(instance);
		GameUIStage.startDialog.show(instance);
	}
}
