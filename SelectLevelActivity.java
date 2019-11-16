package com.ohenzy.games.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;



public class SelectLevelActivity extends AppCompatActivity {

    private final int passingLevel = 0;
    private MediaPlayer soundClick;
    private ImageButton buttonStartGame;
    private ButtonLevel oneLevel;
    private ButtonLevel twoLevel;
    private ButtonLevel threeLevel;
    private ButtonLevel fourLevel;
    private ButtonLevel fiveLevel;
    private ButtonLevel sixLevel;
    private ButtonLevel sevenLevel;
    private ButtonLevel eightLevel;


    private SharedPreferences gemstone;
    private TextView gemstoneText;

    private ArrayList<Integer> listRecord;

    public void onCreate(Bundle save) {
        super.onCreate(save);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.setting_activity);
        listRecord = new ArrayList<>();
        setListRecord();
        gemstone = getSharedPreferences("gemstone", Context.MODE_PRIVATE);
        soundClick = MediaPlayer.create(this,R.raw.sound_click);
        buttonStartGame = findViewById(R.id.buttonStart);
        oneLevel = new ButtonLevel((ImageButton) findViewById(R.id.buttonOneLevel),LvlMap.LEVEL_1);
        twoLevel = new ButtonLevel((ImageButton) findViewById(R.id.buttonTwoLevel),LvlMap.LEVEL_2);
        threeLevel = new ButtonLevel((ImageButton) findViewById(R.id.buttonThreeLevel),LvlMap.LEVEL_3);
        fourLevel = new ButtonLevel((ImageButton) findViewById(R.id.buttonFourLevel),LvlMap.LEVEL_4);
        fiveLevel = new ButtonLevel((ImageButton) findViewById(R.id.buttonFiveLevel),LvlMap.LEVEL_5);
        sixLevel = new ButtonLevel((ImageButton) findViewById(R.id.buttonSixLevel),LvlMap.LEVEL_6);
        sevenLevel = new ButtonLevel((ImageButton)findViewById(R.id.buttonSevenLevel), LvlMap.LEVEL_7);
        eightLevel = new ButtonLevel((ImageButton) findViewById(R.id.buttonEightLevel),LvlMap.LEVEL_8);

        gemstoneText = findViewById(R.id.textGemstone);
        updateCurrency();
        unlockLevel();
        touchListener();
////////////////////////////////////////////////////////////////////////////////////////////////////
        ImageButton restartRecord = findViewById(R.id.buttonRestartRecord);
        restartRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    dropRecordAndGemstone();
                    Intent intent = new Intent(SelectLevelActivity.this, SelectLevelActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        restartRecord.setVisibility(Button.VISIBLE);
////////////////////////////////////////////////////////////////////////////////////////////////////

        selected(LvlMap.LEVEL_1);
    }

    private void updateCurrency(){
        gemstoneText.setText(" "+gemstone.getInt("gemstone",0));
    }

