package com.listdome.app.infrastructure.operation;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by raissa on 08/12/2017.
 */

public class OperationError implements Serializable {

    private final int errorCode;
    private String errorMessage;
    private Object errorData;

    public OperationError(final int errorCode, final String errorMessage) {
        this.errorCode = errorCode;

        if (TextUtils.isEmpty(errorMessage)) {
            throw new RuntimeException("ErrorMessage cannot be empty.");
        }

        this.errorMessage = errorMessage;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(final Object errorData) {
        this.errorData = errorData;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
