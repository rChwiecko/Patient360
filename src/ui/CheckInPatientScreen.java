package ui;

import api.controller.PatientController;
import api.models.Appointment;
import api.models.Doctor;
import api.models.Patient;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class CheckInPatientScreen extends JFrame {
    private JLabel username;

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
        List<Appointment> appointments = patient.getAppointments(); // Assuming getAppointments returns a List<Appointment>

        // Create a DefaultListModel to hold the appointment strings
        DefaultListModel<String> appointmentListModel = new DefaultListModel<>();
        
        // Convert each Appointment to a string representation (using toString or custom formatting)
        for (Appointment appointment : appointments) {
            appointmentListModel.addElement("Description: "+appointment.getDescription()+"\nAppointment Type: "+appointment.getAppointmentType()); // Assuming Appointment has a meaningful toString() method
        }

        // Set the JList with the DefaultListModel
        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentList);
        appointmentScrollPane.setPreferredSize(new Dimension(500, 200));
        optionsPanel.add(appointmentScrollPane);

        // Walk-in Button action
        JButton walkInButton = new JButton("Walk-in");
        walkInButton.addActionListener(e -> {
            optionsFrame.dispose();
            showWalkInForm(patient);
        });

        JButton appointmentButton = new JButton("Appointment");
appointmentButton.addActionListener(e -> {
    String selectedAppointmentDescription = appointmentList.getSelectedValue(); // Get the selected appointment description
    if (selectedAppointmentDescription != null) {
        int confirm = JOptionPane.showConfirmDialog(
                optionsFrame,
                "Confirm check-in for " + patient.getFirstName() + " on " + selectedAppointmentDescription + "?",
                "Check Appointment",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Find the selected Appointment object using its description
            Appointment selectedAppointment = patient.getAppointments().stream()
                    .filter(appointment -> selectedAppointmentDescription.contains(appointment.getDescription()))
                    .findFirst()
                    .orElse(null);

            if (selectedAppointment != null) {
                // Update the appointment status to "Ongoing"
                selectedAppointment.updateStatus("Ongoing");

                JOptionPane.showMessageDialog(
                        optionsFrame,
                        "Appointment status for " + patient.getFirstName() + " is now 'Ongoing'."
                );

                // Dispose the options frame and refresh the Check-In screen
                optionsFrame.dispose();
                new CheckInPatientScreen(patientController);
            } else {
                JOptionPane.showMessageDialog(optionsFrame, "Failed to find the selected appointment.");
            }
        }
    } else {
        JOptionPane.showMessageDialog(optionsFrame, "Please select an appointment to check-in.");
    }
});


        optionsPanel.add(walkInButton);
        optionsPanel.add(appointmentButton);

        optionsFrame.add(optionsPanel);
        optionsFrame.setVisible(true);
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
