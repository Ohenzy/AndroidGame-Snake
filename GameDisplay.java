package com.ohenzy.games.snake;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.ohenzy.games.snake.R.*;

public class GameDisplay extends SurfaceView implements SurfaceHolder.Callback {

    public static int SIZE_ITEM;
    public static boolean IN_RUNNING = true;
    public static boolean PAUSE = true;


    private static final byte HORISON_BUTTON = 2;
    private static final byte VERTICAL_BUTTON = 1;
    private GameActivity gameActivity;
    private boolean startGame;
    private int sizeButtonX1;
    private int sizeButtonY1;
    private int sizeButtonX2;
    private int sizeButtonY2;
    private int showButton;
    private Snake snake;
    private Food food;
    private LvlMap lvlMap;
    private Bitmap buttonUp;
    private Bitmap buttonDown;
    private Bitmap buttonLeft;
    private Bitmap buttonRight;
    private GameButton pauseButton;
    private GameButton resumeButton;
    private GameButton restartButton;
    private GameButton backButton;
    private GameButton soundButton;
    private GameButton musicButton;
    private Bitmap scoreApple;

    private ThreadDraw draw;
    private ThreadUpdate update;
    private MediaPlayer soundClick;
    private Paint textPaint;
    private Paint rect;

    private long timeToStart;




