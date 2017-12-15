package com.listdome.app.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.listdome.app.R;
import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.listdome.app.ui.utils.AnimationUtils.blinkBackground;

/**
 * Created by raissa on 27/11/2017.
 */

public class ListDoneAdapter extends RecyclerView.Adapter<ListDoneAdapter.ViewHolder> {

    private List<Task> doneList;
    private ListDoneListener listener;
    private Context context;
    private int lastPosition;

    public ListDoneAdapter(final List<Task> doneList, final Context context, final ListDoneListener listener) {
        this.doneList = doneList;
        this.listener = listener;
        this.context = context;
        this.lastPosition = -1;
    }

    /**
     * Interface for click item.
     */
    public interface ListDoneListener {
        void onTaskItemLongClick(final Task task);
        void onCheckItemClick(final Task task);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_done, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task task = doneList.get(position);
        holder.bind(task, position, listener);
    }

    @Override
    public int getItemCount() {
        return this.doneList.size();
    }

    public void refreshList(final List<Task> doneList) {
        this.doneList = doneList;
        this.lastPosition = doneList.size() - 1;
        notifyDataSetChanged();
    }

    public void removeLastPosition() {
        lastPosition = lastPosition - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_done_task)
        ConstraintLayout layoutDone;

        @BindView(R.id.text_done_task)
        TextView txtDoneTask;

        @BindView(R.id.check_done_task)
        CheckBox checkDoneTask;

        @BindView(R.id.important_done_task)
        ImageButton importantDoneTask;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Task task, final int position, final ListDoneListener listener) {

            txtDoneTask.setText(task.getName());
            txtDoneTask.setPaintFlags(txtDoneTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            checkDoneTask.setChecked(true);

            if (task.isImportant()) {
                importantDoneTask.setBackgroundResource(R.drawable.ic_star_full);
            } else {
                importantDoneTask.setBackgroundResource(R.drawable.ic_star_empty);
            }

            if (position > lastPosition) {
                blinkBackground(layoutDone,
                        context.getResources().getColor(R.color.opacityBlue),
                        context.getResources().getColor(R.color.white));
                lastPosition = position;
            }

            txtDoneTask.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    listener.onTaskItemLongClick(task);
                    return false;
                }
            });

            checkDoneTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    task.setStatus(TaskStatus.DOING);
                    task.setDateEnd(null);
                    listener.onCheckItemClick(task);
                }
            });
        }
    }
}
