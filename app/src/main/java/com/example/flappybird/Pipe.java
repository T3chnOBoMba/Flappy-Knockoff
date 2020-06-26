package com.example.flappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Pipe {

    private float gapSize, topLength, bottomLength, velocity, x, width;

    /**
     * Constructor for Pipe class
     * @param gap how large the space between the top/bottom portions of the pipe is
     * @param v the velocity, or how quickly the pipe will be approaching the player
     *          (value must be negative or the pipe will move away from the player)
     * @param w the width of the pipe
     */
    public Pipe(float gap, float v, float w){
        gapSize = gap;
        velocity = v;
        width = w;
        topLength = 0;
        bottomLength = 0;
        x = 0;
    }

    public float getX(){return x;}

    public float getWidth(){return width;}

    public float getTopLength(){return topLength;}

    public float getGapSize(){return gapSize;}

    public float getVelocity(){return velocity;}

    public void setVelocity(float newV){velocity = newV;}

    /**
     * Sets the value for the top and bottom lengths of the pipe
     * @param viewHeight the height of the view the pipe will be drawn in
     */
    public void initializePipe(float viewHeight, float viewWidth){
        float usableLength = viewHeight - gapSize;
        // max size an individual length can be
        float max = (usableLength - (float)(usableLength*.1));
        // min size an individual length can be
        float min = (float)(usableLength*.1);
        // random length given to top of pipe, remainder of available length goes to bottom
        topLength = (float)(Math.random() * ((max - min) + 1))  + min;
        bottomLength = usableLength - topLength;

        x = viewWidth;
    }

    public void drawPipe(Canvas canvas, Paint paint){
        paint.setColor(Color.MAGENTA);
        canvas.drawRect(x, 0, x + width, topLength, paint);                     // top
        canvas.drawRect(x, canvas.getHeight() - bottomLength, x + width, canvas.getHeight(), paint);  //bottom
    }

    /**
     * Moves the pipe across the screen
     */
    public void update(){x += velocity;}
}
