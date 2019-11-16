package com.ohenzy.games.snake;

public class ThreadUpdate extends Thread {


    private GameDisplay display;
    public long sleep;


    ThreadUpdate(GameDisplay gameDisplay){
        setName("updateThread");
        display = gameDisplay;
        sleep = 6;

    }


    @Override
    public void run(){
        System.out.println("START UPDATE");
        if (display.getHeight() >= 1080)
            sleep = 4;


        while (!GameDisplay.PAUSE) {

            display.update();

            try {
                sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("FINISH UPDATE");
    }


}
