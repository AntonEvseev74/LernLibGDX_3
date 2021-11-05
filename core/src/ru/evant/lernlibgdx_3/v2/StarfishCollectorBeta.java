package ru.evant.lernlibgdx_3.v2;

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
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StarfishCollectorBeta extends Game {

    private Turtle turtle;
    private ActorBeta starfish;
    private ActorBeta ocean;
    private ActorBeta winMessage;

    private Stage mainStage;

    private boolean win;

    public void create() {
        mainStage = new Stage();

        ocean = new ActorBeta();
        ocean.setTexture(new Texture(Gdx.files.internal("water.jpg")));
        mainStage.addActor(ocean);

        starfish = new ActorBeta();
        starfish.setTexture(new Texture(Gdx.files.internal("starfish.png")));
        starfish.setPosition(380, 380);
        mainStage.addActor(starfish);

        turtle = new Turtle();
        turtle.setTexture(new Texture(Gdx.files.internal("turtle-1.png")));
        turtle.setPosition(20, 20);
        mainStage.addActor(turtle);

        winMessage = new ActorBeta();
        winMessage.setTexture(new Texture(Gdx.files.internal("you-win.jpg")));
        winMessage.setPosition(300, 0);
        winMessage.setVisible(false);
        mainStage.addActor(winMessage);

        win = false;
    }

    public void render() {
// проверить ввод данных пользователем
        mainStage.act(1 / 60f);

// проверить условие выигрыша: черепаха должна перекрывать морскую звезду
        if (turtle.overlaps(starfish)) {
            starfish.remove();
            winMessage.setVisible(true);
        }

// очистить экран
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

// нарисовать графику
        mainStage.draw();
    }
}

