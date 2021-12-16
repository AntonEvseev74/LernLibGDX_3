package ru.evant.lernlibgdx_3.v4;

import ru.evant.lernlibgdx_3.v4.base.BaseGameV4;
import ru.evant.lernlibgdx_3.v4.screen.MenuScreenV4;

public class StarfishGameV4 extends BaseGameV4 {
    @Override
    public void create() {
        setActiveScreen( new MenuScreenV4() );
    }
}
