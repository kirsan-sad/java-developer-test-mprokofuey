package com.space.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.space.game.GameConfig;
import com.space.game.ICollidable;

import java.util.ArrayList;

public class PlayerShip implements ICollidable {
    private final Vector2 position;
    private final Vector2 angle;
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final float size = 40;
    private final float halfSize = size / 2;
    private int lives = GameConfig.LIVES_COUNT;
    private int score = 0;
    private final ArrayList<Bullet> bullets;
    private  final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

    public PlayerShip() {
        texture = new Texture("playerShip2_red.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegion = new TextureRegion(texture);
        position = new Vector2();
        position.set((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        angle = new Vector2();
        bullets = new ArrayList<>();
    }

    public void render(Batch batch, float delta) {
        batch.draw(textureRegion, position.x, position.y, halfSize, halfSize, size, size, 1, 1, angle.angleDeg() - 90);
    }

    public void moveTo(Vector2 direction) {
        position.add(direction);
        if (position.x < 0) {
            position.x = Gdx.graphics.getWidth() - size;
        }
        if (position.x > Gdx.graphics.getWidth() - size) {
            position.x = 0;
        }
        if (position.y < 0) {
            position.y = Gdx.graphics.getHeight() - size;
        }
        if (position.y > Gdx.graphics.getHeight() - size) {
            position.y = 0;
        }
    }

    public void rotateTo(Vector2 mousePos) {
        angle.set(mousePos).sub(position.x + halfSize, position.y + halfSize);
    }

    public void takeDamage(int damage) {
        lives -= damage;
    }

    public void shoot(Vector2 target) {
        Bullet bullet = bulletPool.obtain();
        Vector2 bulletPosition = new Vector2(position.x + size / 2, position.y + size / 2).sub(bullet.getSize() / 2, bullet.getSize() / 2);
        bullet.init(bulletPosition, target);
        bullets.add(bullet);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void removeBullet(Bullet bullet) {
        bullet.reset();
        bulletPool.free(bullet);
        bullets.remove(bullet);
    }

    public int getLives(){
        return lives;
    }

    public void addScore() {
        ++score;
    }

    public int getScore() {
        return score;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSize() {
        return size;
    }

    public void dispose() {
        texture.dispose();
    }
}
