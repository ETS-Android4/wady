package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.screens.dialogs.ExitDialog;

public class MainMenuStage extends Stage {

	private ExitDialog exitDialog;

	public MainMenuStage() {
		// TODO Auto-generated constructor stub
		super();
		float width=this.getWidth()/3.2f;
		float height=Const.PLAYER_SIZE*GVars.BOX_TO_WORLD/1.63f;
		float yPos=this.getWidth()*1.28f;

		Image image = new Image(Assets.skin.getDrawable("menu-screen"));
		image.setSize(getWidth(), getWidth()/image.getWidth()*image.getHeight());
		image.setPosition(0, 0);
		this.addActor(image);

		exitDialog = new ExitDialog("", Assets.skin, "default");
		
		final Button bMusic = new Button(Assets.skin, "music-button");
		final Button bSound  = new Button(Assets.skin, "sound-button");
		
		final TextButton bNewGame = new TextButton("Play", Assets.skin, "menu-button");
		final TextButton bHighScores = new TextButton("Scores", Assets.skin, "menu-button");
		final TextButton bCredits = new TextButton("Credits", Assets.skin, "menu-button");
		final TextButton bExit = new TextButton("Exit", Assets.skin, "menu-button");
		
		bMusic.setSize(width/3f, width/3f);
		bMusic.setChecked(!Settings.musicEnabled);
		
		bSound.setSize(width/3f, width/3f);
		bSound.setChecked(!Settings.soundEnabled);
		
		bNewGame.setSize(width, height);
		bHighScores.setSize(width, height);
		bCredits.setSize(width, height);
		bExit.setSize(width, height);
		
		bNewGame.setPosition(getWidth()*(-0.01f), yPos);
		bHighScores.setPosition(getWidth()*0.22f, yPos);
		bCredits.setPosition(getWidth()*0.51f, yPos);
		bExit.setPosition(getWidth()*0.75f, yPos);
		bSound.setPosition(getWidth()*0.51f, getWidth()*1.5f);
		bMusic.setPosition(getWidth()*0.39f, getWidth()*1.5f);
		
		this.addActor(bNewGame);
		this.addActor(bHighScores);
		this.addActor(bCredits);
		this.addActor(bExit);
		this.addActor(bSound);
		this.addActor(bMusic);
		
		bMusic.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Settings.musicEnabled = !bMusic.isChecked();
				Assets.playMusic(Assets.menuMusic);
			}
		});
		bSound.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Settings.soundEnabled = !bSound.isChecked();
			}
		});
		
		// MENU buttons
		bNewGame.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				GVars.app.setScreen(GVars.app.levelScreen);				
			}
		});
		bHighScores.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				GVars.app.setScreen(GVars.app.scoresScreen);
			}
		});
		bCredits.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				//GVars.app.setScreen(GVars.app.creditsScreen);
			}
		});
		bExit.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
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
