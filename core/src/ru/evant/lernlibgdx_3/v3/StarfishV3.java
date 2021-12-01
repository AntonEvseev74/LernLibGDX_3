package ru.evant.lernlibgdx_3.v3;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


public class StarfishV3 extends BaseActorV3{

    private boolean collected;

    public StarfishV3(float x, float y, Stage s) {
        super(x, y, s);

        collected = false;

        loadTexture("starfish.png");

        setBoundaryPolygon(8);

        Action spin = Actions.rotateBy(30,1);
        this.addAction(Actions.forever(spin));
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
        clearActions();
        addAction( Actions.fadeOut(1) );
        addAction( Actions.after( Actions.removeActor() ) );
    }

}
