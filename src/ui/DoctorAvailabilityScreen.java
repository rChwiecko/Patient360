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
    private final PatientController patientController;
    private JPanel availabilityPanel;

    public DoctorAvailabilityScreen(PatientController patientController) {
        this.patientController = patientController;
        setTitle("View Doctor's Availability");
        setSize(1900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Doctor's Availability");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(label);

        availabilityPanel = new JPanel();
        availabilityPanel.setBackground(Color.WHITE);

        // Prompt user to select a doctor
        Doctor selectedDoctor = selectDoctor();
        if (selectedDoctor == null) {
            dispose();
            return;
        }

        // Build the availability table
        buildAvailabilityTable(selectedDoctor);

        JScrollPane scrollPane = new JScrollPane(availabilityPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);

        // Back button to return to the previous screen
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen(patientController);  // Go back to the starting screen
        });

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(backButtonPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void buildAvailabilityTable(Doctor selectedDoctor) {
        // Prompt user for date selection via a dialog
        JPanel datePanel = new JPanel(new FlowLayout());
        LocalDate today = LocalDate.now(); // Get today's date
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();
    
        JComboBox<Integer> yearCombo = new JComboBox<>(new Integer[]{currentYear, currentYear + 1});
        yearCombo.setSelectedItem(currentYear); // Default to current year
    
        JComboBox<Integer> monthCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        monthCombo.setSelectedItem(currentMonth); // Default to current month
    
        JComboBox<Integer> dayCombo = new JComboBox<>(generateDays(1, 31));
        dayCombo.setSelectedItem(currentDay); // Default to current day
    
        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearCombo);
        datePanel.add(new JLabel("Month:"));
        datePanel.add(monthCombo);
        datePanel.add(new JLabel("Day:"));
        datePanel.add(dayCombo);
    
        int result = JOptionPane.showConfirmDialog(
                this,
                datePanel,
                "Select a Date",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    
        if (result != JOptionPane.OK_OPTION) {
            // If user canceled, just return (no table built)
            return;
        }
    
        int year = (int) yearCombo.getSelectedItem();
        int month = (int) monthCombo.getSelectedItem();
        int day = (int) dayCombo.getSelectedItem();
    
        // Construct the selected date
        LocalDate selectedDate = LocalDate.of(year, month, day);
    
        // Define the time slots
        LocalTime startTime = LocalTime.of(9, 0); // Start at 9:00 AM
        int slots = 15;
        Duration slotDuration = Duration.ofMinutes(30);
    
        // Total columns: 1 for time labels + 15 for time slots
        int cols = 1 + slots;
        // Rows: 1 for time labels + 1 for the doctor
        int rows = 1 + 1; // Only one doctor
    
        availabilityPanel.removeAll(); // Clear if previously built
        availabilityPanel.setLayout(new GridLayout(rows, cols, 5, 5));
    
        // First row (time labels)
        availabilityPanel.add(new JLabel("")); // top-left corner blank cell
        for (int i = 0; i < slots; i++) {
            LocalTime slotTime = startTime.plus(slotDuration.multipliedBy(i));
            String timeStr = String.format("%02d:%02d", slotTime.getHour(), slotTime.getMinute());
            JLabel timeLabel = new JLabel(timeStr, SwingConstants.CENTER);
            timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
            availabilityPanel.add(timeLabel);
        }
    
        // Row for the selected doctor
        JLabel docLabel = new JLabel(selectedDoctor.getFirstName().substring(0, 1) + ". " + selectedDoctor.getLastName(), SwingConstants.CENTER);
        docLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        availabilityPanel.add(docLabel);
    
        // Add the time slots for the selected doctor
        for (int i = 0; i < slots; i++) {
            LocalTime slotTime = startTime.plus(slotDuration.multipliedBy(i));
            LocalDateTime fullDateTime = LocalDateTime.of(selectedDate, slotTime); // Combine date and time
    
            boolean available = selectedDoctor.isAvailable(fullDateTime, slotDuration);
    
            JButton button = new JButton(available ? "Available" : "Unavailable");
            button.setBackground(available ? Color.BLUE : Color.GRAY);
            button.setEnabled(false); // Disabled (no action can be taken)
            button.setToolTipText(timeString(fullDateTime) + " - " + (available ? "Available" : "Unavailable"));
    
            availabilityPanel.add(button);
        }
    
        availabilityPanel.revalidate();
        availabilityPanel.repaint();
    }

    private Integer[] generateDays(int start, int end) {
        Integer[] days = new Integer[end - start + 1];
        for (int i = 0; i < days.length; i++) {
            days[i] = start + i;
        }
        return days;
    }

    private String timeString(LocalDateTime time) {
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    private Doctor selectDoctor() {
        // Fetch all doctors
        List<Doctor> doctors = patientController.getDoctors();

        // Convert the list of doctors to an array of strings for display
        String[] doctorNames = doctors.stream()
                .map(doctor -> doctor.getFirstName() + " " + doctor.getLastName())
                .toArray(String[]::new);

        // Display the doctors in a dialog with a JList
        JList<String> doctorList = new JList<>(doctorNames);
        doctorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(doctorList),
                "Select a Doctor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // If user clicked OK and a doctor is selected
        if (option == JOptionPane.OK_OPTION && doctorList.getSelectedIndex() >= 0) {
            int selectedIndex = doctorList.getSelectedIndex();
            return doctors.get(selectedIndex);
        }
        return null;  // No doctor selected
    }
}
