package com.mpv.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Sounds;
import com.mpv.data.Sounds.ID;
import com.mpv.data.GVars;

public class CreditsScreen implements Screen {

	private Stage stage;
	private String text = "SOFTWARE & TOOLS:\nLibGDX, Eclipse, InkScape, Gimp, Tiled, Synfig\n\n"
			+ "RESOURCES:\nopengameart.org\nsoundbible.com\nfreesound.org";
	private TextArea textArea = new TextArea("", Assets.skin);
	private float bWidth = Gdx.graphics.getWidth() / 3.2f;
	private float bHeight = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;

	public CreditsScreen() {
		stage = new Stage();
		final TextButton button = new TextButton("Ok", Assets.skin);
		textArea.setAlignment(Align.center);
		textArea.setTouchable(Touchable.disabled);
		textArea.setText(text);
		Table table = new Table();
		Image image = new Image(Assets.skin.getDrawable("menu-screen"));
		image.setSize(stage.getWidth(), stage.getWidth() / image.getWidth() * image.getHeight());
		image.setPosition(0, 0);
		stage.addActor(image);
		stage.addActor(table);
		table.setFillParent(true);
		table.add(textArea).size(stage.getWidth() / 1.2f, stage.getHeight() / 1.6f).row();
		table.add(new Widget()).height(stage.getWidth() / 12.8f).row();
		table.add(button).size(bWidth, bHeight);

		button.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				// super.touchDown(event, x, y, pointer, button);
				Sounds.play(ID.BUTTON);
				GVars.app.setScreen(GVars.app.menuScreen);
			}
		});
		stage.addListener(new InputListener() {
			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					Sounds.play(ID.BUTTON);
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