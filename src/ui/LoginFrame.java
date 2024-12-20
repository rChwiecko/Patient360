package ui;

import api.controller.PatientController;  // Import the PatientController to manage the patient's data
import java.awt.*;  // Import AWT classes for GUI components and layout management
import java.awt.event.*;  // Import AWT event handling classes for user interaction
import javax.swing.*;  // Import Swing components for creating the GUI

public class LoginFrame extends JFrame {  // Define the LoginFrame class, inheriting from JFrame for the window
    private static JLabel usernameLabel = new JLabel("Username:");  // Label for the username input field
    private static JTextField textbox = new JTextField(15);  // TextField to capture username input (15 characters wide)
    private static JLabel passwordLabel = new JLabel("Password:");  // Label for the password input field
    private static JTextField textbox2 = new JTextField(15);  // TextField to capture password input (15 characters wide)
    private static JButton usernameVerifyButton = new JButton("Verify");  // Button to trigger username verification

    private String password = "CS3307";  // Default password for login
    private String username = "Recep1";  // Default username for login

    // Variables to store the entered input values
    private String enteredUsername = "";  // Variable to store entered username
    private String enteredPassword = "";  // Variable to store entered password

    // Declare patientController as a class field for managing patient data
    private PatientController patientController;

    public LoginFrame(PatientController patientController) {  // Constructor for LoginFrame, accepts a PatientController object
        this.patientController = patientController;  // Initialize the patientController field with the provided object
    }

    public void initialize() {  // Method to set up the GUI and its components
        // Set up the frame properties
        setTitle("Patient360");  // Set the title of the login window to "Patient360"
        setSize(1900, 650);  // Set the window size to 1900px by 650px
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set the default close operation to exit the application

        // Create the main panel with BorderLayout to manage components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());  // Set the layout manager for the main panel to BorderLayout

        // Set the background color of the main panel to white
        mainPanel.setBackground(Color.WHITE);

        // Create a new panel for the center, which will hold the login components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());  // Set the layout manager for the center panel to GridBagLayout
        centerPanel.setBackground(Color.WHITE);  // Set the background color of the center panel to white

        // Load the image icons for the logo and profile picture
        ImageIcon image1 = new ImageIcon(getClass().getResource("/ui/imgs/Patient360Logo.png"));  // Load the Patient360 logo
        ImageIcon image2 = new ImageIcon(getClass().getResource("/ui/imgs/profilepic.png"));  // Load the profile picture

        // Resize the images to fit the design
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);  // Scale the first image (logo)
        image1 = new ImageIcon(scaledImage);  // Update the image icon with the scaled image
        JLabel imageLabel = new JLabel(image1);  // Create a JLabel to display the logo image
        imageLabel.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());  // Set bounds for the logo image label

        // Resize the second image (profile picture)
        Image scaledImage2 = image2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);  // Scale the profile picture
        image2 = new ImageIcon(scaledImage2);  // Update the image icon with the scaled image
        JLabel imageLabel2 = new JLabel(image2);  // Create a JLabel to display the profile picture

        // Create a GridBagConstraints object to manage the layout in GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;  // Set the horizontal position of the component (0 for first column)
        gbc.gridy = 0;  // Set the vertical position of the component (0 for first row)
        gbc.insets = new Insets(10, 10, 10, 10);  // Set the insets (padding) around the component
        gbc.gridwidth = 2;  // Set the component to span two columns
        gbc.anchor = GridBagConstraints.CENTER;  // Align the component to the center

        // Add the profile image label to the center panel
        centerPanel.add(imageLabel2, gbc);

        // Move to the next row
        gbc.gridy++;  
        gbc.gridwidth = 1;  // Set gridwidth back to 1 (single column)
        centerPanel.add(usernameLabel, gbc);  // Add the username label to the panel
        gbc.gridx++;  // Move to the next column
        centerPanel.add(textbox, gbc);  // Add the username text field to the panel

        // Move to the next row
        gbc.gridy++;  
        gbc.gridx = 0;  // Reset to first column
        centerPanel.add(passwordLabel, gbc);  // Add the password label to the panel
        gbc.gridx++;  // Move to the next column
        centerPanel.add(textbox2, gbc);  // Add the password text field to the panel

        // Move to the next row
        gbc.gridy++;  
        centerPanel.add(usernameVerifyButton, gbc);  // Add the verify button to the panel

        // Add the logo image label to the main panel at the top (NORTH)
        mainPanel.add(imageLabel, BorderLayout.NORTH);
        // Add the center panel to the main panel in the center region
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add the main panel to the content pane of the frame
        getContentPane().add(mainPanel);

        // Set up the action listener for the username verification button
        usernameVerifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  // Event triggered when the button is clicked
                // Capture the values from the input fields
                enteredUsername = textbox.getText().trim();  // Get the entered username and remove leading/trailing spaces
                enteredPassword = textbox2.getText().trim();  // Get the entered password and remove leading/trailing spaces

                // Validate the entered credentials
                if (enteredUsername.equals(username) && enteredPassword.equals(password)) {  // Check if the entered credentials match the correct ones
                    // If valid, show a success message and navigate to the starting screen
                    JOptionPane.showMessageDialog(null, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();  // Close the login frame
                    new StartingScreen(patientController);  // Create and show the StartingScreen window
                } else {  // If invalid credentials, show an error message
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    textbox.setText("");  // Clear the username field
                    textbox2.setText("");  // Clear the password field
                }
            }
        });

        setVisible(true);  // Make the login window visible to the user
    }
}
