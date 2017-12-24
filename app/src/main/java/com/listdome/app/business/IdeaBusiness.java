package com.listdome.app.business;

import android.util.Log;

import com.listdome.app.R;
import com.listdome.app.entity.Idea;
import com.listdome.app.gateway.database.dao.IdeaDao;
import com.listdome.app.infrastructure.operation.OperationResult;

import java.util.Date;
import java.util.List;

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
    public OperationResult<Idea> getDailyInspiration() {
        Log.v(TAG, "[method] getDailyInspiration");

        final OperationResult<Idea> result = new OperationResult<>();

        final Idea idea = new Idea();
        idea.setName("The most important investment you can make is in yourself.");
        idea.setDateCreate(new Date());

        result.setResult(idea);

        return result;
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
