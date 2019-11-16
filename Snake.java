package com.ohenzy.games.snake;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;


public class Snake {

    private Paint paint;
    private Paint paint_dark;
    private GameDisplay display;
    private Bitmap snakeHead;
    private Bitmap snakeHeadClose;
    private Bitmap snakeHeadOpen;
    private Bitmap snakePain;
    private Matrix matrix;
    private MediaPlayer[] soundApple;
    private MediaPlayer[] soundTwist;
    private MediaPlayer soundGemstone;
    private MediaPlayer soundHit;
    private int stepDraw;
    private float headX;
    private float headY;
    private float[] bodyX;
    private float[] bodyY;
    private int degrees;
    private int lastPosX;
    private int lastPosY;
    private int directionSnake;
    private int directionLast;
    private int sizeSnake;
    private float speed;
    private boolean move;
    private int playerScore;


    public static final int MOVE_UP = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_RIGHT = 3;
    public static final int MOVE_LEFT = 4;
    public static final int STOP = 0;

    // f58018 светлый орандж
    // f07100 стандартный орандж

    Snake(GameDisplay gameDisplay){

        playerScore = 0;
        degrees = 0;
        display = gameDisplay;
        paint = new Paint();
        paint_dark = new Paint();
        paint_dark.setColor(Color.parseColor("#fa7601"));
//        paint_dark.setColor(Color.parseColor("#46cd0a"));
        paint_dark.setStyle(Paint.Style.FILL);
        paint_dark.setAntiAlias(true);
        paint.setColor(Color.parseColor("#f07100"));
//        paint.setColor(Color.parseColor("#58e421"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        move = true;
        directionLast = directionSnake = STOP;
//        directionLast = MOVE_UP;
        matrix = new Matrix();
        bodyX = new float[20000];
        bodyY = new float[20000];
        speed = 2;
        stepDraw = 5;
        soundApple = new MediaPlayer[2];
        soundTwist = new MediaPlayer[2];
        soundApple[0] = MediaPlayer.create(display.getContext(),R.raw.sound_apple);
        soundApple[1] = MediaPlayer.create(display.getContext(),R.raw.sound_apple);
        soundTwist[0] =  MediaPlayer.create(display.getContext(),R.raw.sound_twist);
        soundTwist[1] =  MediaPlayer.create(display.getContext(),R.raw.sound_twist);
        soundGemstone = MediaPlayer.create(display.getContext(),R.raw.sound_gemstone);
        soundHit = MediaPlayer.create(display.getContext(),R.raw.sound_gameover);
    }

