package com.listdome.app.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import com.listdome.app.R;
import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;
import com.listdome.app.infrastructure.operation.OperationError;
import com.listdome.app.infrastructure.operation.OperationListener;
import com.listdome.app.manager.TaskManager;
import com.listdome.app.ui.adapter.ListToDoAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by raissa on 21/11/2017.
 */

public class TaskActivity extends BaseActivity {

    private static final String TAG = TaskActivity.class.getSimpleName();

    @BindView(R.id.card_todo)
    protected CardView cardToDo;

    @BindView(R.id.card_doing)
    protected CardView cardDoing;

    @BindView(R.id.card_done)
    protected CardView cardDone;

    @BindView(R.id.new_task)
    protected EditText txtNewText;

    @BindView(R.id.add_task)
    protected ImageButton btnAddTask;

    @BindView(R.id.list_todo)
    protected RecyclerView recyclerListToDo;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    protected ListToDoAdapter listToDoAdapter;

    protected List<Task> toDoList;
    protected List<Task> doingList;
    protected List<Task> doneList;

    protected TaskManager mTaskManager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_list;
    }

    @Override
    protected String getMenuItemTitle() {
        Log.v(TAG, "[method] getMenuItemTitle");

        final SimpleDateFormat format = new SimpleDateFormat("  dd, MMM yyyy", Locale.US);
        final String date = format.format(new Date());

        return getString(R.string.title_list, date);
    }

    @Override
    protected void loadElements() {
        Log.v(TAG, "[method] loadElements");

        mTaskManager = new TaskManager(this);

        hideCards();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setRecyclerViews();
        setAdapters();
        getTaskLists();
    }

    @Override
    protected void cancelOperations() {
        Log.v(TAG, "[method] cancelOperations");

        if (mTaskManager != null) {
            mTaskManager.cancelOperations();
        }
    }

    /**
     * Configuration of Layout Manager and Item Decoration
     */
    private void setRecyclerViews() {
        Log.v(TAG, "[method] setRecyclerViews");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerListToDo.setLayoutManager(layoutManager);
        recyclerListToDo.setHasFixedSize(true);
    }

    /**
     * Load the route list adapter
     */
    private void setAdapters() {
        Log.v(TAG, "[method] setAdapters");

        toDoList = new ArrayList<>();

        listToDoAdapter = new ListToDoAdapter(toDoList, new ListToDoAdapter.ListToDoListener() {
            @Override
            public void onTaskItemLongClick(final Task task) {
                Log.v(TAG, "[method] onTaskItemLongClick");
                hideKeyboard(txtNewText);
                openConfirmationDialog(task);
            }

            @Override
            public void onStartItemClick(final Task task) {
                Log.v(TAG, "[method] onStartItemClick");
                updateTask(task);
            }

            @Override
            public void onImportantItemClick(final Task task) {
                Log.v(TAG, "[method] onImportantItemClick");
                updateTask(task);
            }
        });

        recyclerListToDo.setAdapter(listToDoAdapter);
    }

    /**
     * Save new task in DB.
     *
     * @param newTask - task to be saved
     */
    private void saveTask(final Task newTask) {
        Log.v(TAG, "[method] saveTask");

        progressBar.setVisibility(View.VISIBLE);

        mTaskManager.saveTask(newTask, new OperationListener<Long>() {

            @Override
            public void onSuccess(final Long result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                newTask.setId(result);
                toDoList.add(newTask);
                listToDoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                progressBar.setVisibility(View.GONE);
                generateToast(getString(R.string.task_list_error));
            }
        });
    }

    /**
     * Update task in DB.
     *
     * @param task - task to be updated
     */
    private void updateTask(final Task task) {
        Log.v(TAG, "[method] updateTask");

        progressBar.setVisibility(View.VISIBLE);

        mTaskManager.updateTask(task, new OperationListener<Boolean>() {

            @Override
            public void onSuccess(final Boolean result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                getTaskLists();
                listToDoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                progressBar.setVisibility(View.GONE);
                generateToast(getString(R.string.task_list_error));
            }
        });
    }

    /**
     * Delete task from DB.
     *
     * @param task - task to be removed
     */
    private void deleteTask(final Task task) {
        Log.v(TAG, "[method] deleteTask");

        progressBar.setVisibility(View.VISIBLE);

        mTaskManager.deleteTask(task, new OperationListener<Boolean>() {

            @Override
            public void onSuccess(final Boolean result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                toDoList.remove(task);
                listToDoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                progressBar.setVisibility(View.GONE);
                generateToast(getString(R.string.task_list_error));
            }
        });
    }

    /**
     * Get the tasks from the DB.
     */
    private void getTaskLists() {
        Log.v(TAG, "[method] getTaskLists");

        progressBar.setVisibility(View.VISIBLE);

        mTaskManager.getTasks(new OperationListener<List<Task>>() {

            @Override
            public void onSuccess(final List<Task> result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                processTaskList(result);
                showCards();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                showCards();
                generateToast(getString(R.string.task_list_error));
            }
        });
    }

    /**
     * Divide the tasks inside its correct list, based on the task status.
     *
     * @param tasks
     */
    private void processTaskList(final List<Task> tasks) {
        Log.v(TAG, "[method] processTaskList: " + tasks.size());

        toDoList = new ArrayList<>();
        doingList = new ArrayList<>();
        doneList = new ArrayList<>();

        for (final Task task : tasks) {
            if (task.getStatus() == TaskStatus.TODO) {
                toDoList.add(task);
            } else if (task.getStatus() == TaskStatus.DOING) {
                doingList.add(task);
            } else if (task.getStatus() == TaskStatus.DONE && isTaskDoneToday(task)) {
                doneList.add(task);
            }
        }

        listToDoAdapter.refreshList(toDoList);
    }

    /**
     * Checks if the task was done at the current day.
     *
     * @param task
     * @return
     */
    private boolean isTaskDoneToday(final Task task) {
        return DateUtils.isToday(task.getDateEnd().getTime());
    }

    /**
     * Change visibility of all cards (lists) to hide
     */
    private void hideCards() {
        cardToDo.setVisibility(View.GONE);
        cardDoing.setVisibility(View.GONE);
        cardDone.setVisibility(View.GONE);
    }

    /**
     * Change visibility of all cards (lists) to show
     */
    private void showCards() {
        progressBar.setVisibility(View.GONE);
        cardToDo.setVisibility(View.VISIBLE);
        cardDoing.setVisibility(View.VISIBLE);
        cardDone.setVisibility(View.VISIBLE);
    }

    /**
     * Open the confirmation Dialog to delete task.
     *
     * @param task
     */
    private void openConfirmationDialog(final Task task) {
        Log.v(TAG, "[method] openConfirmationDialog");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.task_remove_dialog_title))
            .setMessage(getString(R.string.task_remove_dialog_message))
            .setIcon(android.R.drawable.ic_delete)

            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deleteTask(task);
                    dialog.cancel();
                }
            })

            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            })
            .show();
    }

    @OnClick(R.id.add_task)
    void onAddTaskClick() {
        Log.v(TAG, "[method] onAddTaskClick");

        if (txtNewText.getText() !=  null && !txtNewText.getText().toString().trim().isEmpty()) {
            final Task task = new Task();
            task.setDateBegin(new Date());
            task.setDateEnd(null);
            task.setImportant(false);
            task.setStatus(TaskStatus.TODO);
            task.setName(txtNewText.getText().toString());

            txtNewText.setText("");
            txtNewText.clearFocus();

            saveTask(task);
        }
    }

    @OnFocusChange(R.id.new_task)
    void onFocusChange(final View v, final boolean hasFocus) {
        if (v.getId() == R.id.new_task && !hasFocus) {
            hideKeyboard(v);
        }
    }

    private void hideKeyboard(final View v) {
        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Generates Toast.
     *
     * @param message
     */
    private void generateToast(final String message) {
        Log.v(TAG, "[method] generateToast");

        int duration = Toast.LENGTH_LONG;
        final Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }
}
