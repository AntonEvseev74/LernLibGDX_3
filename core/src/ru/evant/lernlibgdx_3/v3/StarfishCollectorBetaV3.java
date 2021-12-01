package ru.evant.lernlibgdx_3.v3;

//	Самообразование #3
//	Java Game Development with LibGDX, 2nd Edition

/*
        Будет создан объект сцены, и к нему будут добавлены объекты класса Actor(актеры).
    Кроме того, должны быть вызваны методы act и draw класса Stage(сцена) (напомним,
    что вызов методов act и draw класса Stage приводит к тому, что объект класса Stage
    вызывает методы act и draw всех объектов-участников, которые были добавлены к нему).
    Порядок, в котором актеры добавляются на сцену(Stage), важен так же, как порядок
    операторов рисования был важен при использовании пакетного объекта в альфа-версии
    этой программы. Объекты-актеры, добавленные на сцену первыми, будут нарисованы первыми,
    таким образом, появятся ниже тех, которые будут добавлены позже. По этой причине фон
    должен быть добавлен первым, а элементы пользовательского интерфейса (такие как текстовые дисплеи)
    должны быть добавлены последними.
        Начальная видимость winMessage установлена в значение false, потому что игрок не должен видеть
    это конкретное изображение до тех пор, пока он не выиграет игру.
        Метод act требует ввода: количество времени (в секундах), прошедшего с момента предыдущей итерации
    игрового цикла. Поскольку игры в LibGDX по умолчанию работают со скоростью 60 кадров в секунду,
    когда это возможно, мы использовали 1/60 для этого значения. (На практике количество времени,
    необходимое для каждой итерации игрового цикла, может колебаться, и существуют методы, позволяющие
    получить точное значение, но здесь они не нужны.)
        Когда черепаха перекрывает морскую звезду, для морской звезды вызывается метод remove. Это удаляет
    морскую звезду из объекта сцены, к которому она была добавлена ранее. В результате в этот момент
    больше не вызываются методы действия и рисования объекта starfish, в результате чего изображение
    морской звезды исчезает с экрана. (Однако, несмотря на то, что он больше не виден, объект "морская звезда"
    остается в памяти компьютера.)
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class StarfishCollectorBetaV3 extends GameBetaV3 {

    private TurtleV3 turtleV3;
    private StarfishV3 starfish;
    private BaseActorV3 ocean;

    // создать объекты
    @Override
    public void initialize() {
        ocean = new BaseActorV3(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        starfish = new StarfishV3(380, 380, mainStage);

        turtleV3 = new TurtleV3(20, 20, mainStage);
    }

    // изменить, обновить, нарисовать обекты
    @Override
    public void update(float dt) {
        if (turtleV3.overlaps(starfish) && !starfish.isCollected()) {
            starfish.collect();
            WhirlpoolV3 whirl = new WhirlpoolV3(0, 0, mainStage);
            whirl.centerAtActor(starfish);
            whirl.setOpacity(0.25f);

            BaseActorV3 youWinMessage = new BaseActorV3(0, 0, mainStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

    }
}

