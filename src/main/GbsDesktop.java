package main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import main.GbsApplication;

public class GbsDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-gdx-game";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;
    cfg.useGL20 = true;
		
		LwjglApplication app = new LwjglApplication(new GbsApplication(), cfg);
	}
}
