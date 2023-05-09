package com.space.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.space.game.*;
import com.space.game.objects.Asteroid;
import com.space.game.objects.Bullet;
import com.space.game.objects.PlayerShip;
import com.space.game.ui.Background;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.space.game.ui.Hud;

import java.util.ArrayList;

public class MainGameScreen implements Screen {
    private final Main game;
    private final PlayerShip playerShip;
    private final Background background;
    private final Hud hud;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final KeyboardAdapter keyboardAdapter;
    private final Viewport viewport;
    private final ArrayList<Asteroid> asteroids;
    private  final Pool<Asteroid> asteroidPool = new Pool<Asteroid>() {
        @Override
        protected Asteroid newObject() {
            return new Asteroid();
        }
    };

    public MainGameScreen(Main game) {
        this.game = game;
        background = new Background();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        keyboardAdapter = new KeyboardAdapter();
        playerShip = new PlayerShip();
        hud = new Hud(playerShip);
        viewport = new ScreenViewport(camera);
        asteroids = new ArrayList<>();

        for (int i = 0; i < GameConfig.ASTEROIDS_COUNT; i++) {
            Asteroid asteroid = asteroidPool.obtain();
            asteroid.init();
            asteroids.add(asteroid);
        }
    }

    @Override
    public void show() {
        viewport.apply();
        Gdx.input.setInputProcessor(keyboardAdapter);
    }

    @Override
    public void render (float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        viewport.apply();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (playerShip.getLives() <= 0) {
            game.setScreen(new GameOverScreen(game, playerShip.getScore()));
            dispose();
            return;
        }

        playerShip.moveTo(keyboardAdapter.getDirection(playerShip.getPosition()));
        playerShip.rotateTo(keyboardAdapter.getMousePos());

        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        background.render(batch);
        playerShip.render(batch, delta);

        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid = asteroids.get(i);
            asteroid.update(delta);
            asteroid.render(batch);

            if (asteroid.collidesWith(playerShip)) {
                playerShip.takeDamage(1);

                asteroid.reset();
                asteroidPool.free(asteroid);

                asteroids.remove(asteroid);

                i--;

                Asteroid newAsteroid = asteroidPool.obtain();
                newAsteroid.init();
                asteroids.add(newAsteroid);
            }
        }

        if (keyboardAdapter.isSpacePressed() || keyboardAdapter.touchDown()){
            Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            playerShip.shoot(mousePosition);
        }

        ArrayList<Bullet> bullets = playerShip.getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);
            bullet.render(batch);

            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid asteroid = asteroids.get(j);

                if (asteroid.collidesWith(bullet)) {
                    asteroid.reset();
                    asteroidPool.free(asteroid);

                    asteroids.remove(asteroid);

                    Asteroid newAsteroid = asteroidPool.obtain();
                    newAsteroid.init();
                    asteroids.add(newAsteroid);
                    playerShip.addScore();
                    playerShip.removeBullet(bullet);
                    break;
                }
            }

            if (bullet.getPosition() != null && bullet.isOutOfScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 200)){
                playerShip.removeBullet(bullet);
            }
        }
        hud.render(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        playerShip.dispose();
        background.dispose();
        hud.dispose();
        for (Asteroid asteroid: asteroids) {
            asteroid.dispose();
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
}
