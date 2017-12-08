package com.listdome.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by raissa on 28/11/2017.
 */

public class Task implements Serializable {

    private Long id;
    private String name;
    private boolean important;
    private TaskStatus status;
    private Date dateBegin;
    private Date dateEnd;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(final boolean important) {
        this.important = important;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(final TaskStatus status) {
        this.status = status;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(final Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(final Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
