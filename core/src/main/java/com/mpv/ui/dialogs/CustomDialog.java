package com.mpv.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CustomDialog extends Dialog {

    public CustomDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    @Override
    public Dialog show(Stage stage, Action action) {
        return super.show(stage, null);
    }

    @Override
    public void hide(Action action) {
        super.hide(null);
    }

}
