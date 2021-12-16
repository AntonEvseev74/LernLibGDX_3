package ru.evant.lernlibgdx_3.v3;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BaseActorV3 extends Actor {

    private static Rectangle worldBounds;

    private Animation<TextureRegion> animation; // анимация
    private float elapsedTime; // время которое прошло, используется для отслеживания продолжительности воспроизведения анимации
    private boolean animationPaused; // пауза анимации

    private final Vector2 velocityVec; // Вектор Скорость объекта указывает, как положение объекта меняется с течением времени, включая как скорость, так и направление движения.
    private final Vector2 accelerationVec; // Вектор ускорения
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
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    /*
    следующие 2 метода,
    позволяют сохранять размер игрового мира либо непосредственно из числовых значений,
    либо на основе актера
    (например, актера, отображающего фоновое изображение)
    */
    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(BaseActorV3 ba) {
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    public void boundToWorld() {
        // проверьте левый край
        if (getX() < 0) setX(0);
        // проверьте правый край
        if (getX() + getWidth() > worldBounds.width) setX(worldBounds.width - getWidth());
        // проверьте нижний край
        if (getY() < 0) setY(0);
        // проверьте вехний край
        if (getY() + getHeight() > worldBounds.height) setY(worldBounds.height - getHeight());
    }

    // перемещение камеры за актером
    public void alignCamera() {
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

        // центр камеры на актере
        cam.position.set( this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0 );

        // привязка камеры к экрану
        cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth/2, worldBounds.width - cam.viewportWidth/2);
        cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight/2, worldBounds.height - cam.viewportHeight/2); cam.update();
    }



    // Прозрачность объекта
    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }


    /**
     * Обработка позиции объекта на экране
     */
    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(BaseActorV3 other) {
        centerAtPosition(other.getX() + other.getWidth() / 2, other.getY() + other.getHeight() / 2);
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

    // Предотвращение столкновения
    public Vector2 preventOverlap(BaseActorV3 other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        // начальный тест для повышения производительности
        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())) return null;

        MinimumTranslationVector mtv = new MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

        if (!polygonOverlap) return null;

        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
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
    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w / 2, h / 2);

        if (boundaryPolygon == null) setBoundaryRectangle();
    }

    public void setAnimationPaused(boolean pause) {
        animationPaused = pause;
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<>();

        for (String fileName : fileNames) {
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);

        if (loop) anim.setPlayMode(Animation.PlayMode.LOOP);
        else anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null) setAnimation(anim);

        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<>();

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                textureArray.add(temp[r][c]);

        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);

        if (loop) anim.setPlayMode(Animation.PlayMode.LOOP);
        else anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null) setAnimation(anim);

        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }
}
