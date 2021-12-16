package ru.evant.lernlibgdx_3.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.evant.lernlibgdx_3.v1.StarfishCollectorAlpha;
import ru.evant.lernlibgdx_3.v2.StarfishCollectorBeta;
import ru.evant.lernlibgdx_3.v3.StarfishCollectorBetaV3;
//import ru.evant.lernlibgdx_3.v4.StarfishCollectorBetaV4;
import ru.evant.lernlibgdx_3.v4.StarfishGameV4;

public class DesktopLauncher {
	public static void main (String[] arg) {
		/* //запуск версий с 1й по 3ю
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new StarfishCollectorAlpha(), config); 	// запустить 1ю версию
		//new LwjglApplication(new StarfishCollectorBeta(), config); 	// запустить 2ю версию
		//new LwjglApplication(new StarfishCollectorBetaV3(), config); 	// запустить 3ю версию
		 */

		// запустить 4ю версию
		Game myGame = new StarfishGameV4();
		LwjglApplication launcher = new LwjglApplication(myGame,"Starfish Collector", 800, 600);
	}
}
