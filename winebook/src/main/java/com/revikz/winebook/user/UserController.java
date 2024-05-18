package com.revikz.winebook.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * A Route for registration.
     * @param user The User we want to register.
     * @return Created if OK, otherwise Bad Request.
     */
    @PostMapping("/register")
    HttpStatus register(@RequestBody User user) {
        var isCreated = userRepository.register(user.username(), user.password());

        if (isCreated) {
            return HttpStatus.CREATED;
        }

        return HttpStatus.BAD_REQUEST;
    }

    /**
     * A Route for logging in.
     * @param user The User we want to log in.
     * @return Accepted if OK, otherwise Not Found.
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    User login(@RequestBody User user) {
        var currentUser = userRepository.login(user.username(), user.password());

        if (currentUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Username-Password combination doesn't exist");
        }

        return currentUser.get();
    }

    /**
     * A Route for getting a User by the Id.
     * @param id The Id of the User. [IN THE URL]
     * @return Found if OK, otherwise Not Found.
     */
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    User getUserById(@PathVariable Integer id) {
        var searchedUser = userRepository.getUserById(id);

        if (searchedUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The User with this id was not found.");
        }

        return searchedUser.get();
    }

    /**
     * A Route for deleting a User by the Id.
     * @param id The Id of the User. [IN THE URL]
     * @return Ok if OK, Otherwise Not Modified.
     */
    @DeleteMapping("/{id}")
    HttpStatus delete(@PathVariable Integer id) {
        var isDeleted = userRepository.delete(id);

        if (isDeleted) {
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_MODIFIED;
    }

}
