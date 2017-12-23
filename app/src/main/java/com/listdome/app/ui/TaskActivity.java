package com.listdome.app.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import com.listdome.app.R;
import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;
import com.listdome.app.infrastructure.operation.OperationError;
import com.listdome.app.infrastructure.operation.OperationListener;
import com.listdome.app.manager.TaskManager;
import com.listdome.app.ui.adapter.ListDoingAdapter;
import com.listdome.app.ui.adapter.ListDoneAdapter;
import com.listdome.app.ui.adapter.ListToDoAdapter;
import com.listdome.app.ui.utils.SlideAnimationUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by raissa on 21/11/2017.
 */

public class TaskActivity extends BaseActivity {

    private static final String TAG = TaskActivity.class.getSimpleName();

    @BindView(R.id.container_tasks)
    protected LinearLayout containerTasks;

    @BindView(R.id.card_todo)
    protected CardView cardToDo;

    @BindView(R.id.card_doing)
    protected CardView cardDoing;

    @BindView(R.id.card_done)
    protected CardView cardDone;

    @BindView(R.id.new_task)
    protected EditText txtNewTask;

    @BindView(R.id.add_task)
    protected ImageButton btnAddTask;

    @BindView(R.id.list_todo)
    protected RecyclerView recyclerListToDo;

    @BindView(R.id.list_doing)
    protected RecyclerView recyclerListDoing;

    @BindView(R.id.list_done)
    protected RecyclerView recyclerListDone;

    @BindView(R.id.empty_view_doing)
    protected TextView emptyDoing;

    @BindView(R.id.empty_view_done)
    protected TextView emptyDone;

    protected ListToDoAdapter listToDoAdapter;
    protected ListDoingAdapter listDoingAdapter;
    protected ListDoneAdapter listDoneAdapter;

    protected List<Task> toDoList;
    protected List<Task> doingList;
    protected List<Task> doneList;

