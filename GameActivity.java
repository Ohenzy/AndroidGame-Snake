package com.ohenzy.games.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


public class GameActivity extends AppCompatActivity {


    public static boolean PLAY_SOUND = false;
    public static boolean PLAY_MUSIC = false;





    private SharedPreferences saves;
    private GameDisplay gameDisplay;
    private MediaPlayer musicGame;


    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(" game " + "  onCreate()");
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        saves = getSharedPreferences(LvlMap.CURRENT_LEVEL.toString(), Context.MODE_PRIVATE);

        musicGame = MediaPlayer.create(this, R.raw.music_game);
        if (PLAY_MUSIC)
            musicGame.start();

        if (gameDisplay == null)
            gameDisplay = new GameDisplay(this);

        setContentView(gameDisplay);
    }

    @Override
    public void onResume() {
        System.out.println(" game " + "onResume()");
        super.onResume();
        gameDisplay.startDrawThread();

    }

    @Override
    public void onRestart() {
        System.out.println(" game " + "onRestart()");
        super.onRestart();


    }

    public void resetGame() {
        gameDisplay = new GameDisplay(this);
        setContentView(gameDisplay);
        gameDisplay.startDrawThread();
    }

    @Override
    public void onPause() {
        System.out.println(" game " + "onPause()");
        super.onPause();
        gameDisplay.pause();
        musicPause();

    }

    @Override
    public void onStart() {
        System.out.println(" game " + "onStart()");
        super.onStart();

    }


    @Override
    public void onStop() {
        System.out.println(" game " + "onStop()");
        super.onStop();

    }

    @Override
    public void onDestroy() {
        System.out.println(" game " + "onDestroy()");
        super.onDestroy();
        musicStop();

    }

    public void musicPause() {
        if (musicGame.isPlaying())
            musicGame.pause();


    }

    public void musicUp() {

        if (musicGame == null && PLAY_MUSIC)
            musicGame = MediaPlayer.create(this, R.raw.music_game);

        if (!musicGame.isPlaying() && PLAY_MUSIC)
            musicGame.start();

    }

    public void musicStop() {
        if (musicGame.isPlaying()) {
            musicGame.stop();

        }
        musicGame = null;
    }

    public Integer updateRecord() {
        if (gameDisplay.getSnake().getScore() > saves.getInt(LvlMap.CURRENT_LEVEL.toString(), 0)) {
            SharedPreferences.Editor editor = saves.edit();
            editor.putInt(LvlMap.CURRENT_LEVEL.toString(), gameDisplay.getSnake().getScore());
            editor.apply();
        }
        return saves.getInt(LvlMap.CURRENT_LEVEL.toString(), 0);
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
