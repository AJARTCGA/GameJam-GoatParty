package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Random;

import sun.font.TrueTypeFont;

/**
 * Created by austin on 9/24/17.
 */

public class ToasterGame implements Screen
{


    public enum WireDirection
    {
        NORTH,
        EAST,
        SOUTH,
        WEST;
    }
    private WireDirection getOppositedDirection(WireDirection orig)
    {
        switch(orig)
        {
            case NORTH:
                return WireDirection.SOUTH;
            case SOUTH:
                return WireDirection.NORTH;
            case EAST:
                return WireDirection.WEST;
            case WEST:
                return WireDirection.EAST;
            default:
                return WireDirection.NORTH;
        }
    }
    GameJamGame mGame;
    private class WirePiece
    {
        WireDirection direction1;
        WireDirection direction2;
        Texture texture;
        WirePiece(WireDirection dir1, WireDirection dir2, Texture tex)
        {
            direction1 = dir1;
            direction2 = dir2;
            texture = tex;
        }
        WirePiece(WirePiece p)
        {
            direction1 = p.direction1;
            direction2 = p.direction2;
            texture = p.texture;
        }
    }

    private class Tile
    {
        WirePiece[] pieces = new WirePiece[3];
        Tile()
        {
            for(int i = 0; i < 3; i++)
            {
                pieces[i] = null;
            }
        }
        Tile(Tile t)
        {
            pieces = new WirePiece[3];
            for (int i = 0; i < 3; i++)
            {
                if(t.pieces[i] == null)
                {
                    pieces[i] = null;
                }
                else
                    pieces[i] = new WirePiece(t.pieces[i]);
            }
        }
    }

