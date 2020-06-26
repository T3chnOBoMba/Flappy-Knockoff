package com.example.flappybird;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameController {
    GameView view;
    GameModel model;

    public GameController(){
        view = null;
        model = null;
    }

    public void setView(GameView newView){view = newView;}

    public void setModel(GameModel newModel){model = newModel;}

    public boolean handleTouch(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                boolean pausePressed = view.getPause().checkClick(event.getX(), event.getY());
                switch(getGameState()){
                    case GameModel.BEFORE:
                    case GameModel.OFF:
                        if ((model.getBirdY() + model.getBird().getRadius() >= view.getHeight()))
                            model.restartGame(view.getHeight(), view.getWidth());
                        break;
                    case GameModel.PAUSE:
                        if(pausePressed)
                            model.setGameState(GameModel.ON);
                        break;
                    case GameModel.ON:
                        if(pausePressed)
                            model.setGameState(GameModel.PAUSE);
                        else
                            model.birdJump();
                        break;

                }
        }
        return true;
    }

    public void moveBird(){
        if(model.getGameState() != GameModel.PAUSE)
            model.updateBirdPos();
    }

    public void showPipes(Canvas canvas, Paint paint){model.getContainer().drawPipes(canvas, paint);}

    public void updatePipes(){
        if(model.getGameState() == GameModel.ON)
            model.updatePipes(view.getHeight(), view.getWidth());
    }

    public int score(){return model.getScore();}

    public void showPlayer(Canvas canvas, Paint paint){model.getBird().drawPlayer(model.getBirdY(), canvas, paint);}

    public void checkHit(){
        if(model.getContainer().checkHit((float)(view.getWidth() * .2), model.getBirdY(), model.getBird().getRadius())
        || (model.getBirdY() + model.getBird().getRadius() >= view.getHeight()) && model.getGameState() == GameModel.ON)
            model.handleHit();

    }

    public int getGameState(){return model.getGameState();}

    public int bestScore(){return model.getBestScore();}

    public boolean birdOnGround(){return model.getBirdY() == (view.getHeight() - model.getBird().getRadius());}

}
