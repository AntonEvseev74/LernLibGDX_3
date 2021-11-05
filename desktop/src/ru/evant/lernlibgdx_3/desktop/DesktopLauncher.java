package ru.evant.lernlibgdx_3.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.evant.lernlibgdx_3.v1.StarfishCollectorAlpha;
import ru.evant.lernlibgdx_3.v2.StarfishCollectorBeta;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new StarfishCollectorAlpha(), config); 	// запустить 1ю версию
		new LwjglApplication(new StarfishCollectorBeta(), config); 		// запустить 2ю версию
	}
}
