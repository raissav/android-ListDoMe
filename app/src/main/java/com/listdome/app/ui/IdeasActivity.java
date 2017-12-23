package com.listdome.app.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.listdome.app.R;
import com.listdome.app.entity.Idea;
import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;
import com.listdome.app.infrastructure.operation.OperationError;
import com.listdome.app.infrastructure.operation.OperationListener;
import com.listdome.app.manager.IdeaManager;
import com.listdome.app.manager.TaskManager;
import com.listdome.app.ui.adapter.ListIdeaAdapter;
import com.listdome.app.ui.adapter.ListToDoAdapter;
import com.listdome.app.ui.utils.SlideAnimationUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by raissa on 25/11/2017.
 */

public class IdeasActivity extends BaseActivity {

    private static final String TAG = IdeasActivity.class.getSimpleName();

    @BindView(R.id.container_ideas)
    protected LinearLayout containerIdeas;

    @BindView(R.id.card_inspiration)
    protected CardView cardInspiration;

    @BindView(R.id.card_ideas)
    protected CardView cardIdeas;

    @BindView(R.id.text_inspiration)
    protected TextView txtInspiration;

    @BindView(R.id.save_inspiration)
    protected ImageButton btnSaveInspiration;

    @BindView(R.id.new_idea)
    protected EditText txtNewIdea;

    @BindView(R.id.add_idea)
    protected ImageButton btnAddIdea;

    @BindView(R.id.list_ideas)
    protected RecyclerView recyclerListIdeas;

    protected ListIdeaAdapter listIdeasAdapter;

    protected List<Idea> ideasList;

