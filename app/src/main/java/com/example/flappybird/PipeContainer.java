package com.example.flappybird;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * A container to store Pipe objects when the game is running
 */
public class PipeContainer {

    private Pipe[] pipes;           // array to store the pipes
    private int size, pipeSpeed;    // the amount of pipes in the array/speed of the pipes

    public PipeContainer(){
        pipes = new Pipe[10];
        size = 0;
        pipeSpeed = -10;
    }

    public boolean isFull(){return size > 10;}

    public boolean isEmpty(){return size  == 0;}

    public int getPipeSpeed(){return pipeSpeed;}

    public void setPipeSpeed(int newSpeed){pipeSpeed = newSpeed;}

    /**
     * Adds given pipe in the first open spot of the container
     * @param p the pipe to be added
     */
    public void addPipe(Pipe p){
        if(!isFull()){
            boolean pipeAdded = false;
            for(int i = 0; i < pipes.length && !pipeAdded; i++){
                if(pipes[i] == null) {
                    pipes[i] = p;
                    pipeAdded = true;
                    size++;
                }
            }
        }
    }

    /**
     * Removes a given Pipe from the container
     * @param p
     */
    public void remove(Pipe p){
        boolean pipeRemoved = false;
        for(int i = 0; i < pipes.length && !pipeRemoved; i++){
            if(pipes[i] == p){
                pipes[i] = null;
                pipeRemoved = true;
                size--;
            }
        }
    }

    /**
     * Clears all pipes from the container
     */
    public void clear() {
        for (int i = 0; i < pipes.length; i++)
            pipes[i] = null;
        size = 0;
    }


    public int getSize(){return size;}

    /**
     * Checks if the player has hit a pipe
     * @param playerX the player's x position
     * @param playerY the player's y position
     * @param radius the radius of the player
     * @return true if there is a hit, false otherwise
     */
    public boolean checkHit(float playerX, float playerY, int radius){
        boolean xHit, yHit;
        for(Pipe p: pipes) {
            if (p != null) {
                // check if x-coordinate hits
                xHit = (((playerX + radius) > p.getX()) || (playerX - radius) > p.getX()) &&
                        (((playerX + radius) < (p.getX() + p.getWidth())) || ((playerX - radius) < (p.getX() + p.getWidth())));

                // check if y-coordinate hits
                yHit = (((playerY + radius) < p.getTopLength()) || ((playerY - radius) < p.getTopLength())) ||
                        (((playerY + radius) > (p.getTopLength() + p.getGapSize())) || ((playerY - radius) > (p.getTopLength() + p.getGapSize())));

                if (xHit && yHit)
                    return true;
            }
        }
        return false;
    }

    /**
     * Draws each Pipe in the container onto a canvas
     * @param canvas a canvas to draw on
     * @param paint a paint object for drawing
     */
    public void drawPipes(Canvas canvas, Paint paint) {
        if (!isEmpty()) {
            for (Pipe p : pipes) {
                if(p != null)
                    p.drawPipe(canvas, paint);
            }
        }
    }

    /**
     * Moves each pipe in the container across the screen
     */
    public boolean update() {
            for (Pipe p : pipes) {
                if(p != null) {
                    p.update();
                    // if pipe is offscreen, remove it
                    if ((p.getX() + p.getWidth()) < 0) {
                        remove(p);
                        return true;
                    }
                }
            }
            return false;
    }

    public void updateSpeed(){
        for(Pipe p: pipes){
            if(p != null)
                p.setVelocity(pipeSpeed);
        }
    }
}
