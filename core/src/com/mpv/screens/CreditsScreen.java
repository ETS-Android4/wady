package com.mpv.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Effect;
import com.mpv.data.GVars;

public class CreditsScreen implements Screen {

	private Stage stage;
	private static List<String> list = new List<String>(Assets.skin);
	private static String[] stringlist = { " SOFTWARE & TOOLS:", "    LibGDX, Eclipse,", "    InkScape, Gimp,",
			"     Tiled, Synfig", " ", "      RESOURCES:", "   opengameart.org", "    soundbible.com",
			"     soundjay.com" };

	public CreditsScreen() {
		stage = new Stage();
		final TextButton button = new TextButton("Ok", Assets.skin);

		Table table = new Table();
		Image image = new Image(Assets.skin.getDrawable("window"));
		image.setSize(stage.getWidth(), stage.getHeight());
		image.setPosition(0, 0);
		stage.addActor(image);
		stage.addActor(table);
		table.setFillParent(true);
		table.add(list).row();
		table.add(new Widget()).height(stage.getWidth() / 12.8f).row();
		table.add(button).size(stage.getWidth() / 6.4f, stage.getWidth() / 6.4f);

		list.setItems(stringlist);

		button.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Effect.button();
				GVars.app.setScreen(GVars.app.menuScreen);
			}
		});
		stage.addListener(new InputListener() {
			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					Effect.button();
					GVars.app.setScreen(GVars.app.menuScreen);
				}
				return false;
			}
		});

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setWorldSize(width, height);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Assets.pauseMusic();
		Assets.playMusic(Assets.menuMusic);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}