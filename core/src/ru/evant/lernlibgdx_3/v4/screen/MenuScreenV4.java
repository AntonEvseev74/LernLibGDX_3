package ru.evant.lernlibgdx_3.v4.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import ru.evant.lernlibgdx_3.v4.StarfishGameV4;
import ru.evant.lernlibgdx_3.v4.base.BaseActorV4;
import ru.evant.lernlibgdx_3.v4.base.BaseScreenV4;

public class MenuScreenV4 extends BaseScreenV4 {
    @Override
    public void initialize() {
        BaseActorV4 ocean = new BaseActorV4(0,0, mainStage);
        ocean.loadTexture( "water.jpg" );
        ocean.setSize(800,600);
        BaseActorV4 title = new BaseActorV4(0,0, mainStage);
        title.loadTexture( "starfish-collector.png" );
        title.centerAtPosition(400,300);
        title.moveBy(0,100);
        BaseActorV4 start = new BaseActorV4(0,0, mainStage);
        start.loadTexture( "message-start.png" );
        start.centerAtPosition(400,300);
        start.moveBy(0,-100);

    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) StarfishGameV4.setActiveScreen( new LevelScreen1V4() );
    }
}
