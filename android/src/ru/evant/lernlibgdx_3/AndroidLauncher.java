package ru.evant.lernlibgdx_3;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ru.evant.lernlibgdx_3.v1.StarfishCollectorAlpha;
import ru.evant.lernlibgdx_3.v2.StarfishCollectorBeta;
import ru.evant.lernlibgdx_3.v3.StarfishCollectorBetaV3;
import ru.evant.lernlibgdx_3.v4.StarfishCollectorBetaV4;
import ru.evant.lernlibgdx_3.v4.StarfishGameV4;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new StarfishCollectorAlpha(), config);	// запустить 1ю версию
		//initialize(new StarfishCollectorBeta(), config);	// запустить 2ю версию
		//initialize(new StarfishCollectorBetaV3(), config);	// запустить 3ю версию
		initialize(new StarfishGameV4(), config);	// запустить 4ю версию
	}
}
