package ui;

import api.controller.PatientController;
import api.models.Doctor;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.swing.*;

public class DoctorAvailabilityScreen extends JFrame {
    private final PatientController patientController;  // A reference to the PatientController
    private JPanel availabilityPanel;  // Panel to display doctor's availability

    // Constructor to initialize the screen and setup the UI
    public DoctorAvailabilityScreen(PatientController patientController) {
        this.patientController = patientController;  // Assign the PatientController
        setTitle("View Doctor's Availability");  // Set window title
        setSize(1900, 400);  // Set size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the application when the window is closed

        JPanel mainPanel = new JPanel();  // Panel to hold the components
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));  // Layout manager
        mainPanel.setBackground(Color.WHITE);  // Set background color

        JLabel label = new JLabel("Doctor's Availability");  // Title label
        label.setFont(new Font("Arial", Font.PLAIN, 20));  // Set font for the label
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the label
        mainPanel.add(Box.createVerticalStrut(20));  // Add vertical space
        mainPanel.add(label);  // Add label to mainPanel

        availabilityPanel = new JPanel();  // Panel for doctor availability grid
        availabilityPanel.setBackground(Color.WHITE);  // Set background color

        // Prompt user to select a doctor
        Doctor selectedDoctor = selectDoctor();  // Get selected doctor
        if (selectedDoctor == null) {  // If no doctor is selected, exit the screen
            dispose();  // Close the current screen
            return;
        }

        // Build the availability table for the selected doctor
        buildAvailabilityTable(selectedDoctor);

        JScrollPane scrollPane = new JScrollPane(availabilityPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  // Add scroll pane for availabilityPanel
        mainPanel.add(scrollPane);  // Add scroll pane to the main panel

        // Back button to return to the previous screen
        JButton backButton = new JButton("Back");  // Back button
        backButton.setPreferredSize(new Dimension(150, 50));  // Set button size
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font for the button
        backButton.addActionListener(e -> {
            dispose();  // Close current screen
            new StartingScreen(patientController);  // Go back to the starting screen
        });

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Panel for back button
        backButtonPanel.setBackground(Color.WHITE);  // Set background color for the panel
        backButtonPanel.add(backButton);  // Add back button to the panel
        mainPanel.add(Box.createVerticalStrut(20));  // Add vertical space
        mainPanel.add(backButtonPanel);  // Add back button panel to the main panel

        getContentPane().add(mainPanel);  // Add the mainPanel to the content pane
        setVisible(true);  // Make the frame visible
    }

    // Method to build the table showing doctor's availability
    private void buildAvailabilityTable(Doctor selectedDoctor) {
        // Prompt user for date selection via a dialog
        JPanel datePanel = new JPanel(new FlowLayout());  // Panel for date selection
        LocalDate today = LocalDate.now();  // Get today's date
        int currentYear = today.getYear();  // Get the current year
        int currentMonth = today.getMonthValue();  // Get the current month
        int currentDay = today.getDayOfMonth();  // Get the current day
    
        JComboBox<Integer> yearCombo = new JComboBox<>(new Integer[]{currentYear, currentYear + 1});  // Combo box for year selection
        yearCombo.setSelectedItem(currentYear);  // Default to current year
    
        JComboBox<Integer> monthCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});  // Combo box for month selection
        monthCombo.setSelectedItem(currentMonth);  // Default to current month
    
        JComboBox<Integer> dayCombo = new JComboBox<>(generateDays(1, 31));  // Combo box for day selection
        dayCombo.setSelectedItem(currentDay);  // Default to current day
    
        datePanel.add(new JLabel("Year:"));  // Label for year
        datePanel.add(yearCombo);  // Add year combo box to the panel
        datePanel.add(new JLabel("Month:"));  // Label for month
        datePanel.add(monthCombo);  // Add month combo box to the panel
        datePanel.add(new JLabel("Day:"));  // Label for day
        datePanel.add(dayCombo);  // Add day combo box to the panel
    
        int result = JOptionPane.showConfirmDialog(
                this,
                datePanel,
                "Select a Date",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    
        if (result != JOptionPane.OK_OPTION) {  // If user clicks cancel, exit the method
            return;
        }
    
        int year = (int) yearCombo.getSelectedItem();  // Get selected year
        int month = (int) monthCombo.getSelectedItem();  // Get selected month
        int day = (int) dayCombo.getSelectedItem();  // Get selected day
    
        // Construct the selected date
        LocalDate selectedDate = LocalDate.of(year, month, day);
    
        // Define the time slots
        LocalTime startTime = LocalTime.of(9, 0);  // Start at 9:00 AM
        int slots = 15;  // Number of time slots
        Duration slotDuration = Duration.ofMinutes(30);  // Duration of each time slot
    
        // Total columns: 1 for time labels + 15 for time slots
        int cols = 1 + slots;
        // Rows: 1 for time labels + 1 for the doctor
        int rows = 1 + 1;  // Only one doctor
    
        availabilityPanel.removeAll();  // Clear the panel if it has any components
        availabilityPanel.setLayout(new GridLayout(rows, cols, 5, 5));  // Set grid layout for the panel
    
        // First row (time labels)
        availabilityPanel.add(new JLabel(""));  // top-left corner blank cell
        for (int i = 0; i < slots; i++) {  // Loop to add time labels
            LocalTime slotTime = startTime.plus(slotDuration.multipliedBy(i));  // Calculate the time for each slot
            String timeStr = String.format("%02d:%02d", slotTime.getHour(), slotTime.getMinute());  // Format time
            JLabel timeLabel = new JLabel(timeStr, SwingConstants.CENTER);  // Create label for the time
            timeLabel.setFont(new Font("Arial", Font.BOLD, 12));  // Set font for time labels
            availabilityPanel.add(timeLabel);  // Add time label to the panel
        }
    
        // Row for the selected doctor
        JLabel docLabel = new JLabel(selectedDoctor.getFirstName().substring(0, 1) + ". " + selectedDoctor.getLastName(), SwingConstants.CENTER);  // Create label with doctor's name
        docLabel.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font for doctor's name
        availabilityPanel.add(docLabel);  // Add doctor label to the panel
    
        // Add the time slots for the selected doctor
        for (int i = 0; i < slots; i++) {
            LocalTime slotTime = startTime.plus(slotDuration.multipliedBy(i));  // Calculate the slot time
            LocalDateTime fullDateTime = LocalDateTime.of(selectedDate, slotTime);  // Combine date and time
    
            boolean available = selectedDoctor.isAvailable(fullDateTime, slotDuration);  // Check if the doctor is available for this slot
    
            JButton button = new JButton(available ? "Available" : "Unavailable");  // Create button for time slot
            button.setBackground(available ? Color.BLUE : Color.GRAY);  // Set button color based on availability
            button.setEnabled(false);  // Disable the button (no action can be taken)
            button.setToolTipText(timeString(fullDateTime) + " - " + (available ? "Available" : "Unavailable"));  // Set tooltip text for the button
    
            availabilityPanel.add(button);  // Add the button to the panel
        }
    
        availabilityPanel.revalidate();  // Revalidate the panel to apply changes
        availabilityPanel.repaint();  // Repaint the panel to show changes
    }

    // Method to generate the days of the month
    private Integer[] generateDays(int start, int end) {
        Integer[] days = new Integer[end - start + 1];  // Create an array for the days
        for (int i = 0; i < days.length; i++) {  // Fill the array with day numbers
            days[i] = start + i;
        }
        return days;  // Return the array of days
    }

    // Helper method to format time as a string
    private String timeString(LocalDateTime time) {
        return String.format("%02d:%02d", time.getHour(), time.getMinute());  // Format time in HH:mm format
    }

    // Method to allow the user to select a doctor
    private Doctor selectDoctor() {
        // Fetch all doctors from the controller
        List<Doctor> doctors = patientController.getDoctors();
    
        // Convert the list of doctors to an array of strings for display in the dialog
        String[] doctorNames = doctors.stream()
                .map(doctor -> doctor.getFirstName() + " " + doctor.getLastName())
                .toArray(String[]::new);
    
        // Create a JList to display the list of doctors
        JList<String> doctorList = new JList<>(doctorNames);
        doctorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Allow only one selection
    
        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(doctorList),
                "Select a Doctor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    
        // If user clicked OK and selected a doctor
        if (option == JOptionPane.OK_OPTION && doctorList.getSelectedIndex() >= 0) {
            int selectedIndex = doctorList.getSelectedIndex();  // Get selected index
            return doctors.get(selectedIndex);  // Return the selected doctor
        }
    
        // If user clicked Cancel or no doctor selected, navigate back to the starting screen
        if(option == JOptionPane.OK_CANCEL_OPTION){
            System.out.println("exit");
            dispose();  // Close the current DoctorAvailabilityScreen window
            new StartingScreen(patientController);  // Open the StartingScreen window
        }
        return null;  // Return null if no doctor was selected
    }
}
