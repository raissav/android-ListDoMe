package com.listdome.app.infrastructure.operation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raissa on 08/12/2017.
 */
public class OperationResult<T> {

    private T mResult;
    private List<OperationError> mError;

    public List<OperationError> getErrors() {
        return mError;
    }

    public void addError(final OperationError error) {
        if (mError == null) {
            mError = new ArrayList<>();
        }
        mError.add(error);
    }

    public void addAllErrors(final List<OperationError> errors) {
        if (mError == null) {
            mError = new ArrayList<>();
        }
        mError.addAll(errors);
    }

    public T getResult() {
        return mResult;
    }

    public void setResult(final T result) {
        mResult = result;
    }

    public boolean isOperationSuccessful() {
        return mError == null && getResult() != null;
    }

    public boolean hasErrors() {
        return mError != null && !mError.isEmpty();
    }
}