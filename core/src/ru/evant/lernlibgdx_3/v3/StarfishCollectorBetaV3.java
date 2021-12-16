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

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

public class StarfishCollectorBetaV3 extends GameBetaV3 {

    private int width = 1200;
    private int height = 900;

    private TurtleV3 turtle;
    private boolean win;

    ArrayList<StarfishV3> starfishActors; // Список звезд
    ArrayList<RockV3> rockActors; // Список камней

    public void initialize() {
        BaseActorV3 ocean = new BaseActorV3(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(width, height);
        BaseActorV3.setWorldBounds(ocean); // установить размер игрового мира по размеру фонового рисунка - океан

        // Заполняем список звезд
        starfishActors = new ArrayList<>();
        starfishActors.add(new StarfishV3(200, 400, mainStage));
        starfishActors.add(new StarfishV3(100, 200, mainStage));
        starfishActors.add(new StarfishV3(300, 200, mainStage));
        starfishActors.add(new StarfishV3(400, 400, mainStage));

        // Заполняем список камней
        rockActors = new ArrayList<>();
        rockActors.add(new RockV3(200, 300, mainStage));
        rockActors.add(new RockV3(100, 100, mainStage));
        rockActors.add(new RockV3(300, 100, mainStage));
        rockActors.add(new RockV3(400, 300, mainStage));

        turtle = new TurtleV3(20, 20, mainStage);

        win = false;
    }

    public void update(float dt) {

        for (RockV3 r : rockActors) turtle.preventOverlap(r);

        for (int i = 0; i < starfishActors.size(); i++) {
            StarfishV3 starfish = starfishActors.get(i);

            if (turtle.overlaps(starfish) && !starfish.collected) {

                starfish.collected = true;
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                WhirlpoolV3 whirl = new WhirlpoolV3(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);

                starfishActors.remove(starfish);
            }
        }

        if (starfishActors.size() == 0 && !win) {

            // удалить камни
            for (int i = 0; i < rockActors.size(); i++) {
                rockActors.get(i).clearActions();
                rockActors.get(i).addAction(Actions.fadeOut(1));            // удалить с экрана
                rockActors.get(i).addAction(Actions.after(Actions.removeActor()));  // удалить объект черепаха
            }
            rockActors.clear(); // удалить полигоны столкновений

            // удалить черепаху
            turtle.clearActions();
            turtle.addAction(Actions.fadeOut(1));               // удалить с экрана
           // turtle.addAction(Actions.after(Actions.removeActor()));   // удалить объект черепаха

            win = true;

            // Выводим сообщение о победе
            BaseActorV3 youWinMessage = new BaseActorV3(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            // Вывести в центр сообщение о победе
            youWinMessage.centerAtPosition(300, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}

