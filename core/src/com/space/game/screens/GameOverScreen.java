package com.space.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.space.game.Main;

public class GameOverScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final int score;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    public GameOverScreen(Main game, int score) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);
        this.score = score;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);
    }

    @Override
    public void show() {
        viewport.apply();
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        viewport.apply();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        viewport.update(width, height, true);
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
