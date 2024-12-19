package ui;

import api.controller.PatientController;
import api.models.Appointment;
import api.models.Doctor;
import api.models.Hospital;
import api.models.Patient;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.time.Duration;

public class CheckInPatientScreen extends JFrame {
    private static DefaultListModel<String> patientsModel = new DefaultListModel<>();
    private static final int SCREEN_WIDTH = 1900;
    private static final int SCREEN_HEIGHT = 650;

    private PatientController patientController;
    private Map<String, Patient> patientMap = new HashMap<>();

    public CheckInPatientScreen(PatientController patientController) {
        this.patientController = patientController;

        // Get list of patients and populate the model and map
        List<Patient> patients = patientController.getPatients();
        patientsModel.clear();
        for (Patient patient : patients) {
            String fullName = patient.getFirstName() + " " + patient.getLastName();
            patientsModel.addElement(fullName);
            patientMap.put(fullName, patient);
        }

        //setTitle("Check-in Patient");
        //setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        showPatientListDialog();
        //setVisible(true);
    }

    private void showPatientListDialog() {
        JList<String> patientList = new JList<>(patientsModel);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientList.setFont(new Font("Arial", Font.PLAIN, 16));

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(patientList),
                "Select a Patient",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.CANCEL_OPTION) {
            // If the user clicks "Cancel", dispose of the current frame and return to the previous screen.
            dispose();
            new StartingScreen(patientController).setVisible(true);  // Ensure it's made visible
            return;
        }

