package ru.evant.lernlibgdx_3.v4.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.evant.lernlibgdx_3.v4.base.BaseActorV4;

public class SharkV4 extends BaseActorV4 {

    public SharkV4(float x, float y, Stage s) {
        super(x,y,s);
        loadTexture("sharky.png");
        setBoundaryPolygon(8);
    }
}
