package com.space.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.space.game.GameConfig;
import com.space.game.ICollidable;

public class Asteroid {
    private final Texture[] textures = {
            new Texture("meteorGrey_big1.png"),
            new Texture("meteorBrown_big4.png"),
            new Texture("meteorGrey_med2.png"),
            new Texture("meteorBrown_small2.png")};
    private Vector2 position;
    private Vector2 velocity;
    private float timeElapsed;
    private Texture texture;

    public Asteroid() {
    }

    public void init(){
        float x = MathUtils.randomBoolean() ? MathUtils.random(-Gdx.graphics.getWidth(), Gdx.graphics.getWidth()) :
                MathUtils.randomBoolean() ? -Gdx.graphics.getWidth() : Gdx.graphics.getWidth();
        float y = MathUtils.randomBoolean() ? MathUtils.random(-Gdx.graphics.getHeight(), Gdx.graphics.getHeight()) :
                MathUtils.randomBoolean() ? -Gdx.graphics.getHeight() : Gdx.graphics.getHeight();
        float speed = MathUtils.random(50, 150);
        position = new Vector2(x, y);
        velocity = new Vector2(speed, 0).rotateDeg((float) Math.random() * 360);
        texture = textures[MathUtils.random(3)];
    }

    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);

        if (position.x < -texture.getWidth()) {
            position.x = Gdx.graphics.getWidth();
        }
        if (position.x > Gdx.graphics.getWidth()) {
            position.x = -texture.getWidth();
        }
        if (position.y < -texture.getHeight()) {
            position.y = Gdx.graphics.getHeight();
        }
        if (position.y > Gdx.graphics.getHeight()) {
            position.y = -texture.getHeight();
        }

        timeElapsed += delta;
        if (timeElapsed > 10f) {
            velocity.scl(GameConfig.SPEED_MULTIPLIER);
            timeElapsed = 0f;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void reset(){
        position.set(0, 0);
        velocity.set(0, 0);
        texture = null;
    }

    public <T extends ICollidable> boolean collidesWith(T object){
        Circle asteroidCircle = new Circle(position.x + texture.getWidth() / 2f, position.y + texture.getHeight() / 2f, (texture.getWidth() - 5) / 2f);
        Circle objCircle = new Circle(object.getPosition().x + object.getSize() / 2f, object.getPosition().y + object.getSize() / 2f, object.getSize() / 2f);
        return asteroidCircle.overlaps(objCircle);
    }

    public void dispose(){
        texture.dispose();
    }
}
