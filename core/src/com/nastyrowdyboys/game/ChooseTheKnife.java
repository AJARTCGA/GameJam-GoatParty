package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Magicman on 9/23/2017.
 */

public class ChooseTheKnife  implements Screen {

    OrthographicCamera camera;
    private SpriteBatch batch;
    private final Date createdDate = new java.util.Date();
    int oldtime = 0;
    int newtime = 0;
    double introTimer = 3;      /// You get three seconds to look at the pic;
    private Texture Instruction;
    private Texture WeaponOneImage;
    private Texture WeaponTwoImage;
    private Texture WeaponThreeImage;
    private Texture WeaponFourImage;
    private Texture WeaponRightImage;
    private Texture myTexture;
    private Texture winImage;

    Rectangle rec1 = new Rectangle();
    Rectangle rec2 = new Rectangle();
    Rectangle rec3 = new Rectangle();
    Rectangle rec4 = new Rectangle();
    Rectangle CatRec = new Rectangle();
    private Texture Table;
    private Texture Cat;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private Texture logo;

    boolean YEAH = false;
    boolean SUPERYEAH = false;
    private Texture shhhhh;

    private float CatPosX;
    private float CatPosY;
    private int Speed;
    ArrayList<ImageButton> TheButtons;

    ArrayList<Texture> WeaponList;
    ArrayList<Texture> Weapons;
    BitmapFont font;
    private Stage stage;
    ArrayList<Rectangle> TheRectangles;
    int RightAnswer;

    GameJamGame mGame;

    public ChooseTheKnife(GameJamGame g)
    {
        mGame = g;
    }


    int state = 0;
    float mEndTimer = 2.0f;

    @Override
    public void show() {
        TheRectangles = new ArrayList<Rectangle>();
        CatRec.x = CatPosX;
        CatRec.y = CatPosY;
        CatRec.width = 100;
        CatRec.height = 300;

        Random generator = new Random();
        RightAnswer = generator.nextInt(4) + 0;
        System.out.println(RightAnswer);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        font = new BitmapFont();
        oldtime = getAgeInSeconds();
        newtime = getAgeInSeconds();
        Instruction = new Texture (Gdx.files.internal("deathknife.png"));
        logo = new Texture(Gdx.files.internal("DaNastyBois.png"));
        TheButtons = new ArrayList<ImageButton>();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        WeaponList = new ArrayList<Texture>();
        CatPosX = 20;
        CatPosY = 0;
        Speed = 200;
        shhhhh = new Texture(Gdx.files.internal("pug.jpg"));

        Weapons = new ArrayList<Texture>();


        WeaponOneImage = new Texture(Gdx.files.internal("deathspork.png"));
        WeaponTwoImage = new Texture(Gdx.files.internal("deathbook.png"));
        WeaponThreeImage = new Texture(Gdx.files.internal("deathspork.png"));
        WeaponFourImage = new Texture(Gdx.files.internal("deathbook.png"));
        WeaponRightImage = new Texture(Gdx.files.internal("deathknife.png"));
        winImage = new Texture(Gdx.files.internal("win.PNG"));
        Weapons.add(WeaponOneImage);
        Weapons.add(WeaponTwoImage);

        for (int g = 0; g <3 ; g ++){
            int temp = generator.nextInt(Weapons.size()) + 0;
            WeaponList.add(Weapons.get(temp));
        }


        Table = new Texture(Gdx.files.internal("table.png"));
        Cat = new Texture(Gdx.files.internal("cathand.png"));
        myTexture = new Texture(Gdx.files.internal("realpug.PNG"));

        WeaponList.add(WeaponOneImage);
        WeaponList.add(WeaponTwoImage);
        WeaponList.add(WeaponThreeImage);
        WeaponList.add(WeaponFourImage);

        WeaponList.set(RightAnswer, WeaponRightImage);
        CreateRectangles();



    }

    @Override
    public void render(float delta) {
        if (state == 0) {
            // Draw the wanted poster!
            Gdx.gl.glClearColor(0, 0 ,0.2f,  1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(Instruction, 375, 30,100, 300 );
            batch.draw(logo , 0,0, 100,100);



            font.getData().setScale(2,2);

            font.draw(batch, "Choose The Knife!",  275, 450);
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
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(Table, 0, 0, 800, 500);

            batch.draw(WeaponList.get(0), rec1.x, 0, 100,300 );
            batch.draw(WeaponList.get(1), rec2.x, 0, 100,300 );
            batch.draw(WeaponList.get(2), rec3.x, 0 , 100,300);
            batch.draw(WeaponList.get(3), rec4.x, 0 , 100,300);
            batch.draw(Cat, CatPosX, CatPosY - 200, 100, 300);


            batch.end();

            if(Gdx.input.isTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                    //System.out.println(CatPosY);

                    Speed = 0;
                System.out.println(touchPos.x + " " + touchPos.y);
                if (touchPos.x > 750 & touchPos.y > 400){
                    for (int z = 0; z < WeaponList.size(); z++){
                        WeaponList.set(z, shhhhh);

                    }
                    Table = shhhhh;
                    Cat = shhhhh;
                    SUPERYEAH = true;
                    System.out.println("hhhhhh");
                }

            }

            if (Speed == 0 && CatPosY < 100){
                CatPosY = CatPosY +  100*Gdx.graphics.getDeltaTime();
            }
            if (Speed == 0 && CatPosY>= 100){
                if (YEAH == true){
                    state = 2;
                }
                else {
                    state = 3;
                }

            }




            CatPosX = (float) (CatPosX + (Speed * Gdx.graphics.getDeltaTime()));

            if (CatPosX <= 0){
                Speed = Speed*-1;
            }
            if (CatPosX >= 800){
                Speed = Speed *-1;
            }
            CatRec.x = CatPosX;
            CatRec.y = CatPosY;

            if (CatRec.overlaps(TheRectangles.get(RightAnswer))){
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
            if (SUPERYEAH == true){
                batch.begin();
                batch.draw(myTexture, 0, 0, 800, 500);




                batch.end();
            }
            mEndTimer -= Gdx.graphics.getDeltaTime();
            if(mEndTimer <= 0)
            {
                mGame.setScreen(new TransitionScreen(mGame));
            }


        }
        if (state ==3){
            System.out.println("you lose...");
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            mGame.setScreen(new TransitionScreen(mGame));
        }


    }

    public int getAgeInSeconds() {
        java.util.Date now = new java.util.Date();
        return (int)((now.getTime() - this.createdDate.getTime()) / 1000);
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


    public void CreateRectangles(){

        // really tall rectangles
        rec1.x = 125;
        rec2.x = 275;
        rec3.x = 425;
        rec4.x = 575;

        rec1.y = 300;
        rec2.y = 300;
        rec3.y = 300;
        rec4.y = 300;

        rec1.width = 100;
        rec2.width = 100;
        rec3.width = 100;
        rec4.width = 100;

        rec1.height = 600;
        rec2.height = 600;
        rec3.height = 600;
        rec4.height = 600;
        TheRectangles.add(rec1);
        TheRectangles.add(rec2);
        TheRectangles.add(rec3);
        TheRectangles.add(rec4);












    }
}
