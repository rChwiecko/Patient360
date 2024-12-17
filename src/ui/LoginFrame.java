package ui;

import api.controller.PatientController;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private static JLabel usernameLabel = new JLabel("Username:");
    private static JTextField textbox = new JTextField(15);
    private static JLabel passwordLabel = new JLabel("Password:");
    private static JTextField textbox2 = new JTextField(15);
    private static JButton usernameVerifyButton = new JButton("Verify");

    private String password = "CS3307";
    private String username = "Recep1";

    // Variables to store input values
    private String enteredUsername = "";
    private String enteredPassword = "";

    // Declare patientController as a class field
    private PatientController patientController;

    public LoginFrame(PatientController patientController) {
        this.patientController = patientController;
    }

    public void initialize() {
        // Set up the frame
        setTitle("Patient360");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Set the main panel's background color to white
        mainPanel.setBackground(Color.WHITE);

        // Create a new panel just for the centered components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        // Load the image icon
        ImageIcon image1 = new ImageIcon(getClass().getResource("/ui/imgs/Patient360Logo.png"));
        ImageIcon image2 = new ImageIcon(getClass().getResource("/ui/imgs/profilepic.png"));


        // Resize the images
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(image1);
        imageLabel.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());

        Image scaledImage2 = image2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        image2 = new ImageIcon(scaledImage2);
        JLabel imageLabel2 = new JLabel(image2);

        // GridBagConstraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        centerPanel.add(imageLabel2, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        centerPanel.add(usernameLabel, gbc);
        gbc.gridx++;
        centerPanel.add(textbox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx++;
        centerPanel.add(textbox2, gbc);

        gbc.gridy++;
        centerPanel.add(usernameVerifyButton, gbc);

        mainPanel.add(imageLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        // ActionListener to capture input
        usernameVerifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Capture values from input fields
                enteredUsername = textbox.getText().trim();
                enteredPassword = textbox2.getText().trim();


                // Validation
                if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new StartingScreen(patientController);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    textbox.setText(""); // Clear username field
                    textbox2.setText(""); // Clear password field
                }
            }
        });

        setVisible(true);
    }
}
