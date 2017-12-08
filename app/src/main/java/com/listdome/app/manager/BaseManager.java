package com.listdome.app.manager;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raissa on 05/12/2017.
 */

public class BaseManager {

    private final List<AsyncTask<?, ?, ?>> mTaskList;

    private final Context mContext;

    BaseManager(final Context context) {
        mTaskList = new ArrayList<>();
        this.mContext = context;
    }

    final Context getContext() {
        return this.mContext;
    }

    public void cancelOperations() {
        for (AsyncTask<?, ?, ?> task : mTaskList) {
            task.cancel(false);
        }
    }

    void addToTaskList(final AsyncTask<?, ?, ?> task) {
        mTaskList.add(task);
    }

    void removeFromTaskList(final AsyncTask<?, ?, ?> task) {
        mTaskList.remove(task);
    }

    int getTotalTasks() {
        return mTaskList.size();
    }
}
