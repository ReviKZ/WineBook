package com.revikz.winebook.user;

public record User(Integer id, String username, String password) {
    public User {
        if (username.isBlank()) {
            throw new IllegalArgumentException("Username mustn't be empty!");
        }
        if (username.length() < 6) {
            throw new IllegalArgumentException("Username must be at least 6 characters long!");
        }

        if (username.length() > 20) {
            throw new IllegalArgumentException("Username mustn't be more than 20 characters long!");
        }

        if (password.isBlank()) {
            throw new IllegalArgumentException("Password mustn't be empty!");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long!");
        }

        if (password.length() > 100) {
            throw new IllegalArgumentException("Username mustn't be more than 100 characters long!");
        }
    }

}
