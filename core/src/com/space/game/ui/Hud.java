package com.space.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.game.GameConfig;
import com.space.game.objects.PlayerShip;

import java.util.Arrays;

public class Hud {
    private final BitmapFont font;
    private final Texture[] lifeTextures;
    private final PlayerShip playerShip;

    public Hud(PlayerShip playerShip) {
        font = new BitmapFont();
        lifeTextures = new Texture[GameConfig.LIVES_COUNT];
        Arrays.fill(lifeTextures, new Texture("playerLife2_red.png"));
        this.playerShip = playerShip;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < playerShip.getLives(); i++) {
            batch.draw(lifeTextures[i], i * 40, Gdx.graphics.getHeight() - 30);
        }

        font.draw(batch, "Score: " + playerShip.getScore(), 5, Gdx.graphics.getHeight() - 35);
    }

    public void dispose() {
        font.dispose();
        for (Texture lifeTexture : lifeTextures) {
            lifeTexture.dispose();
        }
        playerShip.dispose();
    }
}