    public GameDisplay(GameActivity game) {
        super(game);
        getHolder().addCallback(this);
        rect = new Paint();
        rect.setColor(Color.BLACK);
        rect.setAlpha(70);
        gameActivity = game;
        snake = new Snake(this);
        food = new Food(this);
        startGame = true;
        showButton = HORISON_BUTTON;
        lvlMap = new LvlMap(this);
        soundClick = MediaPlayer.create(getContext(), raw.sound_click);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        boolean retry = true;

        while (retry){

            try {
                draw.join();
                if(update != null)
                    update.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDraw(Canvas canvas){

        if(startGame){
            resetGame();
        }

        lvlMap.drawBackground(canvas);
        snake.drawSnake(canvas);
        lvlMap.drawBorder(canvas);
        food.drawFood(canvas);
        this.drawButton(canvas);

        if(!snake.isMove()) {
            if (PAUSE)
                lvlMap.drawRecord(canvas);
        }
        else
            drawScore(canvas);

    }


    private void resetGame(){

        SIZE_ITEM = (getHeight()/10);

        textPaint.setTextSize(SIZE_ITEM/1.5f);
        lvlMap.setItemPosition();
        if(LvlMap.CURRENT_LEVEL <= LvlMap.LEVEL_6){
            snake.setStartPosSnake(10, 7);
            food.setStartFood(10, 2);
        }
        else if(LvlMap.CURRENT_LEVEL ==  LvlMap.LEVEL_7){
            snake.setStartPosSnake(13, 7);
            food.setStartFood(13, 2);

        }

        else if(LvlMap.CURRENT_LEVEL >=  LvlMap.LEVEL_8){
            snake.setStartPosSnake((getWidth()/SIZE_ITEM)-2, 7);
            food.setStartFood(2, 2);

        }

        sizeButtonX1 = sizeButtonY2 = (int)(SIZE_ITEM*2.5f);
        sizeButtonY1 = sizeButtonX2 =SIZE_ITEM ;
        buttonUp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_up), sizeButtonX1, sizeButtonY1, true);
        buttonDown = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_down), sizeButtonX1, sizeButtonY1, true);
        buttonRight = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_right),sizeButtonX2,sizeButtonY2,true);
        buttonLeft = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_left),sizeButtonX2,sizeButtonY2,true);

        backButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_back),(int)(SIZE_ITEM*1.5f)/2,getHeight()-((int)(SIZE_ITEM*1.5f)), (int)(SIZE_ITEM*1.5f));
        restartButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_restart),getWidth() / 3 - GameDisplay.SIZE_ITEM * 1.5f, getHeight() / 2 - GameDisplay.SIZE_ITEM * 1.5f,GameDisplay.SIZE_ITEM*3);
        pauseButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_pause),getWidth()  - (SIZE_ITEM*1.5f),0,(int)(SIZE_ITEM*1.5f));
        resumeButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_resume),getWidth() / 1.5f - GameDisplay.SIZE_ITEM * 1.5f, getHeight() / 2 - GameDisplay.SIZE_ITEM * 1.5f,GameDisplay.SIZE_ITEM*3);
        scoreApple = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.food_main),(int)(SIZE_ITEM/1.5f),(int)(SIZE_ITEM/1.5f),true);

        if(GameActivity.PLAY_SOUND)
            soundButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_sound_on),(int)(SIZE_ITEM*1.5f)/2,getHeight()/8, (int)(SIZE_ITEM*1.5f));
        else
            soundButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_sound_off),(int)(SIZE_ITEM*1.5f)/2,getHeight()/8, (int)(SIZE_ITEM*1.5f));

        if(GameActivity.PLAY_MUSIC)
            musicButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_music_on),(int)(SIZE_ITEM*1.5f)/2,getHeight()/8 + (int)(SIZE_ITEM*1.5f), (int)(SIZE_ITEM*1.5f));
        else
            musicButton = new GameButton(BitmapFactory.decodeResource(getResources(), drawable.button_music_off),(int)(SIZE_ITEM*1.5f)/2,getHeight()/8 + (int)(SIZE_ITEM*1.5f), (int)(SIZE_ITEM*1.5f));

        startUpdateThread();
        timeToStart = System.currentTimeMillis();
        startGame = false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!startGame) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                if (!PAUSE) {
                    // КНОПКА ПАУЗЫ

                    if (pauseButton.touch(event.getX(), event.getY())) {
                        gameActivity.onPause();
                        gameActivity.musicPause();
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();
                        return true;
                    }
                    // КНОПКИ УПРАВЛЕНИЯ
                    if (snake.getDirectionSnake() != snake.STOP) {
                        if (event.getX() < (getWidth() / 2) && snake.getDirectionSnake() != Snake.MOVE_RIGHT && showButton != VERTICAL_BUTTON) {
                            showButton = VERTICAL_BUTTON;
                            snake.setDirectionSnake(Snake.MOVE_LEFT);
                            return true;

                        } else if (event.getX() > (getWidth() / 2) && snake.getDirectionSnake() != Snake.MOVE_LEFT && showButton != VERTICAL_BUTTON) {
                            showButton = VERTICAL_BUTTON;
                            snake.setDirectionSnake(Snake.MOVE_RIGHT);
                            return true;
                        } else if (event.getY() < (getHeight() / 2) && snake.getDirectionSnake() != Snake.MOVE_DOWN && showButton != HORISON_BUTTON) {

                            showButton = HORISON_BUTTON;
                            snake.setDirectionSnake(Snake.MOVE_UP);
                            return true;
                        } else if (event.getY() > (getHeight() / 2) && snake.getDirectionSnake() != Snake.MOVE_UP && showButton != HORISON_BUTTON) {

                            showButton = HORISON_BUTTON;
                            snake.setDirectionSnake(Snake.MOVE_DOWN);
                            return true;
                        }
                    }


                    ////////////////////

                    return true;
                } else {

                    // КНОПКА ПРОДОЛЖИТЬ
                    if (snake.isMove()) {
                        if (resumeButton.touch(event.getX(), event.getY())) {
                            startThread();
                            gameActivity.musicUp();
                            if (GameActivity.PLAY_SOUND)
                                soundClick.start();
                            return true;
                        }
                    }

                    // РЕСТАРТ
                    if (restartButton.touch(event.getX(), event.getY())) {
                        reset();
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();

                        gameActivity.musicUp();


                        return true;
                    }

                    //НАЗАД
                    if (backButton.touch(event.getX(), event.getY())) {
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();
                        gameActivity.musicPause();
                        setRecord();
                        Intent intent = new Intent(gameActivity, SelectLevelActivity.class);
                        gameActivity.startActivity(intent);
                        gameActivity.finish();

                        return true;
                    }

                    // ВКЛ / ВЫКЛ ЗВУК
                    if (soundButton.touch(event.getX(), event.getY())) {
                        soundClick.start();

                        if (GameActivity.PLAY_SOUND) {
                            soundButton.setBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_sound_off));
                            GameActivity.PLAY_SOUND = false;
                        } else {
                            soundButton.setBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_sound_on));
                            GameActivity.PLAY_SOUND = true;
                        }
                        Canvas canvas = getHolder().lockCanvas();
                        onDraw(canvas);
                        getHolder().unlockCanvasAndPost(canvas);

                        return true;
                    }
                    if (musicButton.touch(event.getX(), event.getY())) {
                        soundClick.start();
                        if (GameActivity.PLAY_MUSIC) {
                            musicButton.setBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_music_off));
                            gameActivity.musicPause();
                            GameActivity.PLAY_MUSIC = false;
                        } else {
                            musicButton.setBitmap(BitmapFactory.decodeResource(getResources(), drawable.button_music_on));
                            GameActivity.PLAY_MUSIC = true;


                        }
                        Canvas canvas = getHolder().lockCanvas();
                        onDraw(canvas);
                        getHolder().unlockCanvasAndPost(canvas);

                        return true;
                    }
                }
            }
        }

        return false;
    }
    public void startThread(){
        PAUSE = false;
        update = new ThreadUpdate(this);
        update.start();
        startDrawThread();
    }
    public void startUpdateThread(){
        PAUSE = false;
        update = new ThreadUpdate(this);
        update.start();

    }

    private void drawStartGameBanner(Canvas canvas){

        if(timeToStart + 2500 > System.currentTimeMillis()) {

            int size = SIZE_ITEM * 2;
            Integer timer = (int)((timeToStart + 2999 - System.currentTimeMillis() )/1000);

            if(timer == 2)
                canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.timer_two), size, size, true), getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, null);
            else if(timer == 1)
                canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.timer_one), size, size, true), getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, null);
            else if(timer == 0){
                size *= 1.5;
                canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.timer_go), size, size, true), getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, null);

            }


        }
        else snake.startMove();

    }

    private void reset(){
        gameActivity.resetGame();

    }

    private void drawButton(Canvas canvas){
        if(!PAUSE) {
            if(snake.getDirectionSnake() != snake.STOP) {
                if (showButton == HORISON_BUTTON) {
                    canvas.drawBitmap(buttonRight, getWidth() - (buttonRight.getWidth() * 2), (getHeight() / 2) - (buttonRight.getHeight() / 2), null);
                    canvas.drawBitmap(buttonLeft, buttonLeft.getWidth(), (getHeight() / 2) - (buttonLeft.getHeight() / 2), null);
                } else if (showButton == VERTICAL_BUTTON) {
                    canvas.drawBitmap(buttonUp, (getWidth() / 2) - (buttonUp.getWidth() / 2), buttonUp.getHeight() - (buttonUp.getHeight() / 2), null);
                    canvas.drawBitmap(buttonDown, (getWidth() / 2) - (buttonDown.getWidth() / 2), getHeight() - (buttonDown.getHeight() + (buttonDown.getHeight() / 2)), null);
                }
            }
            else drawStartGameBanner(canvas);
            pauseButton.draw(canvas);
        }
        else {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAlpha(120);
            canvas.drawRect(0,0,getWidth(),getHeight(),paint);

            if(snake.isMove())
                resumeButton.draw(canvas);

            restartButton.draw(canvas);
            backButton.draw(canvas);
            soundButton.draw(canvas);
            musicButton.draw(canvas);


        }

    }



    public Snake getSnake(){
        return snake;
    }
    public Food getFood(){
        return food;
    }

    public void pause() {
        IN_RUNNING = false;
        PAUSE = true;
    }

    public synchronized void update() {
        snake.updateSnake();
        gameActivity.musicUp();

    }

    public void startDrawThread(){
        IN_RUNNING = true;
        draw = new ThreadDraw(this);
        draw.start();

    }


    public void updateFoot(){
        food.updateFood();
    }


    public LvlMap getLvlMap(){
        return lvlMap;
    }

    public void setRecord() {
        lvlMap.setRecordAndScore(gameActivity.updateRecord(),snake.getScore());
    }


    private void drawScore(Canvas canvas) {
        canvas.drawRect(0,SIZE_ITEM/8,SIZE_ITEM *2,SIZE_ITEM-SIZE_ITEM/8 ,rect);
        canvas.drawBitmap(scoreApple,SIZE_ITEM/6,SIZE_ITEM/8,null);
        if(snake.getScore() < 10)
            canvas.drawText(" " + snake.getScore().toString(),SIZE_ITEM,SIZE_ITEM/1.5f, textPaint);
        else
            canvas.drawText(snake.getScore().toString(),SIZE_ITEM,SIZE_ITEM/1.5f, textPaint);
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private class GameButton {
        private float posX;
        private float posY;
        private int size;
        private Bitmap background;
        GameButton(Bitmap bitmap, float X, float Y,int sizeButton){
            posX = X;
            posY = Y;
            size = sizeButton;
            background = Bitmap.createScaledBitmap(bitmap,size,size,true);

        }

        public void setBitmap(Bitmap bitmap){
            background = Bitmap.createScaledBitmap(bitmap,size,size,true);
        }


        public void draw(Canvas canvas){
            canvas.drawBitmap(background,posX,posY,null);

        }


        public boolean touch(float X, float Y){
            if(X > posX && X < posX + size  && Y > posY && Y < posY + size)
                return true;

            else
                return false;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
}
