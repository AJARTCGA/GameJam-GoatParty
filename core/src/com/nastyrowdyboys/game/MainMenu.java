package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.rmi.activation.ActivationGroup_Stub;
import java.util.ArrayList;
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
    Texture mMainMenuImg;
    SpriteBatch batch;
    ImageButton mStartBtn;
    ImageButton mCreditBtn;
    Stage mStage;
    ArrayList<String> mGameNames;

    public MainMenu(GameJamGame g)
    {
        this.game = g;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        satanGoatImg = new Texture("goatsatanbase.png");
        mMainMenuImg = new Texture("menu.png");
        batch = new SpriteBatch();

        initButtons();
        mStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(mStage);
        mStage.addActor(mStartBtn);
        mStage.addActor(mCreditBtn);
    }

    private void initButtons()
    {
        Texture btnTexture = new Texture(Gdx.files.internal("garbage_start.png"));
        TextureRegion btnTextureRegion = new TextureRegion(btnTexture);
        TextureRegionDrawable btnTextureRegionDrawable = new TextureRegionDrawable(btnTextureRegion);
        mStartBtn = new ImageButton(btnTextureRegionDrawable);
        mStartBtn.setPosition(Gdx.graphics.getBackBufferWidth() / 2, Gdx.graphics.getBackBufferHeight() / 2 + 20);
        mStartBtn.setSize(Gdx.graphics.getBackBufferWidth() / 2, Gdx.graphics.getBackBufferHeight() / 2);
        mStartBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TransitionScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        mCreditBtn = new ImageButton(btnTextureRegionDrawable);
        mCreditBtn.setPosition(Gdx.graphics.getBackBufferWidth() / 2, 0);
        mCreditBtn.setSize(Gdx.graphics.getBackBufferWidth() / 2, Gdx.graphics.getBackBufferHeight() / 2 + 20);
        mCreditBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CreditScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

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
        batch.draw(mMainMenuImg,0,0,Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        batch.end();
        mStage.act();
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
