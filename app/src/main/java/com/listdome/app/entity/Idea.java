package com.listdome.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by raissa on 23/12/2017.
 */

public class Idea implements Serializable {

    private Long id;
    private String name;
    private Date dateCreate;

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

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(final Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
