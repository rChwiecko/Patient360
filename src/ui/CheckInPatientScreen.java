package ui;

import javax.swing.*;
import api.controller.PatientController;
import api.models.Appointment;
import api.models.Doctor;
import api.models.Patient;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class CheckInPatientScreen extends JFrame {

    private static DefaultListModel<String> patientsModel = new DefaultListModel<>();
    private static final int SCREEN_WIDTH = 1900;
    private static final int SCREEN_HEIGHT = 650;
    // Declare patientController as a class field
    private PatientController patientController;

    // Store the patients with a mapping from patient names to Patient objects
    private Map<String, Patient> patientMap = new HashMap<>();

    public CheckInPatientScreen(PatientController patientController) {
        this.patientController = patientController;

        // Get list of patients from the PatientController
        List<Patient> patients = patientController.getPatients(); // Assuming the controller has this method

        // Clear the previous entries and populate the list with dynamic patients
        patientsModel.clear();
        for (Patient patient : patients) {
            String patientName = patient.getFirstName(); // Use first name for display
            patientsModel.addElement(patientName);
            patientMap.put(patientName, patient); // Store the Patient object mapped to the name
        }

        setTitle("Check-in Patient");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Patient List");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(label);

        JList<String> patientList = new JList<>(patientsModel);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientList.setFont(new Font("Arial", Font.PLAIN, 16));

        patientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedPatientName = patientList.getSelectedValue();
                    if (selectedPatientName != null) {
                        Patient selectedPatient = patientMap.get(selectedPatientName); // Retrieve the full Patient object
                        if (selectedPatient != null) {
                            dispose(); // Close current screen
                            showCheckInOptions(selectedPatient); // Pass the full Patient object
                        }
                    }
                }
            }
        });

        JScrollPane patientScrollPane = new JScrollPane(patientList);
        patientScrollPane.setPreferredSize(new Dimension(300, 400));
        mainPanel.add(patientScrollPane);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen(patientController);
        });

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);
        mainPanel.add(backButtonPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void showCheckInOptions(Patient patient) {
        JFrame optionsFrame = new JFrame("Check-in Options for " + patient.getFirstName());
        optionsFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
    
        JLabel appointmentsLabel = new JLabel("Future Appointments:");
        appointmentsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        optionsPanel.add(appointmentsLabel);
    
        // Fetch appointments dynamically from the Patient object
        List<Appointment> appointments = patient.getAppointments(); // Get the patient's appointments
    
        // Create a DefaultListModel to hold the appointment panels
        JPanel appointmentPanelContainer = new JPanel();
        appointmentPanelContainer.setLayout(new BoxLayout(appointmentPanelContainer, BoxLayout.Y_AXIS));
    
        // Convert each Appointment to a detailed string representation
        if (appointments != null && !appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                JPanel appointmentPanel = createAppointmentPanel(appointment); // Create a JPanel for each appointment
                appointmentPanelContainer.add(appointmentPanel);
            }
        } else {
            JLabel noAppointmentsLabel = new JLabel("No appointments scheduled.");
            noAppointmentsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            appointmentPanelContainer.add(noAppointmentsLabel);
        }
    
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentPanelContainer);
        appointmentScrollPane.setPreferredSize(new Dimension(500, 400));
        optionsPanel.add(appointmentScrollPane);
    
        // Walk-in Button action
        JButton walkInButton = new JButton("Walk-in");
        walkInButton.addActionListener(e -> {
            optionsFrame.dispose();
            showWalkInForm(patient);
        });
    
        // Appointment Button action (for appointment check-in)
        JButton appointmentButton = new JButton("Appointment");
        appointmentButton.addActionListener(e -> {
            // Handle appointment selection and check-in
            String selectedAppointmentDate = JOptionPane.showInputDialog(optionsFrame, "Please select the appointment date (e.g., 2024-12-15 10:00):");
            
            // Find the appointment with the selected date
            Appointment selectedAppointment = null;
            for (Appointment appointment : appointments) {
                String appointmentDate = appointment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                if (appointmentDate.equals(selectedAppointmentDate)) {
                    selectedAppointment = appointment;
                    break;
                }
            }
    
            // If the appointment is found, process the check-in
            if (selectedAppointment != null) {
                int confirm = JOptionPane.showConfirmDialog(optionsFrame, 
                        "Confirm check-in for " + patient.getFirstName() + " on " + selectedAppointmentDate + "?", 
                        "Check Appointment", JOptionPane.YES_NO_OPTION);
    
                if (confirm == JOptionPane.YES_OPTION) {
                    // Change appointment status to "ongoing"
                    selectedAppointment.updateStatus("ongoing");
    
                    // Update patient status or any other related information
                    JOptionPane.showMessageDialog(optionsFrame, "Appointment for " + patient.getFirstName() + " is now 'Ongoing'.");
                    
                    // Close the frame after check-in
                    optionsFrame.dispose();
                    
                    // Optionally, navigate back or show a confirmation screen
                    new CheckInPatientScreen(patientController);
                }
            } else {
                JOptionPane.showMessageDialog(optionsFrame, "Appointment not found. Please ensure the date is correct.");
            }
        });
    
        optionsPanel.add(walkInButton);
        optionsPanel.add(appointmentButton);
    
        optionsFrame.add(optionsPanel);
        optionsFrame.setVisible(true);
    }
    

