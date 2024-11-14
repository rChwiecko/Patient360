import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginFrame extends JFrame
{
    private static JLabel usernameLabel = new JLabel("Username:");
    private static JTextField textbox = new JTextField(15);
    private static JLabel passwordLabel = new JLabel("Password:");
    private static JTextField textbox2 = new JTextField(15);
    private static JButton usernameVerifyButton = new JButton("Verify");

    public void initialize()
    {
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
        centerPanel.setLayout(new GridBagLayout());  // Use GridBagLayout to center components
        centerPanel.setBackground(Color.WHITE);  // Set the background of this panel to white
        
        // Load the image icon
        ImageIcon image1 = new ImageIcon("ui/imgs/Patient360Logo.png");
        ImageIcon image2 = new ImageIcon("ui/imgs/profilepic.png");

        // Resize the image (example: make it smaller, 200x100 px)
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage);  // Set the resized image back to ImageIcon
        JLabel imageLabel = new JLabel(image1);  // Add the image icon to a JLabel
        imageLabel.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());

        Image scaledImage2 = image2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        image2 = new ImageIcon(scaledImage2);  // Set the resized image back to ImageIcon
        JLabel imageLabel2 = new JLabel(image2);  // Add the image icon to a JLabel

        // Create GridBagConstraints to center the components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Centering on the X axis (0 is the first column)
        gbc.gridy = 0; // Start from the top
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components
        gbc.gridwidth = 2; // Make the profile image span two columns (center horizontally)
        gbc.anchor = GridBagConstraints.CENTER; // Align the image in the center

        // Add the profile image above the username label in the centerPanel
        centerPanel.add(imageLabel2, gbc);  // Add profile image at the top of centerPanel

        // Add components to centerPanel using GridBagLayout
        gbc.gridy++;  // Move to the next row (for the username label)
        gbc.gridwidth = 1; // Reset gridwidth to 1 for subsequent components
        centerPanel.add(usernameLabel, gbc);
        gbc.gridx++;  // Move to the next column
        centerPanel.add(textbox, gbc);

        gbc.gridy++;  // Move to the next row (for the password label)
        gbc.gridx = 0; // Reset to the first column
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx++;  // Move to the next column
        centerPanel.add(textbox2, gbc);

        gbc.gridy++;  // Move to the next row (for the verify button)
        centerPanel.add(usernameVerifyButton, gbc);

        // Add the large logo image at the top using BorderLayout.NORTH
        mainPanel.add(imageLabel);

        // Add the centerPanel to the center of the mainPanel
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add the mainPanel to the frame's content pane
        getContentPane().add(mainPanel);

        // Add an ActionListener to the "Verify" button to open the StartingScreen
        usernameVerifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current LoginFrame
                dispose();

                // Open the StartingScreen
                new StartingScreen();  // Open the StartingScreen
            }
        });
        // Set the window to be visible
        setVisible(true);
    }

    // The main method needs to be static to be an entry point
    public static void main(String[] args)
    {
        LoginFrame myFrame = new LoginFrame();
        myFrame.initialize();
    }
}
