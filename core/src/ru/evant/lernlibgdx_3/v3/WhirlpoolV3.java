package ru.evant.lernlibgdx_3.v3;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class WhirlpoolV3 extends BaseActorV3{
    public WhirlpoolV3(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("whirlpool.png", 2,5,0.1f,false);
    }

    public void act(float dt){
        super.act(dt);
        if (isAnimationFinished()) remove();
    }
}
