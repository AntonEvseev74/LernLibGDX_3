package ru.evant.lernlibgdx_3.v2;

//	Самообразование #3
//	Java Game Development with LibGDX, 2nd Edition

/*
    Класс Черепаха
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Turtle extends ActorBeta {

    // конструктор
    public Turtle() {
        super();
    }

    // активность объекта
    public void act(float dt) {
        super.act(dt); // вызвать метод act из суперкласса

        // управление движением объекта
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) this.moveBy(-1,0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) this.moveBy(1,0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) this.moveBy(0,1);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) this.moveBy(0,-1);
    }
}
