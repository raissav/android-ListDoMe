package com.listdome.app.infrastructure;

import android.content.Context;

import com.listdome.app.gateway.database.dao.IdeaDao;
import com.listdome.app.gateway.database.dao.TaskDao;

/**
 * Created by raissa on 05/12/2017.
 */

public final class DaoFactory {

    private static final DaoFactory ourInstance = new DaoFactory();

    public static DaoFactory getInstance() {
        return ourInstance;
    }

    private DaoFactory() {
    }

    public TaskDao createTaskDAO(final Context context) {
        return new TaskDao(context);
    }

    public IdeaDao createIdeaDAO(final Context context) {
        return new IdeaDao(context);
    }
}
