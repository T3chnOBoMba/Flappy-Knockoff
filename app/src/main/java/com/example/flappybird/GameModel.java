package com.example.flappybird;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;

public class GameModel {

    // game states
    public static final int ON = 0;
    public static final int OFF = 1;
    public static final int BEFORE = 2;
    public static final int PAUSE = 3;

    // SharedPreferences
    public static final String SHARED_PREFS = "stats";
    public static final String HIGH_SCORE = "high score";

    private int state;                 // tells whether the game is running or not
    private Player bird;               // the player object
    private GameView view;             // view where the game is drawn
    private int score, bestScore;      // the player's current score
    private PipeContainer pipes;       // container to store all prepared pipes
    private Vibrator v;
    boolean vibrateOn;                 // tells if the device should vibrate or not
    Context context;
    SharedPreferences sharedPref;      // handles data saved to storage


    public GameModel(Vibrator vib, Context c){
        state = BEFORE;
        context = c;
        sharedPref = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        score = 0;
        loadData();
        view = null;
        //2.7 original gravity
        bird = new Player(200, 3, 55);
        pipes = new PipeContainer();
        v = vib;
        vibrateOn = true;
    }

    /*
    Used to retrieve previously saved stats
     */
    private void loadData() {
        // retrieve best score, or assign 0 if it is not found
        bestScore = sharedPref.getInt(HIGH_SCORE, 0);
        // may have more stats added to this
    }

    /*
    Updates the high score if it is broken, could be used to update more stats in the future
     */
    private void updateData(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(HIGH_SCORE, bestScore);
        editor.apply();
    }


    public Player getBird(){return bird;}

    public int getGameState(){return state;}

    public void setGameState(int mode){state = mode;}

    public int getScore(){return score;}

    public int getBestScore(){return bestScore;}

    /*** @return the Y-coordinate of the player*/
    public float getBirdY(){return bird.getyPos();}

    public PipeContainer getContainer(){return pipes;}

    public void setView(GameView newView){view = newView;}

    /**
     * Makes the bird fall and stay on the screen
     */
    public void updateBirdPos() {
        // if player is not on the floor
        if(getBirdY() < view.getHeight()) {
            bird.setVelocity(bird.getVelocity() + bird.getGravity());
            // creates air resistance to prevent momentum
            bird.setVelocity(bird.getVelocity() * 0.9);
        }
        bird.setyPos((float) (bird.getyPos() + bird.getVelocity()));

        // make sure bird stays on screen
        if ((bird.getyPos() + bird.getRadius()) < 0)
            bird.setyPos(bird.getRadius());
        else if((bird.getyPos() + bird.getRadius()) > view.getHeight())
            bird.setyPos(view.getHeight() - bird.getRadius());
    }

    /** Makes the player "jump" */
    public void birdJump(){
        bird.jump();
        updateView();
    }

    /**
     * Creates a Pipe object and puts it in the container
     * @param viewHeight height of the view the Pipe will be drawn on
     * @param viewWidth width of the view the Pipe will be drawn on
     * @return the newly prepared Pipe ready to be put into the game
     */
    public Pipe preparePipe(float viewHeight, float viewWidth){
        // if the score is a multiple of 10, make the game faster
        if(score % 10 == 0) {
            pipes.setPipeSpeed(pipes.getPipeSpeed() - 1);
            pipes.updateSpeed();
        }
        Pipe pipe = new Pipe(390, pipes.getPipeSpeed(), 80);
        pipe.initializePipe(viewHeight, viewWidth);
        return pipe;
    }

    public void updatePipes(float viewHeight, float viewWidth){
        boolean scoreUpdate = false;
        if(!pipes.isEmpty())
            scoreUpdate = pipes.update();
        // if there are no pipes in container, add a new one
        else{
            pipes.addPipe(preparePipe(viewHeight, viewWidth));
        }
        if(scoreUpdate) {
            score++;
        }
    }

    /**
     * When a hit is detected, the game is ended, bestScore is updated if necessary
     */
    public void handleHit(){
        if(vibrateOn)
            v.vibrate(500);
        if(state != BEFORE) {
            state = OFF;
            if (score > bestScore) {
                bestScore = score;
                updateData();
            }
            pipes.setPipeSpeed(-10);
            updateView();
        }
    }

    public void updateView(){view.modelChanged();}

    public void restartGame(float viewHeight, float viewWidth){
        score = 0;
        bird.setyPos(200);
        pipes.clear();
        state = ON;
    }
}
