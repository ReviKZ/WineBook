package com.revikz.winebook.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Registration window
 */
@Component
public class UserRegisterLoginView extends JFrame implements ActionListener {

    // Page Elements
    JPanel welcomePanel;
    JLabel welcomeText;
    JPanel usernamePanel;
    JLabel usernameLabel;
    JTextField username;
    JPanel passwordPanel;
    JLabel passwordLabel;
    JPasswordField password;
    JPanel buttonPanel;
    JButton registerButton;
    JButton loginButton;

    /**
     * Constructor of the page.
     */
    public UserRegisterLoginView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(4,1)); // Make this page a flow layout
        this.setMinimumSize(new Dimension(800, 300)); // Cannot be smaller than this.
        this.setSize(new Dimension(800, 300)); // Initial window size.
        this.setTitle("WineBook"); // Page title

        // Initialize elements
        this.welcomePanel = new JPanel();
        this.welcomeText = new JLabel();
        this.usernamePanel = new JPanel();
        this.usernameLabel = new JLabel();
        this.username = new JTextField();
        this.passwordPanel = new JPanel();
        this.passwordLabel = new JLabel();
        this.password = new JPasswordField();
        this.buttonPanel = new JPanel();
        this.registerButton = new JButton();
        this.loginButton = new JButton();

        // Set captions, sizes
        this.welcomeText.setText("Welcome to WineBook! Please provide your username & password, then" +
                " click the button which you want to do!");
        this.username.setPreferredSize(new Dimension(200, 20));
        this.password.setPreferredSize(new Dimension(200, 20));
        this.usernameLabel.setText("Username:");
        this.passwordLabel.setText("Password:");
        this.registerButton.setText("Register");
        this.loginButton.setText("Log In");
        this.registerButton.setFocusable(false);

        // Adding elements to panels.
        this.welcomePanel.add(welcomeText);
        this.usernamePanel.add(usernameLabel);
        this.usernamePanel.add(username);
        this.passwordPanel.add(passwordLabel);
        this.passwordPanel.add(password);
        this.buttonPanel.add(registerButton);
        this.buttonPanel.add(loginButton);
        this.registerButton.addActionListener(this);
        this.loginButton.addActionListener(this);

        // Adding panels to page.
        this.add(welcomePanel);
        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(buttonPanel);

        // Set visibility to page.
        this.setVisible(true);
    }

    /**
     * Gets events
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        UserRestClient userRestClient = new UserRestClient(); // Get the User Rest Client which will be sending the requests.

        if(e.getSource() == registerButton){ // If the event was on the register button.
            try {
                User user = new User(null, username.getText(), new String(password.getPassword())); // Create the user entity.

                var isCreated = userRestClient.register(user); // Send the request and depending on the return value show dialog window.
                if (isCreated) {
                    JOptionPane.showMessageDialog(null, "Successful! Now you can Log in!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something went wrong! Try again!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE); // Check the password and username validity.
            }
        }

        if(e.getSource() == loginButton){ // If the event was on the login button.
            try {
                User user = new User(null, username.getText(), new String(password.getPassword())); // Create the user entity.

                var loggedInUser = userRestClient.login(user); // Send the request and depending on the return value show dialog window or open new page.
                if (loggedInUser != null) {
                    this.dispose();
                    // TODO: Open new window
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something went wrong! Try again!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE); // Check the password and username validity.
            }
        }
    }
}
