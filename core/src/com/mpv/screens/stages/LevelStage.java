package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;

public class LevelStage extends Stage {

	List<Table> levelList = new List<Table>(Assets.skin);

	public LevelStage() {
		super();
		float width = this.getWidth() / 3.2f;
		float height = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;
		Table mainTable = new Table();

		ScrollPane scrollPane = new ScrollPane(levelList);
		TextButton menuButton = new TextButton("Menu", Assets.skin, "default");
		TextButton playButton = new TextButton("Play", Assets.skin, "default");

		// Debug
		// mainTable.debug();
		// buttonTable.debug();
		mainTable.setBackground(Assets.skin.getTiledDrawable("menu-screen"));
		mainTable.setFillParent(true);
		this.addActor(mainTable);

		// left/right buttons
		Table controlTable = new Table();

		controlTable.add(menuButton).size(width, height).pad(height / 2f).center();
		controlTable.add(playButton).size(width, height).pad(height / 2f).center();
		controlTable.align(Align.top);
		mainTable.add(controlTable).pad(height).row();
		mainTable.add(scrollPane);

		updateList();

		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				GameObject.mapIndex = 0;
				Assets.loadMap(0, GameObject.mapIndex);
				GVars.app.setScreen(GVars.app.gameScreen);
			}
		});
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				GVars.app.setScreen(GVars.app.menuScreen);
			}
		});

		this.addListener(new InputListener() {
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					Assets.playSnd(Assets.buttonSnd);
					GVars.app.setScreen(GVars.app.menuScreen);
				}
				return false;
			}
		});
	}

	public void updateList() {

	}
}
