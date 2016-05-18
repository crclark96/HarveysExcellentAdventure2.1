package com.example.zachary.harveysexcellentadventure2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommentsDataSource3 {

    // Database fields
    private SQLiteDatabase database3;
    private MySQLiteHelper3 dbHelper;
    private String[] allColumns = { MySQLiteHelper3.COLUMN_ID,
            MySQLiteHelper3.COLUMN_COMMENT };

    public CommentsDataSource3(Context context) {
        dbHelper = new MySQLiteHelper3(context);
    }

    public void open() throws SQLException {
        database3 = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper3.COLUMN_COMMENT, comment);
        long insertId = database3.insert(MySQLiteHelper3.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database3.query(MySQLiteHelper3.TABLE_COMMENTS,
                allColumns, MySQLiteHelper3.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database3.delete(MySQLiteHelper3.TABLE_COMMENTS, MySQLiteHelper3.COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList();

        Cursor cursor = database3.query(MySQLiteHelper3.TABLE_COMMENTS,
                allColumns, null, null, null, null, MySQLiteHelper3.COLUMN_COMMENT + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}