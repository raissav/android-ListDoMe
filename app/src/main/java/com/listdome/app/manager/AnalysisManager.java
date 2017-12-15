package com.listdome.app.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.listdome.app.business.AnalysisBusiness;
import com.listdome.app.infrastructure.DaoFactory;
import com.listdome.app.infrastructure.operation.OperationListener;
import com.listdome.app.infrastructure.operation.OperationResult;

/**
 * Created by raissa on 05/12/2017.
 */

public class AnalysisManager extends BaseManager {

    private static final String TAG = AnalysisManager.class.getSimpleName();

    private final AnalysisBusiness analysisBusiness;

    public AnalysisManager(final Context context) {
        super(context);

        analysisBusiness = new AnalysisBusiness(DaoFactory.getInstance().createTaskDAO(context));
    }

    public void getDailyDoneTasks(final OperationListener<Integer> listener) {
        Log.v(TAG, "[method] getDailyDoneTasks");
        final GetDailyDoneTasks asyncTask = new GetDailyDoneTasks(listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void getWeeklyDoneTasks(final OperationListener<Integer> listener) {
        Log.v(TAG, "[method] getWeeklyDoneTasks");
        final GetWeeklyDoneTasks asyncTask = new GetWeeklyDoneTasks(listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void getMonthlyDoneTasks(final OperationListener<Integer> listener) {
        Log.v(TAG, "[method] getMonthlyDoneTasks");
        final GetMonthlyDoneTasks asyncTask = new GetMonthlyDoneTasks(listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }


    /* AsyncTask Classes */

    private class GetDailyDoneTasks extends AsyncTask<Void, Void, OperationResult<Integer>> {

        private OperationListener<Integer> listener;

        private GetDailyDoneTasks(final OperationListener<Integer> listener) {
            this.listener = listener;
        }

        @Override
        protected OperationResult<Integer> doInBackground(final Void... params) {
            return analysisBusiness.getDailyDoneTasks();
        }

        @Override
        protected void onPostExecute(final OperationResult<Integer> operationResult) {
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

    private class GetWeeklyDoneTasks extends AsyncTask<Void, Void, OperationResult<Integer>> {

        private OperationListener<Integer> listener;

        private GetWeeklyDoneTasks(final OperationListener<Integer> listener) {
            this.listener = listener;
        }

        @Override
        protected OperationResult<Integer> doInBackground(final Void... params) {
            return analysisBusiness.getWeeklyDoneTasks();
        }

        @Override
        protected void onPostExecute(final OperationResult<Integer> operationResult) {
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

    private class GetMonthlyDoneTasks extends AsyncTask<Void, Void, OperationResult<Integer>> {

        private OperationListener<Integer> listener;

        private GetMonthlyDoneTasks(final OperationListener<Integer> listener) {
            this.listener = listener;
        }

        @Override
        protected OperationResult<Integer> doInBackground(final Void... params) {
            return analysisBusiness.getMonthlyDoneTasks();
        }

        @Override
        protected void onPostExecute(final OperationResult<Integer> operationResult) {
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
