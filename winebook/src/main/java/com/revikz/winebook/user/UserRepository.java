package com.revikz.winebook.user;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcClient jdbcClient;
    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient; //DI our DataBase Service.
    }

    /**
     * Registers a User with password hashing.
     * @param username The Username of the User
     * @param password The Password of the User
     * @return True, if it could be added to the DB, otherwise False.
     */
    public boolean register(String username, String password) {
        var user = jdbcClient.sql("SELECT * FROM \"User\" WHERE username = :username") // Gets the user with the
                .param("username", username)                                                                             // given username
                .query(User.class)
                .optional();

        if (user.isPresent()) { // Checks if already in DB
            return false;
        }

        Hash hash = Password.hash(password) // Hashing the password
                .with(bcrypt);              // with bcrypt.

        var update = jdbcClient.sql("INSERT INTO \"User\"(username, password) VALUES(?,?)") // Adds the user to the
                .params(username, hash.getResult())                               // DB and gets how many lines
                .update();                                                                // were updated.

        return update == 1; // Returns whether there was only one line updated as it should be.
    }

    /**
     * Logs in a User with password hash checking.
     * @param username The Username of the User
     * @param password The Username of the User
     * @return The User Model, if there's a user in the DB with the given username and the password matches,
     * otherwise an Empty User Model.
     */
    public Optional<User> login(String username, String password) {
        var user = jdbcClient.sql("SELECT * FROM \"User\" WHERE username = :username") // Gets the user with the
                .param("username", username)                                                    // given username
                .query(User.class)
                .optional();

        if (user.isPresent()) {
            var verified = Password.check(password, user.get().password()) // Checks password
                    .with(bcrypt);

            if (verified) {
                return user; // If password was correct returns the user.
            }
        }
        return Optional.empty();
    }

    /**
     * Gets a user with the given Id.
     * @param id The Id of the User.
     * @return The User Model, if the User was found in the DB, otherwise an Empty User Model.
     */
    public Optional<User> getUserById(Integer id) {

        return jdbcClient.sql("SELECT * FROM User WHERE id = :id")
                .params("id", id)
                .query(User.class)
                .optional();
    }

    /**
     * Deletes the user with the given Id.
     * @param id The Id of the User.
     * @return True, if it was successfully deleted, otherwise False.
     */
    public boolean delete(Integer id) {
        var update = jdbcClient.sql("DELETE FROM User WHERE id = :id")
                .param("id", id)
                .update();

        return update == 1; // Returns whether there was only one line updated as it should be.
    }

}
