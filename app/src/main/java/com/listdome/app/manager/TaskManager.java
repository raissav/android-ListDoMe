package com.listdome.app.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.listdome.app.business.TaskBusiness;
import com.listdome.app.entity.Task;
import com.listdome.app.infrastructure.DaoFactory;
import com.listdome.app.infrastructure.operation.OperationListener;
import com.listdome.app.infrastructure.operation.OperationResult;

import java.util.List;

/**
 * Created by raissa on 05/12/2017.
 */

public class TaskManager extends BaseManager {

    private static final String TAG = TaskManager.class.getSimpleName();

    private final TaskBusiness taskBusiness;

    public TaskManager(final Context context) {
        super(context);

        taskBusiness = new TaskBusiness(DaoFactory.getInstance().createTaskDAO(context));
    }

    public void getTasks(final OperationListener<List<Task>> listener) {
        Log.v(TAG, "[method] getTasks");
        final GetTaskList asyncTask = new GetTaskList(listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void saveTask(final Task task, final OperationListener<Long> listener) {
        Log.v(TAG, "[method] saveTask");
        final SaveTask asyncTask = new SaveTask(task, listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void deleteTask(final Task task, final OperationListener<Boolean> listener) {
        Log.v(TAG, "[method] deleteTask");
        final DeleteTask asyncTask = new DeleteTask(task, listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void updateTask(final Task task, final OperationListener<Boolean> listener) {
        Log.v(TAG, "[method] updateTask");
        final UpdateTask asyncTask = new UpdateTask(task, listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    /* AsyncTask Classes */

    private class GetTaskList extends AsyncTask<Void, Void, OperationResult<List<Task>>> {

        private OperationListener<List<Task>> listener;

        private GetTaskList(final OperationListener<List<Task>> listener) {
            this.listener = listener;
        }

        @Override
        protected OperationResult<List<Task>> doInBackground(final Void... params) {
            return taskBusiness.getTasks();
        }

        @Override
        protected void onPostExecute(final OperationResult<List<Task>> operationResult) {
            removeFromTaskList(this);
            if (listener != null) {
                if (operationResult.isOperationSuccessful()) {
                    listener.onSuccess(operationResult.getResult());
                } else {
                    listener.onError(operationResult.getErrors());
                }
            }
        }

        @Override
        protected void onCancelled() {
            removeFromTaskList(this);
            if (listener != null) {
                listener.onCancel();
            }
        }
    }

    private class SaveTask extends AsyncTask<Void, Void, OperationResult<Long>> {

        private OperationListener<Long> listener;
        private Task task;

        private SaveTask(final Task task, final OperationListener<Long> listener) {
            this.task = task;
            this.listener = listener;
        }

        @Override
        protected OperationResult<Long> doInBackground(final Void... params) {
            return taskBusiness.saveTask(task);
        }

        @Override
        protected void onPostExecute(final OperationResult<Long> operationResult) {
            removeFromTaskList(this);
            if (listener != null) {
                if (operationResult.isOperationSuccessful()) {
                    listener.onSuccess(operationResult.getResult());
                } else {
                    listener.onError(operationResult.getErrors());
                }
            }
        }

        @Override
        protected void onCancelled() {
            removeFromTaskList(this);
            if (listener != null) {
                listener.onCancel();
            }
        }
    }

    private class DeleteTask extends AsyncTask<Void, Void, OperationResult<Boolean>> {

        private OperationListener<Boolean> listener;
        private Task task;

        private DeleteTask(final Task task, final OperationListener<Boolean> listener) {
            this.task = task;
            this.listener = listener;
        }

        @Override
        protected OperationResult<Boolean> doInBackground(final Void... params) {
            return taskBusiness.deleteTask(task);
        }

        @Override
        protected void onPostExecute(final OperationResult<Boolean> operationResult) {
            removeFromTaskList(this);
            if (listener != null) {
                if (operationResult.isOperationSuccessful()) {
                    listener.onSuccess(operationResult.getResult());
                } else {
                    listener.onError(operationResult.getErrors());
                }
            }
        }

        @Override
        protected void onCancelled() {
            removeFromTaskList(this);
            if (listener != null) {
                listener.onCancel();
            }
        }
    }

    private class UpdateTask extends AsyncTask<Void, Void, OperationResult<Boolean>> {

        private OperationListener<Boolean> listener;
        private Task task;

        private UpdateTask(final Task task, final OperationListener<Boolean> listener) {
            this.task = task;
            this.listener = listener;
        }

        @Override
        protected OperationResult<Boolean> doInBackground(final Void... params) {
            return taskBusiness.updateTask(task);
        }

        @Override
        protected void onPostExecute(final OperationResult<Boolean> operationResult) {
            removeFromTaskList(this);
            if (listener != null) {
                if (operationResult.isOperationSuccessful()) {
                    listener.onSuccess(operationResult.getResult());
                } else {
                    listener.onError(operationResult.getErrors());
                }
            }
        }

        @Override
        protected void onCancelled() {
            removeFromTaskList(this);
            if (listener != null) {
                listener.onCancel();
            }
        }
    }
}
