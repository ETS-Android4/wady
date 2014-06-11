package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
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
		Table tableButtons = new Table(), tableMain = new Table();
		//Debug
		//tableButtons.debug();
		//tableMain.debug();
		Image image = new Image(Assets.skin.getDrawable("menu-screen"));
		image.setSize(getWidth(), getWidth());
		image.setPosition(0, 0);
		this.addActor(image);
		this.addActor(tableMain);
		
		tableMain.setFillParent(true);
		tableMain.top();
		tableButtons.setFillParent(true);
		//tableMain.setBackground(Assets.skin.getTiledDrawable("menu-bg"));
		exitDialog = 	new ExitDialog("", Assets.skin, "default");
		
		final TextButton bNewGame = new TextButton("Play", Assets.skin, "menu-button");
		final Widget widget1 = new Widget();
		final TextButton bHighScores = new TextButton("Scores", Assets.skin, "menu-button");
		final TextButton bLevels = new TextButton("Levels", Assets.skin, "menu-button");
		final TextButton bExit = new TextButton("Exit", Assets.skin, "menu-button");
		
		tableMain.add(widget1).height(height).row();
		tableMain.add(widget1);
		tableMain.add(bNewGame).size(width/1.6f, height);
		tableMain.add(widget1);
		tableMain.add(bLevels).size(width/1.6f, height);
		tableMain.add(widget1);
		tableMain.row();
		//tableMain.add(tableButtons);
		

		//tableButtons.row();
		tableMain.add(bHighScores).size(width, height);
		tableMain.add(widget1).width(width/4);
		tableMain.add(widget1).width(width/4);
		tableMain.add(widget1).width(width/4);
		tableMain.add(bExit).size(width, height);
		
		

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
		bLevels.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				GVars.app.setScreen(GVars.app.levelScreen);
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
