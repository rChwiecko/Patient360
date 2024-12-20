package ui; // Specifies that this file belongs to the `ui` package, which contains user interface components.

import api.controller.PatientController; // Imports the `PatientController` class for managing patient-related operations.
import api.models.Doctor; // Imports the `Doctor` model, which represents data and behaviors related to doctors.
import api.models.Patient; // Imports the `Patient` model, which represents data and behaviors related to patients.
import java.awt.*; // Imports the `java.awt` package for graphical components, layouts, and windowing features.
import java.time.Duration; // Imports the `Duration` class to handle time intervals (e.g., 30 minutes, 1 hour).
import java.time.LocalDate; // Imports the `LocalDate` class for working with dates (year, month, day).
import java.time.LocalDateTime; // Imports the `LocalDateTime` class to handle both date and time data.
import java.time.LocalTime; // Imports the `LocalTime` class for working with specific times of the day.
import java.util.List; // Imports the `List` interface, used for storing and manipulating ordered collections.
import javax.swing.*; // Imports the `javax.swing` package, which provides classes for building graphical user interfaces.


public class BookAppointmentScreen extends JFrame { // Defines a class extending JFrame to create a GUI for booking appointments.
    private JLabel username; // Label for displaying the username (unused in this snippet).

    private final PatientController patientController; // Reference to the patient controller to manage patient-related operations.
    private JPanel availabilityPanel; // Panel to display available time slots for appointments.
    private Patient selectedPatient; // Variable to store the currently selected patient.

