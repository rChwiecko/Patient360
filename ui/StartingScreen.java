import javax.swing.*;
import java.awt.*;

public class StartingScreen extends JFrame 
{

    public StartingScreen() 
    {
        // Set up the frame
        setTitle("Starting Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label to show on the starting screen
        JLabel welcomeLabel = new JLabel("Welcome to the Starting Screen!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Add the label to the frame
        add(welcomeLabel, BorderLayout.CENTER);

        // Make the window visible
        setVisible(true);
    }

    // The main method to test the StartingScreen independently
    public static void main(String[] args) 
    {
        new StartingScreen();  // Open the starting screen directly for testing
    }
}

