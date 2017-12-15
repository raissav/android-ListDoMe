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

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.listdome.app.ui.utils.ColorAnimationUtils.animateView;
import static com.listdome.app.ui.utils.ColorAnimationUtils.blinkBackground;

/**
 * Created by raissa on 27/11/2017.
 */

public class ListDoingAdapter extends RecyclerView.Adapter<ListDoingAdapter.ViewHolder> {

    private List<Task> doingList;
    private ListDoingListener listener;
    private Context context;
    private int lastPosition;

    public ListDoingAdapter(final List<Task> doingList, final Context context, final ListDoingListener listener) {
        this.doingList = doingList;
        this.listener = listener;
        this.context = context;
        this.lastPosition = -1;
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
        holder.bind(task, position, listener);
    }

    @Override
    public int getItemCount() {
        return this.doingList.size();
    }

    public void refreshList(final List<Task> doingList) {
        this.doingList = doingList;
        this.lastPosition = doingList.size() - 1;
        notifyDataSetChanged();
    }

    public void removeLastPosition() {
        lastPosition = lastPosition - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_doing_task)
        ConstraintLayout layoutDoing;

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

        public void bind(final Task task, final int position, final ListDoingListener listener) {

            txtDoingTask.setText(task.getName());
            checkDoingTask.setChecked(false);

            if (position > lastPosition) {
                blinkBackground(layoutDoing,
                        context.getResources().getColor(R.color.opacityBlue),
                        context.getResources().getColor(R.color.white));
                lastPosition = position;
            }

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
                        animateView(layoutDoing,
                                context.getResources().getColor(R.color.opacityYellow),
                                context.getResources().getColor(R.color.white));
                    } else {
                        importantDoingTask.setBackgroundResource(R.drawable.ic_star_empty);
                    }
                    listener.onImportantItemClick(task);
                }
            });
        }
    }
}
