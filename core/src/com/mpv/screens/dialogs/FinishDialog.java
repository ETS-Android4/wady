package com.mpv.screens.dialogs;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.esotericsoftware.tablelayout.Cell;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.world.GameObject;
import com.mpv.game.world.GameTimer;
import com.mpv.screens.GameScreen;
import com.mpv.tween.ActorAccessor;

public class FinishDialog extends Dialog {

	public static Label points = new Label("", Assets.skin, "normal-text");
	private ArrayList<Image> stars = new ArrayList<Image>(3);
	Widget empty = new Widget();
	Table starsTable = new Table();
	public FinishDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		Table pointsTable = new Table();
		
		//pointsTable.debug();
		//starsTable.debug();
		pointsTable.add(new Label("Points:", Assets.skin, "normal-text")).size(GVars.scrWidth/6.4f);
		pointsTable.add(empty).size(GVars.scrWidth/6.4f);
		pointsTable.add(points).size(GVars.scrWidth/4.8f).row();
		setStars();
		//this.getContentTable().debug();
		this.getContentTable().add(new Label("Completed!", skin, "title-text")).height(GVars.scrWidth/6.4f).row();
		this.getContentTable().add(empty).width(GVars.scrWidth/1.6f).height(GVars.scrWidth/12.8f).row();
		this.getContentTable().add(pointsTable).row();
		//this.getContentTable().add(empty).size(GVars.scrWidth/6.4f).row();
		this.getContentTable().add(starsTable).row();
		this.getContentTable().add(empty).height(GVars.scrWidth/12.8f);
		this.button("Menu", true).button("Retry", false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell :  this.getButtonTable().getCells()) {
			cell.size(Const.PLAYER_SIZE*GVars.BOX_TO_WORLD, Const.PLAYER_HALF*GVars.BOX_TO_WORLD);
		}
		points.setUserObject(Integer.valueOf(0));
	}
	private void setStars() {
		Image img;
		for (int i = 0; i<3; i++) {
			starsTable.add(empty).size(GVars.scrWidth/50f);
			img = new Image(Assets.skin.getDrawable("star-none"));
			img.setOrigin(img.getWidth()/2, img.getHeight()/2);
			stars.add(img);
			starsTable.add(img).width(GVars.scrWidth/6.4f);
			starsTable.add(empty).size(GVars.scrWidth/50f);
		}
	}
	protected void result (Object obj) {
		if (obj.equals(true)){
			this.hide();
			GVars.app.setScreen(GVars.app.levelScreen);
		}else {
			this.hide();
			GameObject.getInstance().gameResume();
			Gdx.input.setInputProcessor(GameScreen.multiplexer);
		}
	}
	private void animateStar(int i) {
		float scale = stars.get(i).getScaleX();
		
			stars.get(i).setDrawable(Assets.skin, "star-gold");
			Tween
				.set(stars.get(i), ActorAccessor.ALPHA).target(0f)
				.start(GVars.tweenManager);
			Tween
				.to(stars.get(i), ActorAccessor.ALPHA, 0.5f)
				.target(1f)
				.delay(4f + i*0.5f)
				.start(GVars.tweenManager);
			//Scale animation
			Tween
				.set(stars.get(i), ActorAccessor.SCALE).target(scale * 7)
				.start(GVars.tweenManager);
			Tween
				.to(stars.get(i), ActorAccessor.SCALE, 0.5f)
				.target(scale)
				.delay(4f + i*0.5f)
				.start(GVars.tweenManager);
	}
	@Override
	public Dialog show(Stage stage) {
		float mapTiming = GameTimer.getInstance().getSpent() / GameObject.getInstance().getMapLimit();
		Tween
			.set(points, ActorAccessor.TEXT).target(0)
			.start(GVars.tweenManager);
		Tween
			.to(points, ActorAccessor.TEXT, 3.5f)
			.target(Settings.points[GameObject.mapIndex])
			.delay(0.5f)
			.start(GVars.tweenManager);
		
		for (Image img : stars) 
			img.setDrawable(Assets.skin, "none");
		
		if (mapTiming < 0.875f) 	animateStar(0);
		if (mapTiming < 0.75f) 	animateStar(1);
		if (mapTiming < 0.5f) 	animateStar(2);

		Assets.playSnd(Assets.winSound);
		return super.show(stage);
	}
	
}
