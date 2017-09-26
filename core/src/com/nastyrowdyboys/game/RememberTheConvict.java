package com.nastyrowdyboys.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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
 * Created by Magicman on 9/22/2017.
 */

public class RememberTheConvict implements Screen {
    private final Date createdDate = new java.util.Date();
    GameJamGame game;
    OrthographicCamera camera;
    private SpriteBatch batch;
    Rectangle Bar = new Rectangle();
    Rectangle Sheriff = new Rectangle();
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;



    double timer = 0;           // The default timer for the mini game. If it runs out, you lose!
    int difficulty = 0;         /// Get this from a passed in value from a main class!
    double introTimer = 5;      /// You get three seconds to look at the pic;
    int oldtime = 0;
    int newtime = 0;
    int state = 0;
    int rightone = 0;
    int righttwo = 0;
    int rightthree = 0;
    int rightfour = 0;

    int pickone = 0;
    int picktwo = 4;
    int pickthree = 8;
    int pickfour = 12;
    float pug = 1;
    int Speed = 10;

    private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;



    int ImageSize = 64;

    private Texture SheriffImage;
    private Texture BarImage;
    private Texture WantedImage;
    private Texture SpriteSheetCatOne;
    private Texture SpriteSheetCatTwo;
    private Texture SpriteSheetCatThree;
    private Texture SpriteSheetCatFour;
    private Texture pugImage;
    private Texture pugImage2;
    private Texture NastyImage;
    private Texture shhhhh;
    private Texture winImage;
    BitmapFont font;

    private int HeckoMeter;
    /// Definitely change to sprite sheet!


    TextureRegion[][] tmp;
    ArrayList<TextureRegion[][]> TheChoices;

    ArrayList<ImageButton> TheButtons;
    TextureRegion[] ButtonImages;

    ArrayList<Integer> Answer;
    ArrayList<Integer> Guess;

    GameJamGame mGame;

    float mEndTimer = 2.0f;

    public RememberTheConvict(GameJamGame g)
    {
        mGame = g;
    }


