package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity {
    Button b;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        b = (Button) findViewById(R.id.button);
    }
    public void onClick(View v){
        i = new Intent(this, MainMenu.class);
        startActivity(i);
    }
}
