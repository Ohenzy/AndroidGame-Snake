package com.ohenzy.games.snake;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private ImageButton startButton;
    private MediaPlayer soundClick;





    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_activity);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        soundClick = MediaPlayer.create(this,R.raw.sound_click);




        startButton = findViewById(R.id.buttonStart);
        startButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE )
                    if (event.getX() > startButton.getX() && event.getX() < startButton.getX() + startButton.getWidth() && event.getY() > startButton.getY() && event.getY() < startButton.getY() + startButton.getHeight()) {
                        startButton.setBackgroundResource(R.drawable.button_play_press);
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            if(GameActivity.PLAY_SOUND)
                                soundClick.start();
                        }
                    }
                    else {

                        startButton.setBackgroundResource(R.drawable.button_play);

                    }

                if(event.getAction() == MotionEvent.ACTION_UP)
                    if (event.getX() > startButton.getX() && event.getX() < startButton.getX() + startButton.getWidth() && event.getY() > startButton.getY() && event.getY() < startButton.getY() + startButton.getHeight()) {
                        if(GameActivity.PLAY_SOUND)
                            soundClick.start();

                        Intent intent = new Intent(MenuActivity.this, SelectLevelActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }

                return false;
            }
        });
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



}
