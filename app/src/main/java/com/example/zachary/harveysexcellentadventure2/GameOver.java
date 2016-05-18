package com.example.zachary.harveysexcellentadventure2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameOver extends AppCompatActivity {
    CommentsDataSource1 datasource1;
    CommentsDataSource2 datasource2;
    CommentsDataSource3 datasource3;
    TextView t;
    int score;
    Intent i, i2;
    Bundle extras;
    String newScore, origin;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        i = getIntent();
        extras = i.getExtras();
        if (extras != null){
            score = extras.getInt("Score");
            origin = extras.getString("Game");
            newScore = Integer.toString(score);

            t = (TextView) findViewById(R.id.textView);
            if (newScore != null && t != null)
                t.setText(newScore);
        }

        b1 = (Button) findViewById(R.id.button5);
        b2 = (Button) findViewById(R.id.button6);

        switch(origin){
            case "Episode 1":
                datasource1 = new CommentsDataSource1(this);
                datasource1.open();

                List<Comment> values1 = datasource1.getAllComments();

                Comment comment1 = datasource1.createComment(newScore);

                // use the SimpleCursorAdapter to show the
                // elements in a ListView
                ArrayAdapter<Comment> adapter1 = new ArrayAdapter<Comment>(this,
                        android.R.layout.simple_list_item_1, values1);

                adapter1.add(comment1);
                break;
            case "Episode 2":
                datasource2 = new CommentsDataSource2(this);
                datasource2.open();

                List<Comment> values2 = datasource2.getAllComments();

                Comment comment2 = datasource2.createComment(newScore);

                // use the SimpleCursorAdapter to show the
                // elements in a ListView
                ArrayAdapter<Comment> adapter2 = new ArrayAdapter<Comment>(this,
                        android.R.layout.simple_list_item_1, values2);

                adapter2.add(comment2);
                break;
            case "Episode 3":
                datasource3 = new CommentsDataSource3(this);
                datasource3.open();

                List<Comment> values3 = datasource3.getAllComments();

                Comment comment3 = datasource3.createComment(newScore);

                // use the SimpleCursorAdapter to show the
                // elements in a ListView
                ArrayAdapter<Comment> adapter3 = new ArrayAdapter<Comment>(this,
                        android.R.layout.simple_list_item_1, values3);

                adapter3.add(comment3);
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    public void onClickRetry(View v){
        switch (origin) {
            case "Episode 1":
                i2 = new Intent(this, Ep1MainActivity.class);
                break;
            case "Episode 2":
                i2 = new Intent(this, Ep2MainActivity.class);
                break;
            case "Episode 3":
                i2 = new Intent(this, Ep3MainActivity.class);
                break;
        }
        startActivity(i2);
    }
    public void onClickMainMenu(View v){
        i2 = new Intent(this, MainMenu.class);
        startActivity(i2);
    }

    public void onClickHighscore(View v){
        switch (origin) {
            case "Episode 1":
                i2 = new Intent(this, Ep1Highscore.class);
                break;
            case "Episode 2":
                i2 = new Intent(this, Ep2Highscore.class);
                break;
            case "Episode 3":
                i2 = new Intent(this, Ep3Highscore.class);
                break;
        }
        startActivity(i2);
    }

}
