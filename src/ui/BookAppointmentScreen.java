package ui;
import api.controller.PatientController;
import api.models.Doctor;
import api.models.Patient;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.swing.*;

public class BookAppointmentScreen extends JFrame {

    private final PatientController patientController;
    private final JPanel availabilityPanel;
    private Patient selectedPatient;

    public BookAppointmentScreen(PatientController patientController) {
        this.patientController = patientController;
        Patient currPatient = selectPatient();
        setTitle("Book an Appointment");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Select a time slot available");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(label);
        mainPanel.add(Box.createVerticalStrut(20));

        availabilityPanel = new JPanel();
        availabilityPanel.setBackground(Color.WHITE);

        buildAvailabilityTable(currPatient);

        JScrollPane scrollPane = new JScrollPane(availabilityPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen(patientController); // go back to the starting screen
        });

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(backButtonPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void buildAvailabilityTable(Patient SelectedPatient) {
        // Prompt user for date selection via a dialog
        JPanel datePanel = new JPanel(new FlowLayout());
        int currentYear = LocalDate.now().getYear();

        JComboBox<Integer> yearCombo = new JComboBox<>(new Integer[]{currentYear, currentYear + 1});
        JComboBox<Integer> monthCombo = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12});
        JComboBox<Integer> dayCombo = new JComboBox<>(generateDays(1,31)); // simple 1-31; no validation

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

        LocalDate selectedDate = LocalDate.of(year, month, day);

        List<Doctor> doctors = patientController.getDoctors();

        // Define the time slots
        // Use the selected date instead of 'now'
        LocalDateTime startTime = LocalDateTime.of(selectedDate, LocalTime.of(9, 0));
        int slots = 15;
        Duration slotDuration = Duration.ofMinutes(30);

        // Total columns: 1 for doctor's name + 15 for time slots
        int cols = 1 + slots;
        // Rows: 1 for time labels + 1 per doctor
        int rows = 1 + doctors.size();

        availabilityPanel.removeAll(); // Clear if previously built
        availabilityPanel.setLayout(new GridLayout(rows, cols, 5, 5));

        // First row (time labels)
        availabilityPanel.add(new JLabel("")); // top-left corner blank cell
        for (int i = 0; i < slots; i++) {
            LocalDateTime slotTime = startTime.plus(slotDuration.multipliedBy(i));
            String timeStr = String.format("%02d:%02d", slotTime.getHour(), slotTime.getMinute());
            JLabel timeLabel = new JLabel(timeStr, SwingConstants.CENTER);
            timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
            availabilityPanel.add(timeLabel);
        }

