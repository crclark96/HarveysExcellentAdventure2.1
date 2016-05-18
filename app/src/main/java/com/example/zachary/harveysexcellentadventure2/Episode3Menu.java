package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Episode3Menu extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode3_menu);
    }
    public void gotoEp3MainActivity(View v){
        i = new Intent(this, Ep3MainActivity.class);
        startActivity(i);
    }
}
