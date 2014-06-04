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
import com.mpv.game.world.GameObject;
import com.mpv.screens.dialogs.ExitDialog;

public class MainMenuStage extends Stage {

	private ExitDialog exitDialog;

	public MainMenuStage() {
		// TODO Auto-generated constructor stub
		super();
		float buttonWidth=this.getWidth()/3.2f;
		float buttonHeight=Const.PLAYER_SIZE*GVars.BOX_TO_WORLD/1.63f;
		Table table = new Table();
		//Debug
		//table.debug();
		this.addActor(table);

		table.setFillParent(true);
		exitDialog = 	new ExitDialog("", Assets.skin, "default");
		
		final TextButton bNewGame = new TextButton("Play", Assets.skin);
		final Widget widget1 = new Widget();
		final TextButton bHighScores = new TextButton("Scores", Assets.skin);
		final TextButton bLevels = new TextButton("Levels", Assets.skin);
		final TextButton bExit = new TextButton("Exit", Assets.skin);
		
		table.add(bNewGame).width(buttonWidth).height(buttonWidth);
		table.add(widget1).width(buttonHeight/2);
		table.add(bLevels).width(buttonWidth).height(buttonWidth);
		table.row();
		table.add(widget1).height(buttonHeight/2);
		table.row();
		table.add(bHighScores).width(buttonWidth).height(buttonWidth);
		table.add(widget1).height(buttonHeight/2);
		table.add(bExit).width(buttonWidth).height(buttonWidth);

		bNewGame.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				GameObject.getInstance().gameResume();
				GVars.app.setScreen(GVars.app.gameScreen);
				
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
