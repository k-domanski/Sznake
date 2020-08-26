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

/**
 *  Is a class managing database containing saved {@link Game}s and final points.
 */
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

    /**
     * Creates new instance of DatabaseHandler.
     *
     * @param context {@link Context}
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Using sql queries checks if Games and Points tables exist, and if not, creates them.
     *
     * @param db {@link SQLiteDatabase} on which the actions are performed
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE_GAMES = "CREATE TABLE IF NOT EXISTS Games ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "data BLOB)";
        String CREATION_TABLE_POINTS = "CREATE TABLE IF NOT EXISTS Points ( " + "points INTEGER)";

        db.execSQL(CREATION_TABLE_GAMES);
        db.execSQL(CREATION_TABLE_POINTS);

    }

    /**
     * Using sl queries removes existing Game and Points tables.
     *
     * @param db {@link SQLiteDatabase} on which the actions are performed
     * @param oldVersion old database version number
     * @param newVersion new database bersion number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FIRST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SECOND_TABLE_NAME);
        this.onCreate(db);

    }

    /**
     * Saves {@link Game} object into the Games table.
     *
     * @param game {@link Game} object saved to database
     * @throws IOException is thrown if error occurs during streaming {@link Game} object
     */
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

    /**
     * Removes all records from Games table.
     */
    public void clearGames() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(FIRST_TABLE_NAME,null,null);
        database.close();
    }

    /**
     * Saves points value into Points table
     *
     * @param points points value
     */
    public void savePoints(int points) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Points,points);
        database.insert(SECOND_TABLE_NAME,null,values);
        database.close();
    }

    /**
     * Returns last record of Games table
     *
     * @return {@link Game} object from last record of Games table
     * @throws IOException is thrown if error occurs during streaming {@link Game} object
     * @throws ClassNotFoundException is thrown if object from database cannot be converted into {@link Game} object
     */
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

    /**
     * Returns record with highest Points value from Points database.
     *
     * @return highest points value from Points database.
     */
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
