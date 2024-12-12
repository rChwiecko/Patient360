package ui;


import java.awt.*;
import javax.swing.*;

public class DoctorAvailabilityScreen extends JFrame {

    public DoctorAvailabilityScreen() {
        // Set up the frame
        setTitle("View Doctor Availability");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // Create the logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(null);
        logoPanel.setBackground(Color.WHITE);

        // Load and scale the logo image
        ImageIcon image1 = new ImageIcon("ui/imgs/Patient360Logo.png");
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(image1);
        imageLabel.setBounds(20, 20, image1.getIconWidth(), image1.getIconHeight());

        logoPanel.add(imageLabel);
        mainPanel.add(logoPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Create a label for the Doctor Availability screen
        JLabel label = new JLabel("This is the View Doctor Availability screen.");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(label);

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen();  // Go back to the starting screen
        });

        // Add back button at the bottom
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);

        mainPanel.add(backButtonPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DoctorAvailabilityScreen();
    }
}
