package com.listdome.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.listdome.app.R;
import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by raissa on 27/11/2017.
 */

public class ListDoingAdapter extends RecyclerView.Adapter<ListDoingAdapter.ViewHolder> {

    private List<Task> doingList;
    private ListDoingListener listener;

    public ListDoingAdapter(final List<Task> doingList, final ListDoingListener listener) {
        this.doingList = doingList;
        this.listener = listener;
    }

    /**
     * Interface for click item.
     */
    public interface ListDoingListener {
        void onTaskItemLongClick(final Task task);
        void onCheckItemClick(final Task task);
        void onImportantItemClick(final Task task);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task task = doingList.get(position);
        holder.bind(task, listener);
    }

    @Override
    public int getItemCount() {
        return this.doingList.size();
    }

    public void refreshList(final List<Task> doingList) {
        this.doingList = doingList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_doing_task)
        EditText txtDoingTask;

        @BindView(R.id.check_doing_task)
        CheckBox checkDoingTask;

        @BindView(R.id.important_doing_task)
        ImageButton importantDoingTask;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Task task, final ListDoingListener listener) {

            txtDoingTask.setText(task.getName());
            checkDoingTask.setChecked(false);

            if (task.isImportant()) {
                importantDoingTask.setBackgroundResource(R.drawable.ic_star_full);
            } else {
                importantDoingTask.setBackgroundResource(R.drawable.ic_star_empty);
            }

            txtDoingTask.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    listener.onTaskItemLongClick(task);
                    return false;
                }
            });

            checkDoingTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    task.setStatus(TaskStatus.DONE);
                    task.setDateEnd(new Date());
                    listener.onCheckItemClick(task);
                }
            });

            importantDoingTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    task.setImportant(!task.isImportant());
                    if (task.isImportant()) {
                        importantDoingTask.setBackgroundResource(R.drawable.ic_star_full);
                    } else {
                        importantDoingTask.setBackgroundResource(R.drawable.ic_star_empty);
                    }
                    listener.onImportantItemClick(task);
                }
            });
        }
    }
}