    private void  touchListener(){

        buttonStartGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE )
                    if (event.getX() > buttonStartGame.getX() && event.getX() < buttonStartGame.getX() + buttonStartGame.getWidth() && event.getY() > buttonStartGame.getY() && event.getY() < buttonStartGame.getY() + buttonStartGame.getHeight()) {
                        buttonStartGame.setBackgroundResource(R.drawable.button_start_press);
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            if(GameActivity.PLAY_SOUND)
                                soundClick.start();

                        }
                    }
                    else {
                        buttonStartGame.setBackgroundResource(R.drawable.button_start);

                    }

                if(event.getAction() == MotionEvent.ACTION_UP)
                    if (event.getX() > buttonStartGame.getX() && event.getX() < buttonStartGame.getX() + buttonStartGame.getWidth() && event.getY() > buttonStartGame.getY() && event.getY() < buttonStartGame.getY() + buttonStartGame.getHeight()) {

                        Intent intent = new Intent(SelectLevelActivity.this, GameActivity.class);
                        startActivity(intent);
                        finish();
                    }

                return false;
            }
        });





        oneLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !oneLevel.lock){
                    if(LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_1) {
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();

                        selected(LvlMap.LEVEL_1);
                        return true;
                    }
                }
                return false;
            }
        });

        twoLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !twoLevel.lock){
                    if(LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_2) {
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();


                        selected(LvlMap.LEVEL_2);
                        return true;
                    }
                }
                return false;
            }
        });

        threeLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !threeLevel.lock){
                    if(LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_3) {
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();


                        selected(LvlMap.LEVEL_3);
                        return true;
                    }
                }
                return false;
            }
        });

        fourLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !fourLevel.lock){
                    if(LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_4) {
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();


                        selected(LvlMap.LEVEL_4);
                        return true;
                    }
                }
                return false;
            }
        });

        fiveLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !fiveLevel.lock){
                    if(LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_5) {
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();

                        selected(LvlMap.LEVEL_5);
                        return true;
                    }
                }
                return false;
            }
        });

        sixLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !sixLevel.lock){
                    if(LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_6) {
                        if (GameActivity.PLAY_SOUND)
                            soundClick.start();

                        selected(LvlMap.LEVEL_6);
                        return true;
                    }
                }
                return false;
            }
        });

        sevenLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN ){
                    if(!sevenLevel.lock) {
                        if (LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_7) {
                            if (GameActivity.PLAY_SOUND)
                                soundClick.start();

                            selected(LvlMap.LEVEL_7);
                            return true;
                        }
                    }
                    else {
                        SharedPreferences preferences = getSharedPreferences("buy_level_7" , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        if(addCurrency(-100)) {
                            editor.putBoolean("buy_level_7", true);
                            selected(LvlMap.LEVEL_7);

                        }
                        else
                            editor.putBoolean("buy_level_7", false  );

                        editor.apply();
                        unlockLevel();


                    }
                }
                return false;
            }
        });

        eightLevel.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN ){
                    if(!eightLevel.lock) {
                        if (LvlMap.CURRENT_LEVEL != LvlMap.LEVEL_8) {
                            if (GameActivity.PLAY_SOUND)
                                soundClick.start();

                            selected(LvlMap.LEVEL_8);
                            return true;
                        }
                    }
                    else {
                        SharedPreferences preferences = getSharedPreferences("buy_level_8" , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        if(addCurrency(-100)) {
                            editor.putBoolean("buy_level_8", true);
                            selected(LvlMap.LEVEL_8);

                        }
                        else
                            editor.putBoolean("buy_level_8", false  );

                        editor.apply();
                        unlockLevel();


                    }
                }
                return false;
            }
        });


    }

    private boolean addCurrency(int currency){
        if(gemstone.getInt("gemstone",0) + currency >= 0){
            SharedPreferences.Editor editor = gemstone.edit();
            editor.putInt("gemstone", gemstone.getInt("gemstone",0) + currency);
            editor.apply();
            updateCurrency();
            return true;
        }
        else return false;
    }

    private void selected(int selectLevel){

        LvlMap.CURRENT_LEVEL = selectLevel;

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_1) {
            oneLevel.button.setBackgroundResource(R.drawable.button_level_one_select);
            oneLevel.update(true);
        }
        else {
            oneLevel.button.setBackgroundResource(R.drawable.button_level_one);
            oneLevel.update(false);
        }

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_2 ) {
            twoLevel.button.setBackgroundResource(R.drawable.button_level_two_select);
            twoLevel.update(true);
        }
        else  {
            twoLevel.button.setBackgroundResource(R.drawable.button_level_two);
            twoLevel.update(false);
        }

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_3){
            threeLevel.button.setBackgroundResource(R.drawable.button_level_three_select);
            threeLevel.update(true);
        }
        else  {
            threeLevel.button.setBackgroundResource(R.drawable.button_level_three);
            threeLevel.update(false);
        }

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_4) {
            fourLevel.button.setBackgroundResource(R.drawable.button_level_four_select);
            fourLevel.update(true);
        }
        else{
            fourLevel.button.setBackgroundResource(R.drawable.button_level_four);
            fourLevel.update(false);
        }

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_5) {
            fiveLevel.button.setBackgroundResource(R.drawable.button_level_five_select);
            fiveLevel.update(true);
        }
        else  {
            fiveLevel.button.setBackgroundResource(R.drawable.button_level_five);
            fiveLevel.update(false);
        }

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_6){
            sixLevel.button.setBackgroundResource(R.drawable.button_level_six_select);
            sixLevel.update(true);
        }

        else  {
            sixLevel.button.setBackgroundResource(R.drawable.button_level_six);
            sixLevel.update(false);
        }

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_7){
            sevenLevel.button.setBackgroundResource(R.drawable.button_level_seven_select);
            sevenLevel.update(true);
        }

        else  {
            sevenLevel.button.setBackgroundResource(R.drawable.button_level_seven);
            sevenLevel.update(false);
        }

        if(LvlMap.CURRENT_LEVEL == LvlMap.LEVEL_8){
            eightLevel.button.setBackgroundResource(R.drawable.button_level_eight_select);
            eightLevel.update(true);
        }

        else  {
            eightLevel.button.setBackgroundResource(R.drawable.button_level_eight);
            eightLevel.update(false);
        }



    }


    private void dropRecordAndGemstone() {
        for (Integer index = LvlMap.LEVEL_1; index <= LvlMap.LEVEL_9; index++) {                         // MAX LEVEL

            SharedPreferences record = getSharedPreferences(index.toString(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = record.edit();
            editor.putInt(index.toString(), 0);
            editor.apply();

        }

        SharedPreferences preferences = getSharedPreferences("buy_level_7" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("buy_level_7", false);
        editor.apply();

        preferences = getSharedPreferences("buy_level_8" , Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("buy_level_8", false);
        editor.apply();


        editor = gemstone.edit();
        editor.putInt("gemstone", 1000);
        editor.apply();
    }

    private void setListRecord(){
        for(Integer index = 1;index<=LvlMap.LEVEL_9;index++){
            SharedPreferences record = getSharedPreferences(index.toString(), Context.MODE_PRIVATE);
            listRecord.add(record.getInt(index.toString(), 0));
        }
    }

    private void unlockLevel(){

        ImageView lockImage;
        TextView text;
        oneLevel.lock = false;
        SharedPreferences preferences;
        if(listRecord.get(LvlMap.LEVEL_1 - 1) >= passingLevel) {
            twoLevel.lock = false;
            lockImage = findViewById(R.id.lockLevel_2);
            lockImage.setVisibility(View.INVISIBLE);
        }

        if(listRecord.get(LvlMap.LEVEL_2 - 1) >= passingLevel) {
            threeLevel.lock = false;
            lockImage = findViewById(R.id.lockLevel_3);
            lockImage.setVisibility(View.INVISIBLE);

        }
        if(listRecord.get(LvlMap.LEVEL_3 - 1) >= passingLevel){
            fourLevel.lock = false;
            lockImage = findViewById(R.id.lockLevel_4);
            lockImage.setVisibility(View.INVISIBLE);
        }

        if(listRecord.get(LvlMap.LEVEL_4 - 1) >= passingLevel) {
            fiveLevel.lock = false;
            lockImage = findViewById(R.id.lockLevel_5);
            lockImage.setVisibility(View.INVISIBLE);
        }
        if(listRecord.get(LvlMap.LEVEL_5 - 1) >= passingLevel) {
            sixLevel.lock = false;
            lockImage = findViewById(R.id.lockLevel_6);
            lockImage.setVisibility(View.INVISIBLE);
        }

        preferences = getSharedPreferences("buy_level_7" , Context.MODE_PRIVATE);
        if(preferences.getBoolean("buy_level_7",false)){
            sevenLevel.lock = false;
            lockImage = findViewById(R.id.lockLevel_7);
            text = findViewById(R.id.textLevel_7);
            text.setVisibility(View.INVISIBLE);
            lockImage.setVisibility(View.INVISIBLE);

        }
        preferences = getSharedPreferences("buy_level_8" , Context.MODE_PRIVATE);
        if(preferences.getBoolean("buy_level_8",false)){
            eightLevel.lock = false;
            lockImage = findViewById(R.id.lockLevel_8);
            text = findViewById(R.id.textLevel_8);
            text.setVisibility(View.INVISIBLE);
            lockImage.setVisibility(View.INVISIBLE);

        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private class ButtonLevel {

        private ImageButton button;
        private ImageView image;
        private TextView text;
        private String strRecord;
        private Integer level;
        private boolean lock;

        ButtonLevel(ImageButton imageButton, Integer levelMap){

            lock = true;
            level= levelMap;
            image = findViewById(R.id.imageApple);
            button = imageButton;
            strRecord = formRecord(level);
            text = findViewById(R.id.textRecord);
            text.setText(strRecord);
        }

        private void update(boolean select) {
            if(select) {

                text.setText(strRecord);
                text.setX(button.getX() + (button.getWidth() / 3f));
                text.setY(button.getY() + (button.getHeight() / 4.1f) );
                image.setX(button.getX() + (button.getWidth() / 13));
                image.setY(button.getY() + (button.getHeight() / 3.5f));

            }

        }



        private String formRecord(Integer level){

            if(level <= LvlMap.LEVEL_6) {
                if (listRecord.get(level - 1) < passingLevel) {
                    Integer tmp = passingLevel;
                    if (listRecord.get(level - 1) < 10)
                        return " " + listRecord.get(level - 1).toString() + "/" + tmp.toString();
                    else return listRecord.get(level - 1).toString() + "/" + tmp.toString();
                }
            }
            return "  " + listRecord.get(level - 1).toString();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
}
