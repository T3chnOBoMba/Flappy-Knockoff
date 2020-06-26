package com.example.flappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {

    private float yPos;
    private double gravity, velocity, lift;
    private int radius;

    /**
     * Represents the player in the game (currently represented by a circle
     * @param y starting y coordinate of the player
     * @param g the gravity, which effects how fast the player falls
     * @param l the lift force, effects how high a player jumps
     */
    public Player(float y, double g, double l){
        yPos = y;
        gravity = g;
        velocity = 0;   // where the player is moving and how fast
        lift = l;
        radius = 50;
    }

    public double getLift(){return lift;}

    public int getRadius(){return radius;}

    public void setyPos(float pos){yPos = pos;}

    public float getyPos(){return yPos;}

    public void setVelocity(double v){velocity = v;}

    public double getVelocity(){return velocity;}

    public double getGravity(){return gravity;}

    public void jump(){
        velocity -= lift;
    }

    public void drawPlayer(float y, Canvas canvas, Paint paint) {
        paint.setColor(Color.CYAN);
        canvas.drawCircle((float) (canvas.getWidth() * .2), y, radius, paint);
    }
}
