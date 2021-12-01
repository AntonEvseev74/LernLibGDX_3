package ru.evant.lernlibgdx_3.v3;

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

    public BaseActorV3(float x, float y, Stage s) {
        // вызов конструктора класса Actor
        super();

        // выполнение дополнительных задач иницилизации
        setPosition(x, y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;
    }

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
    }

    public void setAnimationPaused(boolean pause){
        animationPaused = pause;
    }

    public void act(float dt) {
        super.act( dt );
        if (!animationPaused) elapsedTime += dt;
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw( batch, parentAlpha );

        // применить эффект цветового оттенка
        Color c = getColor();

        batch.setColor(c.r, c.g, c.b, c.a);
        if ( animation != null && isVisible() ) {
            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
        }
    }


    public Animation loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {
        int fileCount = fileNames.length;
        Array textureArray = new Array();

        for (int n = 0; n < fileCount; n++) {
            String fileName = fileNames[n];
            Texture texture = new Texture( Gdx.files.internal(fileName) );
            texture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
            textureArray.add( new TextureRegion( texture ) );
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
                textureArray.add( temp[r][c] );

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
