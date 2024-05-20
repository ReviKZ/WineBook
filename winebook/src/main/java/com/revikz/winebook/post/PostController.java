package com.revikz.winebook.post;

import com.revikz.winebook.user.User;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * A Route for getting all the posts
     * @return The list of the posts.
     */
    @GetMapping("")
    List<Post> getAll() {
        return postRepository.getAll();
    }

    /**
     * A Route for creating a Post.
     * @param post The contents of the Post.
     * @return Created if Ok, otherwise Bad Request.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    void create(@RequestBody Post post) {
        var isCreated = postRepository.create(post);

        if (!isCreated) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * A Route for updating a Post.
     * @param post The Post we want to update the original with.
     * @param id The Id of the original Post.
     * @return Ok if OK, otherwise Not Modified
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    void update(@RequestBody Post post, @PathVariable Integer id) {
        var isUpdated = postRepository.update(post, id);

        if (!isUpdated) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * A Route for deleting a post
     * @param id The Id of the Post we want to delete
     * @return Ok if OK, otherwise Not Modified.
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        var isDeleted = postRepository.delete(id);

        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * A Route for getting the last post.
     * @return The post if there was any, otherwise null.
     */
    @GetMapping("/getLast")
    Post getLast() {
        var post = postRepository.getLast();

        if (post.isPresent()) {
            return post.get();
        }

        return null;
    }

    /**
     * A Route for adding a like to a Post.
     * @param id The Id of the Post we want to add a like to.
     * @return Ok if OK, otherwise Not Modified.
     */
    @PutMapping("/like/{id}/add")
    HttpStatus like(@PathVariable Integer id) {
        var isLikeAdded = postRepository.like(id);

        if (isLikeAdded) {
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_MODIFIED;
    }

    /**
     * A Route for removing the given like form a Post.
     * @param id The Id of the post we want to remove the like from.
     * @return Ok, if OK, otherwise Not Modified.
     */
    @PutMapping("/like/{id}/remove")
    HttpStatus removeLike(@PathVariable Integer id) {
        var isLikeRemoved = postRepository.removeLike(id);

        if (isLikeRemoved) {
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_MODIFIED;
    }

    /**
     * A Route for adding a dislike to a Post.
     * @param id The Id of the Post we want to add a like to.
     * @return Ok if OK, otherwise Not Modified.
     */
    @PutMapping("/dislike/{id}/add")
    HttpStatus dislike(@PathVariable Integer id) {
        var isDislikeAdded = postRepository.dislike(id);

        if (isDislikeAdded) {
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_MODIFIED;
    }

    /**
     * A Route for removing the given like form a Post.
     * @param id The Id of the Post we want to remove the dislike form.
     * @return Ok if OK, otherwise Not Modified.
     */
    @PutMapping("/dislike/{id}/remove")
    HttpStatus removeDislike(@PathVariable Integer id) {
        var isDislikeRemoved = postRepository.removeDislike(id);

        if (isDislikeRemoved) {
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_MODIFIED;
    }
}
