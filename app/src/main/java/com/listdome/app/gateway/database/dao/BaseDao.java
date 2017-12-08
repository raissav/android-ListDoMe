package com.listdome.app.gateway.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.listdome.app.gateway.database.DataBaseHelper;

import java.util.List;

/**
 * Created by raissa on 08/12/2017.
 */

abstract class BaseDao<T> {

    private static final String TAG =  BaseDao.class.getSimpleName();

    private final Context mContext;
    private final DataBaseHelper dataBase;

    public BaseDao(final Context context) {
        this.mContext = context;
        this.dataBase = new DataBaseHelper(this.mContext);
    }

    public SQLiteDatabase getDBForRead() {
        return this.dataBase.getReadableDatabase();
    }

    public SQLiteDatabase getDBForWrite() {
        return this.dataBase.getWritableDatabase();
    }

    public void closeDB() {
        this.dataBase.close();
    }

    public abstract T get(String id);
    public abstract List<T> getAll();
    public abstract long add(T data);
    public abstract boolean addAll(List<T> data);
    public abstract boolean delete(T data);
    public abstract boolean deleteAll();
    public abstract boolean update(T data);

    public abstract ContentValues parseToValues(final T data);

    public Context getContext() {
        return mContext;
    }
}
