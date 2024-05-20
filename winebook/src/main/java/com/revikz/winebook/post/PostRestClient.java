package com.revikz.winebook.post;

import com.revikz.winebook.user.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class PostRestClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8080/api/post";

    public List<Post> getAll() {
        try {
            HttpHeaders headers = new HttpHeaders(); // Add Headers
            headers.set("Content-Type", "application/json");

            HttpEntity<Post> request = new HttpEntity<>(headers); // Add the body of the request

            ResponseEntity<List<Post>> response = restTemplate.exchange( // Send Get Request, wait for response which is
                    BASE_URL,                                            // The List of the Posts in the DB.
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<Post>>() {}
            );

            return response.getBody(); // Return result

        } catch (HttpClientErrorException e) {
            // Log the error or handle it accordingly
            System.err.println("Client error: " + e.getStatusCode());
        } catch (Exception e) {
            // General exception handling
            e.printStackTrace();
        }

        return Collections.emptyList(); // Return empty list if error occurs.
    }

    public boolean create(Post post) {
        try {
            HttpHeaders headers = new HttpHeaders(); // Add Headers
            headers.set("Content-Type", "application/json");

            HttpEntity<Post> request = new HttpEntity<>(post, headers); // Add the body of the request

            ResponseEntity<HttpStatus> response = restTemplate.exchange( // Send Post Request, wait for response which is
                    BASE_URL + "/create",                                // of type HttpStatus.
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

        return false; // Return false if error occurs.
    }

    public boolean delete(Integer id) {
        try {
            HttpHeaders headers = new HttpHeaders(); // Add Headers
            headers.set("Content-Type", "application/json");

            HttpEntity<Post> request = new HttpEntity<>(headers); // Add the body of the request

            ResponseEntity<HttpStatus> response = restTemplate.exchange( // Send Delete Request, wait for response which is
                    BASE_URL + "/" + id,                          // of type HttpStatus.
                    HttpMethod.DELETE,
                    request,
                    HttpStatus.class
            );

            return response.getStatusCode() == HttpStatus.OK; // Return result

        } catch (HttpClientErrorException e) {
            // Log the error or handle it accordingly
            System.err.println("Client error: " + e.getStatusCode());
        } catch (Exception e) {
            // General exception handling
            e.printStackTrace();
        }

        return false; // Return false if error occurs.
    }

    public Post getLast() {
        try {
            HttpHeaders headers = new HttpHeaders(); // Add Headers
            headers.set("Content-Type", "application/json");

            HttpEntity<Post> request = new HttpEntity<>(headers); // Add the body of the request

            ResponseEntity<Post> response = restTemplate.exchange( // Send Get Request, wait for response which is
                    BASE_URL + "/getLast",                         // The requested post.
                    HttpMethod.GET,
                    request,
                    Post.class
            );

            return response.getBody(); // Return result

        } catch (HttpClientErrorException e) {
            // Log the error or handle it accordingly
            System.err.println("Client error: " + e.getStatusCode());
        } catch (Exception e) {
            // General exception handling
            e.printStackTrace();
        }

        return null; // Return null if error occurs.
    }
}