    public void setStartPosSnake(int itemX,int itemY){


        headX = inPixel(itemX);
        headY = inPixel(itemY);
        sizeSnake = GameDisplay.SIZE_ITEM/2;

        lastPosX = itemX;
        lastPosY = itemY;
        snakeHeadClose = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(),R.drawable.head_orange_close),GameDisplay.SIZE_ITEM*2,GameDisplay.SIZE_ITEM*2,true);
        snakeHeadOpen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(),R.drawable.head_orange_open),GameDisplay.SIZE_ITEM*2,GameDisplay.SIZE_ITEM*2,true);
        snakePain = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(),R.drawable.head_orange_pain),GameDisplay.SIZE_ITEM*2,GameDisplay.SIZE_ITEM*2,true);
        snakeHead = snakeHeadClose;

        for(int i = 0; i < bodyX.length;i++){
            bodyX[i] = -GameDisplay.SIZE_ITEM;
            bodyY[i] = -GameDisplay.SIZE_ITEM;
        }
        bodyX[0] = headX;
        bodyY[0] = headY;
        for(int i = 0; i < sizeSnake;i++){
            bodyX[i+1] = bodyX[i];
            bodyY[i+1] = bodyY[i] + speed;

        }

    }

    public void drawSnake(Canvas canvas){

        drawBody(canvas);
        // DRAW HEAD
        matrix.setTranslate(headX-GameDisplay.SIZE_ITEM/2, headY-GameDisplay.SIZE_ITEM/2);
        matrix.preRotate(degrees,GameDisplay.SIZE_ITEM,GameDisplay.SIZE_ITEM);
        canvas.drawBitmap(snakeHead,matrix,null);

    }

    public void updateSnake(){

        if (move ) {
            // ОТВЕЧАЕТ ЗА ДВИЖЕНИЕ ПО КЛЕТКАМ И ПОВОРОТ ГОЛОВЫ
            if (lastPosX != (int)inItem(headX) || lastPosY != (int)inItem( headY)) {
                lastPosX = (int)inItem( headX);
                lastPosY = (int)inItem( headY);


                if (directionSnake != directionLast) {

                    if(GameActivity.PLAY_SOUND){
                        if(soundTwist[0].isPlaying())
                            soundTwist[1].start();
                        else
                            soundTwist[0].start();
                    }

                    if (directionLast == MOVE_UP) {
                        if (directionSnake == MOVE_LEFT)
                            degrees += 90;
                        else if (directionSnake == MOVE_RIGHT)
                            degrees -= 90;
                    } else if (directionLast == MOVE_DOWN) {
                        if (directionSnake == MOVE_LEFT)
                            degrees -= 90;
                        if (directionSnake == MOVE_RIGHT)
                            degrees += 90;
                    } else if (directionLast == MOVE_LEFT) {
                        if (directionSnake == MOVE_UP)
                            degrees -= 90;
                        else if (directionSnake == MOVE_DOWN)
                            degrees += 90;
                    } else if (directionLast == MOVE_RIGHT) {
                        if (directionSnake == MOVE_UP)
                            degrees += 90;
                        else if (directionSnake == MOVE_DOWN)
                            degrees -= 90;
                    }
                    directionSnake = directionLast;

                }
            }
        }

        //ДВИЖЕНИЕ ЗМЕИ
        if (MOVE_UP == directionSnake)
            headY -= speed;

        else if (MOVE_DOWN == directionSnake)
            headY += speed;

        else if (MOVE_RIGHT == directionSnake)
            headX += speed;

        else if (MOVE_LEFT == directionSnake)
            headX -= speed;

        animationHead();



        bodyX[0] = headX;
        bodyY[0] = headY;
        if(directionLast != STOP) {
            for (int i = sizeSnake; i > 0; i--) {
                bodyX[i] = bodyX[i - 1];
                bodyY[i] = bodyY[i - 1];
            }
        }
        else {
            for (int i = sizeSnake; i > 0; i--) {
                bodyX[i] = bodyX[i - 1];
                bodyY[i] = bodyY[i - 1]+speed;
            }

        }

        move = checkCollision();
        if(!move) {
            snakeHead = snakePain;
            if(GameActivity.PLAY_SOUND)
                soundHit.start();

            display.setRecord();
            GameDisplay.PAUSE = true;
            GameDisplay.IN_RUNNING = false;
        }

    }

    private void animationHead(){
        if (MOVE_UP == directionSnake) {
            if ((headY - GameDisplay.SIZE_ITEM / 2 <= display.getFood().getFoodY() + GameDisplay.SIZE_ITEM * 2 && headY + display.SIZE_ITEM / 2 >= display.getFood().getFoodY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getFoodX() && headX + display.SIZE_ITEM / 2 >= display.getFood().getFoodX() || (headY - GameDisplay.SIZE_ITEM / 2 <= display.getFood().getGemstoneY() + GameDisplay.SIZE_ITEM * 2 && headY + display.SIZE_ITEM / 2 >= display.getFood().getGemstoneY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getGemstoneX() && headX + display.SIZE_ITEM / 2 >= display.getFood().getGemstoneX()) {
                snakeHead = snakeHeadOpen;
            } else snakeHead = snakeHeadClose;

        } else if (MOVE_DOWN == directionSnake) {
            if ((headY - display.SIZE_ITEM / 2 >= display.getFood().getFoodY() - display.SIZE_ITEM * 2 && headY + display.SIZE_ITEM / 2 <= display.getFood().getFoodY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getFoodX() && headX + display.SIZE_ITEM / 2 >= display.getFood().getFoodX() || (headY - display.SIZE_ITEM / 2 >= display.getFood().getGemstoneY() - display.SIZE_ITEM * 2 && headY + display.SIZE_ITEM / 2 <= display.getFood().getGemstoneY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getGemstoneX() && headX + display.SIZE_ITEM / 2 >= display.getFood().getGemstoneX()) {
                snakeHead = snakeHeadOpen;
            } else snakeHead = snakeHeadClose;


        } else if (MOVE_RIGHT == directionSnake) {
            if ((headY - display.SIZE_ITEM / 2 <= display.getFood().getFoodY() && headY + display.SIZE_ITEM / 2 >= display.getFood().getFoodY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getFoodX() && headX + display.SIZE_ITEM / 2 >= display.getFood().getFoodX() - display.SIZE_ITEM * 2 || (headY - display.SIZE_ITEM / 2 <= display.getFood().getGemstoneY() && headY + display.SIZE_ITEM / 2 >= display.getFood().getGemstoneY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getGemstoneX() && headX + display.SIZE_ITEM / 2 >= display.getFood().getGemstoneX() - display.SIZE_ITEM * 2) {
                snakeHead = snakeHeadOpen;
            } else snakeHead = snakeHeadClose;

        } else if (MOVE_LEFT == directionSnake) {
            if ((headY - display.SIZE_ITEM / 2 <= display.getFood().getFoodY() && headY + display.SIZE_ITEM / 2 >= display.getFood().getFoodY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getFoodX() + display.SIZE_ITEM * 2 && headX + display.SIZE_ITEM / 2 >= display.getFood().getFoodX() || (headY - display.SIZE_ITEM / 2 <= display.getFood().getGemstoneY() && headY + display.SIZE_ITEM / 2 >= display.getFood().getGemstoneY()) && headX - display.SIZE_ITEM / 2 <= display.getFood().getGemstoneX() + display.SIZE_ITEM * 2 && headX + display.SIZE_ITEM / 2 >= display.getFood().getGemstoneX()) {
                snakeHead = snakeHeadOpen;
            } else snakeHead = snakeHeadClose;
        }


    }

    public void setDirectionSnake(int course) {
        directionLast = course;
    }

    public void startMove(){
        directionSnake = directionLast = MOVE_UP;
    }
    public int getDirectionSnake(){
        return directionSnake;
    }


    private float inPixel(int item){
        return item * GameDisplay.SIZE_ITEM;
    }

    private float inItem(float pixel){
        if(GameDisplay.SIZE_ITEM!=0) {
            if((pixel / GameDisplay.SIZE_ITEM) < 0)
                return -1;
            else
                return pixel / GameDisplay.SIZE_ITEM;
        }
        else return 0;
    }


    public void setHeadY(float Y){
        headY = Y;
    }
    public void setHeadX(float X){
        headX = X;
    }
    private boolean checkCollision(){


        // СТОЛКНОВЕНИЕ С ГРАНИЦЕЙ УРОВНЯ
       if(display.getLvlMap().isOverBorder(headX,headY))
           return false;


        // ПОЕДАНИЕ ПИЩИ
        if( (headY-display.SIZE_ITEM/2 <= display.getFood().getFoodY() && headY+display.SIZE_ITEM/2 >= display.getFood().getFoodY() ) && headX-display.SIZE_ITEM/2 <= display.getFood().getFoodX() && headX+display.SIZE_ITEM/2 >= display.getFood().getFoodX() ){

            if(GameActivity.PLAY_SOUND) {
                if (soundApple[0].isPlaying())
                    soundApple[1].start();
                else
                    soundApple[0].start();
            }
            playerScore++;
            sizeSnake+= GameDisplay.SIZE_ITEM/2;
            display.updateFoot();
            return true;
        }
        if( (headY-display.SIZE_ITEM/2 <= display.getFood().getGemstoneY() && headY+display.SIZE_ITEM/2 >= display.getFood().getGemstoneY() ) && headX-display.SIZE_ITEM/2 <= display.getFood().getGemstoneX() && headX+display.SIZE_ITEM/2 >= display.getFood().getGemstoneX() ) {
            if(GameActivity.PLAY_SOUND)
                soundGemstone.start();

            display.getFood().eatGemstone();


        }
        // СТОЛКНОВЕНИЕ С ХВОСТОМ
        for (int i = GameDisplay.SIZE_ITEM; i <= sizeSnake ; i+=10)
            if(bodyX[i]-GameDisplay.SIZE_ITEM/2 <= headX && bodyX[i] + GameDisplay.SIZE_ITEM/2 >= headX && bodyY[i]-GameDisplay.SIZE_ITEM/2 <= headY && bodyY[i] + GameDisplay.SIZE_ITEM/2 >= headY )
                return false;

        return true;
    }

    public boolean checkHitBox(int foodPosX , int foodPosY){


        for (int i = 0; i <= sizeSnake ; i++)
            if(bodyX[i]-GameDisplay.SIZE_ITEM/2 <= foodPosX && bodyX[i] + GameDisplay.SIZE_ITEM/2 >= foodPosX && bodyY[i]-GameDisplay.SIZE_ITEM/2 <= foodPosY && bodyY[i] + GameDisplay.SIZE_ITEM/2 >= foodPosY )
                return true;
        return false;

    }

    private void drawBody(Canvas canvas) {

        float radius = GameDisplay.SIZE_ITEM / 2.2f;


        for (int i = GameDisplay.SIZE_ITEM / 6; i < sizeSnake; i += stepDraw ) {

            // ПОЛОСАТОСТЬ ЗМЕЙКИ
            if((i / (GameDisplay.SIZE_ITEM/4))%2 == 0 )
                canvas.drawCircle(bodyX[i] + GameDisplay.SIZE_ITEM / 2, bodyY[i] + GameDisplay.SIZE_ITEM / 2, radius, paint);

            else
                canvas.drawCircle(bodyX[i] + GameDisplay.SIZE_ITEM / 2, bodyY[i] + GameDisplay.SIZE_ITEM / 2, radius, paint_dark);

            if (radius > GameDisplay.SIZE_ITEM / 4) {
                radius -= 0.04f;
            }

        }

    }

    public Integer getScore(){
        return playerScore;
    }



    public boolean isMove() {
        return move;
    }
}



