package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {
    GameModel model;
    GameView view;
    GameController controller;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        // setup mvc
        model = new GameModel(vibrator);
        view = new GameView(this);
        controller = new GameController();
        model.setView(view);
        view.setController(controller);
        controller.setView(view);
        controller.setModel(model);

        layout.addView(view);

        layout.setBackgroundColor(Color.rgb(70,39, 89));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(layout);
    }

    /**
     * If game is running while it goes to the background, pause
     */
    @Override
    protected void onPause(){
        super.onPause();
        if(model.getGameState() != GameModel.BEFORE && model.getGameState() != GameModel.OFF)
            model.setGameState(GameModel.PAUSE);
    }
}
