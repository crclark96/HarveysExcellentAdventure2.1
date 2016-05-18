package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button b2,b3,b4;
    Intent i2,i3,i4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
    }

    public void gotoEp1Menu(View v){
        i2 = new Intent(this, Episode1Menu.class);
        startActivity(i2);
    }
    public void gotoEp2Menu(View v){
        i3 = new Intent(this, Episode2Menu.class);
        startActivity(i3);
    }
    public void gotoEp3Menu(View v){
        i4 = new Intent(this, Episode3Menu.class);
        startActivity(i4);
    }
}
