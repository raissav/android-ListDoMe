package com.listdome.app.ui;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;

import com.listdome.app.R;
import com.listdome.app.ui.adapter.ListToDoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raissa on 21/11/2017.
 */

public class ListActivity extends BaseActivity {

    private static final String TAG = ListActivity.class.getSimpleName();

    @BindView(R.id.new_task)
    protected EditText txtNewText;

    @BindView(R.id.add_task)
    protected ImageButton btnAddTask;

    @BindView(R.id.list_todo)
    protected RecyclerView recyclerListTodo;

    protected ListToDoAdapter listToDoAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_list;
    }

    @Override
    protected int getMenuItemTitleId() {
        return R.string.title_list;
    }

    @Override
    protected void configureLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        configureTodoRecyclerView();
        loadTodoAdapter();
        listToDoAdapter.notifyDataSetChanged();
    }

    /**
     * Configuration of Layout Manager and Item Decoration
     */
    private void configureTodoRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerListTodo.setLayoutManager(layoutManager);
        recyclerListTodo.setHasFixedSize(true);
    }

    /**
     * Load the route list adapter
     */
    private void loadTodoAdapter() {
        Log.v(TAG, "[method] loadTodoAdapter");

        final List<String> toDoList = new ArrayList<>();
        toDoList.add("1 Tarefa");
        

        listToDoAdapter = new ListToDoAdapter(toDoList, new ListToDoAdapter.ListToDoListener() {
            @Override
            public void onStartItemClick(View v, int position, String toDo) {
                Log.v(TAG, "[method] onStartItemClick");
            }

            @Override
            public void onImportantItemClick(View v, int position, String toDo) {
                Log.v(TAG, "[method] onImportantItemClick");
            }
        });

        recyclerListTodo.setAdapter(listToDoAdapter);
    }
}