// Helper method to create the appointment panel with collapsible details
private JPanel createAppointmentPanel(Appointment appointment) {
    JPanel appointmentPanel = new JPanel();
    appointmentPanel.setLayout(new BoxLayout(appointmentPanel, BoxLayout.Y_AXIS));
    appointmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // Create the title (date of appointment)
    String appointmentDate = appointment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    JLabel titleLabel = new JLabel(appointmentDate);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    titleLabel.setForeground(Color.BLUE);

    // Create the details panel (hidden by default)
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.setVisible(false);  // Start as hidden

    // Format the appointment details
    String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
    String appointmentType = appointment.getAppointmentType();
    String description = appointment.getDescription();
    String status = appointment.getAppointmentStatus();
    String location = appointment.getLocation().getAddress(); // Assuming Hospital class has a getName() method

    detailsPanel.add(new JLabel("Doctor: " + doctorName));
    detailsPanel.add(new JLabel("Type: " + appointmentType));
    detailsPanel.add(new JLabel("Description: " + description));
    detailsPanel.add(new JLabel("Status: " + status));
    detailsPanel.add(new JLabel("Location: " + location));

    // Make the title clickable to toggle the details
    titleLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Toggle visibility of the details panel
            detailsPanel.setVisible(!detailsPanel.isVisible());
            appointmentPanel.revalidate();  // Revalidate to update the UI
            appointmentPanel.repaint();  // Repaint to reflect changes
        }
    });

    // Add the title and the details to the appointment panel
    appointmentPanel.add(titleLabel);
    appointmentPanel.add(detailsPanel);

    return appointmentPanel;
}

    
    // Helper method to format the appointment details into a readable string
    private String formatAppointmentDetails(Appointment appointment) {
        String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        String appointmentType = appointment.getAppointmentType();
        String description = appointment.getDescription();
        String appointmentDate = appointment.getDate().toString(); // Can format this date as needed
        String status = appointment.getAppointmentStatus();
        String location = appointment.getLocation().getAddress(); // Assuming Hospital class has a getName() method
        
        return String.format("Doctor: %s\nType: %s\nDescription: %s\nDate: %s\nLocation: %s\nStatus: %s", 
                doctorName, appointmentType, description, appointmentDate, location, status);
    }
    
    private void showWalkInForm(Patient patient) {
        JFrame walkInFrame = new JFrame("Walk-in Details for " + patient.getFirstName());
        walkInFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        walkInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel walkInPanel = new JPanel();
        walkInPanel.setLayout(new BoxLayout(walkInPanel, BoxLayout.Y_AXIS));

        JTextField reasonField = new JTextField();
        reasonField.setPreferredSize(new Dimension(250, 30));
        walkInPanel.add(new JLabel("Reason for Visit:"));
        walkInPanel.add(reasonField);

        // Fetch doctor names dynamically from the PatientController
        List<String> doctorNames = new ArrayList<>();
        List<Doctor> doctors = patientController.getDoctors();  // Fetch doctors from the controller

        // If no doctors are available, show a fallback option
        if (doctors != null && !doctors.isEmpty()) {
            for (Doctor doctor : doctors) {
                doctorNames.add(doctor.getFirstName());  // Assuming Doctor has getFullName() method
            }
        } else {
            doctorNames.add("No doctors available");
        }

        // Create JComboBox with doctor names
        JComboBox<String> doctorDropdown = new JComboBox<>(doctorNames.toArray(new String[0]));
        walkInPanel.add(new JLabel("Select Doctor:"));
        walkInPanel.add(doctorDropdown);

        // Appointment type options
        String[] appointmentTypes = {"Surgery", "Follow-up", "General Consultation"};
        JComboBox<String> appointmentTypeDropdown = new JComboBox<>(appointmentTypes);
        walkInPanel.add(new JLabel("Appointment Type:"));
        walkInPanel.add(appointmentTypeDropdown);

        // Location field
        JTextField locationField = new JTextField();
        locationField.setPreferredSize(new Dimension(250, 30));
        walkInPanel.add(new JLabel("Location:"));
        walkInPanel.add(locationField);

        // Duration field
        JTextField durationField = new JTextField();
        durationField.setPreferredSize(new Dimension(250, 30));
        walkInPanel.add(new JLabel("Duration (in minutes):"));
        walkInPanel.add(durationField);

        // Appointment date field
        JTextField dateField = new JTextField();
        dateField.setPreferredSize(new Dimension(250, 30));
        walkInPanel.add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        walkInPanel.add(dateField);

        // Save button action
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String reason = reasonField.getText();
            String selectedDoctor = (String) doctorDropdown.getSelectedItem();
            String appointmentType = (String) appointmentTypeDropdown.getSelectedItem();
            String location = locationField.getText();
            String duration = durationField.getText();
            String appointmentDate = dateField.getText();

            // Validate the input
            if (!reason.isEmpty() && selectedDoctor != null && appointmentType != null &&
                !location.isEmpty() && !duration.isEmpty() && !appointmentDate.isEmpty()) {
                JOptionPane.showMessageDialog(walkInFrame, "Walk-in saved for " + patient.getFirstName() + " with Dr. " + selectedDoctor + 
                    ": " + appointmentType + " on " + appointmentDate + ".\nLocation: " + location + "\nDuration: " + duration + " mins");

                JOptionPane.showMessageDialog(walkInFrame, "Appointment status for " + patient.getFirstName() + " is now 'Ongoing'.");

                walkInFrame.dispose();
                new CheckInPatientScreen(patientController); // Assuming you're navigating back to CheckInPatientScreen
            } else {
                JOptionPane.showMessageDialog(walkInFrame, "Please fill all fields.");
            }
        });

        walkInPanel.add(saveButton);
        walkInFrame.add(walkInPanel);
        walkInFrame.setVisible(true);
    }
}
