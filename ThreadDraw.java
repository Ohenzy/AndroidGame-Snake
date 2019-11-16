package com.ohenzy.games.snake;
import android.graphics.Canvas;


public class ThreadDraw extends Thread {



    private GameDisplay display;
    private Canvas canvas;
    private long time;

    private int FPS;
    ThreadDraw(GameDisplay display){
        setName("drawThread");
        this.display = display;

    }


    @Override
    public void run() {

        time = System.currentTimeMillis();
        FPS = 0;
        System.out.println("START DRAW");

        while (GameDisplay.IN_RUNNING) {

            if (time + 1000 <= System.currentTimeMillis()) {
                time = System.currentTimeMillis();

                System.out.println(FPS);
                FPS = 0;

                if( GameDisplay.PAUSE) {
                    return;

                }
            }
                canvas = null;
                try {
                    canvas = display.getHolder().lockCanvas();
                    if (canvas != null)
                        synchronized (canvas) {
                            display.onDraw(canvas);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null)
                        display.getHolder().unlockCanvasAndPost(canvas);
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            FPS++;
        }

        canvas = null;
        try {
            canvas = display.getHolder().lockCanvas();
            if (canvas != null)
                synchronized (canvas) {
                    display.onDraw(canvas);
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null)
                display.getHolder().unlockCanvasAndPost(canvas);


        }
        System.out.println("FINISH DRAW");
    }

}
