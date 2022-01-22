package com.mpv.ui.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Sounds;
import com.mpv.data.Sounds.ID;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.world.GameObj;

public class LevelStage extends Stage {

    private Table levelList;
    private final ScrollPane scrollPane;
    private final float bWidth = this.getWidth() / 3.2f;
    private final float bHeight = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;
    private final ClickListener itemClick = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            // super.touchDown(event, x, y, pointer, button);
            if (!((Button) event.getListenerActor()).isDisabled()) {
                Sounds.play(ID.BUTTON);
                GameObj.mapIndex = (Integer) event.getListenerActor().getUserObject();
                Assets.loadMap(GameObj.mapIndex);
                GVars.app.setScreen(GVars.app.gameScreen);
            }
        }
    };

    public LevelStage() {
        super();

        scrollPane = new ScrollPane(levelList);
        scrollPane.setScrollingDisabled(true, false);

        updateList();

        Table mainTable = new Table();
        Table controlTable = new Table();
        Table scrollTable = new Table();
        Table scrollBg = new Table();
        scrollBg.setBackground(Assets.skin.getDrawable("edit"));
        scrollBg.add(scrollPane);
        scrollTable.setBackground(Assets.skin.getDrawable("window"));
        scrollTable.add(new Label("Select level", Assets.skin, "title-text")).height(bHeight).pad(bHeight / 6f).row();
        scrollTable.add(scrollBg).pad(bHeight / 6f);
        TextButton exitButton = new TextButton("Exit", Assets.skin, "default");

        Image image = new Image(Assets.skin.getDrawable("menu-screen"));
        image.setSize(getWidth(), getWidth() / image.getWidth() * image.getHeight());
        image.setPosition(0, 0);
        this.addActor(image);

        mainTable.setFillParent(true);
        this.addActor(mainTable);

        controlTable.add(exitButton).size(bWidth, bHeight).center();

        mainTable.add(scrollTable).pad(bHeight / 2f, bHeight / 6f, bHeight / 4f, bHeight / 6f).row();
        mainTable.add(controlTable).pad(bHeight / 4f);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // super.touchDown(event, x, y, pointer, button);
                Sounds.play(ID.BUTTON);
                GVars.app.setScreen(GVars.app.menuScreen);
            }
        });

        this.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
                    Sounds.play(ID.BUTTON);
                    GVars.app.setScreen(GVars.app.menuScreen);
                }
                return false;
            }
        });
    }

    public void updateList() {
        levelList = new Table();
        for (int i = 0; i < Settings.points.length; i++) {
            TextButton tb = new TextButton(String.valueOf(i + 1), Assets.skin, "default");
            tb.addListener(itemClick);
            tb.setUserObject(i);
            if (i != 0 && Settings.stars[i - 1] == 0 && Settings.stars[i] == 0) {
                tb.setDisabled(true);
            }
            Label lb = new Label(String.valueOf(Settings.points[i]), Assets.skin);
            lb.setAlignment(Align.right);
            levelList.add(tb).size(bHeight).pad(bHeight / 8f);
            levelList.add(lb).width(bWidth / 1.4f).pad(bHeight / 8f);
            for (int j = 0; j < Settings.stars[i]; j++) {
                levelList.add(new Image(Assets.skin.getDrawable("star-gold"))).size(bHeight / 3f);
            }
            for (int j = 0; j < 3 - Settings.stars[i]; j++) {
                levelList.add(new Image(Assets.skin.getDrawable("star-none"))).size(bHeight / 3f);
            }
            levelList.row();
        }
        scrollPane.setActor(levelList);
    }
}
