package com.listdome.app.business;

import com.listdome.app.entity.Task;
import com.listdome.app.gateway.database.dao.TaskDao;
import com.listdome.app.infrastructure.operation.OperationResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskBusinessTest {

    @Mock
    TaskDao taskDao;

    @Test
    public void getTasks_success() {

        final List<Task> taskList = new ArrayList<>();
        taskList.add(new Task());

        when(taskDao.getAll())
                .thenReturn(taskList);

        final TaskBusiness taskBusiness = new TaskBusiness(taskDao);

        final OperationResult<List<Task>> result = taskBusiness.getTasks();

        assertNotNull(result);
        assertThat(result.getResult(), is(taskList));
    }

    @Test
    public void saveTask_success() {

        final Task task = new Task();
        when(taskDao.add(task))
                .thenReturn(1L);

        final TaskBusiness taskBusiness = new TaskBusiness(taskDao);

        final OperationResult<Long> result = taskBusiness.saveTask(task);

        assertNotNull(result);
        assertThat(result.getResult(), is(1L));
    }

    @Test
    public void deleteTask_success() {

        final Task task = new Task();
        when(taskDao.delete(task))
                .thenReturn(true);

        final TaskBusiness taskBusiness = new TaskBusiness(taskDao);

        final OperationResult<Boolean> result = taskBusiness.deleteTask(task);

        assertNotNull(result);
        assertTrue(result.getResult());
    }

    @Test
    public void updateTask_success() {

        final Task task = new Task();
        when(taskDao.update(task))
                .thenReturn(true);

        final TaskBusiness taskBusiness = new TaskBusiness(taskDao);

        final OperationResult<Boolean> result = taskBusiness.updateTask(task);

        assertNotNull(result);
        assertTrue(result.getResult());
    }
}