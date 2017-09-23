package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;

/**
 * Created by austin on 9/22/17.
 */

public class MainMenu implements Screen
{
    GameJamGame game;
    OrthographicCamera camera;
    Random random = new Random();
    Texture satanGoatImg;
    SpriteBatch batch;
    Texture satanGoatImgScaled;
    TextButton startButton;
    TextureAtlas mTextureAtlas;
    Skin mSkin;
    BitmapFont mFont;

    public MainMenu(GameJamGame g)
    {
        this.game = g;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        satanGoatImg = new Texture("goatsatanbase.png");
        batch = new SpriteBatch();
        mTextureAtlas = new TextureAtlas("PlsWork.pack");
        mSkin = new Skin();
        mSkin.addRegions(mTextureAtlas);
        mFont = new BitmapFont();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = mSkin.getDrawable("Test");
        style.down = mSkin.getDrawable("Test");
        style.font = mFont;
        startButton = new TextButton("Start!", style);
        startButton.setPosition(Gdx.graphics.getBackBufferWidth() / 2 - 100, Gdx.graphics.getBackBufferHeight() * 1 / 8);
        startButton.setSize(200, 100);
    }

    public void update()
    {
        if (Gdx.input.isTouched()) {
            dispose();
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        satanGoatImg.getWidth();
        int scaleX = satanGoatImg.getWidth() * 4;
        int scaleY = satanGoatImg.getHeight() * 4;
        int x = Gdx.graphics.getBackBufferWidth() / 2 - scaleX / 2;
        int y = Gdx.graphics.getBackBufferHeight() / 2 - scaleY / 2;
        startButton.draw(batch, 1.0f);
        batch.draw(satanGoatImg, x, y, scaleX, scaleY);
        batch.end();
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
