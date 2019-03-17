package com.ivan.pazar.web.model.binding;

import javax.validation.constraints.Size;

public class UserChangePasswordBindingModel {

    @Size(min = 8, max = 32)
    private String password;

    @Size(min = 8, max = 32)
    private String newPassword;

    @Size(min = 8, max = 32)
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
