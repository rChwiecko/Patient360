package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookAppointmentScreen extends JFrame {

    // List of doctors and their availability (boolean matrix)
    private String[] doctors = {"Dr. Smith", "Dr. Johnson", "Dr. Williams", "Dr. Brown"};
    private boolean[][] availability = {
        {true, true, false, true, false, true, false, true, true, false, true, true, false, false, true},  // Dr. Smith
        {false, true, true, true, false, true, true, false, true, false, true, false, true, true, true},  // Dr. Johnson
        {true, true, true, false, false, false, true, true, true, true, false, false, true, false, true},  // Dr. Williams
        {false, false, false, true, true, true, false, false, false, false, false, false, true, true, false}  // Dr. Brown
    };

    private JPanel availabilityPanel;

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
        JLabel label = new JLabel("Select a time slot available");
        label.setFont(new Font("Arial", Font.PLAIN, 20));  // Increase font size for visibility
        label.setHorizontalAlignment(SwingConstants.CENTER);  // Center align the label text
        mainPanel.add(label);  // Add the label to the main panel

        // Create a split pane for doctor list and availability grid
        JSplitPane splitPane = createSplitPane();
        mainPanel.add(splitPane);

        // Add back button
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

    private JSplitPane createSplitPane() {
        // Create the left panel with the doctor list
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String doctor : doctors) {
            model.addElement(doctor);
        }
        JList<String> doctorList = new JList<>(model);
        doctorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doctorList.setSelectedIndex(0);
        doctorList.addListSelectionListener(e -> updateAvailability());

        JScrollPane doctorListScrollPane = new JScrollPane(doctorList);
        doctorListScrollPane.setPreferredSize(new Dimension(200, 0));

        // Create the right panel for availability
        availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new GridLayout(0, 15));  // Use GridLayout to arrange time slots
        updateAvailability();  // Initial update for the first doctor

        // Split the panels horizontally
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, doctorListScrollPane, new JScrollPane(availabilityPanel));
        splitPane.setDividerLocation(200); // Set divider location to give space for the list

        return splitPane;
    }

    private void updateAvailability() {
        availabilityPanel.removeAll();
        int selectedDoctorIndex = 0;  // Default to first doctor, will update dynamically on selection

        // Create time slots (30 min increments)
        for (int i = 0; i < 15; i++) {
            String time = String.format("%02d:%02d", 9 + i / 2, (i % 2) * 30);
            JLabel timeLabel = new JLabel(time, SwingConstants.CENTER);
            timeLabel.setPreferredSize(new Dimension(60, 30));  // Size of the time label
            availabilityPanel.add(timeLabel);  // Add time label to the grid
        }

        // Create availability blocks (blue for available, grey for unavailable)
        for (boolean available : availability[selectedDoctorIndex]) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(30, 30));  // Size of the availability button
            if (available) {
                button.setBackground(Color.BLUE);  // Blue if available
            } else {
                button.setBackground(Color.GRAY);  // Gray if not available
            }
            button.setEnabled(available);  // Disable button if not available
            availabilityPanel.add(button);  // Add button to the grid
        }

        availabilityPanel.revalidate();
        availabilityPanel.repaint();  // Refresh the panel to reflect changes
    }
    /* 
    public static void main(String[] args) 
    {
        new BookAppointmentScreen();
    }*/
}
