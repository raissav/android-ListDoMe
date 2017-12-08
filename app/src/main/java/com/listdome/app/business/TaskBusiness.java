package com.listdome.app.business;

import android.util.Log;

import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;
import com.listdome.app.gateway.database.dao.TaskDao;
import com.listdome.app.infrastructure.Constants;
import com.listdome.app.infrastructure.operation.OperationError;
import com.listdome.app.infrastructure.operation.OperationResult;

import java.util.List;

/**
 * Created by raissa on 05/12/2017.
 */

public class TaskBusiness extends BaseBusiness {

    private static final String TAG = TaskBusiness.class.getSimpleName();

    private final TaskDao taskDao;

    public TaskBusiness(final TaskDao taskDao) {
        super();
        this.taskDao = taskDao;
    }

    /**
     * Get all tasks from DB.
     *
     * @return the list of tasks
     */
    public OperationResult<List<Task>> getTasks() {
        Log.v(TAG, "[method] getTasks");

        final OperationResult<List<Task>> result = new OperationResult<>();

        result.setResult(taskDao.getAll());

        return result;
    }

    /**
     * Save a new task in DB.
     *
     * @param task
     * @return the ID from the new task
     */
    public OperationResult<Long> saveTask(final Task task) {
        Log.v(TAG, "[method] saveTask");

        final OperationResult<Long> result = new OperationResult<>();

        result.setResult(taskDao.add(task));

        return result;
    }

    /**
     * Delete a task from DB.
     *
     * @param task
     * @return if operation was successful
     */
    public OperationResult<Boolean> deleteTask(final Task task) {
        Log.v(TAG, "[method] deleteTask");

        final OperationResult<Boolean> result = new OperationResult<>();

        result.setResult(taskDao.delete(task));

        return result;
    }

    /**
     * Update a task from DB.
     *
     * @param task
     * @return if operation was successful
     */
    public OperationResult<Boolean> updateTask(final Task task) {
        Log.v(TAG, "[method] updateTask");

        final OperationResult<Boolean> result = new OperationResult<>();

        result.setResult(taskDao.update(task));

        return result;
    }
}
