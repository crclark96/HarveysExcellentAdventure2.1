package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Episode1Menu extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode1_menu);
    }
    public void gotoEp1MainActivity(View v){
        i = new Intent(this, Ep1MainActivity.class);
        startActivity(i);
    }
}
