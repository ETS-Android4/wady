package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.world.GameObject;

public class LevelStage extends Stage {

    private int tmpIndex = 0;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    public LevelStage() {
	super();
	float buttonSize = getWidth() / 6f;
	Table mainTable = new Table();
	Table buttonTable = new Table();
	Widget emptyWidget = new Widget();
	TextButton menuButton = new TextButton("Menu", Assets.skin, "default");
	TextButton playButton = new TextButton("Play", Assets.skin, "default");

	// Debug
	// mainTable.debug();
	// buttonTable.debug();
	Image image = new Image(Assets.skin.getDrawable("menu-screen"));
	image.setSize(getWidth(), getWidth() / image.getWidth() * image.getHeight());
	image.setPosition(0, 0);
	this.addActor(image);
	this.addActor(mainTable);

	mainTable.setFillParent(true);
	// left/right buttons
	Table controlTable = new Table();

	controlTable.add(menuButton).size(buttonSize * 1.6f, buttonSize).left();
	controlTable.add(emptyWidget).width(buttonSize);
	controlTable.add(playButton).size(buttonSize * 1.6f, buttonSize).right();
	mainTable.add(controlTable).row();
	mainTable.add(emptyWidget).height(buttonSize).row();
	mainTable.add(buttonTable).row();
	mainTable.add(emptyWidget).height(buttonSize / 2).row();
	// buttonTable.setFillParent(true);
	ImageButton tmp;
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		buttonTable.add(emptyWidget).width(buttonSize / 4);
		tmp = new ImageButton(Assets.skin, "default");
		tmp.getImageCell().size(buttonSize / 1.6f);
		buttonGroup.add(tmp);
		buttonTable.add(tmp).size(buttonSize);
		buttonTable.add(emptyWidget).width(buttonSize / 4);
	    }
	    buttonTable.row();
	    buttonTable.add(emptyWidget).height(buttonSize / 2).row();
	}
	updateButtons();

	buttonTable.addListener(new ClickListener() {
	    @Override
	    public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
		// super.touchDown(event, x, y, pointer, button);
		Assets.playSnd(Assets.buttonSnd);
		tmpIndex = buttonGroup.getButtons().indexOf(buttonGroup.getChecked(), true);
	    }
	});

	playButton.addListener(new ClickListener() {
	    @Override
	    public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
		// super.touchDown(event, x, y, pointer, button);
		Assets.playSnd(Assets.buttonSnd);
		GameObject.mapIndex = tmpIndex;
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

    public void updateButtons() {
	for (int i = 0; i < 16; i++) {
	    ImageButton t = (ImageButton) buttonGroup.getButtons().get(i);
	    if ((i != 0 && Settings.points[i - 1] == 0) || i > 8) {
		t.setStyle(Assets.skin.get("default", ImageButtonStyle.class));
		t.setDisabled(true);
	    } else {
		t.setDisabled(false);
		switch (Settings.stars[i]) {
		case 0:
		    t.setStyle(Assets.skin.get("default", ImageButtonStyle.class));
		    t.setChecked(true);
		    tmpIndex = i;
		    break;
		case 1:
		    t.setStyle(Assets.skin.get("none", ImageButtonStyle.class));
		    break;
		case 2:
		    t.setStyle(Assets.skin.get("silver", ImageButtonStyle.class));
		    break;
		case 3:
		    t.setStyle(Assets.skin.get("gold", ImageButtonStyle.class));
		    break;
		default:
		    t.setStyle(Assets.skin.get("default", ImageButtonStyle.class));
		    break;
		}

	    }
	}
    }
}
