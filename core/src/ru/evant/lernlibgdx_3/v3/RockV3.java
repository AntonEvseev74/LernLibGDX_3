package ru.evant.lernlibgdx_3.v3;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class RockV3 extends BaseActorV3 {


    public RockV3(float x, float y, Stage s) {
        super(x,y,s);
        loadTexture("rock.png");
        setBoundaryPolygon(8);
    }
}