    protected IdeaManager mIdeaManager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ideas;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_ideas;
    }

    @Override
    protected String getMenuItemTitle() {
        return getString(R.string.title_ideas);
    }

    @Override
    protected void loadElements() {
        Log.v(TAG, "[method] loadElements");

        mIdeaManager = new IdeaManager(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        txtNewIdea.clearFocus();

        setRecyclerView();
        setAdapter();
        getDailyInspiration();
        getIdeaList();
        showCards();
    }

    @Override
    protected void cancelOperations() {
        Log.v(TAG, "[method] cancelOperations");

        if (mIdeaManager != null) {
            mIdeaManager.cancelOperations();
        }
    }

    @Override
    protected void animateTransaction() {
        SlideAnimationUtils.slideInFromRight(this, containerIdeas);
    }

    /**
     * Configuration of Layout Manager and Item Decoration
     */
    private void setRecyclerView() {
        Log.v(TAG, "[method] setRecyclerView");

        final LinearLayoutManager lmToDoList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lmToDoList.setMeasurementCacheEnabled(false);
        recyclerListIdeas.setLayoutManager(lmToDoList);
        recyclerListIdeas.setHasFixedSize(true);
    }

    /**
     * Load the list adapter
     */
    private void setAdapter() {
        Log.v(TAG, "[method] setAdapter");

        ideasList = new ArrayList<>();

        listIdeasAdapter = new ListIdeaAdapter(ideasList, this, new ListIdeaAdapter.ListIdeaListener() {
            @Override
            public void onIdeaItemLongClick(final Idea idea) {
                Log.v(TAG, "[method] onIdeaItemLongClick");
                hideKeyboard(txtNewIdea);
                showRemoveIdeaDialog(idea);
            }

            @Override
            public void onFocusChange(final Idea idea) {
                Log.v(TAG, "[method] onFocusChange");

                if (idea.getName().trim().isEmpty()) {
                    deleteIdea(idea);
                } else {
                    updateIdea(idea);
                }
            }
        });

        recyclerListIdeas.setAdapter(listIdeasAdapter);
    }

    /**
     * Get the daily inspiration.
     */
    private void getDailyInspiration() {
        Log.v(TAG, "[method] getInspiration");

        mIdeaManager.getDailyInspiration(new OperationListener<Idea>() {

            @Override
            public void onSuccess(final Idea result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                txtInspiration.setText(result.getName());
                txtNewIdea.clearFocus();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.ideas_list_error));
            }
        });
    }

    /**
     * Get the ideas from the DB.
     */
    private void getIdeaList() {
        Log.v(TAG, "[method] getIdeaList");

        mIdeaManager.getIdeas(new OperationListener<List<Idea>>() {

            @Override
            public void onSuccess(final List<Idea> result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                processIdeaList(result);
                txtNewIdea.clearFocus();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.ideas_list_error));
            }
        });
    }

    /**
     * Process the ideas.
     *
     * @param ideas
     */
    private void processIdeaList(final List<Idea> ideas) {
        Log.v(TAG, "[method] processIdeaList: " + ideas.size());

        ideasList.clear();
        ideasList.addAll(ideas);

        verifyEmptyList();

        listIdeasAdapter.refreshList(ideasList);
    }

    /**
     * Checks if the lists are empty.
     * If so, shows an empty result message.
     */
    private void verifyEmptyList() {
        if (ideasList.isEmpty()) {
            recyclerListIdeas.setVisibility(View.GONE);
        } else {
            recyclerListIdeas.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Change visibility of all cards (lists) to show
     */
    private void showCards() {
        cardInspiration.setVisibility(View.VISIBLE);
        cardIdeas.setVisibility(View.VISIBLE);
    }

    /**
     * Open the confirmation Dialog to delete the idea.
     *
     * @param idea
     */
    private void showRemoveIdeaDialog(final Idea idea) {
        Log.v(TAG, "[method] showRemoveIdeaDialog");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.remove_idea_dialog_title))
                .setMessage(getString(R.string.remove_idea_dialog_message))
                .setIcon(android.R.drawable.ic_delete)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteIdea(idea);
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
     * Save new idea in DB.
     *
     * @param newIdea - idea to be saved
     */
    private void saveIdea(final Idea newIdea) {
        Log.v(TAG, "[method] saveIdea");

        mIdeaManager.saveIdea(newIdea, new OperationListener<Long>() {

            @Override
            public void onSuccess(final Long result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                newIdea.setId(result);
                ideasList.add(newIdea);
                verifyEmptyList();
                listIdeasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.ideas_list_error));
            }
        });
    }

    /**
     * Update idea in DB.
     *
     * @param idea - idea to be updated
     */
    private void updateIdea(final Idea idea) {
        Log.v(TAG, "[method] updateIdea");

        mIdeaManager.updateIdea(idea, new OperationListener<Boolean>() {

            @Override
            public void onSuccess(final Boolean result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.ideas_list_error));
            }
        });
    }

    /**
     * Delete idea from DB.
     *
     * @param idea - idea to be removed
     */
    private void deleteIdea(final Idea idea) {
        Log.v(TAG, "[method] deleteIdea");

        mIdeaManager.deleteIdea(idea, new OperationListener<Boolean>() {

            @Override
            public void onSuccess(final Boolean result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);

                ideasList.remove(idea);
                listIdeasAdapter.removeLastPosition();
                listIdeasAdapter.notifyDataSetChanged();

                verifyEmptyList();
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);

                generateToast(getString(R.string.ideas_list_error));
            }
        });
    }

    @OnClick(R.id.add_idea)
    void onAddIdeaClick() {
        Log.v(TAG, "[method] onAddIdeaClick");

        if (txtNewIdea.getText() !=  null && !txtNewIdea.getText().toString().trim().isEmpty()) {
            final Idea idea = new Idea();
            idea.setDateCreate(new Date());
            idea.setName(txtNewIdea.getText().toString());

            txtNewIdea.setText("");
            txtNewIdea.clearFocus();

            saveIdea(idea);
        }
    }

    @OnClick(R.id.save_inspiration)
    void onSaveInspirationClick() {
        Log.v(TAG, "[method] onSaveInspirationClick");

        if (txtInspiration.getText() !=  null && !txtInspiration.getText().toString().trim().isEmpty()) {
            final Idea idea = new Idea();
            idea.setDateCreate(new Date());
            idea.setName(txtInspiration.getText().toString());

            boolean ideaSaved = false;
            for (final Idea existingIdea : ideasList) {
                if (existingIdea.getName().compareToIgnoreCase(idea.getName()) == 0) {
                    ideaSaved = true;
                    break;
                }
            }

            if (!ideaSaved) {
                saveIdea(idea);
            }
        }
    }

}
