package com.revikz.winebook.post;

import com.revikz.winebook.user.User;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

@Component
public class ForumView extends JFrame {

    private DefaultListModel<Post> listModel;
    private JList<Post> itemList;
    private JLabel titleLabel;
    private JButton createButton;
    private JButton deleteButton;
    private PostRestClient postRestClient;

    public ForumView() {
        // Set up the frame
        setTitle("Item List Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Create postRestClient
        postRestClient = new PostRestClient();

        // Create the title label
        titleLabel = new JLabel("Items List");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Create the list model and list
        listModel = new DefaultListModel<>();
        listModel.addAll(postRestClient.getAll());
        itemList = new JList<>(listModel);
        add(new JScrollPane(itemList), BorderLayout.CENTER);

        // Create the panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create the "Create" button
        createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(ForumView.this, "Enter a new title:");
                String content = JOptionPane.showInputDialog(ForumView.this, "Write the content:");
                if (title != null && !title.trim().isEmpty()) {
                    System.out.println(title);
                    System.out.println(content);
                    Post createdPost = new Post(null, title, content, 0, 0);
                    postRestClient.create(createdPost); // Send to db
                    Post lastPost = postRestClient.getLast();
                    listModel.addElement(lastPost); // Add to list in view
                }
            }
        });
        buttonPanel.add(createButton);

        // Create the "Delete" button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = itemList.getSelectedIndex();
                int selectedId = itemList.getSelectedValue().id();
                if (selectedIndex != -1) {
                    postRestClient.delete(selectedId);
                    listModel.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(ForumView.this, "No item selected to delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(deleteButton);

        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);
    }

}
