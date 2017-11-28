package com.listdome.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.listdome.app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by raissa on 27/11/2017.
 */

public class ListToDoAdapter extends RecyclerView.Adapter<ListToDoAdapter.ViewHolder> {

    private List<String> toDoList;
    private ListToDoListener listener;

    public ListToDoAdapter(final List<String> toDoList, final ListToDoListener listener) {
        this.toDoList = toDoList;
        this.listener = listener;
    }

    /**
     * Interface for click item.
     */
    public interface ListToDoListener {
        void onStartItemClick(final View v, int position, String toDo);
        void onImportantItemClick(final View v, int position, String toDo);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String toDo = toDoList.get(position);
        holder.bind(position, toDo, listener);
    }

    @Override
    public int getItemCount() {
        return this.toDoList.size();
    }

    public void refreshList(final List<String> toDoList) {
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.todo_task)
        EditText txtTodoTask;

        @BindView(R.id.start_task)
        ImageButton btnStartTask;

        @BindView(R.id.important_task)
        ImageButton btnImportantTask;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final int position, final String toDo, final ListToDoListener listener) {

            txtTodoTask.setText(toDo);

            btnStartTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    listener.onStartItemClick(btnStartTask, position, toDo);
                }
            });

            btnImportantTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    listener.onImportantItemClick(btnImportantTask, position, toDo);
                }
            });
        }
    }
}
