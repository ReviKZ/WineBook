package com.revikz.winebook.post;

import com.revikz.winebook.user.User;

import java.util.Optional;

public record Post(Integer id, String title, String content, Integer likes, Integer dislikes) {
    public Post {
        if (title.isBlank()) {
            throw new IllegalArgumentException("There must be a title!");
        }

        if (content.isBlank()) {
            throw new IllegalArgumentException("There must be content!");
        }

        if (likes == null){
            likes = 0;
        }

        if (dislikes == null) {
            dislikes = 0;
        }
    }
}
