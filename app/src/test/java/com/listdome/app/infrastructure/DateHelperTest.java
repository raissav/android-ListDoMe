package com.listdome.app.infrastructure;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DateHelperTest {

    @Test
    public void dateHelper_getWeekDateBegin_notNull() {
        assertNotNull(DateHelper.getWeekDateBegin());
    }

    @Test
    public void dateHelper_getWeekDateEnd_notNull() {
        assertNotNull(DateHelper.getWeekDateEnd());
    }

    @Test
    public void dateHelper_getMonthDateBegin_notNull() {
        assertNotNull(DateHelper.getMonthDateBegin());
    }

    @Test
    public void dateHelper_getMonthDateEnd_notNull() {
        assertNotNull(DateHelper.getWeekDateEnd());
    }
}