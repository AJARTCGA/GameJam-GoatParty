package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by austin on 9/24/17.
 */

public class CreditScreen implements Screen
{
    Texture mCreditImg;
    SpriteBatch mBatch;
    GameJamGame mGame;

    public CreditScreen(GameJamGame g)
    {
        mGame = g;
    }

    @Override
    public void show()
    {
        mCreditImg = new Texture("credits.png");
        mBatch = new SpriteBatch();
    }

    @Override
    public void render(float v)
    {
        mBatch.begin();
        mBatch.draw(mCreditImg, 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        mBatch.end();
        if(Gdx.input.isTouched())
        {
            mGame.setScreen(new MainMenu(mGame));
        }
    }

    @Override
    public void resize(int i, int i1)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose() {

    }
}
