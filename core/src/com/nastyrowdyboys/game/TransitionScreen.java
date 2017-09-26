package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by austin on 9/24/17.
 */

public class TransitionScreen implements Screen
{
    GameJamGame mGame;
    SpriteBatch mBatch;
    Texture mSatanTorsoImg;
    Texture mSatanHeadImg;
    float mVerticalOffset = 0.0f;
    long mOldTime;
    float mSinSeed = 0.0f;
    float mTimerMax = 2.0f;
    float mTimerCur;
    Random mRandom = new Random(System.currentTimeMillis());

    ArrayList<String> mGameNames;


    public TransitionScreen(GameJamGame g)
    {
        mGame = g;
        mSatanTorsoImg = new Texture("goatsatantorso.png");
        mSatanHeadImg = new Texture("goatsatanhead.png");
        mBatch = new SpriteBatch();
        populateGames();
    }

    private void populateGames()
    {
        mGameNames = new ArrayList<String>();
        mGameNames.add("Toasters");
        mGameNames.add("Outlaw");
        mGameNames.add("Knife");
        mGameNames.add("Piano");
        mGameNames.add("Frogger");
    }

    @Override
    public void show()
    {
        mTimerCur = mTimerMax;
        mOldTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        long curTime = System.currentTimeMillis();
        long deltaTime = curTime - mOldTime;
        mTimerCur -= deltaTime / 1000.0f;
        if(mTimerCur <= 0)
        {
            int idx = mRandom.nextInt(mGameNames.size());
            switch(idx)
            {
                case 0:
                    mGame.setScreen(new ToasterGame(mGame));
                    break;
                case 1:
                    mGame.setScreen(new ToasterGame(mGame));
                    break;
                case 2:
                    mGame.setScreen(new ToasterGame(mGame));
                    break;
                case 3:
                    mGame.setScreen(new ToasterGame(mGame));
                    break;
                case 4:
                    mGame.setScreen(new ToasterGame(mGame));
                    break;
            }

        }
        mOldTime = curTime;
        mSinSeed += deltaTime / 1000.0f * 3.14f * 2f;
        if(mSinSeed >= 3.14 * 2)
        {
            mSinSeed = 0;
        }

        mVerticalOffset = (float)Math.sin(mSinSeed) * 5.0f;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        int width = mSatanTorsoImg.getWidth() * 4;
        int height = mSatanTorsoImg.getHeight() * 4;
        int x = Gdx.graphics.getBackBufferWidth() / 2 - width / 2;
        int y = Gdx.graphics.getBackBufferHeight() / 2 - 2 * (height / 2);
        mBatch.draw(mSatanTorsoImg, x, y, width, height);
        width = mSatanHeadImg.getWidth() * 4;
        height = mSatanHeadImg.getHeight() * 4;
        x = Gdx.graphics.getBackBufferWidth() / 2 - width / 2;
        y = Gdx.graphics.getBackBufferHeight() / 2 - Gdx.graphics.getBackBufferHeight() / 8 + (int)mVerticalOffset;

        mBatch.draw(mSatanHeadImg, x, y, width, height);
        mBatch.end();
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
