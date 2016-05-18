package com.example.zachary.harveysexcellentadventure2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommentsDataSource1 {

    // Database fields
    private SQLiteDatabase database1;
    private MySQLiteHelper1 dbHelper;
    private String[] allColumns = { MySQLiteHelper1.COLUMN_ID,
            MySQLiteHelper1.COLUMN_COMMENT };

    public CommentsDataSource1(Context context) {
        dbHelper = new MySQLiteHelper1(context);
    }

    public void open() throws SQLException {
        database1 = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper1.COLUMN_COMMENT, comment);
        long insertId = database1.insert(MySQLiteHelper1.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database1.query(MySQLiteHelper1.TABLE_COMMENTS,
                allColumns, MySQLiteHelper1.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database1.delete(MySQLiteHelper1.TABLE_COMMENTS, MySQLiteHelper1.COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList();

        Cursor cursor = database1.query(MySQLiteHelper1.TABLE_COMMENTS,
                allColumns, null, null, null, null, MySQLiteHelper1.COLUMN_COMMENT + " DESC");

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