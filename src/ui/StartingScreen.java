package ui;

import api.controller.PatientController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartingScreen extends JFrame {
    private PatientController patientController;
    private JTextField username;

    public StartingScreen(PatientController patientController) 
    {
        // Set up the frame
        setTitle("Starting Screen");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with BoxLayout to center components vertically and horizontally
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));  // Stack components vertically
        mainPanel.setBackground(Color.WHITE);  // Set the background of this panel to white

        // Create a panel for the logo (using null layout to position it in the top-left)
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(null);  // Use null layout so we can set bounds manually
        logoPanel.setBackground(Color.WHITE);  // Set background to white for consistency

        // Load and scale the logo image
        ImageIcon image1 = new ImageIcon("src/ui/imgs/Patient360Logo.png");
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage);  // Set the resized image back to ImageIcon
        JLabel imageLabel = new JLabel(image1);  // Add the image icon to a JLabel

        // Set the bounds of the imageLabel to position it in the top-left corner
        imageLabel.setBounds(20, 20, image1.getIconWidth(), image1.getIconHeight());

        // Add the image label to the logoPanel
        logoPanel.add(imageLabel);  // Position logo at top-left of the logo panel

        // Create a welcome label
        JLabel welcomeLabel = new JLabel("<html>Welcome,"+username.getText() + "<br>Where would you like to start?</html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 15));  // Increased font size for better visibility
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the text horizontally

        // Add the logoPanel and the welcome label to the mainPanel
        mainPanel.add(logoPanel);  // Add the logo panel (which has the logo)
        mainPanel.add(Box.createVerticalStrut(20));  // Add some space between the image and the text
        mainPanel.add(welcomeLabel);  // Add the welcome label

        // Create a panel for the buttons (GridLayout to arrange them in a grid)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));  // 2x2 grid layout with gaps between buttons
        buttonPanel.setBackground(Color.WHITE);

        // Create square buttons with labels (larger size now)
        JButton bookAppointmentButton = createButton("Book an Appointment", 200, 70);
        JButton viewAvailabilityButton = createButton("View Doctor Availability", 200, 70);
        JButton accessDatabaseButton = createButton("Access Patient Database", 200, 70);
        JButton checkInPatientButton = createButton("Check-in/out Patient", 200, 70);

        // Add action listeners to buttons
        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Book Appointment screen
                dispose();
                new BookAppointmentScreen(patientController);
            }
        });

        viewAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the View Doctor Availability screen
                dispose();
                new DoctorAvailabilityScreen(patientController);
            }
        });

        accessDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Access Patient Database screen
                dispose();
                new PatientDatabaseScreen(patientController);
            }
        });

        checkInPatientButton.addActionListener(e -> {
            // Prompt the user to choose between Check-In and Check-Out
            Object[] options = {"Check-In", "Check-Out"};
            int choice = JOptionPane.showOptionDialog(
                this,
                "Would you like to Check-In or Check-Out a patient?",
                "Check-In/Out Patient",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == JOptionPane.YES_OPTION) {
                // User chose Check-In
                dispose();
                new CheckInPatientScreen(patientController);
            } else if (choice == JOptionPane.NO_OPTION) {
                // User chose Check-Out
                dispose();
                new CheckOutPatientScreen(patientController);
            }
        });
        // Add buttons to the button panel
        buttonPanel.add(bookAppointmentButton);
        buttonPanel.add(viewAvailabilityButton);
        buttonPanel.add(accessDatabaseButton);
        buttonPanel.add(checkInPatientButton);

        // Add the button panel to the main panel
        mainPanel.add(buttonPanel);

        // Add the "Back" button below the button panel
        JButton quitButton = createButton("Quit", 150, 50);  // Smaller size for back button
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action when the back button is clicked
                dispose();  // Close the current screen
                new StartingScreen(patientController);  // Open the starting screen again (or navigate to another screen)
                System.exit(ABORT);
            }
        });

        // Create a panel for the back button (aligned to the bottom-left corner)
        JPanel quitButtonPanel = new JPanel();
        quitButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Align to the left
        quitButtonPanel.setBackground(Color.WHITE);  // Set the background color to white for consistency

        // Add the "Back" button to the panel
        quitButtonPanel.add(quitButton);

        // Add the quitButtonPanel to the main panel, at the bottom
        mainPanel.add(quitButtonPanel);  // Add this panel below the button panel

        // Add the main panel to the frame's content pane
        getContentPane().add(mainPanel);

        // Make the window visible
        setVisible(true);
    }

    // Helper method to create a button with a label and a custom size
    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));  // Set custom size for the button
        button.setFont(new Font("Poppins", Font.PLAIN, 16));  // Set font for button text (slightly larger)
        button.setBackground(Color.WHITE);  // Set background color for the buttons
        button.setFocusPainted(false);  // Remove focus border
        return button;
    }
}
/*
    // The main method to test the StartingScreen independently
    public static void main(String[] args) {
        new StartingScreen();  // Open the starting screen directly for testing
    }
} */