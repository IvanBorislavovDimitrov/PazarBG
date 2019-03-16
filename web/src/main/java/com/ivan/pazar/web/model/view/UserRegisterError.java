package com.ivan.pazar.web.model.view;

import java.util.Objects;

public class UserRegisterError {

    private String fieldName;

    private String errorMessage;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public int hashCode() {
        return fieldName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserRegisterError)) {
            return false;
        }
        return Objects.equals(fieldName, ((UserRegisterError) obj).fieldName);
    }
}
