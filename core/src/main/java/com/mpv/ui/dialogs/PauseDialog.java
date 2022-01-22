package com.mpv.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Sounds;
import com.mpv.data.Sounds.ID;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObj;
import com.mpv.ui.GameScreen;

public class PauseDialog extends CustomDialog {

    private final float bSize = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD * 1.2f;

    public PauseDialog(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
        Button play = new Button(Assets.skin, "play-button");
        Button no = new Button(Assets.skin, "no-button");
        this.getContentTable().add(new Label("PAUSE", skin, "title-text")).height(bSize / 1.6f).row();
        // this.getContentTable().add(new Image()).size(GVars.scrWidth/3.2f);
        this.button(no, true).button(play, false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
        for (Cell<?> cell : this.getButtonTable().getCells()) {
            cell.size(bSize);
        }
    }

    @Override
    protected void result(Object obj) {
        Sounds.play(ID.BUTTON);
        if (obj.equals(true)) {
            this.hide();
            GVars.app.setScreen(GVars.app.levelScreen);
        } else {
            this.hide();
            Gdx.input.setInputProcessor(GameScreen.multiplexer);
            GameObj.get().gameResume();
        }
    }
}
