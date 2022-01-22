package com.mpv.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
import com.mpv.ui.GameScreen;

import java.util.Locale;

public class StartDialog extends CustomDialog {

    private final Label lTitle;
    private final Label lPoints;
    private final float bSize = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD * 1.2f;

    public StartDialog(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
        Button play = new Button(Assets.skin, "play-button");
        lTitle = new Label("", Assets.skin, "title-text");
        lPoints = new Label("", Assets.skin, "normal-text");
        Table goals = new Table();
        Table content = this.getContentTable();
        content.setFillParent(false);
        content.add(lTitle).height(bSize / 2f).center().row();
        content.add(new Label("Collect:", Assets.skin, "normal-text")).row();
        content.add(goals).height(bSize / 1.6f).width(bSize * 1.6f).row();
        goals.setBackground(Assets.skin.getDrawable("edit"));
        goals.add(new Image(Assets.skin.getDrawable("star-silver"))).size(bSize / 2f);
        goals.add(lPoints).height(bSize / 1.4f);
        this.button(play, true).key(Keys.ENTER, true);

        for (Cell<?> cell : this.getButtonTable().getCells()) {
            cell.size(bSize);
        }
    }

    @Override
    protected void result(Object obj) {
        GameObj.get().gameStart();
        Assets.gameMusic();
        Gdx.input.setInputProcessor(GameScreen.multiplexer);
    }

    @Override
    public Dialog show(Stage stage, Action action) {
        lTitle.setText(String.format(Locale.US, "Level %d", GameObj.mapIndex + 1));
        lPoints.setText(String.valueOf(GameObj.get().getPoints(0)));
        return super.show(stage, action);
    }
}
