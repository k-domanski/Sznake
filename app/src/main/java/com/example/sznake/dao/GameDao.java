package com.example.sznake.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.sznake.gameCore.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class GameDao implements  Dao<Game>,AutoCloseable {
    private String fileName;

    public GameDao(String filename) {
        this.fileName = filename;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Game read() throws Exception {
        Game object = null;
        try (FileInputStream fileInput = new FileInputStream(fileName);
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
            object = (Game) objectInput.readObject();
        } catch (ClassNotFoundException | IOException  exception) {
            throw new Exception();
        }

        return object;
    }

    @Override
    public void write(Game obj) throws Exception {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(obj);
        } catch (IOException exception) {
            throw new Exception();
        }
    }

    @Override
    public void close() throws Exception {

    }
}
