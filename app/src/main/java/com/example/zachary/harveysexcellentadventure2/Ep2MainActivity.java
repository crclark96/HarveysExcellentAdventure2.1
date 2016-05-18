package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Ep2MainActivity extends AppCompatActivity {
    Intent i;
    com.example.zachary.harveysexcellentadventure2.Ep2AnimationView am;
    public float[] clickLoc = {-100, -100};
    //public float yCoordLoc = -100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep2_main);

        am = (com.example.zachary.harveysexcellentadventure2.Ep2AnimationView) findViewById(R.id.view);

        am.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //System.out.println("Touch coordinates : " + String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
                    clickLoc[0] = event.getX();
                    clickLoc[1] = event.getY();
                }
                //clickLoc[0] = -100;
                //clickLoc[1] = -100;
                return true;
            }
        });


        i = new Intent(this, GameOver.class);


    }

    public float[] getClickLoc(){
        return clickLoc;
    }

    public void gotoGameOver(int score){
        i.putExtra("Score", score);
        i.putExtra("Game", "Episode 2");
        startActivity(i);
    }
}
