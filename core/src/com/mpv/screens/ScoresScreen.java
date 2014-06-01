package com.mpv.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;

public class ScoresScreen implements Screen {

	public Stage stage;
	private Label label1;
	public Dialog nameDialog;
	public static List<String> list = new List<String>(Assets.skin);
	
	public ScoresScreen() {
		float buttonSize=Const.BLOCK_SIZE*GVars.BOX_TO_WORLD*1.6f;
		float itemSize=Const.BLOCK_SIZE*GVars.BOX_TO_WORLD*1.6f;
		stage = new Stage();
		label1 =new Label("Your score: ", Assets.skin, "default");
		Button okButton = new Button(Assets.skin, "button-ok");		
		Widget widget = new Widget();
        Table scoresTable = new Table();
        Table table1 = new Table();
        
        table1.setFillParent(true);
               
        table1.add(scoresTable).row();
        //scoresTable.setFillParent(true);
        scoresTable.setBackground(Assets.skin.getDrawable("none"));
		final Button buttonExit = new Button(Assets.skin, "button-exit");
		final TextField textField = new TextField(Settings.name, Assets.skin);
		textField.setMaxLength(10);
		scoresTable.add(new Label("High scores", Assets.skin, "default")).row();
		scoresTable.add(widget).height(itemSize/2).row();
		scoresTable.add(list).row();
        scoresTable.add(widget).height(itemSize).row();
        scoresTable.add(label1).height(itemSize/2).row();
        scoresTable.add(widget).height(itemSize/2);
        table1.add(widget).height(itemSize).row();
        table1.add(buttonExit).size(buttonSize);
        
        stage.addActor(table1);
        nameDialog = new Dialog("", Assets.skin, "dialog");
        nameDialog.getContentTable().add("New score!").row();
        nameDialog.getContentTable().add(textField).height(itemSize).width(buttonSize*3);
        nameDialog.getButtonTable().add(okButton).size(buttonSize);
        //nameDialog.button(okButton, true);
        okButton.addListener(new ClickListener() {
    		public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
    			//super.touchDown(event, x, y, pointer, button);
    			Assets.playSnd(Assets.buttonSound);
    			Settings.name = textField.getText();
    			//Settings.addScore(Settings.name, GameApp.gameObject.getMoves());
    			getScoreList();
    			nameDialog.hide();
        	}
    	});
        buttonExit.addListener(new ClickListener() {
    		public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
    			//super.touchDown(event, x, y, pointer, button);
    			Assets.playSnd(Assets.buttonSound);
    			GVars.app.setScreen(GVars.app.menuScreen);
        	}
    	});	            
        stage.addListener(new InputListener() {
        	public boolean keyUp (InputEvent event, int keycode) {
        		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
        			GVars.app.setScreen(GVars.app.menuScreen);
        		}
        		return false;
        	}
        });
	}

	public void resize (int width, int height) {
	        stage.getViewport().setWorldSize(width, height);
	}

	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);    
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
		label1.setText("Your score: ");
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //Table.drawDebug(stage); // Enables debug lines for tables.
	}

	@Override
	public void show() {
		getScoreList();
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Assets.pauseMusic();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Assets.playMusic(Assets.menuMusic);
	}
	private void getScoreList() {
		String[] tmplist = new String[5];
		for (int i = 0; i < 5; i++) {
			tmplist[i] = Integer.toString(Settings.highscores[i])
					.concat("   ")
					.concat(Settings.scorenames[i]);
		}
		ScoresScreen.list.setItems(tmplist);
	}
}