package com.example.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PauseButton{

    private boolean isPaused;
    private Canvas canvas;
    private Paint paint;
    private float x1, y1;

    public PauseButton(){
        isPaused = false;
        canvas = new Canvas();
        paint = new Paint();
        x1 = 108;
        y1 = 63.504f;
    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        canvas.drawRect(x1, y1, x1 + 15, y1 + 110, paint);
        canvas.drawRect(x1+ 40, y1, x1 + 55, y1 + 110, paint);
//        if(!isPaused) {
//            canvas.drawRect(x1, y1, x1 + 15, y1 + 110, paint);
//            canvas.drawRect(x1+ 40, y1, x1 + 55, y1 + 110, paint);
//        }
//        else{
//            canvas.drawLine(x1,y1, x1, y1 + 20, paint);
//            canvas.drawLine(x1, y1 + 20, x1 + 18, y1 + 10, paint);
//            canvas.drawLine(x1 + 18, y1 + 10, x1, y1, paint);
//        }
    }

    public boolean checkClick(float xCoord, float yCoord){
        boolean xHit = (xCoord >= (x1 - 20)) && (xCoord <= (x1 + 75));
        boolean yHit = (yCoord >= (y1 - 10)) && (yCoord <= y1 + 120);
        return (xHit && yHit);
    }

    public void setPaused(boolean b){isPaused = b;}

}
