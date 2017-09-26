package com.nastyrowdyboys.game;

/**
 * Created by Dude XPS on 9/22/2017.
 */


        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;

        import com.badlogic.gdx.Screen;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Animation;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
        import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
        import com.badlogic.gdx.math.Rectangle;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.math.Vector3;
        import com.badlogic.gdx.utils.viewport.ExtendViewport;

        import java.util.Random;


public class CatchThePiano implements Screen {
    //public static final float WORLD_SIZE = 480.0f;
    float stateTime = 0;
    ShapeRenderer renderer;
    ExtendViewport viewport;
    SpriteBatch batch;
    boolean isDead = false;
    boolean didWin = false;
    boolean pianoFell = false;
    boolean animPlayed = false;
    Rectangle piano = new Rectangle();
    Rectangle shadow = new Rectangle();
    Rectangle turtle = new Rectangle();
    Animation<TextureRegion> turtleAnim;


    int wave = 5;
    int velChange = 0;

    Vector2 pianoVel;
    float pianoHeight = 500f;


    private Texture pianoImg;
    private Texture brokenImg;
    private Texture shadowImg;
    private Texture turtleImg;
    private Texture background;
    private Texture turtleL;
    private Texture turtleM;
    private Texture turtleR;
    Random rand = new Random();

    boolean done;
    float mEndTimer;

    TextureRegion[] turtleText = new TextureRegion[3];

    Vector3 touch = new Vector3();

    GameJamGame mGame;

    public CatchThePiano(GameJamGame g)
    {
        mGame = g;
    }

    @Override
    public void show() {
        init();
    }

    @Override
    public void render(float delta) {
        viewport.apply();

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeType.Filled);
        update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        //TextureRegion currentFrame = turtleText[1];
                //turtleAnim.getKeyFrame(stateTime, true);
        batch.begin();

        if(Gdx.input.isTouched())
        {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.getCamera().unproject(touch);

            turtle.setX(touch.x - turtleL.getWidth()/2);

        }

        batch.draw(background,0,0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());

        if(pianoHeight > 0)
        {
            batch.draw(shadowImg, shadow.x, shadow.y);

        }
        if(!isDead)
        {
            if((stateTime <= .33f) || (stateTime > 1f && stateTime <= 1.33f))
            {
                batch.draw(turtleL, turtle.x, turtle.y);
            }
            else if((stateTime > .33f && stateTime <= .66f) ||(stateTime > 1f && stateTime <= 1.33) ||(stateTime > 1.66f && stateTime <= 2))
            {
                batch.draw(turtleM, turtle.x, turtle.y);
            }
            else if((stateTime > .66f && stateTime <= 1f) || (stateTime > 1.33f && stateTime <= 1.66))
            {
                batch.draw(turtleR, turtle.x, turtle.y);
            }
        }

        if(pianoHeight < 0)
            batch.draw(brokenImg,shadow.x, 25);
        batch.end();

        renderer.end();

    }

    public void update()
    {
        if(pianoHeight > 0) {
            shadow.x += pianoVel.x * Gdx.graphics.getDeltaTime();
            if (shadow.x > viewport.getWorldWidth() - shadowImg.getWidth())
                pianoVel.x *= -1;
            else if (shadow.x < 0)
                pianoVel.x *= -1;


            if(wave == 0)
            {
                velChange++;
                if(rand.nextFloat() * 100 < velChange)
                {
                    velChange = 0;
                    if(rand.nextBoolean())
                    {
                        if(rand.nextBoolean())
                        {
                            pianoVel.x *= -1;
                        }
                        else
                        {
                            pianoVel.x *= -.5;
                        }
                    }
                    else{
                        pianoVel.x *= 1.05;
                    }

                }

                wave = 50;

            }
            wave--;

        }

        if(!pianoFell)
        {
            if(pianoHeight < 0)
            {
                if(turtle.x-turtleL.getWidth()/2 < shadowImg.getWidth()+shadow.x)
                {
                    if(turtle.x+turtle.getWidth()/2 > shadow.x - shadowImg.getWidth())
                    {
                        isDead = true;
                        System.out.println("hit");
                    }

                }
                pianoFell= true;
            }

            if(isDead && pianoFell)
                didWin = true;
            if(pianoFell)
            {
                mEndTimer -= Gdx.graphics.getDeltaTime();
                if(mEndTimer <= 0)
                {
                    mGame.setScreen(new TransitionScreen(mGame));
                }
            }
        }


        pianoHeight-= 5*wave*Gdx.graphics.getDeltaTime();
        piano.x = shadow.x;
        stateTime += Gdx.graphics.getDeltaTime();
        if(stateTime >= 2)
            stateTime = 0;
    }

   /* public void animPianoFalling()
    {
            while(piano.y > 10)
            {
                piano.y += pianoVel.y * Gdx.graphics.getDeltaTime();
                batch.draw(pianoImg, shadow.x, piano.y);
            }

        //batch.draw(brokenImg, piano.x, piano.y);
        animPlayed = true;

    } */

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
        renderer.dispose();
        batch.dispose();
    }

    private void init()
    {
        batch = new SpriteBatch();
        pianoImg = new Texture(Gdx.files.internal("piano.png"));
        brokenImg = new Texture(Gdx.files.internal("pianoBroken.png"));

        shadowImg = new Texture(Gdx.files.internal("shadow.png"));
        //turtleImg = new Texture(Gdx.files.internal("turtle.png"));
        background = new Texture(Gdx.files.internal("catchBackground.png"));

        turtleL = new Texture(Gdx.files.internal("turtleL.png"));
        turtleM = new Texture(Gdx.files.internal("turtleM.png"));
        turtleR = new Texture(Gdx.files.internal("turtleR.png"));

        pianoVel = new Vector2(600,-1);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        viewport = new ExtendViewport(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        //turtleAnim = new Animation<TextureRegion>(0.33f, turtleText);
        //float turtleHeight = (float)((turtleImg.getHeight()*.33)/Gdx.graphics.getBackBufferHeight());

        //turtleText[0] = new TextureRegion(turtleImg,0,0,.33f, 1f);
        //turtleText[1] = new TextureRegion(turtleImg,.33f, 0,.33f, 1f);
        //turtleText[2] = new TextureRegion(turtleImg,.66f,0,.33f, 1f);
        shadow.set(shadowImg.getWidth(), 160, shadowImg.getWidth(),shadowImg.getHeight());
        piano.set(shadow.x,1080, pianoImg.getWidth(), pianoImg.getHeight());
        turtle.set((float)(viewport.getScreenWidth()*.5), turtleL.getHeight(), turtleL.getWidth()/3, turtleL.getHeight()/3);


    }

}
