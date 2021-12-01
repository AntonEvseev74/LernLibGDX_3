package ru.evant.lernlibgdx_3.v3;

//	Самообразование #3
//	Java Game Development with LibGDX, 2nd Edition

/*
    Класс Черепаха
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TurtleV3 extends BaseActorV3 {

    // конструктор
    public TurtleV3(float x, float y, Stage s) {
        super(x, y, s);

        String[] fileNames = {
                "turtle-1.png", "turtle-2.png", "turtle-3.png",
                "turtle-4.png", "turtle-5.png", "turtle-6.png"
        };

        loadAnimationFromFiles(fileNames,0.1f,true);
    }

    /*
    // активность объекта
    public void act(float dt) {
        super.act(dt); // вызвать метод act из суперкласса

        // управление движением объекта
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) this.moveBy(-1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) this.moveBy(1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) this.moveBy(0, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) this.moveBy(0, -1);
    }

     */
}
