package ru.evant.lernlibgdx_3.v1;

//	Самообразование #3
//  Код из Самообразование #2
//	Java Game Development with LibGDX, 2nd Edition

/*
	Вы помогаете персонажу игрока, черепахе, плавать по океанскому дну в поисках морской звезды.
 */

/*
•	Игрок будет управлять черепахой, цель которой - собрать одну морскую звезду.
•	Движение контролируется клавишами со стрелками. Клавиша со стрелкой вверх перемещает черепаху к вверху экрана.
		Клавиша со стрелкой вправо перемещает черепаху к правой стороне экрана, и так далее.
		Одновременно можно нажать несколько клавиш со стрелками, чтобы переместить черепаха по диагонали.
		Скорость передвижения постоянная.
•	Черепаха собирает морские звезды, входя в контакт с ними (когда их картинки перекрываются).
		Когда это происходит, морская звезда исчезает, и появляется сообщение «Вы выиграли».
•	Графика, необходимая для этой игры, включает изображения черепахи, морской звезды, воды и сообщение,
		содержащее текст «Вы выиграли».
 */

/*
    Все файлы ресурсов для игры (картинки, звуки, музыка) находятся по дирректории:
    LernLibGDX_2 -> android -> assets
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class StarfishCollectorAlpha extends ApplicationAdapter {
	private SpriteBatch batch;

	// Данные черепахи
	private Texture turtleTexture;
	private float turtleX;
	private float turtleY;
	private Rectangle turtleRectangle;

	// Данные звезды
	private Texture starfishTexture;
	private float starfishX;
	private float starfishY;
	private Rectangle starfishRectangle;

	// Фон игры
	private Texture oceanTexture;

	// Табличка "Вы победили"
	private Texture winMessageTexture;

	// Состояние игрока (победил или не победил)
	private boolean win;

	public void create() {
		batch = new SpriteBatch(); // отрисовщик

		// черепаха
		turtleTexture = new Texture(Gdx.files.internal("turtle-1.png")); // картинка
		turtleX = 20; // координата начала отрисовки по оси Х
		turtleY = 20; // координата начала отрисовки по оси У
		turtleRectangle = new Rectangle(turtleX, turtleY, turtleTexture.getWidth(), turtleTexture.getHeight());
		// прямоугольник с координатами и размерами черепахи, используется для проверки на столкновения

		// звезда
		starfishTexture = new Texture(Gdx.files.internal("starfish.png")); // картинка
		starfishX = 380; // координата начала отрисовки по оси Х
		starfishY = 380; // координата начала отрисовки по оси У
		starfishRectangle = new Rectangle(starfishX, starfishY, starfishTexture.getWidth(), starfishTexture.getHeight());
		// прямоугольник с координатами и размерами звезды, используется для проверки на столкновения

		// фон игры
		oceanTexture = new Texture(Gdx.files.internal("water.jpg")); // картнка

		// Табличка "Вы победили"
		winMessageTexture = new Texture(Gdx.files.internal("you-win.jpg"));

		// Состояние игрока - не выиграл
		win = false;
	}

	public void render() {
		// проверить ввод данных пользователем
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) turtleX--; // если нажата кнопка влево, уменьшаем координату Х
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) turtleX++; // если нажата кнопка вправо, увеличиваем координату Х
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) turtleY++; // если нажата кнопка вверх, увеличиваем координату У
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) turtleY--; // если нажата кнопка вниз, уменьшаем координату У

		// обновить местоположение прямоугольника черепахи
		turtleRectangle.setPosition(turtleX, turtleY); // установить новые координаты для отрисовки черепахи

		// проверить условие выигрыша: черепаха должна перекрывать морскую звезду
		if (turtleRectangle.overlaps(starfishRectangle)) win = true;
		// если прямоугольник черепахи столкнулся с прямоугольником звезды, то установить состояние игрока - выиграл

		// очистить экран
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// нарисовать графику
		batch.begin(); // начало цикла отрисовки
		batch.draw(oceanTexture, 0, 0); // нарисовать фон игры по координатам х,у
		if (!win) batch.draw(starfishTexture, starfishX, starfishY); // если не выиграл нарисовать звезду по координатам х,у
		batch.draw(turtleTexture, turtleX, turtleY); // нарисовать черепаху по координатам х,у
		if (win) batch.draw(winMessageTexture, 220, 180); // если выиграл нарисовать табличку "Вы выиграли по координатам х,у
		batch.end(); // конец цикла отрисовки
	}
}
