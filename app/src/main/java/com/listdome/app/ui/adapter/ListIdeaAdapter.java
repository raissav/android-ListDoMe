package com.listdome.app.ui.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.listdome.app.R;
import com.listdome.app.entity.Idea;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.listdome.app.ui.utils.ColorAnimationUtils.animateView;
import static com.listdome.app.ui.utils.ColorAnimationUtils.blinkBackground;

/**
 * Created by raissa on 23/12/2017.
 */

public class ListIdeaAdapter extends RecyclerView.Adapter<ListIdeaAdapter.ViewHolder> {

    private List<Idea> ideaList;
    private ListIdeaListener listener;
    private Context context;
    private int lastPosition;

    public ListIdeaAdapter(final List<Idea> ideaList, final Context context, final ListIdeaListener listener) {
        this.ideaList = ideaList;
        this.listener = listener;
        this.context = context;
        this.lastPosition = -1;
    }

    /**
     * Interface for click item.
     */
    public interface ListIdeaListener {
        void onIdeaItemLongClick(final Idea idea);
        void onFocusChange(final Idea idea);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Idea idea = ideaList.get(position);
        holder.bind(idea, position, listener);
    }

    @Override
    public int getItemCount() {
        return this.ideaList.size();
    }

    public void refreshList(final List<Idea> ideaList) {
        this.ideaList = ideaList;
        this.lastPosition = ideaList.size() - 1;
        notifyDataSetChanged();
    }

    public void removeLastPosition() {
        lastPosition = lastPosition - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_ideas)
        ConstraintLayout layoutIdeas;

        @BindView(R.id.text_idea)
        EditText txtToDoTask;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Idea idea, final int position, final ListIdeaListener listener) {

            txtToDoTask.setText(idea.getName());
            txtToDoTask.clearFocus();

            if (position > lastPosition) {
                blinkBackground(layoutIdeas,
                        context.getResources().getColor(R.color.opacityBlue),
                        context.getResources().getColor(R.color.white));
                lastPosition = position;
            }

            txtToDoTask.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    listener.onIdeaItemLongClick(idea);
                    return false;
                }
            });

            txtToDoTask.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        idea.setName(txtToDoTask.getText().toString());
                        listener.onFocusChange(idea);
                    }
                }
            });
        }
    }
}
