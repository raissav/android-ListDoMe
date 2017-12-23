package com.listdome.app.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.listdome.app.business.IdeaBusiness;
import com.listdome.app.entity.Idea;
import com.listdome.app.infrastructure.DaoFactory;
import com.listdome.app.infrastructure.operation.OperationListener;
import com.listdome.app.infrastructure.operation.OperationResult;

import java.util.List;

/**
 * Created by raissa on 23/12/2017.
 */

public class IdeaManager extends BaseManager {

    private static final String TAG = IdeaManager.class.getSimpleName();

    private final IdeaBusiness ideasBusiness;

    public IdeaManager(final Context context) {
        super(context);

        ideasBusiness = new IdeaBusiness(DaoFactory.getInstance().createIdeaDAO(context));
    }

    public void getDailyInspiration(final OperationListener<Idea> listener) {
        Log.v(TAG, "[method] getDailyInspiration");
        final GetDailyInspiration asyncTask = new GetDailyInspiration(listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void getIdeas(final OperationListener<List<Idea>> listener) {
        Log.v(TAG, "[method] getIdeas");
        final GetIdeaList asyncTask = new GetIdeaList(listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void saveIdea(final Idea idea, final OperationListener<Long> listener) {
        Log.v(TAG, "[method] saveIdea");
        final SaveIdea asyncTask = new SaveIdea(idea, listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void deleteIdea(final Idea idea, final OperationListener<Boolean> listener) {
        Log.v(TAG, "[method] deleteIdea");
        final DeleteIdea asyncTask = new DeleteIdea(idea, listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }

    public void updateIdea(final Idea idea, final OperationListener<Boolean> listener) {
        Log.v(TAG, "[method] updateIdea");
        final UpdateIdea asyncTask = new UpdateIdea(idea, listener);
        addToTaskList(asyncTask);
        asyncTask.execute();
    }


    /* AsyncTask Classes */

    private class GetDailyInspiration extends AsyncTask<Void, Void, OperationResult<Idea>> {

        private OperationListener<Idea> listener;

        private GetDailyInspiration(final OperationListener<Idea> listener) {
            this.listener = listener;
        }

        @Override
        protected OperationResult<Idea> doInBackground(final Void... params) {
            return ideasBusiness.getDailyInspiration();
        }

        @Override
        protected void onPostExecute(final OperationResult<Idea> operationResult) {
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

    private class GetIdeaList extends AsyncTask<Void, Void, OperationResult<List<Idea>>> {

        private OperationListener<List<Idea>> listener;

        private GetIdeaList(final OperationListener<List<Idea>> listener) {
            this.listener = listener;
        }

        @Override
        protected OperationResult<List<Idea>> doInBackground(final Void... params) {
            return ideasBusiness.getIdeas();
        }

        @Override
        protected void onPostExecute(final OperationResult<List<Idea>> operationResult) {
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

    private class SaveIdea extends AsyncTask<Void, Void, OperationResult<Long>> {

        private OperationListener<Long> listener;
        private Idea idea;

        private SaveIdea(final Idea idea, final OperationListener<Long> listener) {
            this.idea = idea;
            this.listener = listener;
        }

        @Override
        protected OperationResult<Long> doInBackground(final Void... params) {
            return ideasBusiness.saveIdea(idea);
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

    private class DeleteIdea extends AsyncTask<Void, Void, OperationResult<Boolean>> {

        private OperationListener<Boolean> listener;
        private Idea idea;

        private DeleteIdea(final Idea idea, final OperationListener<Boolean> listener) {
            this.idea = idea;
            this.listener = listener;
        }

        @Override
        protected OperationResult<Boolean> doInBackground(final Void... params) {
            return ideasBusiness.deleteIdea(idea);
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

    private class UpdateIdea extends AsyncTask<Void, Void, OperationResult<Boolean>> {

        private OperationListener<Boolean> listener;
        private Idea idea;

        private UpdateIdea(final Idea idea, final OperationListener<Boolean> listener) {
            this.idea = idea;
            this.listener = listener;
        }

        @Override
        protected OperationResult<Boolean> doInBackground(final Void... params) {
            return ideasBusiness.updateIdea(idea);
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
