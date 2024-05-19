package com.revikz.winebook.user;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRestClient {
    private final RestTemplate restTemplate = new RestTemplate(); // Create RestTemplate for synchronous requests to REST API.
    private final String BASE_URL = "http://localhost:8080/api/user"; // Set the base URL for the route

    /**
     * Send a Post request to the /register uri of the backend server.
     * @param user The User entity we want to register.
     * @return True if successfully registered, otherwise False.
     */
    public boolean register(User user) {
        try {
            HttpHeaders headers = new HttpHeaders(); // Add Headers
            headers.set("Content-Type", "application/json");

            HttpEntity<User> request = new HttpEntity<>(user, headers); // Add the body of the request

            ResponseEntity<HttpStatus> response = restTemplate.exchange( // Send Post Request, wait for response which is
                    BASE_URL + "/register",                              // Of type HttpStatus to the "/register" uri.
                    HttpMethod.POST,
                    request,
                    HttpStatus.class
            );

            return response.getStatusCode() == HttpStatus.CREATED; // Return result

        } catch (HttpClientErrorException e) {
            // Log the error or handle it accordingly
            System.err.println("Client error: " + e.getStatusCode());
        } catch (Exception e) {
            // General exception handling
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Send a Post request to the /login uri of the backend server.
     * @param user The User entity we want to sign into.
     * @return The User entity if it was successful, otherwise Null.
     */
    public User login(User user) {
        try {
            HttpHeaders headers = new HttpHeaders(); // Add Headers
            headers.set("Content-Type", "application/json");

            HttpEntity<User> request = new HttpEntity<>(user, headers); // Add the body of the request

            ResponseEntity<User> response = restTemplate.exchange( // Send Post Request, wait for response which is
                    BASE_URL + "/login",                           // the User Entity to the "/login" uri.
                    HttpMethod.POST,
                    request,
                    User.class
            );

            return response.getBody(); // Return the response body (The User Entity)

        } catch (HttpClientErrorException e) {
            // Log the error or handle it accordingly
            System.err.println("Client error: " + e.getStatusCode());
        } catch (Exception e) {
            // General exception handling
            e.printStackTrace();
        }

        return null; // Otherwise return null.
    }
}