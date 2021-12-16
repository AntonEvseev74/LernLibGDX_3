package ru.evant.lernlibgdx_3.v4.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.evant.lernlibgdx_3.v4.base.BaseActorV4;

public class WhirlpoolV4 extends BaseActorV4 {
    public WhirlpoolV4(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("whirlpool.png", 2,5,0.1f,false);
    }

    public void act(float dt){
        super.act(dt);
        if (isAnimationFinished()) remove();
    }
}
