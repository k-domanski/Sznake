package com.example.sznake.dao;

public interface Dao<T> {
    T read() throws Exception;
    void write(T obj) throws Exception;
}