        String selectedPatientName = patientList.getSelectedValue();
        if (selectedPatientName != null) {
            Patient selectedPatient = patientMap.get(selectedPatientName);
            if (selectedPatient != null) {
                // Show check-in options dialog
                showCheckInOptions(selectedPatient);
            }
        }
    }

    private void showCheckInOptions(Patient patient) {
        String[] options = {"Appointment Made", "Walk-In"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Check-In Option for " + patient.getFirstName() + " " + patient.getLastName(),
                "Check-In Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            // If the user clicks "Cancel" or closes the dialog, dispose of the current frame and return to the previous screen.
            dispose();
            new StartingScreen(patientController).setVisible(true); // Ensure the screen is displayed properly.
            return;
        }

        if (choice == 0) {
            // "Appointment Made" selected
            showAppointmentList(patient);
        } else if (choice == 1) {
            // "Walk-In" selected
            showWalkInForm(patient);
        }
    }

    private void showAppointmentList(Patient patient) {
        List<Appointment> appointments = patient.getAppointments();
        if (appointments == null || appointments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments found for this patient.");
            dispose();
            new StartingScreen(patientController).setVisible(true); // Ensure the starting screen is visible
            return;
        }
        
        DefaultListModel<String> appointmentListModel = new DefaultListModel<>();
        Map<String, Appointment> appointmentMap = new HashMap<>();
        for (Appointment appointment : appointments) {
            String appointmentDetails = "Description: " + appointment.getDescription() +
                                        " | Type: " + appointment.getAppointmentType();
            appointmentListModel.addElement(appointmentDetails);
            appointmentMap.put(appointmentDetails, appointment);
        }
        
        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(appointmentList),
                "Select an Appointment",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        
        if (option == JOptionPane.CANCEL_OPTION) {
            dispose();
            new StartingScreen(patientController).setVisible(true);  // Ensure the starting screen is visible
            return;
        }
        
        if (option == JOptionPane.OK_OPTION) {
            String selectedAppointmentDetails = appointmentList.getSelectedValue();
            if (selectedAppointmentDetails != null) {
                Appointment selectedAppointment = appointmentMap.get(selectedAppointmentDetails);
                
                // Check if the appointment is already checked in (status is "Ongoing")
                if ("Ongoing".equals(selectedAppointment.getAppointmentStatus())) {
                    JOptionPane.showMessageDialog(this, "This appointment has already been checked in.");
                    dispose();
                    new StartingScreen(patientController).setVisible(true);  // Return to the starting screen if already checked in
                    return;
                }
    
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Confirm check-in for appointment:\n" + selectedAppointmentDetails,
                        "Confirm Appointment",
                        JOptionPane.YES_NO_OPTION
                );
        
                if (confirm == JOptionPane.YES_OPTION) {
                    selectedAppointment.updateStatus("Ongoing");
                    JOptionPane.showMessageDialog(
                            this,
                            "Appointment Confirmed! Status updated to 'Ongoing' for " + patient.getFirstName() + "."
                    );
        
                    // Dispose the current CheckInPatientScreen and return to the StartingScreen
                    dispose();
                    new StartingScreen(patientController).setVisible(true);  // Return to the starting screen after confirmation
                }
            }
        }
    }
    
    private void showWalkInForm(Patient selectedPatient) {
        JPanel walkInPanel = new JPanel();
        walkInPanel.setLayout(new BoxLayout(walkInPanel, BoxLayout.Y_AXIS));
    
        // 1. Date Panel - Show current date
        JLabel dateLabel = new JLabel("Current Date: " + java.time.LocalDate.now());
        walkInPanel.add(dateLabel);
    
        // 2. Doctor Selection Panel
        List<Doctor> availableDoctors = patientController.getDoctors();  // Assuming you have a method to get available doctors
        if (availableDoctors.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No doctors available.");
            return; // Exit if no doctors are available
        }
    
        String[] doctorOptions = new String[availableDoctors.size()];
        for (int i = 0; i < availableDoctors.size(); i++) {
            doctorOptions[i] = availableDoctors.get(i).getFirstName();  // Assuming Doctor has a getFirstName() method
        }
        JComboBox<String> doctorComboBox = new JComboBox<>(doctorOptions);
        walkInPanel.add(new JLabel("Select a Doctor:"));
        walkInPanel.add(doctorComboBox);
    
        // 3. Appointment Type Panel
        String[] appointmentTypes = {"General Consultation", "Surgery", "Follow-Up"};
        JComboBox<String> appointmentTypeComboBox = new JComboBox<>(appointmentTypes);
        walkInPanel.add(new JLabel("Select Appointment Type:"));
        walkInPanel.add(appointmentTypeComboBox);
    
        // 4. Appointment Duration Panel
        String[] durations = {"30 minutes", "1 hour"};
        JComboBox<String> durationComboBox = new JComboBox<>(durations);
        walkInPanel.add(new JLabel("Select Duration:"));
        walkInPanel.add(durationComboBox);
    
        // 5. Appointment Description Panel
        JTextField descriptionField = new JTextField(20);
        walkInPanel.add(new JLabel("Description of Appointment:"));
        walkInPanel.add(descriptionField);
    
        // Confirm or Cancel
        int option = JOptionPane.showConfirmDialog(this, walkInPanel, 
                                                    "Walk-In Appointment for " + selectedPatient.getFirstName() + " " + selectedPatient.getLastName(),
                                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (option == JOptionPane.OK_OPTION) {
            // Gather all input data
            String selectedDoctorName = (String) doctorComboBox.getSelectedItem();
            if (selectedDoctorName == null || selectedDoctorName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a valid doctor.");
                return;
            }
    
            Doctor selectedDoctor = null;
            for (Doctor doctor : availableDoctors) {
                if (doctor.getFirstName().equals(selectedDoctorName)) {  // Ensure full name match
                    selectedDoctor = doctor;
                    break;
                }
            }
    
            if (selectedDoctor == null) {
                JOptionPane.showMessageDialog(this, "Invalid doctor selected.");
                return;
            }
    
            // Handle appointment type, description, and duration
            String selectedAppointmentType = (String) appointmentTypeComboBox.getSelectedItem();
            switch (selectedAppointmentType){
                case "General Consultation":
                    selectedAppointmentType = "general";
                    break;
                case "Surgery":
                    selectedAppointmentType = "surgery";
                    break;
                case "Follow-Up":
                    selectedAppointmentType = "follow";
                    break;
            }
            String selectedDurationStr = (String) durationComboBox.getSelectedItem();
            Duration selectedDuration = selectedDurationStr.equals("30 minutes") ? Duration.ofMinutes(30) : Duration.ofHours(1);
            String description = descriptionField.getText();
    
            // Check doctor availability
            LocalDateTime currentDateTime = LocalDateTime.now();  // Get current date and time
            Hospital hospital = Hospital.getInstance();  // Assuming this method gets the current hospital instance
            String preAppointmentInstructions = "";  // Set pre-appointment instructions if any, else leave it empty
    
            // Book the appointment
            boolean bookingRes = patientController.bookAppointment(
                selectedPatient,  // The patient
                selectedDoctor,  // Doctor object (not the name)
                selectedAppointmentType,  // Type of appointment
                description,  // Description of the appointment
                currentDateTime,  // Appointment time
                hospital,  // The hospital location
                selectedDuration,  // Appointment duration
                preAppointmentInstructions  // Pre-appointment instructions
            );
    
            // Check result and show confirmation dialog
            if (bookingRes) {
                // After booking, immediately update the status to "Ongoing"
                Appointment walkInAppointment = selectedPatient.getAppointments().get(selectedPatient.getAppointments().size() - 1);
                walkInAppointment.updateStatus("Ongoing");
    
                JOptionPane.showMessageDialog(this, "Walk-In Confirmed! Status updated to 'Ongoing' for " + selectedPatient.getFirstName() + ".");
                System.out.println("Booking Successful for " + selectedPatient.getFirstName());
            } else {
                JOptionPane.showMessageDialog(this, "Appointment Could Not Be Created!");
                System.out.println("Booking Failed for " + selectedPatient.getFirstName());
            }
    
            // Dispose of the current window and go back to the starting screen
            dispose();
            new StartingScreen(patientController).setVisible(true);  // Ensure that the starting screen is visible
        } else {
            // If canceled, return to the starting screen without making any changes
            dispose();
            new StartingScreen(patientController).setVisible(true);
        }
    }
}    