        // Each subsequent row: one doctor
        for (Doctor doc : doctors) {
            JLabel docLabel = new JLabel(doc.getFirstName(), SwingConstants.CENTER);
            docLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            availabilityPanel.add(docLabel);

            for (int i = 0; i < slots; i++) {
                LocalDateTime slotTime = startTime.plus(slotDuration.multipliedBy(i));
                boolean available = doc.isAvailable(slotTime, slotDuration);

                JButton button = new JButton();
                button.setBackground(available ? Color.BLUE : Color.GRAY);
                button.setEnabled(available);
                button.setToolTipText(timeString(slotTime) + " - " + (available ? "Available" : "Unavailable"));

                if (available) {
                    String selectedTime = timeString(slotTime);
                    Doctor doctorName = doc;
                    button.addActionListener(e -> {
                        new AppointmentDetailsScreen(doctorName, selectedTime, patientController, SelectedPatient);
                    });
                }

                availabilityPanel.add(button);
            }
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


    // Separate class for the new full window with details and a description field
    private class AppointmentDetailsScreen extends JFrame {
        private JTextArea descriptionTextArea;
        private JComboBox<String> appointmentTypeCombo;

        public AppointmentDetailsScreen(Doctor doctorName, String selectedTime, PatientController pc, Patient selectedPatient) {
            setTitle("Appointment Details");
            setSize(600, 500); // Increased height to accommodate new components
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null); // Center the window

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);

            JLabel infoLabel = new JLabel("Doctor: " + doctorName.getLastName() + " | Time Slot: " + selectedTime);
            infoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(Box.createVerticalStrut(30));
            panel.add(infoLabel);
            panel.add(Box.createVerticalStrut(20));

            // Add a label for the appointment type
            JLabel typeLabel = new JLabel("Appointment Type:");
            typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(typeLabel);
            panel.add(Box.createVerticalStrut(10));

            // Add a combo box for the appointment type
            appointmentTypeCombo = new JComboBox<>(new String[] {
                "General Consultation", 
                "Surgery", 
                "Follow Up"
            });
            appointmentTypeCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(appointmentTypeCombo);
            panel.add(Box.createVerticalStrut(20));

            // Add a label for the description
            JLabel descLabel = new JLabel("Appointment Description:");
            descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(descLabel);
            panel.add(Box.createVerticalStrut(10));

            // Add the text area for appointment description
            descriptionTextArea = new JTextArea(5, 30);
            descriptionTextArea.setWrapStyleWord(true);
            descriptionTextArea.setLineWrap(true);
            descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

            JScrollPane descScrollPane = new JScrollPane(descriptionTextArea,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            descScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(descScrollPane);
            panel.add(Box.createVerticalStrut(20));

            // Confirm and cancel buttons
            JButton confirmButton = new JButton("Confirm Appointment");
            confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            confirmButton.addActionListener(e -> {
                String description = descriptionTextArea.getText().trim();
                // Determine the appointment type code based on the user's selection
                String selectedTypeText = (String) appointmentTypeCombo.getSelectedItem();
                String appointmentType;
                if ("General Consultation".equals(selectedTypeText)) {
                    appointmentType = "general";
                } else if ("Surgery".equals(selectedTypeText)) {
                    appointmentType = "surgery";
                } else {
                    appointmentType = "follow";
                }

                LocalDateTime currentDateTime = LocalDateTime.now();
                Duration thirtyMinutes = Duration.ofMinutes(30);
                boolean bookingRes = patientController.bookAppointment(
                    selectedPatient, 
                    doctorName, 
                    appointmentType, 
                    description, 
                    currentDateTime, 
                    patientController.getHospital(), 
                    thirtyMinutes, 
                    description
                );

                if (bookingRes) {
                    JOptionPane.showMessageDialog(this, "Appointment Confirmed!\nDescription: " + description);
                } else {
                    JOptionPane.showMessageDialog(this, "Appointment Could Not Be Created!");
                }
                dispose();
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            cancelButton.addActionListener(e -> {
                dispose();
            });

            panel.add(confirmButton);
            panel.add(Box.createVerticalStrut(10));
            panel.add(cancelButton);

            getContentPane().add(panel);
            setVisible(true);
        }
    }


    private Patient selectPatient() {
        // Fetch all patients
        List<Patient> patients = patientController.getPatients();

        // Convert the list of patients to an array of strings for display (e.g., using patient's name)
        // Adjust getName() or any other method to display patients as desired
        String[] patientNames = patients.stream()
                .map(patient -> patient.getFirstName() + " " + patient.getLastName())
                .toArray(String[]::new);

        // Display the patients in a dialog with a JList
        JList<String> patientList = new JList<>(patientNames);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(patientList),
                "Select a Patient",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // If user clicked OK and a patient is selected
        if (option == JOptionPane.OK_OPTION && patientList.getSelectedIndex() >= 0) {
            int selectedIndex = patientList.getSelectedIndex();
            Patient chosen = patients.get(selectedIndex);

            // Confirm selection
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Select patient " + chosen.getFirstName() + " " + chosen.getLastName() + "?",
                    "Confirm Patient",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            // If user clicked YES, set the selectedPatient variable
            if (confirm == JOptionPane.YES_OPTION) {
                this.selectedPatient = chosen;
                return selectedPatient;
                // selectedPatient now holds the chosen patient's object
            }
        }return selectPatient();
    }



}
