package com.listdome.app.business;

import android.content.Context;
import android.util.Log;

import com.listdome.app.entity.Idea;
import com.listdome.app.gateway.database.dao.IdeaDao;
import com.listdome.app.infrastructure.Constants;
import com.listdome.app.infrastructure.operation.OperationResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by raissa on 23/12/2017.
 */

public class IdeaBusiness extends BaseBusiness {

    private static final String TAG = IdeaBusiness.class.getSimpleName();

    private final IdeaDao ideaDao;

    public IdeaBusiness(final IdeaDao ideaDao) {
        super();
        this.ideaDao = ideaDao;
    }

    /**
     * Get daily inspiration from WS.
     *
     * @return the daily inspiration sentence.
     */
    public OperationResult<Idea> getDailyInspiration(final Context context) {
        Log.v(TAG, "[method] getDailyInspiration");

        final OperationResult<Idea> result = new OperationResult<>();

        final Idea idea = new Idea();
        idea.setName(getNextInspiration(context));
        idea.setDateCreate(new Date());
        result.setResult(idea);

        return result;
    }

    /**
     * Get the next inspiration.
     *
     * @return the next inspiration string ID.
     */
    private String getNextInspiration(final Context context) {
        final Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        final int totalInspirations = Constants.Inspirations.inspirationList.length;
        final int nextInspiration = Constants.Inspirations.inspirationList[dayOfYear % totalInspirations];
        return context.getString(nextInspiration);
    }

    /**
     * Get all ideas from DB.
     *
     * @return the list of ideas
     */
    public OperationResult<List<Idea>> getIdeas() {
        Log.v(TAG, "[method] getIdeas");

        final OperationResult<List<Idea>> result = new OperationResult<>();

        result.setResult(ideaDao.getAll());

        return result;
    }

    /**
     * Save a new idea in DB.
     *
     * @param idea
     * @return the ID from the new idea
     */
    public OperationResult<Long> saveIdea(final Idea idea) {
        Log.v(TAG, "[method] saveIdea");

        final OperationResult<Long> result = new OperationResult<>();

        result.setResult(ideaDao.add(idea));

        return result;
    }

    /**
     * Delete a idea from DB.
     *
     * @param idea
     * @return if operation was successful
     */
    public OperationResult<Boolean> deleteIdea(final Idea idea) {
        Log.v(TAG, "[method] deleteIdea");

        final OperationResult<Boolean> result = new OperationResult<>();

        result.setResult(ideaDao.delete(idea));

        return result;
    }

    /**
     * Update a idea from DB.
     *
     * @param idea
     * @return if operation was successful
     */
    public OperationResult<Boolean> updateIdea(final Idea idea) {
        Log.v(TAG, "[method] updateIdea");

        final OperationResult<Boolean> result = new OperationResult<>();

        result.setResult(ideaDao.update(idea));

        return result;
    }
}
