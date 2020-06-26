package com.example.flappybird;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Listener {

    private Canvas canvas;
    private Paint paint;
    private GameController controller;
    PauseButton pause;

    public GameView(Context context){
        super(context);
        canvas = new Canvas();
        paint = new Paint();
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.CENTER);
        pause = new PauseButton();
    }

    public PauseButton getPause(){return pause;}

    public void setController(GameController newController){controller = newController;}

    public void onDraw(Canvas canvas){
        // this is only drawn as the "welcome" screen
        if(controller.getGameState() == GameModel.BEFORE){
            canvas.drawText("Tap to Start", (float)getWidth() / 2, (float)getHeight() / 2, paint);
            paint.setColor(Color.MAGENTA);
            canvas.drawRect((float)(getWidth() *0.8), 0, (float)(getWidth() * 0.8) + 80, (float)(getHeight() * 0.333), paint);
            canvas.drawRect((float)(getWidth() *0.8), (float)(getHeight() * 0.333) + 390, (float)(getWidth() * 0.8) + 80, getHeight(), paint);
        }
        controller.moveBird();
        controller.showPlayer(canvas, paint);
        controller.updatePipes();
        controller.showPipes(canvas, paint);
        if(controller.getGameState() == GameModel.ON)
            controller.checkHit();
        pause.draw(canvas, paint);
        paint.setColor(Color.WHITE);
        if(controller.getGameState() != GameModel.BEFORE)
            canvas.drawText(Integer.toString(controller.score()), (float)getWidth() / 2, (float)(getHeight() * 0.125), paint);
        if(controller.getGameState() == GameModel.OFF){
            canvas.drawText("Best: " + Integer.toString(controller.bestScore()),
                    (float)(getWidth() / 2), (float)(getHeight() * 0.25), paint);
            canvas.drawText("Tap to restart", (float)(getWidth() / 2), (float)(getHeight() / 2), paint);
        }
        if(!((controller.getGameState() == GameModel.OFF || controller.getGameState() == GameModel.BEFORE) && controller.birdOnGround()))
            invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        return controller.handleTouch(event);
    }

    @Override
    public void modelChanged() {
        pause.setPaused(controller.getGameState() == GameModel.PAUSE);
        invalidate();
    }

}
