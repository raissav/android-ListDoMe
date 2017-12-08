package com.listdome.app.entity;

/**
 * Created by raissa on 28/11/2017.
 */

public enum TaskStatus {

    TODO(1L),
    DOING(2L),
    DONE(3L);

    private final Long id;

    TaskStatus(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static TaskStatus getById(final Long id) {
        for (final TaskStatus status : values()) {
            if (status.getId().equals(id)) {
                return status;
            }
        }
        return null;
    }
}
