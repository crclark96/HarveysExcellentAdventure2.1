package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ep3MainActivity extends AppCompatActivity {
    Button b;
    com.example.zachary.harveysexcellentadventure2.Ep3AnimationView am;
    float xPosition;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep3_main);
        //setContentView(new AnimationView(this));
        am = (com.example.zachary.harveysexcellentadventure2.Ep3AnimationView) findViewById(R.id.view);
        b = (Button) findViewById(R.id.button);
        i = new Intent(this, GameOver.class);
    }
    public void onClickJump(View v){
        if (am.getHarveyyLocation() > 870){
            am.setyVelocity(1.20f);
        }
    }
    public void gotoGameOver(int score){
        i.putExtra("Score", score);
        i.putExtra("Game", "Episode 3");
        startActivity(i);
    }
}
