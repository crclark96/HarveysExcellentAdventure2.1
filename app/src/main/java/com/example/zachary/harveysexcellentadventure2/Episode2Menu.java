package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Episode2Menu extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode2_menu);
    }
    public void gotoEp2MainActivity(View v){
        i = new Intent(this, Ep2MainActivity.class);
        startActivity(i);
    }
}
