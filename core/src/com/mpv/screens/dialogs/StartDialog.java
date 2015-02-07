package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObj;
import com.mpv.screens.GameScreen;

public class StartDialog extends CustomDialog {

	private Label lTitle;
	private Label lPoints;
	private float bWidth = Gdx.graphics.getWidth() / 3.2f;
	private float bHeight = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;

	public StartDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		lTitle = new Label("", Assets.skin, "title-text");
		lPoints = new Label("", Assets.skin, "normal-text");
		Table goals = new Table();
		Table content = this.getContentTable();
		content.setFillParent(false);
		content.add(lTitle).height(bHeight / 1.6f).pad(bHeight / 6f).center().row();
		content.add(new Label("Collect:", Assets.skin, "normal-text")).pad(bHeight / 6f).row();
		content.add(goals).height(bHeight).width(bWidth * 1.6f).pad(bHeight / 6f).row();
		goals.setBackground(Assets.skin.getDrawable("edit"));
		goals.add(new Image(Assets.skin.getDrawable("star-silver"))).size(bHeight / 1.2f).pad(bHeight / 6f);
		goals.add(lPoints).height(bHeight / 1.6f).pad(bHeight / 6f);
		this.button("Start", true).key(Keys.ENTER, true);

		for (Cell<?> cell : this.getButtonTable().getCells()) {
			cell.size(bWidth, bHeight).pad((bHeight / 6f), (bHeight / 4f), (bHeight / 6f), (bHeight / 4f));
		}
	}

	@Override
	protected void result(Object obj) {
		GameObj.get().gameStart();
		Gdx.input.setInputProcessor(GameScreen.multiplexer);
	}

	@Override
	public Dialog show(Stage stage, Action action) {
		lTitle.setText(String.format("Level %d", GameObj.mapIndex + 1));
		lPoints.setText(String.valueOf(GameObj.get().getPoints(0)));
		return super.show(stage, action);
	}
}
