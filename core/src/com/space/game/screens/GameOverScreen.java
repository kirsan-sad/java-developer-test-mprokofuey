package com.space.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.space.game.Main;

public class GameOverScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final int score;

    public GameOverScreen(Main game, int score) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);
        this.score = score;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2f - 100f, Gdx.graphics.getHeight() / 2f + 100f);
        font.draw(batch, "Your score is: " + score, Gdx.graphics.getWidth() / 2f - 100f, Gdx.graphics.getHeight() / 2f + 50f);
        font.draw(batch, "Tap to Play Again", Gdx.graphics.getWidth() / 2f - 100f, Gdx.graphics.getHeight() / 2f);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainGameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