    private void arrayCopy(Tile[] source, Tile[] dest)
    {
        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                dest[x + y * WIDTH] = new Tile(source[x + y * WIDTH]);
            }
        }
    }


    static final int WIDTH = 7;
    static final int HEIGHT = 14;
    static final int TILE_SIZE = Gdx.graphics.getBackBufferWidth() / 9;
    Tile[] mTileArray = new Tile[WIDTH * HEIGHT];
    Texture[] mWireTextures = new Texture[6];
    WirePiece[] mWirePieces = new WirePiece[6];
    SpriteBatch mBatch;
    Random mRandom = new Random();
    float mTimer = 1.0f;
    long mOldTime;
    float mVerticalOffset = 0;
    boolean mKeepScrolling = true;
    Texture mToasterImg;
    ImageButton mWinButton;
    ImageButton mLoseButton1;
    ImageButton mLoseButton2;
    Stage mStage;

    public ToasterGame(GameJamGame g)
    {
        mGame = g;
        for(int i = 0; i < WIDTH * HEIGHT; i++)
        {
            mTileArray[i] = new Tile();
        }
        mWirePieces[0] = new WirePiece(WireDirection.NORTH, WireDirection.SOUTH, new Texture(Gdx.files.internal("Wire_Vertical.png")));
        mWirePieces[1] = new WirePiece(WireDirection.EAST, WireDirection.WEST, new Texture(Gdx.files.internal("Wire_Horizontal.png")));
        mWirePieces[2] = new WirePiece(WireDirection.EAST, WireDirection.NORTH, new Texture(Gdx.files.internal("Wire_Right_Top.png")));
        mWirePieces[3] = new WirePiece(WireDirection.EAST, WireDirection.SOUTH, new Texture(Gdx.files.internal("Wire_Right_Bottom.png")));
        mWirePieces[4] = new WirePiece(WireDirection.WEST, WireDirection.NORTH, new Texture(Gdx.files.internal("Wire_Left_Top.png")));
        mWirePieces[5] = new WirePiece(WireDirection.WEST, WireDirection.SOUTH, new Texture(Gdx.files.internal("Wire_Left_Bottom.png")));
        ArrayList<Integer> L = new ArrayList<Integer>();
        L.add(3);
        L.add(1);
        L.add(5);
        layWire(L);
        Texture btnTexture = new Texture(Gdx.files.internal("Toaster.png"));
        TextureRegion btnTextureRegion = new TextureRegion(btnTexture);
        TextureRegionDrawable btnTextureRegionDrawable = new TextureRegionDrawable(btnTextureRegion);

        mStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(mStage);

        mWinButton = new ImageButton(btnTextureRegionDrawable);
        mLoseButton1 = new ImageButton(btnTextureRegionDrawable);
        mLoseButton2 = new ImageButton(btnTextureRegionDrawable);
        for(int x = 0; x < WIDTH; x++)
        {
            Tile t = mTileArray[x + (HEIGHT - 1) * WIDTH];
            if(t.pieces[0] != null && (t.pieces[0].direction1 == WireDirection.SOUTH || t.pieces[0].direction2 == WireDirection.SOUTH))
            {
                mWinButton.setPosition((x + 1) * TILE_SIZE - TILE_SIZE * 0.375f, -1 * (HEIGHT + 1) * TILE_SIZE + Gdx.graphics.getBackBufferHeight());
                mWinButton.setSize(TILE_SIZE, TILE_SIZE);
                mWinButton.getImage().setFillParent(true);
                mWinButton.addListener(new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("Win");
                        mGame.setScreen(new TransitionScreen(mGame));
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                });
            }
            if(t.pieces[1] != null && (t.pieces[1].direction1 == WireDirection.SOUTH || t.pieces[1].direction2 == WireDirection.SOUTH))
            {
                mLoseButton1.setPosition((x + 1) * TILE_SIZE - TILE_SIZE * 0.375f, -1 * (HEIGHT + 1) * TILE_SIZE + Gdx.graphics.getBackBufferHeight());
                mLoseButton1.setSize(TILE_SIZE, TILE_SIZE);
                mLoseButton1.getImage().setFillParent(true);
                mLoseButton1.addListener(new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("Lose");
                        mGame.setScreen(new TransitionScreen(mGame));
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                });
            }
            if(t.pieces[2] != null && (t.pieces[2].direction1 == WireDirection.SOUTH || t.pieces[2].direction2 == WireDirection.SOUTH))
            {
                mLoseButton2.setPosition((x + 1) * TILE_SIZE - TILE_SIZE * 0.375f, -1 * (HEIGHT + 1) * TILE_SIZE + Gdx.graphics.getBackBufferHeight());
                mLoseButton2.setSize(TILE_SIZE, TILE_SIZE);
                mLoseButton2.getImage().setFillParent(true);
                mLoseButton2.addListener(new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("Lose");
                        mGame.setScreen(new TransitionScreen(mGame));
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                });
            }
        }
        mStage.addActor(mWinButton);
        mStage.addActor(mLoseButton1);
        mStage.addActor(mLoseButton2);
    }

    private void layWire(ArrayList<Integer> xList)
    {
        Tile[] fullClean = new Tile[WIDTH * HEIGHT];
        arrayCopy(mTileArray, fullClean);//Tile[] fullClean = mTileArray.clone();
        Tile[] clean = new Tile[WIDTH * HEIGHT];
        arrayCopy(fullClean, clean);//Tile[] clean = mTileArray.clone();
        int numDone = 0;
        int xIdx = 0;
        int count = 0;

        boolean outerDone = false;
        boolean reset = true;

        while(!outerDone)
        {
            boolean complete = false;
            if(reset)
            {
                arrayCopy(fullClean, clean);
                complete = false;
                numDone = 0;
                xIdx = 0;
                count = 0;
                reset = false;
            }
            else
            {
                arrayCopy(mTileArray, clean);
            }
            while(!complete)
            {
                int x = xList.get(xIdx);
                count++;
                if(count > 1000)
                {
                    complete = true;
                    reset = true;
                    break;
                }
                arrayCopy(clean, mTileArray);//mTileArray = clean.clone();
                int y =  0;
                WireDirection dir = WireDirection.SOUTH;
                mTileArray[x + y * WIDTH].pieces[xIdx] = mWirePieces[0]; //Vertical
                while(true)
                {
                    int targetX = 0;
                    int targetY = 0;
                    switch(dir) {
                        case NORTH:
                            targetX = x;
                            targetY = y - 1;
                            break;
                        case SOUTH:
                            targetX = x;
                            targetY = y + 1;
                            break;
                        case EAST:
                            targetX = x + 1;
                            targetY = y;
                            break;
                        case WEST:
                            targetX = x - 1;
                            targetY = y;
                            break;
                    }

                    if(targetX < 0  || targetY < 0 || targetX >= WIDTH)
                    {
                        break;
                    }
                    if(targetY >= HEIGHT)
                    {
                        Tile end = mTileArray[x + y * WIDTH];
                        int numNulls = 0;
                        for(int n = 0; n < end.pieces.length; n++)
                        {
                            if(end.pieces[n] == null)
                            {
                                numNulls++;
                            }
                        }
                        if(numNulls <= 1)
                        {
                            reset = true;
                        }
                        complete = true;
                        numDone++;
                        xIdx++;
                        if(numDone >= xList.size() && numNulls > 1)
                        {
                            outerDone = true;
                        }
                        break;
                    }
                    Tile tile = mTileArray[targetX + targetY * WIDTH];
                    int numNulls = 0;
                    for(int n = 0; n < tile.pieces.length; n++)
                    {
                        if(tile.pieces[n] == null)
                        {
                            numNulls++;
                        }
                    }
                    if(tile.pieces[xIdx] == null && numNulls >= 2)
                    {
                        int idx = mRandom.nextInt(mWirePieces.length);
                        WirePiece p = mWirePieces[idx];
                        if(p.direction1 == getOppositedDirection(dir) || p.direction2 == getOppositedDirection(dir))
                        {
                            mTileArray[targetX + targetY * WIDTH].pieces[xIdx] = p;
                            x = targetX;
                            y = targetY;
                            if(getOppositedDirection(dir) == p.direction1)
                            {
                                dir = p.direction2;
                            }
                            else
                            {
                                dir = p.direction1;
                            }
                        }
                    }
                    else
                        break;
                }
            }
        }
    }

    @Override
    public void show()
    {
        mBatch = new SpriteBatch();
        mOldTime = System.currentTimeMillis();
        mKeepScrolling = true;
        mToasterImg = new Texture("Toaster.png");
    }

    public void update()
    {
        long curTime = System.currentTimeMillis();
        float deltaTime = (curTime - mOldTime) / 1000.0f;
        mOldTime = curTime;

        if(mTimer > 0)
        {
            mTimer -= deltaTime;
        }
        else
        {
            if(mKeepScrolling)
            {
                mVerticalOffset += deltaTime * 250;
                if(mVerticalOffset >= (HEIGHT - 2) * TILE_SIZE)
                {
                    mVerticalOffset = (HEIGHT - 2) * TILE_SIZE;
                    mKeepScrolling = false;
                }
                mWinButton.setPosition(mWinButton.getX(), mWinButton.getY() + deltaTime * 250);
                mLoseButton1.setPosition(mLoseButton1.getX(), mLoseButton1.getY() + deltaTime * 250);
                mLoseButton2.setPosition(mLoseButton2.getX(), mLoseButton2.getY() + deltaTime * 250);
            }
        }

    }

    @Override
    public void render(float v)
    {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                Tile t = mTileArray[x + y * WIDTH];
                for(int i = 0; i < 3; i++)
                {
                    if(t.pieces[i] != null)
                    {
                        mBatch.draw(t.pieces[i].texture, ((x + 1) * TILE_SIZE), Gdx.graphics.getBackBufferHeight() - (y + 1) * TILE_SIZE + mVerticalOffset, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
        mBatch.end();
        mStage.act();
        mStage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
