package com.ohenzy.games.snake;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import java.util.LinkedList;
import java.util.List;


public class LvlMap {
    public static final Integer LEVEL_1 = 1;
    public static final Integer LEVEL_2 = 2;
    public static final Integer LEVEL_3 = 3;
    public static final Integer LEVEL_4 = 4;
    public static final Integer LEVEL_5 = 5;
    public static final Integer LEVEL_6 = 6;
    public static final Integer LEVEL_7 = 7;
    public static final Integer LEVEL_8 = 8;
    public static final Integer LEVEL_9 = 9;
    public static Integer CURRENT_LEVEL = LEVEL_1;

    private List<Integer> borderX;
    private List<Integer> borderY;

    private Paint paintRect;
    private Paint text;
    private GameDisplay display;
    private Bitmap appleBitmap;
    private Bitmap cupBitmap;

    private Integer score;
    private Integer record;
    private Bitmap borderBot;
    private Bitmap borderTop;
    private Bitmap borderRight;
    private Bitmap borderLeft;
    private Bitmap border;
    private String mainColor;


    LvlMap(GameDisplay gameDisplay) {
        borderX = new LinkedList<>();
        borderY = new LinkedList<>();
        display = gameDisplay;
        paintRect = new Paint();

        if(CURRENT_LEVEL != LEVEL_7 || CURRENT_LEVEL != LEVEL_8) {
            paintRect.setColor(Color.parseColor("#58e421"));
            mainColor = "#46cd0a";
        }
        else   {
            paintRect.setColor(Color.parseColor("#7ead36"));
            mainColor ="#91c73e";
        }

        paintRect.setStyle(Paint.Style.FILL);
        paintRect.setAntiAlias(true);
        text = new Paint();
        text.setColor(Color.WHITE);
        text.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));

    }

    // 58e421 - светлый
    // 46cd0a - темный

    public void drawBackground(Canvas canvas) {

            canvas.drawColor(Color.parseColor(mainColor));


        for (int w = 0; w < display.getWidth(); w += GameDisplay.SIZE_ITEM * 2)
            for (int h = 0; h < display.getHeight(); h += (GameDisplay.SIZE_ITEM * 2)) {
                canvas.drawRect(w, h, w + GameDisplay.SIZE_ITEM, h + GameDisplay.SIZE_ITEM, paintRect);
                canvas.drawRect(w + GameDisplay.SIZE_ITEM, h + GameDisplay.SIZE_ITEM, w + GameDisplay.SIZE_ITEM * 2, h + GameDisplay.SIZE_ITEM * 2, paintRect);
            }




    }

    public void drawBorder(Canvas canvas){

        if(CURRENT_LEVEL != LEVEL_1 && CURRENT_LEVEL != LEVEL_4)
            for(int i = 0; i < borderX.size();i++)
                canvas.drawBitmap(border, borderX.get(i), borderY.get(i), null);



        if(CURRENT_LEVEL == LEVEL_4 || CURRENT_LEVEL == LEVEL_5 || CURRENT_LEVEL == LEVEL_6 || CURRENT_LEVEL == LEVEL_8 ){
            for(int i = 0;i<display.getWidth();i+=GameDisplay.SIZE_ITEM) {
                canvas.drawBitmap(borderBot, i, (display.getHeight() - GameDisplay.SIZE_ITEM), null);
                canvas.drawBitmap(borderTop, i, 0, null);
            }
            for(int i = 0;i<display.getHeight();i+=GameDisplay.SIZE_ITEM) {
                canvas.drawBitmap(borderLeft, 0, i, null);
                canvas.drawBitmap(borderRight, display.getWidth()-GameDisplay.SIZE_ITEM, i, null);
            }

        }
    }




    public void setItemPosition() {
        appleBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.image_record_apple), (int)(GameDisplay.SIZE_ITEM * 3.5f), (int)(GameDisplay.SIZE_ITEM * 3.5f), true);
        cupBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.image_record_cup), (int)(GameDisplay.SIZE_ITEM * 3.5f), (int)(GameDisplay.SIZE_ITEM * 3.5f), true);


        if(CURRENT_LEVEL == LEVEL_2) {

            border = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_rock_1), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);

            for (int i = 0; i < 4; i++){
                borderX.add(GameDisplay.SIZE_ITEM*5);
                borderY.add(GameDisplay.SIZE_ITEM*(3+i));
                borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*5);
                borderY.add(GameDisplay.SIZE_ITEM*(3+i));

            }
        }

        else if( CURRENT_LEVEL == LEVEL_3){

            border = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_rock_1), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            // TOP LEFT
            borderX.add(GameDisplay.SIZE_ITEM*6);
            borderY.add(GameDisplay.SIZE_ITEM*3);
            borderX.add(GameDisplay.SIZE_ITEM*6);
            borderY.add(GameDisplay.SIZE_ITEM*2);
            borderX.add(GameDisplay.SIZE_ITEM*5);
            borderY.add(GameDisplay.SIZE_ITEM*3);

            // BOT LEFT
            borderX.add(GameDisplay.SIZE_ITEM*6);
            borderY.add(GameDisplay.SIZE_ITEM*6);
            borderX.add(GameDisplay.SIZE_ITEM*5);
            borderY.add(GameDisplay.SIZE_ITEM*6);
            borderX.add(GameDisplay.SIZE_ITEM*6);
            borderY.add(GameDisplay.SIZE_ITEM*7);

            //TOP RIGHT
            borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6 );
            borderY.add(GameDisplay.SIZE_ITEM*3);
            borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*5 );
            borderY.add(GameDisplay.SIZE_ITEM*3);
            borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6 );
            borderY.add(GameDisplay.SIZE_ITEM*2);

            //BOT RIGHT
            borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6) ;
            borderY.add(GameDisplay.SIZE_ITEM*6);
            borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*5 );
            borderY.add(GameDisplay.SIZE_ITEM*6);
            borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6) ;
            borderY.add(GameDisplay.SIZE_ITEM*7);
        }

        else if(CURRENT_LEVEL == LEVEL_4 || CURRENT_LEVEL == LEVEL_5 || CURRENT_LEVEL == LEVEL_6){

            borderBot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_bottom), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            borderTop = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_top), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            borderRight = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_right), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            borderLeft = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_left), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);

            if(CURRENT_LEVEL == LEVEL_5){

                border = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_rock_2), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);

                borderX.add(GameDisplay.SIZE_ITEM*6);
                borderY.add(GameDisplay.SIZE_ITEM*2);
                borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6) ;
                borderY.add(GameDisplay.SIZE_ITEM*2);


                borderX.add(GameDisplay.SIZE_ITEM*6);
                borderY.add(GameDisplay.SIZE_ITEM*3);
                borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6) ;
                borderY.add(GameDisplay.SIZE_ITEM*3);


                borderX.add(GameDisplay.SIZE_ITEM*6);
                borderY.add(GameDisplay.SIZE_ITEM*6);
                borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6 );
                borderY.add(GameDisplay.SIZE_ITEM*6);


                borderX.add(GameDisplay.SIZE_ITEM*6);
                borderY.add(GameDisplay.SIZE_ITEM*7);
                borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6) ;
                borderY.add(GameDisplay.SIZE_ITEM*7) ;
            }

            if (CURRENT_LEVEL == LEVEL_6){
                border = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_rock_2), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);

                for(int i = 0;i<3;i++){
                    borderX.add(GameDisplay.SIZE_ITEM*(1+i));
                    borderY.add(GameDisplay.SIZE_ITEM*3);

                    borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*(1+i));
                    borderY.add(GameDisplay.SIZE_ITEM*6);

                    borderX.add(GameDisplay.SIZE_ITEM*6);
                    borderY.add(GameDisplay.SIZE_ITEM*(6+i));

                    borderX.add(((display.getWidth()/GameDisplay.SIZE_ITEM)*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*6);
                    borderY.add(GameDisplay.SIZE_ITEM*(1+i));
                }
            }
        }
        else if(CURRENT_LEVEL == LEVEL_7){

            border = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_rock_3), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            int halfScreen =((display.getWidth()/GameDisplay.SIZE_ITEM)/2);
            if(((int)(display.getWidth()/GameDisplay.SIZE_ITEM)) % 2 == 0)halfScreen-=1;
            int j = 9;
            for (int i = 0; i < 4;i++) {
                borderX.add(halfScreen * GameDisplay.SIZE_ITEM);
                borderY.add(i*GameDisplay.SIZE_ITEM);
                borderX.add((halfScreen * GameDisplay.SIZE_ITEM)+GameDisplay.SIZE_ITEM);
                borderY.add(j--*GameDisplay.SIZE_ITEM);
            }
            for (int i = 0; i < (display.getWidth()/GameDisplay.SIZE_ITEM)-1;i++) {
                if(i < halfScreen) {
                    borderX.add(i * GameDisplay.SIZE_ITEM);
                    borderY.add(5 * GameDisplay.SIZE_ITEM);
                }
                else {
                    borderX.add((i+2) * GameDisplay.SIZE_ITEM);
                    borderY.add(4 * GameDisplay.SIZE_ITEM);

                }
            }
        }
        else if(CURRENT_LEVEL == LEVEL_8){

            borderBot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_bottom_2), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            borderTop = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_top_2), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            borderRight = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_right_2), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);
            borderLeft = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_left_2), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);

            border = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.border_rock_3), GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM, true);

            int test = (display.getWidth()/GameDisplay.SIZE_ITEM)/4;
            int j = 8;
            for(int i = 0; i<3;i++){
                borderX.add(inPixel(6));
                borderY.add(inPixel(j--));

                borderX.add(inPixelToRight(6));
                borderY.add(inPixel(i+1));
            }

            for(int i = 0; i<test;i++){
                borderX.add(inPixel(5+i));
                borderY.add(inPixel(3));

                borderX.add(inPixelToRight(5+i));
                borderY.add(inPixel(6));
            }

            borderX.add(inPixel(4));
            borderY.add(inPixel(4));
            borderX.add(inPixel(4));
            borderY.add(inPixel(5));
            borderX.add(inPixel(5));
            borderY.add(inPixel(6));

            borderX.add(inPixelToRight(5));
            borderY.add(inPixel(3));
            borderX.add(inPixelToRight(4));
            borderY.add(inPixel(4));
            borderX.add(inPixelToRight(4));
            borderY.add(inPixel(5));

        }

    }

    private int inPixel(int item){
            return item*GameDisplay.SIZE_ITEM ;

    }
    private int inPixelToRight(int item){
        return (display.getWidth()/GameDisplay.SIZE_ITEM*GameDisplay.SIZE_ITEM) - GameDisplay.SIZE_ITEM*item;

    }


    public void drawRecord(Canvas canvas) {

        text.setTextSize(GameDisplay.SIZE_ITEM * 1.5f);
        Paint rect = new Paint();
        rect.setColor(Color.BLACK);
        rect.setAlpha(100);
        canvas.drawRect(display.getWidth()/2,0,display.getWidth()-(GameDisplay.SIZE_ITEM*1.5f),display.getHeight(),rect);


        if(record == score){
            cupBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(display.getResources(), R.drawable.image_newrecord_cup), GameDisplay.SIZE_ITEM * 4, GameDisplay.SIZE_ITEM * 4, true);
            canvas.drawBitmap(cupBitmap,display.getWidth()/2,GameDisplay.SIZE_ITEM*3,null);
            canvas.drawText(record.toString(),display.getWidth()/2 + (cupBitmap.getWidth()),GameDisplay.SIZE_ITEM*3+(cupBitmap.getHeight()/2+(GameDisplay.SIZE_ITEM/2)),text);


        }else {
            drawScore(canvas,appleBitmap,score.toString(),display.getWidth()/2+GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM*1.5f + appleBitmap.getHeight());
            drawScore(canvas,cupBitmap,record.toString(),display.getWidth()/2+GameDisplay.SIZE_ITEM, GameDisplay.SIZE_ITEM*1.5f);

        }
    }
    public void setRecordAndScore(Integer record, Integer score) {
        this.record = record;
        this.score = score;

    }

    private void drawScore(Canvas canvas,Bitmap bitmap,String string,float X, float Y){
        canvas.drawBitmap(bitmap,X,Y,null);
        canvas.drawText(string,X+bitmap.getWidth(),Y+(bitmap.getHeight()-(GameDisplay.SIZE_ITEM)),text);
    }

    public boolean isOverBorder(float X, float Y){
        if(CURRENT_LEVEL == LEVEL_1 || CURRENT_LEVEL == LEVEL_2 || CURRENT_LEVEL == LEVEL_3 || CURRENT_LEVEL == LEVEL_7) {
            if (X < 0 - GameDisplay.SIZE_ITEM / 2 || Y < 0 - GameDisplay.SIZE_ITEM / 2 || X > display.getWidth() - GameDisplay.SIZE_ITEM / 2 || Y > display.getHeight() - GameDisplay.SIZE_ITEM / 2){
                headSnakeTeleport();

            }


        }
        else if (CURRENT_LEVEL == LEVEL_4 || CURRENT_LEVEL == LEVEL_5 || CURRENT_LEVEL == LEVEL_6 || CURRENT_LEVEL == LEVEL_8) {
            if (X < GameDisplay.SIZE_ITEM * 0.5f || Y < GameDisplay.SIZE_ITEM * 0.5f || X > display.getWidth() - GameDisplay.SIZE_ITEM * 1.5f || Y > display.getHeight() - GameDisplay.SIZE_ITEM * 1.5f)
                return true;

        }

        if(CURRENT_LEVEL != LEVEL_1 && CURRENT_LEVEL != LEVEL_4){

            //// BORDER

            for(int i = 0;i<borderX.size();i++){
                if(borderX.get(i) - GameDisplay.SIZE_ITEM/2 <= X && borderX.get(i) + GameDisplay.SIZE_ITEM/2 >= X && borderY.get(i)-GameDisplay.SIZE_ITEM/2 <= Y && borderY.get(i) + GameDisplay.SIZE_ITEM/2 >= Y )
                    return true;

            }
        }
        return false;
    }


    private void headSnakeTeleport(){
            if (display.getSnake().getDirectionSnake() ==Snake.MOVE_UP)
                display.getSnake().setHeadY(display.getHeight() - GameDisplay.SIZE_ITEM / 2);
            else if (display.getSnake().getDirectionSnake() ==Snake.MOVE_DOWN)
                display.getSnake().setHeadY(-GameDisplay.SIZE_ITEM / 2);
            else if (display.getSnake().getDirectionSnake() ==Snake.MOVE_RIGHT)
                display.getSnake().setHeadX(-GameDisplay.SIZE_ITEM / 2);
            else if (display.getSnake().getDirectionSnake() ==Snake.MOVE_LEFT)
                display.getSnake().setHeadX(display.getWidth() - GameDisplay.SIZE_ITEM / 2);

    }

}