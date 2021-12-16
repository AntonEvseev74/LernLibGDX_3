package ru.evant.lernlibgdx_3.v4.actor;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.evant.lernlibgdx_3.v4.base.BaseActorV4;

public class StarfishV4 extends BaseActorV4 {
    public boolean collected;

    public StarfishV4(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("starfish.png");

        Action spin = Actions.rotateBy(30,1);
        this.addAction(Actions.forever(spin));

        setBoundaryPolygon(8);

        collected = false;
    }
}
