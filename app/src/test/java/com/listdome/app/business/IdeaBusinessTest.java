package com.listdome.app.business;

import com.listdome.app.entity.Idea;
import com.listdome.app.gateway.database.dao.IdeaDao;
import com.listdome.app.infrastructure.operation.OperationResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IdeaBusinessTest {

    @Mock
    IdeaDao ideaDao;

    @Test
    public void getInspiration_success() {

        final IdeaBusiness ideaBusiness = new IdeaBusiness(ideaDao);

        final OperationResult<Idea> result = ideaBusiness.getDailyInspiration();

        assertNotNull(result);
        assertNotNull(result.getResult());
        assertFalse(result.getResult().getName().isEmpty());
    }

    @Test
    public void getIdeas_success() {

        final List<Idea> ideaList = new ArrayList<>();
        ideaList.add(new Idea());

        when(ideaDao.getAll())
                .thenReturn(ideaList);

        final IdeaBusiness ideaBusiness = new IdeaBusiness(ideaDao);

        final OperationResult<List<Idea>> result = ideaBusiness.getIdeas();

        assertNotNull(result);
        assertThat(result.getResult(), is(ideaList));
    }

    @Test
    public void saveTask_success() {

        final Idea idea = new Idea();
        when(ideaDao.add(idea))
                .thenReturn(1L);

        final IdeaBusiness ideaBusiness = new IdeaBusiness(ideaDao);

        final OperationResult<Long> result = ideaBusiness.saveIdea(idea);

        assertNotNull(result);
        assertThat(result.getResult(), is(1L));
    }

    @Test
    public void deleteTask_success() {

        final Idea idea = new Idea();
        when(ideaDao.delete(idea))
                .thenReturn(true);

        final IdeaBusiness ideaBusiness = new IdeaBusiness(ideaDao);

        final OperationResult<Boolean> result = ideaBusiness.deleteIdea(idea);

        assertNotNull(result);
        assertTrue(result.getResult());
    }

    @Test
    public void updateTask_success() {

        final Idea idea = new Idea();
        when(ideaDao.update(idea))
                .thenReturn(true);

        final IdeaBusiness ideaBusiness = new IdeaBusiness(ideaDao);

        final OperationResult<Boolean> result = ideaBusiness.updateIdea(idea);

        assertNotNull(result);
        assertTrue(result.getResult());
    }
}