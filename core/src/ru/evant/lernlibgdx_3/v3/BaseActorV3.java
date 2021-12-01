package ru.evant.lernlibgdx_3.v3;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;


public class BaseActorV3 extends Actor {

    private Animation animation; // анимация
    private float elapsedTime; // время которое прошло, используется для отслеживания продолжительности воспроизведения анимации
    private boolean animationPaused; // пауза анимации

    private Vector2 velocityVec; // Вектор Скорость объекта указывает, как положение объекта меняется с течением времени, включая как скорость, так и направление движения.
    private Vector2 accelerationVec; // Вектор ускорения
    private float acceleration; // ускорение
    private float maxSpeed;
    private float deceleration;

    private Polygon boundaryPolygon; // Полигон, необхоим для обработки столкновений объектов


    public BaseActorV3(float x, float y, Stage s) {
        // вызов конструктора класса Actor
        super();

        // выполнение дополнительных задач иницилизации
        setPosition(x, y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;

        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;

    }

    public void act(float dt) {
        super.act(dt);
        if (!animationPaused) elapsedTime += dt;
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // применить эффект цветового оттенка
        Color c = getColor();

        batch.setColor(c.r, c.g, c.b, c.a);
        if (animation != null && isVisible()) {
            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    // Прозрачность объекта
    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }


    /** Обработка позиции объекта на экране */
    public void centerAtPosition(float x, float y) {
        setPosition( x - getWidth()/2 , y - getHeight()/2 );
    }

    public void centerAtActor(BaseActorV3 other) {
        centerAtPosition( other.getX() + other.getWidth()/2 , other.getY() + other.getHeight()/2 );
    }


    /**
     * Столкновения
     */
    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0, 0, w, 0, w, h, 0, h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2 * numSides];

        for (int i = 0; i < numSides; i++) {
            float angle = i * 6.28f / numSides;
            // x-координата
            vertices[2 * i] = w / 2 * MathUtils.cos(angle) + w / 2;
            // y-координата
            vertices[2 * i + 1] = h / 2 * MathUtils.sin(angle) + h / 2;
        }

        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    // Проверка на столкновение
    public boolean overlaps(BaseActorV3 other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        // начальный тест для повышения производительности
        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())) return false;
        return Intersector.overlapConvexPolygons(poly1, poly2);
    }


    /**
     * Движение
     */
    public void applyPhysics(float dt) {
        // применить ускорение
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        float speed = getSpeed();

        // уменьшайте скорость (замедляйтесь), если не ускоряетесь
        if (accelerationVec.len() == 0) speed -= deceleration * dt;

        // держите скорость в установленных пределах
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        // отбновить скорость
        setSpeed(speed);

        // применить скорость
        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        // сбросить ускорение
        accelerationVec.set(0, 0);
    }


    /**
     * Физика
     */
    public void setSpeed(float speed) {
        // если длина равна нулю, то предположим, что угол движения равен нулю градусов
        if (velocityVec.len() == 0) velocityVec.set(speed, 0);
        else velocityVec.setLength(speed);
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(float angle) {
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angle();
    }

    public boolean isMoving() {
        return (getSpeed() > 0);
    }

    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }

    public void setDeceleration(float dec) {
        deceleration = dec;
    }


    /**
     * Анимация
     */
    /* используется для настройки анимации.
       Как только анимация будет установлена, можно будет установить размер (ширину и высоту) актера,
       а также начало координат (точку, вокруг которой должен вращаться актер, обычно центр актера).
       Ширина и высота актера будут установлены на ширину и высоту первого изображения анимации
       (изображения анимации также называются ключевыми кадрами).
     */
    public void setAnimation(Animation anim) {
        animation = anim;
        TextureRegion tr = (TextureRegion) animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w / 2, h / 2);

        if (boundaryPolygon == null) setBoundaryRectangle();
    }

    public void setAnimationPaused(boolean pause) {
        animationPaused = pause;
    }

    public Animation loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {
        int fileCount = fileNames.length;
        Array textureArray = new Array();

        for (int n = 0; n < fileCount; n++) {
            String fileName = fileNames[n];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation anim = new Animation(frameDuration, textureArray);

        if (loop) anim.setPlayMode(Animation.PlayMode.LOOP);
        else anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null) setAnimation(anim);

        return anim;
    }

    public Animation loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array textureArray = new Array();

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                textureArray.add(temp[r][c]);

        Animation anim = new Animation(frameDuration, textureArray);

        if (loop) anim.setPlayMode(Animation.PlayMode.LOOP);
        else anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null) setAnimation(anim);

        return anim;
    }

    public Animation loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }
}