    public BookAppointmentScreen(PatientController patientController) { // Constructor to initialize the screen with the patient controller.
        this.patientController = patientController; // Assigns the provided patient controller to the local variable.
        Patient currPatient = selectPatient(); // Calls a method to select a patient and stores the result.
        if (currPatient == null) { // Checks if no patient was selected.
            dispose(); // Closes the current window.
            new StartingScreen(patientController); // Opens the starting screen if no patient is selected.
            return; // Exits the constructor.
        }
        setTitle("Book an Appointment"); // Sets the title of the window.
        setSize(1900, 650); // Sets the dimensions of the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configures the application to exit when the window is closed.

        JPanel mainPanel = new JPanel(); // Creates a main panel for organizing the layout.
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Sets a vertical box layout for the main panel.
        mainPanel.setBackground(Color.WHITE); // Sets the background color of the main panel to white.

        JLabel label = new JLabel("Select a time slot available"); // Creates a label prompting the user to select a time slot.
        label.setFont(new Font("Arial", Font.PLAIN, 20)); // Sets the font style and size for the label.
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Aligns the label to the center horizontally.
        mainPanel.add(Box.createVerticalStrut(20)); // Adds vertical spacing before the label.
        mainPanel.add(label); // Adds the label to the main panel.
        mainPanel.add(Box.createVerticalStrut(20)); // Adds vertical spacing after the label.

        availabilityPanel = new JPanel(); // Creates a panel to display available time slots.
        availabilityPanel.setBackground(Color.WHITE); // Sets the background color of the availability panel to white.

        boolean res = buildAvailabilityTable(currPatient); // Builds the availability table for the selected patient.
        if (!res) { // Checks if the availability table could not be built (e.g., no available slots).
            dispose(); // Closes the current window.
            new StartingScreen(patientController); // Opens the starting screen.
            return; // Exits the constructor.
        }

        JScrollPane scrollPane = new JScrollPane(availabilityPanel, // Wraps the availability panel in a scroll pane.
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Adds scroll bars as needed.
        mainPanel.add(scrollPane); // Adds the scroll pane to the main panel.

        JButton backButton = new JButton("Back"); // Creates a "Back" button.
        backButton.setPreferredSize(new Dimension(150, 50)); // Sets the preferred size of the button.
        backButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Sets the font style and size for the button.
        backButton.addActionListener(e -> { // Adds an action listener to handle button clicks.
            dispose(); // Closes the current window.
            new StartingScreen(patientController); // Opens the starting screen.
        });

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Creates a panel with left-aligned flow layout for the back button.
        backButtonPanel.setBackground(Color.WHITE); // Sets the background color of the back button panel to white.
        backButtonPanel.add(backButton); // Adds the "Back" button to the panel.

        mainPanel.add(Box.createVerticalStrut(20)); // Adds vertical spacing before the back button panel.
        mainPanel.add(backButtonPanel); // Adds the back button panel to the main panel.

        getContentPane().add(mainPanel); // Adds the main panel to the content pane of the frame.
        setVisible(true); // Makes the frame visible on the screen.
    }

    private boolean buildAvailabilityTable(Patient selectedPatient) { // Method to build the availability table for booking an appointment.
        // Prompt user for date selection via a dialog
        JPanel datePanel = new JPanel(new FlowLayout()); // Creates a new panel with a flow layout for date selection.
        LocalDate today = LocalDate.now(); // Gets today's date using LocalDate.
        int currentYear = today.getYear(); // Extracts the current year from today's date.
        int currentMonth = today.getMonthValue(); // Extracts the current month from today's date.
        int currentDay = today.getDayOfMonth(); // Extracts the current day from today's date.
    
        // Create a JComboBox for selecting the year with current year and next year as options
        JComboBox<Integer> yearCombo = new JComboBox<>(new Integer[] { currentYear, currentYear + 1 });
        yearCombo.setSelectedItem(currentYear); // Default to current year
    
        // Create a JComboBox for selecting the month with all 12 months as options
        JComboBox<Integer> monthCombo = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
        monthCombo.setSelectedItem(currentMonth); // Default to current month
    
        // Create a JComboBox for selecting the day, generating days between 1 and 31
        JComboBox<Integer> dayCombo = new JComboBox<>(generateDays(1, 31));
        dayCombo.setSelectedItem(currentDay); // Default to current day
    
        // Add the labels and combo boxes to the date panel
        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearCombo);
        datePanel.add(new JLabel("Month:"));
        datePanel.add(monthCombo);
        datePanel.add(new JLabel("Day:"));
        datePanel.add(dayCombo);
    
        // Show a confirmation dialog for the user to select a date
        int result = JOptionPane.showConfirmDialog(
                this,
                datePanel,
                "Select a Date",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    
        if (result != JOptionPane.OK_OPTION) { // If the user canceled the date selection
            return false; // No table is built, return false
        }
    
        int year = (int) yearCombo.getSelectedItem(); // Get selected year from the combo box
        int month = (int) monthCombo.getSelectedItem(); // Get selected month from the combo box
        int day = (int) dayCombo.getSelectedItem(); // Get selected day from the combo box
    
        // Construct the selected date from year, month, and day
        LocalDate selectedDate = LocalDate.of(year, month, day);
    
        // Retrieve the list of doctors from the patient controller
        List<Doctor> doctors = patientController.getDoctors();
    
        // Define the start time and the number of slots
        LocalTime startTime = LocalTime.of(9, 0); // Start at 9:00 AM
        int slots = 15; // Define the number of available time slots (15 slots)
        Duration slotDuration = Duration.ofMinutes(30); // Each slot lasts 30 minutes
    
        // Total columns: 1 for doctor's name + 15 for time slots
        int cols = 1 + slots;
        // Rows: 1 for time labels + 1 per doctor
        int rows = 1 + doctors.size();
    
        availabilityPanel.removeAll(); // Clears the availability panel if it was previously built
        availabilityPanel.setLayout(new GridLayout(rows, cols, 5, 5)); // Set the grid layout for availabilityPanel
    
        // First row (time labels): Add empty cell in the top-left corner
        availabilityPanel.add(new JLabel(""));
        for (int i = 0; i < slots; i++) { // Loop through each time slot
            LocalTime slotTime = startTime.plus(slotDuration.multipliedBy(i)); // Calculate the time for each slot
            String timeStr = String.format("%02d:%02d", slotTime.getHour(), slotTime.getMinute()); // Format time as string
            JLabel timeLabel = new JLabel(timeStr, SwingConstants.CENTER); // Create a label for the time slot
            timeLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Set font for time labels
            availabilityPanel.add(timeLabel); // Add the time label to the panel
        }
    
        // Each subsequent row: one doctor
        for (Doctor doc : doctors) { // Loop through each doctor
            // Create a label with the doctor's name (first initial + last name)
            JLabel docLabel = new JLabel(doc.getFirstName().substring(0, 1) + ". " + doc.getLastName(),
                    SwingConstants.CENTER);
            docLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the doctor labels
            availabilityPanel.add(docLabel); // Add the doctor label to the panel
    
            // Loop through each time slot for the current doctor
            for (int i = 0; i < slots; i++) {
                LocalTime slotTime = startTime.plus(slotDuration.multipliedBy(i)); // Calculate the slot time
                LocalDateTime fullDateTime = LocalDateTime.of(selectedDate, slotTime); // Combine date and time into one object
    
                // Check if the doctor is available for the current time slot
                boolean available = doc.isAvailable(fullDateTime, slotDuration);
    
                // Create a button for each time slot
                JButton button = new JButton();
                button.setBackground(available ? Color.BLUE : Color.GRAY); // Set background color based on availability
                button.setEnabled(available); // Enable the button only if the slot is available
                button.setToolTipText(timeString(fullDateTime) + " - " + (available ? "Available" : "Unavailable")); // Set tool tip
    
                // If the slot is available, add an action listener to open the appointment details screen when clicked
                if (available) {
                    String selectedTime = timeString(fullDateTime); // Get the time as a string
                    Doctor doctorName = doc; // Store the doctor object
                    button.addActionListener(e -> {
                        new AppointmentDetailsScreen(doctorName, fullDateTime, patientController, selectedPatient);
                    });
                }
    
                // Add the button to the panel
                availabilityPanel.add(button);
            }
        }
    
        availabilityPanel.revalidate(); // Revalidate the panel to reflect the changes
        availabilityPanel.repaint(); // Repaint the panel to update the UI
        return true; // Return true indicating the table was successfully built
    }
    

    private Integer[] generateDays(int start, int end) { // Method to generate an array of integers representing days
        Integer[] days = new Integer[end - start + 1]; // Create an array of integers with length based on the given range
        for (int i = 0; i < days.length; i++) { // Loop through the array
            days[i] = start + i; // Assign each element the corresponding day number
        }
        return days; // Return the array of days
    }
    
    private String timeString(LocalDateTime time) { // Method to format a LocalDateTime object into a string (HH:mm)
        return String.format("%02d:%02d", time.getHour(), time.getMinute()); // Format time with leading zeros for single-digit hours/minutes
    }
    
    // Separate class for the new full window with details and a description field
    private class AppointmentDetailsScreen extends JFrame { // Inner class to create the appointment details screen
        private JTextArea descriptionTextArea; // Text area for appointment description
        private JTextArea preAppointmentTextArea; // Text area for pre-appointment instructions
        private JComboBox<String> appointmentTypeCombo; // Combo box for selecting appointment type
    
        public AppointmentDetailsScreen(Doctor doctorName, LocalDateTime selectedTime, PatientController pc,
                Patient selectedPatient) { // Constructor for the AppointmentDetailsScreen
            setTitle("Appointment Details"); // Set window title
            setSize(600, 600); // Set window size
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set close operation to dispose the window
            setLocationRelativeTo(null); // Center the window on the screen
    
            JPanel panel = new JPanel(); // Create a panel to hold the components
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use vertical box layout for the panel
            panel.setBackground(Color.WHITE); // Set panel background color to white
    
            // Create a label displaying doctor's name and selected time slot
            JLabel infoLabel = new JLabel("Doctor: " + doctorName.getLastName() + " | Time Slot: " + selectedTime);
            infoLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font for the label
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the label
    
            panel.add(Box.createVerticalStrut(30)); // Add vertical space
            panel.add(infoLabel); // Add the info label to the panel
            panel.add(Box.createVerticalStrut(20)); // Add vertical space
    
            // Add a label for the appointment type selection
            JLabel typeLabel = new JLabel("Appointment Type:");
            typeLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the label
            typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the label
            panel.add(typeLabel); // Add the label to the panel
            panel.add(Box.createVerticalStrut(10)); // Add vertical space
    
            // Add a combo box for the appointment type (e.g., "General Consultation", "Surgery", "Follow Up")
            appointmentTypeCombo = new JComboBox<>(new String[] {
                    "General Consultation",
                    "Surgery",
                    "Follow Up"
            });
            appointmentTypeCombo.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the combo box
            panel.add(appointmentTypeCombo); // Add the combo box to the panel
            panel.add(Box.createVerticalStrut(20)); // Add vertical space
    
            // Add a label for the appointment description field
            JLabel descLabel = new JLabel("Appointment Description:");
            descLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the label
            descLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the label
            panel.add(descLabel); // Add the label to the panel
            panel.add(Box.createVerticalStrut(10)); // Add vertical space
    
            // Add a text area for entering the appointment description
            descriptionTextArea = new JTextArea(5, 30); // Create a text area with 5 rows and 30 columns
            descriptionTextArea.setWrapStyleWord(true); // Enable word wrap
            descriptionTextArea.setLineWrap(true); // Enable line wrapping
            descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the text area
    
            JScrollPane descScrollPane = new JScrollPane(descriptionTextArea, // Wrap the text area in a scroll pane
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, // Vertical scrollbar if needed
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // No horizontal scrollbar
            descScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the scroll pane
    
            panel.add(descScrollPane); // Add the scroll pane to the panel
            panel.add(Box.createVerticalStrut(20)); // Add vertical space
    
            // Add a label for pre-appointment instructions field
            JLabel preAppLabel = new JLabel("Pre-Appointment Instructions:");
            preAppLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the label
            preAppLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the label
            panel.add(preAppLabel); // Add the label to the panel
            panel.add(Box.createVerticalStrut(10)); // Add vertical space
    
            // Add a text area for entering pre-appointment instructions
            preAppointmentTextArea = new JTextArea(5, 30); // Create a text area with 5 rows and 30 columns
            preAppointmentTextArea.setWrapStyleWord(true); // Enable word wrap
            preAppointmentTextArea.setLineWrap(true); // Enable line wrapping
            preAppointmentTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the text area
    
            JScrollPane preAppScrollPane = new JScrollPane(preAppointmentTextArea, // Wrap the text area in a scroll pane
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, // Vertical scrollbar if needed
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // No horizontal scrollbar
            preAppScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the scroll pane
    
            panel.add(preAppScrollPane); // Add the scroll pane to the panel
            panel.add(Box.createVerticalStrut(20)); // Add vertical space
    
            // Create and add the "Confirm Appointment" button
            JButton confirmButton = new JButton("Confirm Appointment");
            confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the button
            confirmButton.addActionListener(e -> { // Add action listener for confirming appointment
                String description = descriptionTextArea.getText().trim(); // Get the appointment description
                String preInstructions = preAppointmentTextArea.getText().trim(); // Get the pre-appointment instructions
                // Determine the appointment type based on user's selection
                String selectedTypeText = (String) appointmentTypeCombo.getSelectedItem();
                String appointmentType;
                if ("General Consultation".equals(selectedTypeText)) {
                    appointmentType = "general";
                } else if ("Surgery".equals(selectedTypeText)) {
                    appointmentType = "surgery";
                } else {
                    appointmentType = "follow";
                }
    
                LocalDateTime currentDateTime = LocalDateTime.now(); // Get current date and time
                Duration thirtyMinutes = Duration.ofMinutes(30); // Define the duration of the appointment
                boolean bookingRes = patientController.bookAppointment(
                        selectedPatient, // The selected patient
                        doctorName, // The doctor
                        appointmentType, // The type of appointment
                        description, // The appointment description
                        selectedTime, // The selected time
                        patientController.getHospital(), // The hospital
                        thirtyMinutes, // The duration of the appointment
                        preInstructions // The pre-appointment instructions
                );
    
                // Show confirmation message if booking is successful, otherwise show failure message
                if (bookingRes) {
                    JOptionPane.showMessageDialog(this, "Appointment Confirmed!\nDescription: " + description);
                } else {
                    JOptionPane.showMessageDialog(this, "Appointment Could Not Be Created!");
                }
                dispose(); // Close the appointment details screen
            });
    
            // Create and add the "Cancel" button
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the button
            cancelButton.addActionListener(e -> { // Add action listener for canceling appointment
                dispose(); // Close the current screen
                new StartingScreen(patientController); // Open the starting screen
            });
    
            panel.add(confirmButton); // Add the confirm button to the panel
            panel.add(Box.createVerticalStrut(10)); // Add vertical space
            panel.add(cancelButton); // Add the cancel button to the panel
    
            getContentPane().add(panel); // Add the panel to the window content
            setVisible(true); // Make the window visible
        }
    }
    
    private Patient selectPatient() { // Method to allow the user to select a patient
        // Fetch all patients from the patient controller
        List<Patient> patients = patientController.getPatients();
    
        // Convert the list of patients to an array of strings for display (e.g., using patient's name)
        String[] patientNames = patients.stream()
                .map(patient -> patient.getFirstName() + " " + patient.getLastName()) // Map each patient to their full name
                .toArray(String[]::new); // Convert the stream to an array
    
        // Display the patients in a dialog with a JList for selection
        JList<String> patientList = new JList<>(patientNames); 
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow single selection
    
        int option = JOptionPane.showConfirmDialog(
                this, // The parent component
                new JScrollPane(patientList), // The list of patients in a scroll pane
                "Select a Patient", // Dialog title
                JOptionPane.OK_CANCEL_OPTION, // Options for the dialog
                JOptionPane.PLAIN_MESSAGE); // Plain message dialog
    
        // If user clicked OK and a patient is selected
        if (option == JOptionPane.OK_OPTION && patientList.getSelectedIndex() >= 0) {
            int selectedIndex = patientList.getSelectedIndex(); // Get the selected patient's index
            Patient chosen = patients.get(selectedIndex); // Get the selected patient from the list
    
            // Confirm the selection
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Select patient " + chosen.getFirstName() + " " + chosen.getLastName() + "?", // Ask for confirmation
                    "Confirm Patient", // Dialog title
                    JOptionPane.YES_NO_OPTION, // Yes/No option
                    JOptionPane.QUESTION_MESSAGE); // Question icon
    
            // If user clicked YES, set the selectedPatient variable
            if (confirm == JOptionPane.YES_OPTION) {
                this.selectedPatient = chosen; // Set the selected patient
                return selectedPatient; // Return the selected patient
            }
        }
        return null; // Return null if no patient was selected or confirmed
    }
}    