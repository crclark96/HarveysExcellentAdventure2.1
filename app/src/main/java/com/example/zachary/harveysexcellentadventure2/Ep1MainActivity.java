package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ep1MainActivity extends AppCompatActivity {
    Button br, bl;
    com.example.zachary.harveysexcellentadventure2.Ep1AnimationView am;
    float xPosition;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep1_main);
        //setContentView(new AnimationView(this));
        am = (com.example.zachary.harveysexcellentadventure2.Ep1AnimationView) findViewById(R.id.view);
        br = (Button) findViewById(R.id.button2);
        bl = (Button) findViewById(R.id.button);
        i = new Intent(this, GameOver.class);
    }
    public void onClickRight(View v){
        xPosition = am.getxPosition();
        if (xPosition < 750)
            am.setxPosition(xPosition + 250);
    }
    public void onClickLeft(View v){
        xPosition = am.getxPosition();
        if (xPosition > 250)
            am.setxPosition(xPosition - 250);
    }
    public void gotoGameOver(int score){
        i.putExtra("Score", score);
        i.putExtra("Game", "Episode 1");
        startActivity(i);
    }
}
