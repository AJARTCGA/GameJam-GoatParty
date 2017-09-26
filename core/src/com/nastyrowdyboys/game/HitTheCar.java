package com.nastyrowdyboys.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Date;

/**
 * Created by Magicman on 9/24/2017.
 */

public class HitTheCar implements Screen {



    private Texture frog;
    private Texture car;
    private Texture road;
    private Texture logo;
    BitmapFont font;
    GameJamGame game;
    OrthographicCamera camera;
    private SpriteBatch batch;
    int state = 0;
    float FrogPosX;
    float FrogPosY;
    float CarPosX;
    float CarPosY;
    int Speed;

    private Texture winImage;

    double introTimer = 3;      /// You get three seconds to look at the pic;

    int oldtime = 0;
    int newtime = 0;
    boolean YEAH = false;

    Rectangle FrogRec = new Rectangle();
    Rectangle CarRec = new Rectangle();

    private final Date createdDate = new java.util.Date();

    GameJamGame mGame;
    float mEndTimer = 2.0f;

    public HitTheCar(GameJamGame g)
    {
        mGame = g;
    }

    @Override
    public void show() {


        frog = new Texture(Gdx.files.internal("frog.png"));
        car = new Texture(Gdx.files.internal("car.png"));
        road = new Texture(Gdx.files.internal("road.png"));
        font = new BitmapFont();

        logo = new Texture(Gdx.files.internal("DaNastyBois.png"));
        winImage = new Texture(Gdx.files.internal("win.PNG"));

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        FrogPosX = 500;
        FrogPosY = 0;
        CarPosY = 150;
        CarPosX = 0;
        Speed = 200;
        CarRec.x = CarPosX;
        CarRec.y = CarPosY;
        CarRec.width = 200;
        CarRec.height = 100;
        FrogRec.x = FrogPosX;
        FrogRec.y = FrogPosY;
        FrogRec.width = 50;
        FrogRec.height = 100;
        oldtime = getAgeInSeconds();
        newtime = getAgeInSeconds();


    }

    @Override
    public void render(float delta) {


        if (state == 0){
            Gdx.gl.glClearColor(0, 0 ,0.2f,  1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);



            batch.begin();


            batch.draw(logo , 0,0, 100,100);
            batch.draw(car, 250,150);

            font.getData().setScale(2,2);
            font.draw(batch, "Hit the car!", 275, 450);
            batch.end();

            newtime = getAgeInSeconds();

            if (newtime != oldtime){        // A second has passed!
                introTimer -=1;
                oldtime = newtime;

            }
            if (introTimer <= 0){
                state ++;
            }



        }


        if (state == 1){


            Gdx.gl.glClearColor(0, 0 ,0.2f,  1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);



            batch.begin();


            //batch.draw(logo , 0,0, 100,100);
            //batch.draw(car, 250,150);
            batch.draw(road, 0,0, 800,500);
            batch.draw(car, CarPosX, CarPosY, CarRec.width, CarRec.height);
            batch.draw(frog, FrogPosX,FrogPosY, FrogRec.width, FrogRec.height);

            //font.getData().setScale(2,2);
            //font.draw(batch, "Hit the car!", 275, 450);
            batch.end();






            if(Gdx.input.isTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                //System.out.println(CatPosY);

                Speed = 0;
                System.out.println(touchPos.x + " " + touchPos.y);

                }
            if (Speed == 0 && FrogPosY < 150){
                FrogPosY = FrogPosY +  200*Gdx.graphics.getDeltaTime();
            }
            if (Speed == 0 && FrogPosY>= 150){
                if (YEAH == true){
                    state = 2;
                }
                else {
                    state = 3;
                }

            }
            CarRec.x = CarPosX;
            CarRec.y = CarPosY;


            CarPosX = (float) (CarPosX + (200 * Gdx.graphics.getDeltaTime()));
            FrogRec.x = FrogPosX;
            FrogRec.y = FrogPosY;

            if (FrogRec.overlaps(CarRec)){
                System.out.println("kk slider");
                YEAH = true;
            }

        }

        if (state == 2){

            System.out.println("youwin!");
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);


            batch.begin();


            batch.draw(winImage , 0,0, 800,500);

            batch.end();
            mEndTimer -= Gdx.graphics.getDeltaTime();
            if(mEndTimer <= 0)
            {
                mGame.setScreen(new TransitionScreen(mGame));
            }
        }

        if (state == 3){

            Gdx.gl.glClearColor(0, 0 ,0.2f,  1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            System.out.println("you Lose");
            mGame.setScreen(new TransitionScreen(mGame));
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

    }


    public int getAgeInSeconds() {
        java.util.Date now = new java.util.Date();
        return (int)((now.getTime() - this.createdDate.getTime()) / 1000);
    }
}