    protected TaskManager mTaskManager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tasks;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_list;
    }

    @Override
    protected String getMenuItemTitle() {
        return getString(R.string.title_tasks);
    }

    @Override
    protected void loadElements() {
        Log.v(TAG, "[method] loadElements");

        mTaskManager = new TaskManager(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        txtNewTask.requestFocus();

        setRecyclerViews();
        setAdapters();
        getTaskLists();
        showCards();
    }

    @Override
    protected void cancelOperations() {
        Log.v(TAG, "[method] cancelOperations");

        if (mTaskManager != null) {
            mTaskManager.cancelOperations();
        }
    }

    @Override
    protected void animateTransaction() {
        SlideAnimationUtils.slideInFromRight(this, containerTasks);
    }

    /**
     * Configuration of Layout Manager and Item Decoration
     */
    private void setRecyclerViews() {
        Log.v(TAG, "[method] setRecyclerViews");

        final LinearLayoutManager lmToDoList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lmToDoList.setMeasurementCacheEnabled(false);
        recyclerListToDo.setLayoutManager(lmToDoList);
        recyclerListToDo.setHasFixedSize(true);

        final LinearLayoutManager lmDoingList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lmDoingList.setMeasurementCacheEnabled(false);
        recyclerListDoing.setLayoutManager(lmDoingList);
        recyclerListDoing.setHasFixedSize(true);

        final LinearLayoutManager lmDoneList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lmDoneList.setMeasurementCacheEnabled(false);
        recyclerListDone.setLayoutManager(lmDoneList);
        recyclerListDone.setHasFixedSize(true);
    }

    /**
     * Load the lists adapters
     */
    private void setAdapters() {
        Log.v(TAG, "[method] setAdapters");
        setToDoAdapter();
        setDoingAdapter();
        setDoneAdapter();
    }

    /**
     * Load the to do list adapter
     */
    private void setToDoAdapter() {
        toDoList = new ArrayList<>();

        listToDoAdapter = new ListToDoAdapter(toDoList, this, new ListToDoAdapter.ListToDoListener() {
            @Override
            public void onTaskItemLongClick(final Task task) {
                Log.v(TAG, "[method] onTaskItemLongClick");
                hideKeyboard(txtNewTask);
                showRemoveTaskDialog(task, TaskStatus.TODO);
            }

            @Override
            public void onCheckItemClick(final Task task) {
                Log.v(TAG, "[method] onCheckItemClick");
                updateTask(task, TaskStatus.TODO);
            }

            @Override
            public void onImportantItemClick(final Task task) {
                Log.v(TAG, "[method] onImportantItemClick");
                updateTask(task, null);
            }

            @Override
            public void onFocusChange(final Task task) {
                Log.v(TAG, "[method] onFocusChange");

                if (task.getName().trim().isEmpty()) {
                    deleteTask(task, TaskStatus.TODO);
                } else {
                    updateTask(task, null);
                }
            }
        });

        recyclerListToDo.setAdapter(listToDoAdapter);
    }

    /**
     * Load the doing list adapter
     */
    private void setDoingAdapter() {
        doingList = new ArrayList<>();

        listDoingAdapter = new ListDoingAdapter(doingList, this, new ListDoingAdapter.ListDoingListener() {
            @Override
            public void onTaskItemLongClick(final Task task) {
                Log.v(TAG, "[method] onTaskItemLongClick");
                hideKeyboard(txtNewTask);
                showStopTaskDialog(task);
            }

            @Override
            public void onCheckItemClick(final Task task) {
                Log.v(TAG, "[method] onCheckItemClick");
                updateTask(task, TaskStatus.DOING);
            }

            @Override
            public void onImportantItemClick(final Task task) {
                Log.v(TAG, "[method] onImportantItemClick");
                updateTask(task, null);
            }
        });

        recyclerListDoing.setAdapter(listDoingAdapter);
    }

    /**
     * Load the done list adapter
     */
    private void setDoneAdapter() {
        doneList = new ArrayList<>();

        listDoneAdapter = new ListDoneAdapter(doneList, this, new ListDoneAdapter.ListDoneListener() {
            @Override
            public void onTaskItemLongClick(final Task task) {
                Log.v(TAG, "[method] onTaskItemLongClick");
                hideKeyboard(txtNewTask);
                showRemoveTaskDialog(task, TaskStatus.DONE);
            }

            @Override
            public void onCheckItemClick(final Task task) {
                Log.v(TAG, "[method] onCheckItemClick");
                updateTask(task, TaskStatus.DONE);
            }

        });

        recyclerListDone.setAdapter(listDoneAdapter);
    }

    /**
     * Save new task in DB.
     *
     * @param newTask - task to be saved
     */
    private void saveTask(final Task newTask) {
        Log.v(TAG, "[method] saveTask");

        mTaskManager.saveTask(newTask, new OperationListener<Long>() {

            @Override
            public void onSuccess(final Long result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                newTask.setId(result);
                toDoList.add(newTask);
                verifyEmptyLists();
                listToDoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.task_list_error));
            }
        });
    }

    /**
     * Update task in DB.
     *
     * @param task - task to be updated
     * @param status - the previous task status
     */
    private void updateTask(final Task task, final TaskStatus status) {
        Log.v(TAG, "[method] updateTask");

        mTaskManager.updateTask(task, new OperationListener<Boolean>() {

            @Override
            public void onSuccess(final Boolean result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                if (status != null) {
                    updateLists(status, task);
                }
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.task_list_error));
            }
        });
    }

    /**
     * Updates the lists displayed based on the previous status.
     *
     * @param previousStatus
     * @param task
     */
    private void updateLists(final TaskStatus previousStatus, final Task task) {
        Log.v(TAG, "[method] updateLists");

        if (previousStatus == TaskStatus.TODO) {
            toDoList.remove(task);
            listToDoAdapter.removeLastPosition();
            listToDoAdapter.notifyDataSetChanged();

            doingList.add(task);
            listDoingAdapter.notifyDataSetChanged();

        } else if (previousStatus == TaskStatus.DOING) {
            doingList.remove(task);
            listDoingAdapter.removeLastPosition();
            listDoingAdapter.notifyDataSetChanged();

            if (task.getStatus() == TaskStatus.TODO) {
                toDoList.add(task);
                listToDoAdapter.notifyDataSetChanged();

            } else {
                doneList.add(task);
                listDoneAdapter.notifyDataSetChanged();
            }

        } else {
            doneList.remove(task);
            listDoneAdapter.removeLastPosition();
            listDoneAdapter.notifyDataSetChanged();

            doingList.add(task);
            listDoingAdapter.notifyDataSetChanged();
        }

        verifyEmptyLists();
    }

    /**
     * Delete task from DB.
     *
     * @param task - task to be removed
     */
    private void deleteTask(final Task task, final TaskStatus status) {
        Log.v(TAG, "[method] deleteTask");

        mTaskManager.deleteTask(task, new OperationListener<Boolean>() {

            @Override
            public void onSuccess(final Boolean result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                if (status == TaskStatus.TODO) {
                    toDoList.remove(task);
                    listToDoAdapter.removeLastPosition();
                    listToDoAdapter.notifyDataSetChanged();

                } else if (status == TaskStatus.DONE) {
                    doneList.remove(task);
                    listDoneAdapter.removeLastPosition();
                    listDoneAdapter.notifyDataSetChanged();
                }
                verifyEmptyLists();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.task_list_error));
            }
        });
    }

    /**
     * Get the tasks from the DB.
     */
    private void getTaskLists() {
        Log.v(TAG, "[method] getTaskLists");

        mTaskManager.getTasks(new OperationListener<List<Task>>() {

            @Override
            public void onSuccess(final List<Task> result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                processTaskList(result);
                txtNewTask.clearFocus();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

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

        toDoList.clear();
        doingList.clear();
        doneList.clear();

        for (final Task task : tasks) {
            if (task.getStatus() == TaskStatus.TODO) {
                toDoList.add(task);
            } else if (task.getStatus() == TaskStatus.DOING) {
                doingList.add(task);
            } else if (task.getStatus() == TaskStatus.DONE && isTaskDoneToday(task)) {
                doneList.add(task);
            }
        }

        verifyEmptyLists();

        listToDoAdapter.refreshList(toDoList);
        listDoingAdapter.refreshList(doingList);
        listDoneAdapter.refreshList(doneList);
    }

    /**
     * Checks if the lists are empty.
     * If so, shows an empty result message.
     */
    private void verifyEmptyLists() {

        recyclerListToDo.setVisibility(View.VISIBLE);
        recyclerListDoing.setVisibility(View.VISIBLE);
        recyclerListDone.setVisibility(View.VISIBLE);

        emptyDoing.setVisibility(View.GONE);
        emptyDone.setVisibility(View.GONE);

        if (toDoList.isEmpty()) {
            recyclerListToDo.setVisibility(View.GONE);
        }

        if (doingList.isEmpty()) {
            emptyDoing.setText(getString(R.string.no_tasks_available, getString(R.string.doing)));
            emptyDoing.setVisibility(View.VISIBLE);
            recyclerListDoing.setVisibility(View.GONE);
        }

        if (doneList.isEmpty()) {
            emptyDone.setText(getString(R.string.no_tasks_available, getString(R.string.done)));
            emptyDone.setVisibility(View.VISIBLE);
            recyclerListDone.setVisibility(View.GONE);
        }
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
     * Change visibility of all cards (lists) to show
     */
    private void showCards() {
        cardToDo.setVisibility(View.VISIBLE);
        cardDoing.setVisibility(View.VISIBLE);
        cardDone.setVisibility(View.VISIBLE);
    }

    /**
     * Open the confirmation Dialog to delete the task.
     *
     * @param task
     */
    private void showRemoveTaskDialog(final Task task, final TaskStatus status) {
        Log.v(TAG, "[method] showRemoveTaskDialog");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.remove_task_dialog_title))
            .setMessage(getString(R.string.remove_task_dialog_message))
            .setIcon(android.R.drawable.ic_delete)

            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deleteTask(task, status);
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

    /**
     * Open the confirmation Dialog to stop the task.
     *
     * @param task
     */
    private void showStopTaskDialog(final Task task) {
        Log.v(TAG, "[method] showStopTaskDialog");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.stop_task_dialog_title))
                .setMessage(getString(R.string.stop_task_dialog_message))
                .setIcon(R.drawable.ic_pause)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        task.setStatus(TaskStatus.TODO);
                        updateTask(task, TaskStatus.DOING);
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

        if (txtNewTask.getText() !=  null && !txtNewTask.getText().toString().trim().isEmpty()) {
            final Task task = new Task();
            task.setDateBegin(new Date());
            task.setDateEnd(null);
            task.setImportant(false);
            task.setStatus(TaskStatus.TODO);
            task.setName(txtNewTask.getText().toString());

            txtNewTask.setText("");
            txtNewTask.clearFocus();

            saveTask(task);
        }
    }

}
