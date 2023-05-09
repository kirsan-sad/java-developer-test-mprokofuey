package com.space.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
public class Background {
    private final Texture texture;

    public Background() {
        texture = new Texture("darkPurple.png");
    }

    public void render(SpriteBatch batch) {
        int xCopies = MathUtils.ceil((float) Gdx.graphics.getWidth() / texture.getWidth());
        int yCopies = MathUtils.ceil((float) Gdx.graphics.getHeight() / texture.getHeight());
        for (int i = 0; i < xCopies; i++) {
            for (int j = 0; j < yCopies; j++) {
                batch.draw(texture, i * texture.getWidth(), j * texture.getHeight());
            }
        }
    }

    public void dispose(){
        texture.dispose();
    }
}
