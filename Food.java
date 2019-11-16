package com.ohenzy.games.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.Random;

public class Food {

    private int foodX;
    private int foodY;
    private Random random;
    private Bitmap foodImage;       //f0220c цвет яблока
    private Bitmap gemstoneImage;
    private Bitmap foodImageScale;
    private Bitmap gemstoneImageScale;

    private GameDisplay display;
    private Long timer;



    private int sizeImage;
    private int step = -2;
    private int gemstoneX;
    private int gemstoneY;
    private int lastGemstoneX;
    private int lastGemstoneY;
    private boolean luck;
    private long timeGemstone;
    private long timeAnimation;
    private Paint textPaint;

    Food(GameDisplay gameDisplay){

        display = gameDisplay;
        random = new Random();
        luck = false;
        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#7f2fb4"));
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
    }

    public void drawFood(Canvas canvas){

        canvas.drawBitmap(foodImageScale, foodX, foodY,null);

        if(timeOutGemstone() && luck) {
            timer = ((timeGemstone + 9000 - System.currentTimeMillis() )/1000);
            canvas.drawBitmap(gemstoneImageScale, gemstoneX, gemstoneY, null);
            canvas.drawText(timer.toString(),lastGemstoneX+GameDisplay.SIZE_ITEM/3, correctPosition(lastGemstoneY + GameDisplay.SIZE_ITEM*1.6f) ,textPaint);
        }
        foodAnimation();
    }

    private float correctPosition(float Y ){
        if(Y > (display.getHeight()-GameDisplay.SIZE_ITEM*2))
            return Y - GameDisplay.SIZE_ITEM * 2;
        else return Y;
    }

    public void setStartFood(int itemX, int itemY) {
        foodX = inPixel(itemX);
        foodY = inPixel(itemY);
        textPaint.setTextSize(GameDisplay.SIZE_ITEM/1.5f);
        sizeImage = GameDisplay.SIZE_ITEM;
        foodImage = (BitmapFactory.decodeResource(display.getResources(), R.drawable.food_main));
        gemstoneImage = (BitmapFactory.decodeResource(display.getResources(), R.drawable.gemstone));
        foodImageScale = Bitmap.createScaledBitmap(foodImage, sizeImage, sizeImage, true);
        gemstoneImageScale = Bitmap.createScaledBitmap(gemstoneImage, sizeImage, sizeImage, true);
    }

    public void updateFood(){


        sizeImage = GameDisplay.SIZE_ITEM;
        if(step < 0) step *= -1;
        if(luck) {
            gemstoneX = lastGemstoneX;
            gemstoneY = lastGemstoneY;
        }

        boolean flag;
        do { // SET FOOD
            flag = false;
            foodX = inPixel(random.nextInt((display.getWidth()/GameDisplay.SIZE_ITEM)-1));
            foodY = inPixel(random.nextInt((display.getHeight()/GameDisplay.SIZE_ITEM)-1));

            if(display.getSnake().checkHitBox(foodX, foodY))
                flag = true;
            if(display.getLvlMap().isOverBorder(foodX, foodY))
                flag = true;

            if(luck)
                if(gemstoneX-GameDisplay.SIZE_ITEM/2 <= foodX && gemstoneX + GameDisplay.SIZE_ITEM/2 >= foodX && gemstoneX-GameDisplay.SIZE_ITEM/2 <= foodY && gemstoneX + GameDisplay.SIZE_ITEM/2 >= foodY )
                    flag = true;


        }while (flag);


        if(!luck) {
            if(random.nextInt(100) >= 80){
                luck = true;
                do {// SET GEMSTONE
                    flag = false;
//
                    gemstoneX = inPixel(random.nextInt((display.getWidth() / GameDisplay.SIZE_ITEM) - 1));
                    gemstoneY = inPixel(random.nextInt((display.getHeight() / GameDisplay.SIZE_ITEM) - 1));
                    lastGemstoneX = gemstoneX;
                    lastGemstoneY = gemstoneY;
                    if (display.getSnake().checkHitBox(gemstoneX, gemstoneY))
                        flag = true;
                    if (display.getLvlMap().isOverBorder(gemstoneX, gemstoneY))
                        flag = true;
                    if (gemstoneX - GameDisplay.SIZE_ITEM / 2 <= foodX && gemstoneX + GameDisplay.SIZE_ITEM / 2 >= foodX && gemstoneX - GameDisplay.SIZE_ITEM / 2 <= foodY && gemstoneX + GameDisplay.SIZE_ITEM / 2 >= foodY)
                        flag = true;
                    timeGemstone = System.currentTimeMillis();
                } while (flag);
            }
        }
    }


    private void foodAnimation(){


        if(timeAnimation + 50 < System.currentTimeMillis()) {
            sizeImage += step;
            if (step < 0) {
                if(luck) {
                    gemstoneX += ((step / 2) * -1);
                    gemstoneY += ((step / 2) * -1);
                }
                foodX += ((step / 2) * -1);
                foodY += ((step / 2) * -1);

            } else {
                if(luck) {
                    gemstoneX -= step / 2;
                    gemstoneY -= step / 2;
                }
                foodX -= step / 2;
                foodY -= step / 2;

            }

            if (sizeImage < GameDisplay.SIZE_ITEM)
                step *= -1;

            if (sizeImage >= GameDisplay.SIZE_ITEM *1.2)
                step *= -1;

                foodImageScale = Bitmap.createScaledBitmap(foodImage, sizeImage, sizeImage, true);

            if(luck)
                gemstoneImageScale = Bitmap.createScaledBitmap(gemstoneImage, sizeImage, sizeImage, true);


            timeAnimation = System.currentTimeMillis();
        }

    }

    private int inPixel(int item){
        return item*GameDisplay.SIZE_ITEM;
    }


    public int getFoodY(){
        return foodY;
    }
    public int getFoodX(){
        return foodX;
    }

    public int getGemstoneX(){
        return gemstoneX;
    }
    public int getGemstoneY(){
        return gemstoneY;
    }

    private boolean timeOutGemstone(){

        if(timeGemstone + 9000 < System.currentTimeMillis()){
            luck = false;
            gemstoneX = -GameDisplay.SIZE_ITEM;
            gemstoneY = -GameDisplay.SIZE_ITEM;
            return false;
        }


        return true;
    }

    public void eatGemstone() {
        luck = false;
        gemstoneX = -GameDisplay.SIZE_ITEM;
        gemstoneY = -GameDisplay.SIZE_ITEM;

        SharedPreferences bankGemston = display.getContext().getSharedPreferences("gemstone", Context.MODE_PRIVATE);
        int tmp = bankGemston.getInt("gemstone", 0)+1;
        SharedPreferences.Editor editor = bankGemston.edit();
        editor.putInt("gemstone", tmp);
        editor.apply();

    }

}
