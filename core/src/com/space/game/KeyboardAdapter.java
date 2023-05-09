package com.space.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class KeyboardAdapter extends InputAdapter {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private final Vector2 mousePos = new Vector2();
    private final Vector2 direction = new Vector2();

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = true;
        if (keycode == Input.Keys.D) rightPressed = true;
        if (keycode == Input.Keys.W) upPressed = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) leftPressed = false;
        if (keycode == Input.Keys.D) rightPressed = false;
        if (keycode == Input.Keys.W) upPressed = false;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenX = MathUtils.clamp(screenX, 0, Gdx.graphics.getWidth());
        screenY = MathUtils.clamp(screenY, 0, Gdx.graphics.getHeight());

        mousePos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    public Vector2 getDirection(Vector2 shipPos) {
        direction.set(0, 0);

        if (leftPressed || rightPressed) {
            Vector2 mouseDirection = mousePos.cpy().sub(shipPos);

            if (Math.abs(mouseDirection.x) <= Math.abs(mouseDirection.y)) {
                if (leftPressed) direction.add(-5, 0);
                if (rightPressed) direction.add(5, 0);
            } else {
                if (leftPressed) direction.add(0, 5);
                if (rightPressed) direction.add(0, -5);
            }
        } else if (upPressed) {
            Vector2 mouseDirection = mousePos.cpy().sub(shipPos);

            if (mouseDirection.len() < 1) {
                mouseDirection.setZero();
            } else {
                mouseDirection.nor();
                mouseDirection.scl(5);
            }

            direction.add(mouseDirection);
        }

        return direction;
    }

    public Vector2 getMousePos() {
        return mousePos;
    }

    public boolean isSpacePressed() {
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    public boolean touchDown(){
        return Gdx.input.justTouched();
    }
}
