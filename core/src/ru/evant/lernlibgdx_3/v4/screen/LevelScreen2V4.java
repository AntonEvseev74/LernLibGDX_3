package ru.evant.lernlibgdx_3.v4.screen;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import ru.evant.lernlibgdx_3.v4.actor.RockV4;
import ru.evant.lernlibgdx_3.v4.actor.SharkV4;
import ru.evant.lernlibgdx_3.v4.actor.StarfishV4;
import ru.evant.lernlibgdx_3.v4.actor.TurtleV4;
import ru.evant.lernlibgdx_3.v4.actor.WhirlpoolV4;
import ru.evant.lernlibgdx_3.v4.base.BaseActorV4;
import ru.evant.lernlibgdx_3.v4.base.BaseScreenV4;

public class LevelScreen2V4 extends BaseScreenV4 {

    private TurtleV4 turtle;
    private boolean win;

    ArrayList<StarfishV4> starfishActors; // Список звезд
    ArrayList<RockV4> rockActors; // Список камней
    ArrayList<SharkV4> sharkActors; // Список акул

    @Override
    public void initialize() {
        BaseActorV4 ocean = new BaseActorV4(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);
        BaseActorV4.setWorldBounds(ocean); // установить размер игрового мира по размеру фонового рисунка - океан

        // Заполняем список звезд
        starfishActors = new ArrayList<>();
        starfishActors.add(new StarfishV4(200, 400, mainStage));
        starfishActors.add(new StarfishV4(100, 200, mainStage));
        starfishActors.add(new StarfishV4(300, 200, mainStage));
        starfishActors.add(new StarfishV4(400, 400, mainStage));

        // Заполняем список камней
        rockActors = new ArrayList<>();
        rockActors.add(new RockV4(200 , 300, mainStage));
        rockActors.add(new RockV4(100, 100, mainStage));
        rockActors.add(new RockV4(300, 100, mainStage));
        rockActors.add(new RockV4(400, 300, mainStage));

        // Заполняем список акул
        sharkActors = new ArrayList<>();
        sharkActors.add(new SharkV4(250, 350, mainStage));
        sharkActors.add(new SharkV4(150, 150, mainStage));
        sharkActors.add(new SharkV4(350, 150, mainStage));
        sharkActors.add(new SharkV4(450, 350, mainStage));

        turtle = new TurtleV4(20, 20, mainStage);

        win = false;
    }

    @Override
    public void update(float dt) {

        for (RockV4 r : rockActors) turtle.preventOverlap(r); // столкновение с камнес

        for (SharkV4 s : sharkActors) turtle.preventOverlap(s); // столкновение с акулой
        // !!!!!!
        // сделать так чтобы при столкновении с акулой черепаха отлетала назад

        for (int i = 0; i < starfishActors.size(); i++) {
            StarfishV4 starfish = starfishActors.get(i);

            if (turtle.overlaps(starfish) && !starfish.collected) {

                starfish.collected = true;
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                WhirlpoolV4 whirl = new WhirlpoolV4(0, 0, mainStage);
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
                rockActors.get(i).addAction(Actions.after(Actions.removeActor()));  // удалить объект камень
            }
            rockActors.clear(); // удалить полигоны столкновений

            // удалить акул
            for (int i = 0; i < sharkActors.size(); i++) {
                sharkActors.get(i).clearActions();
                sharkActors.get(i).addAction(Actions.fadeOut(1));            // удалить с экрана
                sharkActors.get(i).addAction(Actions.after(Actions.removeActor()));  // удалить объект акула
            }
            sharkActors.clear(); // удалить полигоны столкновений

            // удалить черепаху
            turtle.clearActions();
            turtle.addAction(Actions.fadeOut(1));               // удалить с экрана
            // turtle.addAction(Actions.after(Actions.removeActor()));   // удалить объект черепаха

            win = true;

            // Выводим сообщение о победе
            BaseActorV4 youWinMessage = new BaseActorV4(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            // Вывести в центр сообщение о победе
            youWinMessage.centerAtPosition(300, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}
