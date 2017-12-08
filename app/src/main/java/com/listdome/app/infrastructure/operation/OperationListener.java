package com.listdome.app.infrastructure.operation;

import java.util.List;

/**
 * Created by raissa on 08/12/2017.
 */
public abstract class OperationListener<T> {
    public void onSuccess(T result){}

    public void onError(final List<OperationError> errors) {}

    @SuppressWarnings("EmptyMethod")
    public void onCancel(){}
}
