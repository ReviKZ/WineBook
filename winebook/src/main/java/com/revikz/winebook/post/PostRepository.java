package com.revikz.winebook.post;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    private final JdbcClient jdbcClient;

    public PostRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient; //DI our DataBase Service.
    }

    /**
     * Gets all stored records in Post table.
     * @return List of Posts.
     */
    public List<Post> getAll() {
        return jdbcClient.sql("SELECT * FROM \"Post\"")
                .query(Post.class)
                .list();
    }

    /**
     * Gets last item in Post table.
     * @return Last post.
     */
    public Optional<Post> getLast() {
        return jdbcClient.sql("SELECT * FROM \"Post\" ORDER BY id DESC LIMIT 1")
                .query(Post.class)
                .optional();
    }

    /**
     * Creates a Post.
     * @param post The Post we want to create.
     * @return True if it was Created, otherwise False.
     */
    public boolean create(Post post) {
        var createdLines = jdbcClient.sql("INSERT INTO \"Post\"(author_id, title, content, likes, dislikes) VALUES (?,?,?,?,?) ") // Add the Post.
                .params(null, post.title(), post.content(), post.likes(), post.dislikes())
                .update();

        return createdLines == 1;
    }

    /**
     * Updates the Post with the given Id.
     * @param post The Post we want to edit the previous to.
     * @param id The Id of the previous post.
     * @return True if it was edited, otherwise False.
     */
    public boolean update(Post post, Integer id) {
        var updatedLines = jdbcClient.sql("UPDATE \"Post\" SET author_id = ?, title = ?, content = ? WHERE id = ?") // Update the Post
                .params(null, post.title(), post.content(), id)
                .update();

        return updatedLines == 1;
    }

    /**
     * Deletes the Post with the given Id.
     * @param id The Id of the post we want to delete.
     * @return True if it was successfully deleted, otherwise False.
     */
    public boolean delete(Integer id) {
        var update = jdbcClient.sql("DELETE FROM \"Post\" WHERE id = :id") // Delete the Post
                .param("id", id)
                .update();

        return update == 1;
    }

    /**
     * Adds a like to the Post with the given Id.
     * @param id The Id of the Post.
     * @return True if it was added, otherwise False.
     */
    public boolean like(Integer id) {
        var currentPost = jdbcClient.sql("SELECT * FROM \"Post\" WHERE id = :id") // Get the Post with the id.
                .param("id", id)
                .query(Post.class)
                .optional();

        if (currentPost.isEmpty()){ // If it was not found return false.
            return false;
        }

        Integer likes = currentPost.get().likes(); // Get the likes
        likes++; // Increase the likes by one

        var updatedLines = jdbcClient.sql("UPDATE \"Post\" SET likes = :likes WHERE id = :id") // Update the DB
                .param("likes", likes)
                .param("id", id)
                .update();

        return updatedLines == 1;
    }

    /**
     * Removes the given like of the Post with the given Id.
     * @param id The Id of the Post.
     * @return True if removed, otherwise False.
     */
    public boolean removeLike(Integer id) {
        var currentPost = jdbcClient.sql("SELECT * FROM \"Post\" WHERE id = :id") // Get the Post with the id.
                .param("id", id)
                .query(Post.class)
                .optional();

        if (currentPost.isEmpty()){ // Return false if it was not found.
            return false;
        }

        Integer likes = currentPost.get().likes(); // Get the likes
        if (likes <= 0) { // Check if its smaller or equals to 0
            return false; // If so, return false. || This is an unlikely event, but just to be sure ||
        }
        likes--; // Decrement likes by one.

        var updatedLines = jdbcClient.sql("UPDATE \"Post\" SET likes = :likes WHERE id = :id") // Update Post in DB.
                .param("likes", likes)
                .param("id", id)
                .update();

        return updatedLines == 1;
    }

    /**
     * Adds a dislike to the Post with the given Id.
     * @param id The Id of the Post.
     * @return True if it was added, otherwise False.
     */
    public boolean dislike(Integer id) {
        var currentPost = jdbcClient.sql("SELECT * FROM \"Post\" WHERE id = :id") // Get the Post with the id.
                .param("id", id)
                .query(Post.class)
                .optional();

        if (currentPost.isEmpty()){ // If it was not found return false.
            return false;
        }

        Integer dislikes = currentPost.get().dislikes(); // Get the dislikes
        dislikes++; // Increase the dislikes by one

        var updatedLines = jdbcClient.sql("UPDATE \"Post\" SET dislikes = :dislikes WHERE id = :id") // Update the DB
                .param("dislikes", dislikes)
                .param("id", id)
                .update();

        return updatedLines == 1;
    }

    /**
     * Removes the given dislike of the Post with the given Id.
     * @param id The Id of the Post.
     * @return True if removed, otherwise False.
     */
    public boolean removeDislike(Integer id) {
        var currentPost = jdbcClient.sql("SELECT * FROM \"Post\" WHERE id = :id") // Get the Post with the id.
                .param("id", id)
                .query(Post.class)
                .optional();

        if (currentPost.isEmpty()){ // Return false if it was not found.
            return false;
        }

        Integer dislikes = currentPost.get().dislikes(); // Get the dislikes
        if (dislikes <= 0) { // Check if its smaller or equals to 0
            return false; // If so, return false. || This is an unlikely event, but just to be sure ||
        }
        dislikes--; // Decrement likes by one.

        var updatedLines = jdbcClient.sql("UPDATE \"Post\" SET dislikes = :dislikes WHERE id = :id") // Update Post in DB.
                .param("dislikes", dislikes)
                .param("id", id)
                .update();

        return updatedLines == 1;
    }
}
