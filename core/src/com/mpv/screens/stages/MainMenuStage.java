package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.screens.dialogs.ExitDialog;

public class MainMenuStage extends Stage {

	private ExitDialog exitDialog;

	public MainMenuStage() {
		// TODO Auto-generated constructor stub
		float buttonWidth=this.getWidth()/1.63f;
		float buttonHeight=Const.PLAYER_SIZE*GVars.BOX_TO_WORLD/1.63f;
		Table table = new Table();
		//Debug
		//table.debug();
		this.addActor(table);

		table.setFillParent(true);
		exitDialog = 	new ExitDialog("", Assets.skin, "default");
		
		final TextButton bNewGame = new TextButton("Play", Assets.skin);
		final Widget widget1 = new Widget();
		final TextButton bHighScores = new TextButton("High scores", Assets.skin);
		final TextButton bExit = new TextButton("Exit", Assets.skin);
		
		table.add(bNewGame).width(buttonWidth).height(buttonHeight);
		table.row();
		table.add(widget1).height(buttonHeight/2);
		table.row();
		table.add(bHighScores).width(buttonWidth).height(buttonHeight);
		table.row();
		table.add(widget1).height(buttonHeight/2);
		table.row();
		table.add(bExit).width(buttonWidth).height(buttonHeight);


		bNewGame.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				GVars.app.setScreen(GVars.app.gameScreen);
			}
		});
		bHighScores.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
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
					//
				}
				return false;
			}
		});     
	}

}
