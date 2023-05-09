package com.space.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.game.Main;

public class StartScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    public StartScreen(Main game){
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        batch.begin();
        font.draw(batch, "the space".toUpperCase(), Gdx.graphics.getWidth() / 2f - 80f, Gdx.graphics.getHeight() / 2f + 150f);
        font.draw(batch, "Start Play", Gdx.graphics.getWidth() / 2f - 60f, Gdx.graphics.getHeight() / 2f);
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
