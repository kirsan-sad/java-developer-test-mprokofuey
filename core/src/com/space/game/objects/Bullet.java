package com.space.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.space.game.GameConfig;
import com.space.game.ICollidable;

public class Bullet implements ICollidable {
    private final Texture texture = new Texture("laserRed09.png");
    private Vector2 position;
    private float rotation;
    private final float size = 20;
    private float distance;

    public Bullet() {
    }

    public  void init(Vector2 position, Vector2 target){
        this.position = position;

        float dx = target.x - position.x;
        float dy = target.y - position.y;
        rotation = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        distance = 0;
    }

    public void reset(){
        position.set(0, 0);
        rotation = 0;
    }

    public void update(float delta) {
        float dx = GameConfig.BULLET_SPEED * delta * MathUtils.cosDeg(rotation);
        float dy = GameConfig.BULLET_SPEED * delta * MathUtils.sinDeg(rotation);
        position.add(dx, dy);
        distance += Math.sqrt(dx * dx + dy * dy);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, size / 2f, size / 2f, size, size, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public boolean isOutOfScreen(float screenWidth, float screenHeight, float maxDistance) {
        return position.x < -size || position.y < -size || position.x > screenWidth + size || position.y > screenHeight + size || distance > maxDistance;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSize() {
        return size;
    }
}
