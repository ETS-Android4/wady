package com.wady.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mpv.ApplicationHandler;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Organizo";
		config.width = 480;
		config.height = 800;
		config.samples = 4;
		config.resizable = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new ApplicationHandler(), config);
	}
}
