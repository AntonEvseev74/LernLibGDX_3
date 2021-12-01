package ru.evant.lernlibgdx_3.v3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameBetaV3 extends Game {

    protected Stage mainStage;

    @Override
    public void create() {
        mainStage = new Stage();
        initialize();
    }
    public abstract void initialize();

    public void render(){
        float dt = Gdx.graphics.getDeltaTime();

        // Действия объекта(активность объекта)
        mainStage.act(dt);

        // Определяется пользователем
        update(dt);

        // Очистить экран
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // нарисовать графику
        mainStage.draw();
    }

    public abstract void update(float dt);
}
