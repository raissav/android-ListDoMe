package com.listdome.app.business;

import android.util.Log;

import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;
import com.listdome.app.gateway.database.dao.TaskDao;
import com.listdome.app.infrastructure.operation.OperationResult;

import java.util.Date;
import java.util.List;

import static com.listdome.app.infrastructure.DateHelper.getMonthDateBegin;
import static com.listdome.app.infrastructure.DateHelper.getMonthDateEnd;
import static com.listdome.app.infrastructure.DateHelper.getWeekDateBegin;
import static com.listdome.app.infrastructure.DateHelper.getWeekDateEnd;

/**
 * Created by raissa on 05/12/2017.
 */

public class AnalysisBusiness extends BaseBusiness {

    private static final String TAG = AnalysisBusiness.class.getSimpleName();

    private final TaskDao taskDao;

    public AnalysisBusiness(final TaskDao taskDao) {
        super();
        this.taskDao = taskDao;
    }

    /**
     * Get daily done tasks count from DB.
     *
     * @return the list of tasks done today.
     */
    public OperationResult<Integer> getDailyDoneTasks() {
        Log.v(TAG, "[method] getDailyDoneTasks");

        final OperationResult<Integer> result = new OperationResult<>();

        final List<Task> dailyDoneTasks = taskDao.getTasksByStatusInterval(
                TaskStatus.DONE, new Date(), new Date());

        result.setResult(dailyDoneTasks.size());

        return result;
    }

    /**
     * Get weekly done tasks count from DB.
     *
     * @return the list of tasks done this week.
     */
    public OperationResult<Integer> getWeeklyDoneTasks() {
        Log.v(TAG, "[method] getWeeklyDoneTasks");

        final OperationResult<Integer> result = new OperationResult<>();

        final List<Task> dailyDoneTasks = taskDao.getTasksByStatusInterval(
                TaskStatus.DONE, getWeekDateBegin(), getWeekDateEnd());

        result.setResult(dailyDoneTasks.size());

        return result;
    }

    /**
     * Get monthly done tasks count from DB.
     *
     * @return the list of tasks done this month.
     */
    public OperationResult<Integer> getMonthlyDoneTasks() {
        Log.v(TAG, "[method] getMonthlyDoneTasks");

        final OperationResult<Integer> result = new OperationResult<>();

        final List<Task> dailyDoneTasks = taskDao.getTasksByStatusInterval(
                TaskStatus.DONE, getMonthDateBegin(), getMonthDateEnd());

        result.setResult(dailyDoneTasks.size());

        return result;
    }

}
