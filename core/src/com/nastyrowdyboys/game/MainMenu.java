package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.Random;

/**
 * Created by austin on 9/22/17.
 */

public class MainMenu implements Screen
{
    GameJamGame game;
    OrthographicCamera camera;
    Random random = new Random();
    float mCurRed = 0.0f;
    float mCurGreen = 1.0f;
    float mCurBlue = 0.0f;


    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    public void show(GameJamGame g)
    {
        game = g;
        this.show();
    }


    public void update()
    {
        if (Gdx.input.isTouched()) {
            mCurRed = random.nextFloat();
            mCurGreen = random.nextFloat();
            mCurBlue = random.nextFloat();
            dispose();
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(mCurRed, mCurGreen, mCurBlue, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
