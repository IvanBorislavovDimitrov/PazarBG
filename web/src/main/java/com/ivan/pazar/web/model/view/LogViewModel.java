package com.ivan.pazar.web.model.view;

import java.time.LocalDateTime;

public class LogViewModel {

    private String username;

    private String action;

    private LocalDateTime date;

    public static class Builder {
        private LogViewModel logViewModel = new LogViewModel();

        public Builder username(String username) {
            logViewModel.username = username;
            return this;
        }

        public Builder action(String action) {
            logViewModel.action = action;
            return this;
        }

        public Builder date(LocalDateTime date) {
            logViewModel.date = date;
            return this;
        }

        public LogViewModel build() {
            return logViewModel;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
