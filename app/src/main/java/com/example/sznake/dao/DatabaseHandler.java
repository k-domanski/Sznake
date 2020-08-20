package com.example.sznake.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sznake.gameCore.Game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "SnakeDB";
    private static final String FIRST_TABLE_NAME = "Games";
    private static final String SECOND_TABLE_NAME = "Points";
    private static final String KEY_ID = "id";
    private static final String KEY_DATA = "data";
    private static final String KEY_Points = "points";
    private static final String[] FIRST_COLUMNS = { KEY_ID, KEY_DATA};
    private static final String[] SECOND_COLUMNS = {KEY_Points};

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS Games ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "data BLOB)";
        String CREATION_TABLE_POINTS = "CREATE TABLE IF NOT EXISTS Points ( " + "points INTEGER)";

        db.execSQL(CREATION_TABLE_GAMES);
        db.execSQL(CREATION_TABLE_POINTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FIRST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SECOND_TABLE_NAME);
        this.onCreate(db);

    }

    public void saveGame(Game game) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(game);
        byte[] buffor = byteArrayOutputStream.toByteArray();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA,buffor);
        database.delete(FIRST_TABLE_NAME,null,null);
        database.insert(FIRST_TABLE_NAME,null,values);
        database.close();

        objectOutputStream.close();
        byteArrayOutputStream.close();
    }

    public void clearGames() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(FIRST_TABLE_NAME,null,null);
        database.close();
    }

    public void savePoints(int points) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Points,points);
        database.insert(SECOND_TABLE_NAME,null,values);
        database.close();
    }

    public Game getGame() throws IOException, ClassNotFoundException {
        Game game = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(FIRST_TABLE_NAME,FIRST_COLUMNS,null,
                null,null,null,KEY_ID,null);
        if (cursor != null && cursor.moveToLast()) {
            byte[] buffor = cursor.getBlob(1);
            if (buffor != null) {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffor));
                game = (Game)ois.readObject();
                ois.close();
            }
            cursor.close();
            database.close();

        }
        return game;
    }

    public int getHighestScore() {
        int highscore = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(SECOND_TABLE_NAME,SECOND_COLUMNS,null,
                null,null,null,KEY_Points,null);
        if (cursor != null && cursor.moveToLast()) {
            highscore = cursor.getInt(0);
            cursor.close();
        }
        database.close();
        return highscore;
    }
}
