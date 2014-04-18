package com.mpv.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class MainMenuStage extends Stage {

	private Dialog exitDialog;

	public MainMenuStage() {
		// TODO Auto-generated constructor stub
		float buttonWidth=Const.BLOCK_SIZE*GVars.BOX_TO_WORLD;
		float buttonHeight=Const.BLOCK_HALF*GVars.BOX_TO_WORLD*1.5f;
		Table table = new Table();
		this.addActor(table);	  

		table.setFillParent(true); 
		exitDialog = 
				new Dialog("", Assets.skin, "default") {
			protected void result (Object obj) {
				if (obj.equals(true)){
					Gdx.app.exit();
				}else {

				}
			}
		}.text("Are you sure?").button("Exit", true).button("Back", false).key(Keys.ENTER, true)
		.key(Keys.ESCAPE, false);
		final TextButton button1 = new TextButton("New game", Assets.skin);
		final Widget widget1 = new Widget();
		final TextButton button2 = new TextButton("Continue", Assets.skin);

		button2.setDisabled(true);
		final TextButton button3 = new TextButton("Exit", Assets.skin);       
		table.add(button1).width(buttonWidth).height(buttonHeight);
		table.row();
		table.add(widget1).height(buttonHeight/2);
		table.row();
		table.add(button2).width(buttonWidth).height(buttonHeight);
		table.row();
		table.add(widget1).height(buttonHeight/2);
		table.row();
		table.add(button3).width(buttonWidth).height(buttonHeight);


		button1.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);

			}
		});
		button2.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
			}
		});
		button3.addListener(new ClickListener() {
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
