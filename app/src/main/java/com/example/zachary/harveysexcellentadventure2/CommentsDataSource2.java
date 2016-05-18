package com.example.zachary.harveysexcellentadventure2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommentsDataSource2 {

    // Database fields
    private SQLiteDatabase database2;
    private MySQLiteHelper2 dbHelper;
    private String[] allColumns = { MySQLiteHelper2.COLUMN_ID,
            MySQLiteHelper2.COLUMN_COMMENT };

    public CommentsDataSource2(Context context) {
        dbHelper = new MySQLiteHelper2(context);
    }

    public void open() throws SQLException {
        database2 = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper2.COLUMN_COMMENT, comment);
        long insertId = database2.insert(MySQLiteHelper2.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database2.query(MySQLiteHelper2.TABLE_COMMENTS,
                allColumns, MySQLiteHelper2.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database2.delete(MySQLiteHelper2.TABLE_COMMENTS, MySQLiteHelper2.COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList();

        Cursor cursor = database2.query(MySQLiteHelper2.TABLE_COMMENTS,
                allColumns, null, null, null, null, MySQLiteHelper2.COLUMN_COMMENT + " DESC");

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