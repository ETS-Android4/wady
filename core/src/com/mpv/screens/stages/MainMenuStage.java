package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Effect;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.screens.dialogs.ExitDialog;

public class MainMenuStage extends Stage {

	private ExitDialog exitDialog;

	public MainMenuStage() {
		super();
		float width = this.getWidth() / 1.6f;
		float height = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;

		Image image = new Image(Assets.skin.getDrawable("menu-screen"));
		image.setSize(getWidth(), getWidth() / image.getWidth() * image.getHeight());
		image.setPosition(0, 0);
		this.addActor(image);

		exitDialog = new ExitDialog("", Assets.skin, "default");

		final Button bMusic = new Button(Assets.skin, "music-button");
		final Button bSound = new Button(Assets.skin, "sound-button");

		final TextButton bNewGame = new TextButton("Play", Assets.skin);
		// final TextButton bHighScores = new TextButton("Scores", Assets.skin, "menu-button");
		final TextButton bCredits = new TextButton("Credits", Assets.skin);
		final TextButton bExit = new TextButton("Exit", Assets.skin);

		Table buttonTable = new Table();
		Table sndTable = new Table();
		Table parentTable = new Table();
		parentTable.setFillParent(true);
		sndTable.add(bSound).center().pad(height / 4f);
		sndTable.add(bMusic).center().pad(height / 4f);

		buttonTable.setBackground(Assets.skin.getDrawable("window"));
		buttonTable.align(Align.center);
		this.addActor(parentTable);
		parentTable.add(buttonTable);

		buttonTable.add(sndTable).size(height).pad(height / 4f).row();
		buttonTable.add(bNewGame).size(width, height).pad(height / 4f).row();
		buttonTable.add(bCredits).size(width, height).pad(height / 4f).row();
		buttonTable.add(bExit).size(width, height).pad(height / 4f);

		bMusic.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Settings.musicEnabled = !bMusic.isChecked();
				Assets.playMusic(Assets.menuMusic);
			}
		});
		bSound.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Settings.soundEnabled = !bSound.isChecked();
			}
		});

		// MENU buttons
		bNewGame.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Effect.button();
				GVars.app.setScreen(GVars.app.levelScreen);
			}
		});

		bCredits.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Effect.button();
				GVars.app.setScreen(GVars.app.creditsScreen);
			}
		});
		bExit.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Effect.button();
				exitDialog.show(event.getStage());
			}
		});
		this.addListener(new InputListener() {
			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					exitDialog.show(event.getStage());
				}
				return false;
			}
		});
	}

}
