package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;
import com.mpv.screens.dialogs.ExitDialog;

public class MainMenuStage extends Stage {

	private ExitDialog exitDialog;

	public MainMenuStage() {
		// TODO Auto-generated constructor stub
		super();
		float width=this.getWidth()/3.2f;
		float height=Const.PLAYER_SIZE*GVars.BOX_TO_WORLD/1.63f;

		Image image = new Image(Assets.skin.getDrawable("menu-screen"));
		image.setWidth(getWidth());
		image.setPosition(0, 0);
		this.addActor(image);

		exitDialog = new ExitDialog("", Assets.skin, "default");
		
		
		final TextButton bNewGame = new TextButton("Play", Assets.skin, "menu-button");

		final TextButton bHighScores = new TextButton("Scores", Assets.skin, "menu-button");
		final TextButton bCredits = new TextButton("Credits", Assets.skin, "menu-button");
		final TextButton bExit = new TextButton("Exit", Assets.skin, "menu-button");
		bNewGame.setSize(width, height);
		bHighScores.setSize(width, height);
		bCredits.setSize(width, height);
		bExit.setSize(width, height);
		
		bNewGame.setPosition(getWidth()*(-0.01f), getHeight()*0.8f);
		bHighScores.setPosition(getWidth()*0.22f, getHeight()*0.8f);
		bCredits.setPosition(getWidth()*0.51f, getHeight()*0.8f);
		bExit.setPosition(getWidth()*0.75f, getHeight()*0.8f);
		
		this.addActor(bNewGame);
		this.addActor(bHighScores);
		this.addActor(bCredits);
		this.addActor(bExit);
		
		bNewGame.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				if (GameObject.state == GameObject.FINISH) {
					GVars.app.setScreen(GVars.app.levelScreen);
				} else {
					GVars.app.setScreen(GVars.app.gameScreen);
				}
			}
		});
		bHighScores.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				GVars.app.setScreen(GVars.app.scoresScreen);
			}
		});
		bCredits.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				//GVars.app.setScreen(GVars.app.creditsScreen);
			}
		});
		bExit.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				exitDialog.show(event.getStage());
			}
		});        
		this.addListener(new InputListener() {
			public boolean keyUp (InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					exitDialog.show(event.getStage());
				}
				return false;
			}
		});     
	}

}
