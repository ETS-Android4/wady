package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.world.GameObject;

public class LevelStage extends Stage {

	private Table levelList;
	private ScrollPane scrollPane;
	private float bWidth = this.getWidth() / 3.2f;
	private float bHeight = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;
	private ClickListener itemClick = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// super.touchDown(event, x, y, pointer, button);
			if (!((Button) event.getListenerActor()).isDisabled()) {
				Assets.playSnd(Assets.buttonSnd);
				GameObject.mapIndex = (Integer) event.getListenerActor().getUserObject();
				Assets.loadMap(GameObject.mapIndex);
				GVars.app.setScreen(GVars.app.gameScreen);
			}
		}
	};

	public LevelStage() {
		super();

		scrollPane = new ScrollPane(levelList);
		scrollPane.setScrollingDisabled(true, false);

		updateList();

		Table mainTable = new Table();
		Table controlTable = new Table();
		Table scrollTable = new Table();
		scrollTable.setBackground(Assets.skin.getDrawable("window"));
		scrollTable.add(scrollPane);
		TextButton menuButton = new TextButton("Menu", Assets.skin, "default");
		TextButton playButton = new TextButton("Play", Assets.skin, "default");
		// Debug
		// mainTable.debug();
		// buttonTable.debug();
		mainTable.setBackground(Assets.skin.getTiledDrawable("menu-screen"));
		mainTable.setFillParent(true);
		this.addActor(mainTable);

		controlTable.add(menuButton).size(bWidth, bHeight).pad(bHeight / 2f).center();
		controlTable.add(playButton).size(bWidth, bHeight).pad(bHeight / 2f).center();
		controlTable.align(Align.top);

		mainTable.add(controlTable).pad(bHeight / 4f).row();
		mainTable.add(scrollTable).pad(bHeight / 4f);

		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				GameObject.mapIndex = 0;
				Assets.loadMap(GameObject.mapIndex);
				GVars.app.setScreen(GVars.app.gameScreen);
			}
		});
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
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
		levelList = new Table();
		for (int i = 0; i < Settings.points.length; i++) {
			TextButton tb = new TextButton(String.valueOf(i + 1), Assets.skin, "default");
			tb.addListener(itemClick);
			tb.setUserObject(new Integer(i));
			if (i != 0 && Settings.points[i - 1] == 0 && Settings.points[i] == 0) {
				tb.setDisabled(true);
			}
			Label lb = new Label(String.valueOf(Settings.points[i]), Assets.skin);
			lb.setAlignment(Align.right);
			levelList.add(tb).size(bHeight).pad(bHeight / 8f);
			levelList.add(lb).width(bWidth).pad(bHeight / 8f);
			for (int j = 0; j < Settings.stars[i]; j++) {
				levelList.add(new Image(Assets.skin.getDrawable("star-gold"))).size(bHeight / 3f);
			}
			for (int j = 0; j < 3 - Settings.stars[i]; j++) {
				levelList.add(new Image(Assets.skin.getDrawable("star-none"))).size(bHeight / 3f);
			}
			levelList.row();
		}
		scrollPane.setWidget(levelList);
	}
}
