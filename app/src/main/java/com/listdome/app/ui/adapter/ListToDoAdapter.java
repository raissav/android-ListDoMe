package com.listdome.app.ui.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.listdome.app.ui.utils.AnimationUtils.animateView;
import static com.listdome.app.ui.utils.AnimationUtils.blinkBackground;

/**
 * Created by raissa on 27/11/2017.
 */

public class ListToDoAdapter extends RecyclerView.Adapter<ListToDoAdapter.ViewHolder> {

    private List<Task> toDoList;
    private ListToDoListener listener;
    private Context context;
    private int lastPosition;

    public ListToDoAdapter(final List<Task> toDoList, final Context context, final ListToDoListener listener) {
        this.toDoList = toDoList;
        this.listener = listener;
        this.context = context;
        this.lastPosition = -1;
    }

    /**
     * Interface for click item.
     */
    public interface ListToDoListener {
        void onTaskItemLongClick(final Task task);
        void onCheckItemClick(final Task task);
        void onImportantItemClick(final Task task);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task task = toDoList.get(position);
        holder.bind(task, position, listener);
    }

    @Override
    public int getItemCount() {
        return this.toDoList.size();
    }

    public void refreshList(final List<Task> toDoList) {
        this.toDoList = toDoList;
        this.lastPosition = toDoList.size() - 1;
        notifyDataSetChanged();
    }

    public void removeLastPosition() {
        lastPosition = lastPosition - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_todo_task)
        ConstraintLayout layoutToDo;

        @BindView(R.id.text_todo_task)
        EditText txtToDoTask;

        @BindView(R.id.check_todo_task)
        CheckBox checkToDoTask;

        @BindView(R.id.important_todo_task)
        ImageButton importantToDoTask;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Task task, final int position, final ListToDoListener listener) {

            txtToDoTask.setText(task.getName());
            checkToDoTask.setChecked(false);

            if (task.isImportant()) {
                importantToDoTask.setBackgroundResource(R.drawable.ic_star_full);
            } else {
                importantToDoTask.setBackgroundResource(R.drawable.ic_star_empty);
            }

            if (position > lastPosition) {
                blinkBackground(layoutToDo,
                        context.getResources().getColor(R.color.opacityBlue),
                        context.getResources().getColor(R.color.white));
                lastPosition = position;
            }

            txtToDoTask.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    listener.onTaskItemLongClick(task);
                    return false;
                }
            });

            checkToDoTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    task.setStatus(TaskStatus.DOING);
                    task.setDateEnd(null);
                    listener.onCheckItemClick(task);
                }
            });

            importantToDoTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    task.setImportant(!task.isImportant());
                    if (task.isImportant()) {
                        importantToDoTask.setBackgroundResource(R.drawable.ic_star_full);
                        animateView(layoutToDo,
                                context.getResources().getColor(R.color.opacityYellow),
                                context.getResources().getColor(R.color.white));
                    } else {
                        importantToDoTask.setBackgroundResource(R.drawable.ic_star_empty);
                    }
                    listener.onImportantItemClick(task);
                }
            });
        }
    }
}
