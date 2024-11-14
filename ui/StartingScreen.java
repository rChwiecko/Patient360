import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingScreen extends JFrame {

    public StartingScreen() {
        // Set up the frame
        setTitle("Starting Screen");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with BoxLayout to center components vertically and horizontally
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));  // Stack components vertically
        mainPanel.setBackground(Color.WHITE);  // Set the background of this panel to white

        // Create a panel for the logo (using BorderLayout to position it in the top-left)
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(null);  // Use null layout so we can set bounds manually
        logoPanel.setBackground(Color.WHITE);  // Set background to white for consistency

        // Load and scale the logo image
        ImageIcon image1 = new ImageIcon("ui/imgs/Patient360Logo.png");
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage);  // Set the resized image back to ImageIcon
        JLabel imageLabel = new JLabel(image1);  // Add the image icon to a JLabel

        // Set the bounds of the imageLabel to position it in the top-left corner
        imageLabel.setBounds(20, 20, image1.getIconWidth(), image1.getIconHeight());

        // Add the image label to the logoPanel
        logoPanel.add(imageLabel);  // Position logo at top-left of the logo panel

        // Create a welcome label
        JLabel welcomeLabel = new JLabel("<html>Welcome, NAME<br>Patient360 offers.....Where would you like to start?\n</html>", SwingConstants.CENTER);
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

        // Create square buttons with labels (smaller size)
        JButton bookAppointmentButton = createButton("Book an Appointment");
        JButton viewAvailabilityButton = createButton("View Doctor Availability");
        JButton accessDatabaseButton = createButton("Access Patient Database");
        JButton checkInPatientButton = createButton("Check-in Patient");

        // Add action listeners to buttons
        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Book Appointment screen
                dispose();
                new BookAppointmentScreen();
            }
        });

        viewAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the View Doctor Availability screen
                dispose();
                new DoctorAvailabilityScreen();
            }
        });

        accessDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Access Patient Database screen
                dispose();
                new PatientDatabaseScreen();
            }
        });

        checkInPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Check-in Patient screen
                dispose();
                new CheckInPatientScreen();
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(bookAppointmentButton);
        buttonPanel.add(viewAvailabilityButton);
        buttonPanel.add(accessDatabaseButton);
        buttonPanel.add(checkInPatientButton);

        // Add the button panel to the main panel
        mainPanel.add(buttonPanel);

        // Add the main panel to the frame's content pane
        getContentPane().add(mainPanel);

        // Make the window visible
        setVisible(true);
    }

    // Helper method to create a square button with a label (smaller size)
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 150));  // Set smaller size for the buttons (150x150)
        button.setFont(new Font("Poppins", Font.PLAIN, 13));  // Set font for button text
        button.setBackground(Color.WHITE);  // Set background color for the buttons
        button.setFocusPainted(false);  // Remove focus border
        return button;
    }

    // The main method to test the StartingScreen independently
    public static void main(String[] args) {
        new StartingScreen();  // Open the starting screen directly for testing
    }
}
