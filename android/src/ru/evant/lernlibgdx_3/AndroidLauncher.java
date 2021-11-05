package ru.evant.lernlibgdx_3;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ru.evant.lernlibgdx_3.v1.StarfishCollectorAlpha;
import ru.evant.lernlibgdx_3.v2.StarfishCollectorBeta;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new StarfishCollectorAlpha(), config);	// запустить 1ю версию
		initialize(new StarfishCollectorBeta(), config);	// запустить 2ю версию
	}
}
