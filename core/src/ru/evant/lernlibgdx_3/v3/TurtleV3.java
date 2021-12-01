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

        /*
        Максимальная скорость черепахи составит 100 пикселей в секунду.
        Значение ускорения 400 означает, что скорость будет увеличиваться на 400 пикселей в секунду каждую секунду,
        но поскольку максимальная скорость составляет 100 пикселей в секунду,
        черепаха достигнет этой скорости за 100/400 = 0,25 секунды (при запуске из состояния покоя).
         */
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
    }


    // активность объекта
    public void act(float dt) {
        super.act( dt );

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) accelerateAtAngle(270);

        applyPhysics(dt);

        setAnimationPaused( !isMoving() );

        if ( getSpeed() > 0 ) setRotation( getMotionAngle() );
    }
}
