import javax.swing.*;
import java.awt.*;

public class BookAppointmentScreen extends JFrame {

    public BookAppointmentScreen() {
        // Set up the frame
        setTitle("Book an Appointment");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));  // Stack components vertically
        mainPanel.setBackground(Color.WHITE);  // Set the background of this panel to white

        // Create the logo panel (using null layout to position logo manually)
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(null);  // Use null layout to manually position the logo
        logoPanel.setBackground(Color.WHITE);  // Set background to white for consistency

        // Load and scale the logo image
        ImageIcon image1 = new ImageIcon("ui/imgs/Patient360Logo.png");
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage);  // Set the resized image back to ImageIcon

        // Add the image icon to a JLabel and position it
        JLabel imageLabel = new JLabel(image1);
        imageLabel.setBounds(20, 20, image1.getIconWidth(), image1.getIconHeight());  // Position logo at the top-left corner

        // Add the logo to the logoPanel
        logoPanel.add(imageLabel);

        // Add logo panel to the main panel
        mainPanel.add(logoPanel);

        // Add some vertical spacing between logo and text
        mainPanel.add(Box.createVerticalStrut(20));

        // Create a label to indicate this is the Book Appointment screen
        JLabel label = new JLabel("This is the Book Appointment screen.");
        label.setFont(new Font("Arial", Font.PLAIN, 20));  // Increase font size for visibility
        label.setHorizontalAlignment(SwingConstants.CENTER);  // Center align the label text
        mainPanel.add(label);  // Add the label to the main panel

        // Create a back button and add action listener
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen();  // Go back to the starting screen
        });

        // Add back button to the panel at the bottom
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);

        // Add backButtonPanel to the main panel
        mainPanel.add(backButtonPanel);

        // Add the main panel to the content pane
        getContentPane().add(mainPanel);

        // Make the window visible
        setVisible(true);
    }

    public static void main(String[] args) {
        new BookAppointmentScreen();
    }
}
