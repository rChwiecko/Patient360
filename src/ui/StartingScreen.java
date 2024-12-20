package ui;

import api.controller.PatientController;  // Import PatientController to manage patient data
import java.awt.*;  // Import AWT classes for GUI components and layout management
import java.awt.event.ActionEvent;  // Import ActionEvent class for handling button clicks
import java.awt.event.ActionListener;  // Import ActionListener interface for listening to button events
import javax.swing.*;  // Import Swing components for creating the GUI

public class StartingScreen extends JFrame {  // Define the StartingScreen class, inheriting from JFrame for the window
    private PatientController patientController;  // Declare the PatientController to manage patient data
    private JTextField username;  // Declare a JTextField for the username (although it's not used in this code)

    public StartingScreen(PatientController patientController) {  // Constructor to initialize the screen with PatientController
        //this.username = username;  // Commented out, not used in the constructor

        // Set up the frame
        setTitle("Starting Screen");  // Set the title of the starting screen window
        setSize(1900, 650);  // Set the window size to 1900px by 650px
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set the default close operation to exit the application

        // Create the main panel with BoxLayout to center components vertically and horizontally
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));  // Stack components vertically using BoxLayout
        mainPanel.setBackground(Color.WHITE);  // Set the background color of the main panel to white

        // Create a panel for the logo (using null layout to position it in the top-left)
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(null);  // Use null layout to manually set the position of the logo
        logoPanel.setBackground(Color.WHITE);  // Set the background of the logo panel to white for consistency

        // Load and scale the logo image
        ImageIcon image1 = new ImageIcon(getClass().getResource("/ui/imgs/Patient360Logo.png"));  // Load the logo image
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);  // Scale the image to fit the design
        image1 = new ImageIcon(scaledImage);  // Set the scaled image back into ImageIcon
        JLabel imageLabel = new JLabel(image1);  // Create a JLabel to display the logo image

        // Set the bounds of the imageLabel to position it in the top-left corner
        imageLabel.setBounds(20, 20, image1.getIconWidth(), image1.getIconHeight());  // Set the position and size of the logo image

        // Add the image label to the logoPanel
        logoPanel.add(imageLabel);  // Add the logo to the logo panel at the top-left

        // Create a welcome label with dynamic first name of the receptionist
        JLabel welcomeLabel = new JLabel("Welcome " + patientController.getReceptionist().getFirstName() + ", Where would you like to start?", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 15));  // Set the font of the welcome message
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the text horizontally

        // Add the logoPanel and the welcome label to the mainPanel
        mainPanel.add(logoPanel);  // Add the logo panel to the main panel
        mainPanel.add(Box.createVerticalStrut(20));  // Add some space between the logo and the welcome label
        mainPanel.add(welcomeLabel);  // Add the welcome label to the main panel

        // Create a panel for the buttons (GridLayout to arrange them in a grid)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));  // 2x2 grid layout with gaps between buttons
        buttonPanel.setBackground(Color.WHITE);  // Set the background color of the button panel to white

        // Create square buttons with labels and custom sizes
        JButton bookAppointmentButton = createButton("Book an Appointment", 200, 70);  // Create "Book an Appointment" button
        JButton viewAvailabilityButton = createButton("View Doctor Availability", 200, 70);  // Create "View Doctor Availability" button
        JButton accessDatabaseButton = createButton("Access Patient Database", 200, 70);  // Create "Access Patient Database" button
        JButton checkInPatientButton = createButton("Check-in/out Patient", 200, 70);  // Create "Check-in/out Patient" button

        // Add action listeners to buttons
        bookAppointmentButton.addActionListener(new ActionListener() {  // Action listener for the Book Appointment button
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Book Appointment screen
                dispose();  // Close the current screen
                new BookAppointmentScreen(patientController);  // Open the Book Appointment screen
            }
        });

        viewAvailabilityButton.addActionListener(new ActionListener() {  // Action listener for the View Doctor Availability button
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the View Doctor Availability screen
                dispose();  // Close the current screen
                new DoctorAvailabilityScreen(patientController);  // Open the Doctor Availability screen
            }
        });

        accessDatabaseButton.addActionListener(new ActionListener() {  // Action listener for the Access Patient Database button
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Access Patient Database screen
                dispose();  // Close the current screen
                new PatientDatabaseScreen(patientController);  // Open the Patient Database screen
            }
        });

        checkInPatientButton.addActionListener(e -> {  // Action listener for the Check-In/Out Patient button
            // Prompt the user to choose between Check-In and Check-Out
            Object[] options = {"Check-In", "Check-Out"};  // Define the options for the dialog
            int choice = JOptionPane.showOptionDialog(
                this,  // This is the parent component
                "Would you like to Check-In or Check-Out a patient?",  // Message in the dialog
                "Check-In/Out Patient",  // Dialog title
                JOptionPane.YES_NO_OPTION,  // Option type (Yes/No)
                JOptionPane.QUESTION_MESSAGE,  // Message type (question)
                null,  // No custom icon
                options,  // Options for the dialog (Check-In/Check-Out)
                options[0]  // Default option (Check-In)
            );

            if (choice == JOptionPane.YES_OPTION) {  // If the user chose Check-In
                dispose();  // Close the current screen
                new CheckInPatientScreen(patientController);  // Open the Check-In screen
            } else if (choice == JOptionPane.NO_OPTION) {  // If the user chose Check-Out
                dispose();  // Close the current screen
                new CheckOutPatientScreen(patientController);  // Open the Check-Out screen
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(bookAppointmentButton);  // Add the "Book an Appointment" button
        buttonPanel.add(viewAvailabilityButton);  // Add the "View Doctor Availability" button
        buttonPanel.add(accessDatabaseButton);  // Add the "Access Patient Database" button
        buttonPanel.add(checkInPatientButton);  // Add the "Check-in/out Patient" button

        // Add the button panel to the main panel
        mainPanel.add(buttonPanel);  // Add the button panel to the main panel

        // Add the "Back" button below the button panel
        JButton quitButton = createButton("Quit", 150, 50);  // Create a smaller "Quit" button
        quitButton.addActionListener(new ActionListener() {  // Action listener for the "Quit" button
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action when the "Quit" button is clicked
                dispose();  // Close the current screen
                new StartingScreen(patientController);  // Open the starting screen again
                System.exit(ABORT);  // Exit the application
            }
        });

        // Create a panel for the back button (aligned to the bottom-left corner)
        JPanel quitButtonPanel = new JPanel();
        quitButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Align to the left
        quitButtonPanel.setBackground(Color.WHITE);  // Set background color to white for consistency

        // Add the "Back" button to the panel
        quitButtonPanel.add(quitButton);  // Add the Quit button to the quit button panel

        // Add the quitButtonPanel to the main panel, at the bottom
        mainPanel.add(quitButtonPanel);  // Add the quit button panel to the main panel

        // Add the main panel to the frame's content pane
        getContentPane().add(mainPanel);  // Add the main panel to the frame

        // Make the window visible
        setVisible(true);  // Show the starting screen window
    }

    // Helper method to create a button with a label and a custom size
    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);  // Create a button with the given text
        button.setPreferredSize(new Dimension(width, height));  // Set the custom size for the button
        button.setFont(new Font("Poppins", Font.PLAIN, 16));  // Set font for the button text
        button.setBackground(Color.WHITE);  // Set the background color of the button
        button.setFocusPainted(false);  // Remove focus border when the button is clicked
        return button;  // Return the created button
    }
}
