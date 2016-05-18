package com.example.zachary.harveysexcellentadventure2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

public class Ep2Highscore extends ListActivity {
    private CommentsDataSource2 datasource;
    private Bundle extras;
    private String score;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep2_highscore);

        datasource = new CommentsDataSource2(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);

    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml

//    public void delete(View view) {
//        @SuppressWarnings("unchecked")
//        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
//        if (getListAdapter().getCount() > 0) {
//            Comment comment = (Comment) getListAdapter().getItem(0);
//            datasource.deleteComment(comment);
//            adapter.remove(comment);
//        }
//        adapter.notifyDataSetChanged();
//    }

    public void retry(View view){
        Intent i = new Intent(this, Ep2MainActivity.class);
        startActivity(i);
    }

    public void mainMenu(View view){
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}