    @Override
    public void show() {
        timer = 20 - (difficulty * 2);
        TheChoices = new ArrayList<TextureRegion[][]>();
        TheButtons = new ArrayList<ImageButton>();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;


        Random generator = new Random();

        // These are the correct choices for the game!
        rightone = generator.nextInt(4) + 0;
        righttwo = generator.nextInt(4) + 4;
        rightthree = generator.nextInt(4) + 8;
        rightfour = generator.nextInt(4) + 12;
        Answer = new ArrayList<Integer>();
        Guess = new ArrayList<Integer>();
        Guess.add(0);
        Guess.add(4);
        Guess.add(8);
        Guess.add(12);
        Answer.add(rightone);
        Answer.add(righttwo);
        Answer.add(rightthree);
        Answer.add(rightfour);

        System.out.println("my answers" );
        for (int r = 0; r < Answer.size(); r ++){
            System.out.println(Answer.get(r));
        }

        // these are our images!
        SpriteSheetCatThree = new Texture(Gdx.files.internal("eyes.png"));
        SpriteSheetCatTwo = new Texture(Gdx.files.internal("shirts.png"));
        SpriteSheetCatOne = new Texture(Gdx.files.internal("Hats.png"));
        SpriteSheetCatFour = new Texture(Gdx.files.internal("neck.png"));
        pugImage = new Texture(Gdx.files.internal("bear.png"));
        pugImage2 = new Texture(Gdx.files.internal("goodbear.png"));
        shhhhh = new Texture(Gdx.files.internal("realpug.PNG"));
        winImage = new Texture(Gdx.files.internal("win.PNG"));
        int index = 0;
        ButtonImages= new TextureRegion[16];




        tmp = TextureRegion.split(SpriteSheetCatOne, 904/2, 904/2 );
        TheChoices.add(tmp);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                ButtonImages[index++] = tmp[i][j];
            }
        }
        tmp = TextureRegion.split(SpriteSheetCatTwo, 1344/2, 1628/2  );
        TheChoices.add(tmp);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                ButtonImages[index++] = tmp[i][j];
            }
        }
        tmp = TextureRegion.split(SpriteSheetCatThree, 748/2, 748/2 );
        TheChoices.add(tmp);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                ButtonImages[index++] = tmp[i][j];
            }
        }
        tmp = TextureRegion.split(SpriteSheetCatFour, 802/2, 994/2 );
        TheChoices.add(tmp);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                ButtonImages[index++] = tmp[i][j];
            }
        }





        spawnBar();
        spawnSheriff();
        BarImage = new Texture(Gdx.files.internal("saloon.PNG"));
        SheriffImage = new Texture(Gdx.files.internal("pug.jpg"));
        WantedImage = new Texture (Gdx.files.internal("wantedPoster.png"));
        NastyImage = new Texture(Gdx.files.internal("DaNastyBois.png"));
        font = new BitmapFont();



        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        oldtime = getAgeInSeconds();
        newtime = getAgeInSeconds();

        myTexture = new Texture(Gdx.files.internal("dog.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton button3 = new ImageButton(myTexRegionDrawable);

        for (int b = 0; b < 16; b++){
            button3 = new ImageButton(myTexRegionDrawable);
            TheButtons.add(button3);
        }
        for (int b = 0; b < TheButtons.size(); b++ ){
            TheButtons.get(b).setSize(100,100);

            TheButtons.get(b).getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(ButtonImages[b]));
            TheButtons.get(b).getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("notbear2.png"))));
            //TheButtons.get(b).setPosition(0 + 100*b,980);
            if (b/4 == 0){
                // First group
                TheButtons.get(b).setPosition(0 + 150* (b % 4),900);


            }
            if (b /4 == 1){

                TheButtons.get(b).setPosition(1200 + 150* (b % 4),900);
            }
            if (b /4 == 2){

                TheButtons.get(b).setPosition(0 + 150* (b % 4),400);

            }
            if (b /4 == 3){

                TheButtons.get(b).setPosition(1200 + 150* (b % 4),400);

            }

            final int finalB1 = b;
            TheButtons.get(b).addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    //outputLabel.setText("Press a Button");
                    System.out.println(finalB1/4);
                    if (finalB1 /4 == 0){
                        pickone = finalB1;
                        Guess.set(0 , pickone);
                    }
                    if (finalB1 /4 == 1){
                        picktwo = finalB1;
                        Guess.set(1 , picktwo);
                    }
                    if (finalB1 /4 == 2){
                        pickthree = finalB1;
                        Guess.set(2 , pickthree);
                    }
                    if (finalB1 /4 == 3){
                        pickfour = finalB1;
                        Guess.set(3 , pickfour);
                    }
                    if (finalB1 == 15){
                        HeckoMeter ++;
                    }




                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    //outputLabel.setText("Pressed Image Button");
                    return true;
                }
            });
            stage.addActor(TheButtons.get(b));





        }





    }

    @Override
    public void render(float delta) {

        if (state == 0) {
            // Draw the wanted poster!
            Gdx.gl.glClearColor(0, 0 ,0.2f,  1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.rect(825,300,360,500);





            shapeRenderer.end();

            batch.begin();


            batch.draw(NastyImage , 0,0, 100,100);
            batch.draw(pugImage, 350,150);
            batch.draw(ButtonImages[Answer.get(2)], 378 , 277 , 63 , 63);
            batch.draw(ButtonImages[Answer.get(0)], 359 , 277 , 97 , 97);
            batch.draw(ButtonImages[Answer.get(1)], 342, 155, 135, 160);
            batch.draw(ButtonImages[Answer.get(3)], 368, 150, 82, 152);
            batch.draw(WantedImage, 190, 100, 440, 330);
            font.getData().setScale(2,2);
            font.draw(batch, "Remember the Convict!", 275, 450);
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

        if (state == 1) {


            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(700,400,500,900);  // left to middle
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(0,0,1920,164);
            shapeRenderer.setColor((Color.LIGHT_GRAY));
            shapeRenderer.rect(0,164,700,1080);
            shapeRenderer.rect(1150,164,700,1080);
            shapeRenderer.setColor(Color.BLACK);

            shapeRenderer.rect(700, 300, 450, 25 );
            shapeRenderer.rect(700, 400, 450, 25 );
            shapeRenderer.rect(700, 500, 450, 25 );
            shapeRenderer.rect(700, 600, 450, 25 );
            shapeRenderer.rect(700, 700, 450, 25 );
            shapeRenderer.rect(700, 800, 450, 25 );
            shapeRenderer.rect(700, 900, 450, 25 );
            shapeRenderer.rect(700, 1000, 450, 25 );
            shapeRenderer.rect(700, 1100, 450, 25 );
            shapeRenderer.rect(700, 1200, 450, 25 );






            shapeRenderer.end();

            batch.begin();
            batch.draw(BarImage, Bar.x, Bar.y, 75 , 75);
            batch.draw(SheriffImage, Sheriff.x, Sheriff.y , 50 , 75);
            batch.draw(pugImage, 350,150 );
            batch.draw(ButtonImages[pickthree], 378 , 277 , 63 , 63);
            batch.draw(ButtonImages[pickone], 359 , 277 , 97 , 97);


            batch.draw(ButtonImages[picktwo], 342, 155, 135, 160);
            batch.draw(ButtonImages[pickfour], 368, 150, 82, 152);


                System.out.println("your guess " + Guess.get(0) + " " + Guess.get(1)+ " " + Guess.get(2)+ " " + Guess.get(3) );
                //System.out.println("the Correct answer " + Answer.get(0) + " " + Answer.get(1)+ " " + Answer.get(2)+ " " + Answer.get(3) );

            for (int g = 0; g < 4; g++) {
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        //batch.draw(TheChoices.get(g)[j][i], (100 *g  + i * (ImageSize / 2 + 10)) , 350 + j * (ImageSize / 2 + 10));

                    }
                }
            }




            batch.end();
            stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
            stage.draw(); //Draw the ui



            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(0,600,650,50);  // left to middle
            shapeRenderer.rect(0,164,1920,50);  // bottom all the way across
            shapeRenderer.rect(1150,600,1920,50); // middle to left
            shapeRenderer.rect(650,164,50,1080);   // left pole
            shapeRenderer.rect(1150,164,50,1080);  // right pole
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(700, 204, 450, 200 );



            shapeRenderer.end();



            newtime = getAgeInSeconds();
            if (newtime != oldtime) {
                timer -= 1;
                oldtime = newtime;



            }
            if (timer <= 0){
                state = 2;
                if (isTwoArrayListsWithSameValues(Guess, Answer)){
                    state = 3;
                }

            }
            update();

        }
        if (state == 2){
            // You lose!
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            System.out.println("lose!");
            mGame.setScreen(new TransitionScreen(mGame));


        }
        if (state == 3){
            // you win!
            Gdx.gl.glClearColor(1, 1, 1, 1);
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

        if (HeckoMeter >= 15){
            state = 4;

        }
        if (state == 4){
            System.out.println("ENTERING THE PUG ZONE");
            batch.begin();
            batch.draw(shhhhh, 0, 0, 800, 500);
            font.getData().setScale(pug,pug);

            font.draw(batch, "THE PUG ZONE!", 100, 400);
            batch.end();


            pug = (float) (pug + (Speed * Gdx.graphics.getDeltaTime()));

            if (pug <= 1){
                Speed = Speed*-1;
            }
            if (pug >= 6){
                Speed = Speed *-1;
            }

        }

    }

    @Override
    public void resize(int width, int height) {

    }

    public void update()
    {
        Sheriff.x = (float) (Bar.x + 32 + (timer*20));
       // System.out.println(getAgeInSeconds());

    }



    public int getAgeInSeconds() {
        java.util.Date now = new java.util.Date();
        return (int)((now.getTime() - this.createdDate.getTime()) / 1000);
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
    public void spawnSheriff(){
        Sheriff.x = (float) (Bar.x + 32+ (timer*20));
        Sheriff.y = 0;
        Sheriff.width = 64;
        Sheriff.height = 64;

    }
    public void spawnBar(){
        Bar.x= 0;
        Bar.y = 0;
        Bar.width = 64;
        Bar.height = 64;
    }
// make a timer
    // make a win state
    // make a button
    // load images during gameplay
    // get transparent box

    public boolean isTwoArrayListsWithSameValues(ArrayList<Integer> list1, ArrayList<Integer> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(Object itemList1: list1)
        {
            if(!list2.contains(itemList1))
                return false;
        }

        return true;
    }

}
