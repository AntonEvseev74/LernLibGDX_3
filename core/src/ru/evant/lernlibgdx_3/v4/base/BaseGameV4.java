package ru.evant.lernlibgdx_3.v4.base;

import com.badlogic.gdx.Game;

public abstract class BaseGameV4 extends Game {
    private static BaseGameV4 game;

    public BaseGameV4() {
        game = this;
    }

    public static void setActiveScreen(BaseScreenV4 s) {
        game.setScreen(s);
    }

}
