package com.mpv.screens.dialogs;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.world.GameObject;
import com.mpv.game.world.GameTimer;
import com.mpv.screens.GameScreen;
import com.mpv.tween.ActorAccessor;

public class FinishDialog extends CustomDialog {

	private float bWidth = Gdx.graphics.getWidth() / 3.2f;
	private float bHeight = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;
	private static Label points = new Label("", Assets.skin, "title-text");
	private static Label ldiam = new Label("000", Assets.skin, "normal-text");
	private static Label ltime = new Label("000", Assets.skin, "normal-text");
	private ArrayList<Image> stars = new ArrayList<Image>(3);
	Widget empty = new Widget();
	Table starsTable = new Table();
	Boolean dvisible = false;
	private FinishDialog instance;
	TweenCallback cb = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			if (dvisible) {
				Assets.playSnd(Assets.blopSnd);
			}
		}
	};
	TweenCallback cbCounterBegin = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			// TODO Auto-generated method stub
			instance.getButtonTable().setVisible(true);
			if (dvisible) {
				Assets.playSnd(Assets.counterSnd, 0.05f);
			}
		}
	};

	public FinishDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		instance = this;
		Table pointsTable = new Table();
		Table diamondsTable = new Table();
		Table timeTable = new Table();
		Table content = this.getContentTable();
		// pointsTable.debug();
		// starsTable.debug();
		diamondsTable.add(new Image(Assets.skin.getDrawable("diamond"))).size(bHeight / 1.6f).pad(bHeight / 6f);
		diamondsTable.add(ldiam).height(bHeight / 1.6f).width(bWidth / 2).pad(bHeight / 6f);
		timeTable.add(new Image(Assets.skin.getDrawable("battery"))).size(bHeight / 1.6f).pad(bHeight / 6f);
		timeTable.add(ltime).height(bHeight / 1.6f).width(bWidth / 2).pad(bHeight / 6f);
		pointsTable.setBackground(Assets.skin.getDrawable("edit"));
		pointsTable.add(diamondsTable).height(bHeight / 1.6f).pad(bHeight / 6f).row();
		pointsTable.add(timeTable).height(bHeight / 1.6f).pad(bHeight / 6f).row();
		pointsTable.add(points).size(bWidth, bHeight / 1.2f)
				.pad(bHeight / 1.6f, bHeight / 6f, bHeight / 6f, bHeight / 6f).row();
		points.setAlignment(Align.center);
		starsTable.setBackground(Assets.skin.getDrawable("edit"));
		setStars();
		// this.getContentTable().debug();
		content.add(new Label("Completed!", skin, "title-text")).height(bHeight / 1.6f).pad(bHeight / 6f).row();
		content.add(pointsTable).width(bWidth * 2f).pad(bHeight / 6f).row();
		content.add(starsTable).width(bWidth * 2f).pad(bHeight / 6f).row();
		this.button("Retry", false).button("Menu", true).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell : this.getButtonTable().getCells()) {
			cell.size(bWidth, bHeight).pad(bHeight / 6f);
		}
		points.setUserObject(Float.valueOf(0));
		ldiam.setUserObject(Float.valueOf(0));
		ltime.setUserObject(Float.valueOf(0));
	}

	private void setStars() {
		Image img;
		for (int i = 0; i < 3; i++) {
			starsTable.add(empty).size(GVars.scrWidth / 50f);
			img = new Image(Assets.skin.getDrawable("star-none"));
			img.setOrigin(img.getWidth() / 2, img.getHeight() / 2);
			stars.add(img);
			starsTable.add(img).width(GVars.scrWidth / 6.4f);
			starsTable.add(empty).size(GVars.scrWidth / 50f);
		}
	}

	@Override
	protected void result(Object obj) {
		if (obj.equals(true)) {
			this.hide();
			GVars.app.setScreen(GVars.app.levelScreen);
		} else {
			this.hide();
			GameObject.getInstance().gameResume();
			Gdx.input.setInputProcessor(GameScreen.multiplexer);
		}
		Assets.counterSnd.stop();
		dvisible = false;
		Assets.playSnd(Assets.buttonSnd);
	}

	private void animateStar(int i, float delay) {
		float scale = stars.get(i).getScaleX();

		stars.get(i).setDrawable(Assets.skin, "star-gold");
		Tween.set(stars.get(i), ActorAccessor.ALPHA).target(0f).start(GVars.tweenManager);
		Tween.to(stars.get(i), ActorAccessor.ALPHA, 0.5f).target(1f).delay(delay + i * 0.5f).start(GVars.tweenManager);
		// Scale animation
		Tween.set(stars.get(i), ActorAccessor.SCALE).target(scale * 7).start(GVars.tweenManager);
		Tween.to(stars.get(i), ActorAccessor.SCALE, 0.5f).target(scale).delay(delay + i * 0.5f).setCallback(cb)
				.setCallbackTriggers(TweenCallback.END).start(GVars.tweenManager);

	}

	@Override
	public Dialog show(Stage stage) {
		float diamDelay = 0.3f * GameObject.getInstance().getCoinCount();
		float timeDelay = 3.5f;
		float pause = 0.5f;
		int totalPoints = GameObject.getInstance().getCoinCount() * 10;
		this.getButtonTable().setVisible(false);

		Tween.set(ldiam, ActorAccessor.TEXT).target(GameObject.getInstance().getCoinCount()).start(GVars.tweenManager);
		Tween.to(ldiam, ActorAccessor.TEXT, diamDelay).target(0).delay(pause).start(GVars.tweenManager);
		Tween.set(ltime, ActorAccessor.TEXT).target(GameTimer.getInstance().getLeftSec()).start(GVars.tweenManager);
		Tween.to(ltime, ActorAccessor.TEXT, timeDelay).target(0).delay(diamDelay + pause * 2).start(GVars.tweenManager);

		Tween.set(points, ActorAccessor.TEXT).target(0).start(GVars.tweenManager);
		Tween.to(points, ActorAccessor.TEXT, diamDelay).target(totalPoints).delay(pause)
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						if (dvisible) {
							Assets.playSnd(Assets.dingSnd);
						}
					}
				}).setCallbackTriggers(TweenCallback.BEGIN).start(GVars.tweenManager);

		totalPoints += GameTimer.getInstance().getLeftSec() * 5;
		Tween.to(points, ActorAccessor.TEXT, timeDelay).target(totalPoints).delay(diamDelay + pause * 2)
				.setCallback(cbCounterBegin).setCallbackTriggers(TweenCallback.BEGIN).start(GVars.tweenManager);
		if (Settings.points[GameObject.mapIndex] < totalPoints) {
			Settings.points[GameObject.mapIndex] = totalPoints;
		}
		float totalDelay = diamDelay + pause * 2 + timeDelay;
		int star = 0;
		for (Image img : stars) {
			img.setDrawable(Assets.skin, "none");
		}

		if (totalPoints >= GameObject.getInstance().getPoints(0)) {
			animateStar(0, totalDelay);
			star = 1;
		}
		if (totalPoints >= GameObject.getInstance().getPoints(1)) {
			animateStar(1, totalDelay);
			star = 2;
		}
		if (totalPoints >= GameObject.getInstance().getPoints(2)) {
			animateStar(2, totalDelay);
			star = 3;
		}
		if (Settings.stars[GameObject.mapIndex] < star) {
			Settings.stars[GameObject.mapIndex] = star;
		}
		if (star == 0) {
			Assets.playSnd(Assets.failSnd);
		} else {
			Assets.playSnd(Assets.winSnd);
		}
		dvisible = true;
		return super.show(stage);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		points.setText(String.format("%04d", Math.round((Float) points.getUserObject())));
		ldiam.setText(String.format("%03d", Math.round((Float) ldiam.getUserObject())));
		ltime.setText(String.format("%03d", Math.round((Float) ltime.getUserObject())));
		super.draw(batch, parentAlpha);
	}

}