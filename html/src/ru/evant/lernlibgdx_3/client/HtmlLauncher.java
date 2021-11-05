package ru.evant.lernlibgdx_3.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import ru.evant.lernlibgdx_3.v1.StarfishCollectorAlpha;
import ru.evant.lernlibgdx_3.v2.StarfishCollectorBeta;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                //return new StarfishCollectorAlpha();  // запустить 1ю версию
                return new StarfishCollectorBeta();     // запустить 2ю версию
        }
}