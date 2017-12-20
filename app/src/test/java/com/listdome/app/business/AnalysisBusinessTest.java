package com.listdome.app.business;


import com.listdome.app.entity.Task;
import com.listdome.app.entity.TaskStatus;
import com.listdome.app.gateway.database.dao.TaskDao;
import com.listdome.app.infrastructure.operation.OperationResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalysisBusinessTest {

    @Mock
    TaskDao taskDao;

    @Test
    public void getDailyDoneTasks_success() {

        final List<Task> taskList = new ArrayList<>();
        taskList.add(new Task());

        when(taskDao.getTasksByStatusInterval(
                any(TaskStatus.class), any(Date.class), any(Date.class)))
                .thenReturn(taskList);

        final AnalysisBusiness analysisBusiness = new AnalysisBusiness(taskDao);

        final OperationResult<Integer> result = analysisBusiness.getDailyDoneTasks();

        assertNotNull(result);
        assertThat(result.getResult(), is(1));
    }

    @Test
    public void getWeeklyDoneTasks_success() {

        final List<Task> taskList = new ArrayList<>();

        when(taskDao.getTasksByStatusInterval(
                any(TaskStatus.class), any(Date.class), any(Date.class)))
                .thenReturn(taskList);

        final AnalysisBusiness analysisBusiness = new AnalysisBusiness(taskDao);

        final OperationResult<Integer> result = analysisBusiness.getWeeklyDoneTasks();

        assertNotNull(result);
        assertThat(result.getResult(), is(0));
    }

    @Test
    public void getMonthlyDoneTasks_success() {

        final List<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        taskList.add(new Task());

        when(taskDao.getTasksByStatusInterval(
                any(TaskStatus.class), any(Date.class), any(Date.class)))
                .thenReturn(taskList);

        final AnalysisBusiness analysisBusiness = new AnalysisBusiness(taskDao);

        final OperationResult<Integer> result = analysisBusiness.getMonthlyDoneTasks();

        assertNotNull(result);
        assertThat(result.getResult(), is(2));
    }
}