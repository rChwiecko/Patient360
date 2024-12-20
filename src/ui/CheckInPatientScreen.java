package ui;  // The package that the class belongs to. 'ui' indicates this class is part of the user interface.

import api.controller.PatientController;  // Importing the PatientController class which handles patient-related logic.
import api.models.Appointment;  // Importing the Appointment model class to work with appointment data.
import api.models.Doctor;  // Importing the Doctor model class to work with doctor data.
import api.models.Hospital;  // Importing the Hospital model class to work with hospital data.
import api.models.Patient;  // Importing the Patient model class to work with patient data.
import java.awt.*;  // Importing AWT (Abstract Window Toolkit) classes for creating the graphical user interface (GUI).
import java.awt.event.MouseAdapter;  // Importing MouseAdapter for handling mouse events.
import java.awt.event.MouseEvent;  // Importing MouseEvent to capture mouse-related events.
import java.time.LocalDateTime;  // Importing LocalDateTime to handle date and time functionality.
import java.util.*;  // Importing classes from the Java Collections framework (like List, HashMap, etc.).
import java.util.List;  // Importing List specifically for handling ordered collections of elements.
import javax.swing.*;  // Importing Swing classes for GUI components like JList, JOptionPane, etc.
import java.time.Duration;  // Importing Duration class to handle time durations.


public class CheckInPatientScreen extends JFrame {  // Declaring the CheckInPatientScreen class which extends JFrame.
    private static DefaultListModel<String> patientsModel = new DefaultListModel<>();  // A model that holds the patient names to be displayed.
    private static final int SCREEN_WIDTH = 1900;  // Constant defining the width of the screen/window.
    private static final int SCREEN_HEIGHT = 650;  // Constant defining the height of the screen/window.
    
    private PatientController patientController;  // Reference to the PatientController to handle patient operations.
    private Map<String, Patient> patientMap = new HashMap<>();  // A map to store patient names as keys and Patient objects as values.

    public CheckInPatientScreen(PatientController patientController) {  // Constructor that accepts a PatientController object.
        this.patientController = patientController;  // Assigning the passed PatientController object to the field.

        // Get list of patients and populate the model and map
        List<Patient> patients = patientController.getPatients();  // Fetching the list of patients from the PatientController.
        patientsModel.clear();  // Clearing the existing patients data in the list model before adding new data.

        for (Patient patient : patients) {  // Iterating over the list of patients.
            String fullName = patient.getFirstName() + " " + patient.getLastName();  // Creating a full name for each patient.
            patientsModel.addElement(fullName);  // Adding the full name to the list model.
            patientMap.put(fullName, patient);  // Storing the patient in the map with full name as key.
        }

        showPatientListDialog();  // Calling the method to display the patient selection dialog.
    }
    
    private void showPatientListDialog() {  // Method to display a dialog with a list of patients to select from.
        JList<String> patientList = new JList<>(patientsModel);  // Create a JList to display the list of patients.
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Allow only one patient to be selected.
        patientList.setFont(new Font("Arial", Font.PLAIN, 16));  // Set the font for the patient list.
    
        // Show a confirmation dialog with the list of patients.
        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(patientList),  // Place the patient list inside a scroll pane for better handling of long lists.
                "Select a Patient",  // Dialog title.
                JOptionPane.OK_CANCEL_OPTION,  // Options to either confirm or cancel.
                JOptionPane.PLAIN_MESSAGE  // Plain message style (no icon).
        );
    
        if (option == JOptionPane.CANCEL_OPTION) {  // If the user clicks "Cancel" in the dialog.
            // Dispose the current screen and show the starting screen again.
            dispose();
            new StartingScreen(patientController).setVisible(true);  // Ensure the starting screen is visible.
            return;  // Exit the method if the user cancels the selection.
        }
    
        String selectedPatientName = patientList.getSelectedValue();  // Get the name of the selected patient.
        if (selectedPatientName != null) {  // If a patient was selected.
            Patient selectedPatient = patientMap.get(selectedPatientName);  // Retrieve the full Patient object using the name from the map.
            if (selectedPatient != null) {  // If a valid patient object is found.
                // Show the check-in options dialog for the selected patient.
                showCheckInOptions(selectedPatient);
            }
        }
    }
    
    private void showCheckInOptions(Patient patient) {  // Method to show the available check-in options for a selected patient.
        String[] options = {"Appointment Made", "Walk-In"};  // The available check-in options: "Appointment Made" or "Walk-In".
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Check-In Option for " + patient.getFirstName() + " " + patient.getLastName(),  // Display patient's name in the dialog.
                "Check-In Options",  // Dialog title.
                JOptionPane.DEFAULT_OPTION,  // Default option (no icon).
                JOptionPane.PLAIN_MESSAGE,  // Plain message style (no icon).
                null,
                options,  // The check-in options to display.
                options[0]  // Default selection: "Appointment Made".
        );
    
        if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {  // If the user clicks "Cancel" or closes the dialog.
            dispose();  // Dispose of the current frame.
            new StartingScreen(patientController).setVisible(true);  // Return to the starting screen.
            return;  // Exit the method if the user cancels or closes the dialog.
        }
    
        if (choice == 0) {  // If the user selects "Appointment Made".
            // Show the list of appointments for the patient.
            showAppointmentList(patient);
        } else if (choice == 1) {  // If the user selects "Walk-In".
            // Show the walk-in form for the patient.
            showWalkInForm(patient);
        }
    }
    
    private void showAppointmentList(Patient patient) {  // Method to show the list of appointments for the selected patient.
        List<Appointment> appointments = patient.getAppointments();  // Get the list of appointments for the patient.
        if (appointments == null || appointments.isEmpty()) {  // If no appointments are found.
            // Show a message that there are no appointments for the patient.
            JOptionPane.showMessageDialog(this, "No appointments found for this patient.");
            dispose();  // Dispose of the current frame.
            new StartingScreen(patientController).setVisible(true);  // Return to the starting screen.
            return;  // Exit the method if there are no appointments.
        }
    
        // Create a list model to display the appointments in the dialog.
        DefaultListModel<String> appointmentListModel = new DefaultListModel<>();
        // A map to store appointment details.
        Map<String, Appointment> appointmentMap = new HashMap<>();
        for (Appointment appointment : appointments) {  // Loop through each appointment.
            // Format the appointment details as a string.
            String appointmentDetails = "Description: " + appointment.getDescription() +
                                        " | Type: " + appointment.getAppointmentType();
            appointmentListModel.addElement(appointmentDetails);  // Add the formatted appointment details to the list model.
            appointmentMap.put(appointmentDetails, appointment);  // Store the appointment object in the map.
        }
    
        // Create a JList to display the appointments in the dialog.
        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Allow only one selection.
    
        // Show a confirmation dialog with the list of appointments.
        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(appointmentList),  // Place the appointment list inside a scroll pane.
                "Select an Appointment",  // Dialog title.
                JOptionPane.OK_CANCEL_OPTION,  // Options to either confirm or cancel.
                JOptionPane.PLAIN_MESSAGE  // Plain message style (no icon).
        );
    
        if (option == JOptionPane.CANCEL_OPTION) {  // If the user clicks "Cancel".
            dispose();  // Dispose of the current frame.
            new StartingScreen(patientController).setVisible(true);  // Return to the starting screen.
            return;  // Exit the method if the user cancels the selection.
        }
    
        if (option == JOptionPane.OK_OPTION) {  // If the user clicks "OK" to confirm the selection.
            String selectedAppointmentDetails = appointmentList.getSelectedValue();  // Get the details of the selected appointment.
            if (selectedAppointmentDetails != null) {  // If an appointment is selected.
                Appointment selectedAppointment = appointmentMap.get(selectedAppointmentDetails);  // Get the corresponding Appointment object.
    
                // Check if the appointment is already checked in (status is "Ongoing").
                if ("Ongoing".equals(selectedAppointment.getAppointmentStatus())) {
                    // Show a message if the appointment is already checked in.
                    JOptionPane.showMessageDialog(this, "This appointment has already been checked in.");
                    dispose();  // Dispose of the current frame.
                    new StartingScreen(patientController).setVisible(true);  // Return to the starting screen.
                    return;  // Exit the method if the appointment is already checked in.
                }
    
                // Show a confirmation dialog to check-in the appointment.
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Confirm check-in for appointment:\n" + selectedAppointmentDetails,  // Display the selected appointment details.
                        "Confirm Appointment",  // Dialog title.
                        JOptionPane.YES_NO_OPTION  // Options to confirm or cancel.
                );
    
                if (confirm == JOptionPane.YES_OPTION) {  // If the user confirms the check-in.
                    selectedAppointment.updateStatus("Ongoing");  // Update the appointment status to "Ongoing".
                    // Show a success message that the appointment has been confirmed and its status has been updated.
                    JOptionPane.showMessageDialog(
                            this,
                            "Appointment Confirmed! Status updated to 'Ongoing' for " + patient.getFirstName() + "."
                    );
    
                    // Dispose of the current frame and return to the starting screen.
                    dispose();
                    new StartingScreen(patientController).setVisible(true);  // Return to the starting screen after confirmation.
                }
            }
        }
    }
    
    private void showWalkInForm(Patient selectedPatient) {  // Method to display the walk-in appointment form for a selected patient.
        JPanel walkInPanel = new JPanel();  // Create a JPanel to hold the form components.
        walkInPanel.setLayout(new BoxLayout(walkInPanel, BoxLayout.Y_AXIS));  // Set the layout of the panel to vertical stacking.
    
        // 1. Date Panel - Show current date
        JLabel dateLabel = new JLabel("Current Date: " + java.time.LocalDate.now());  // Display the current date using LocalDate.
        walkInPanel.add(dateLabel);  // Add the date label to the panel.
    
        // 2. Doctor Selection Panel
        List<Doctor> availableDoctors = patientController.getDoctors();  // Retrieve a list of available doctors from the controller.
        if (availableDoctors.isEmpty()) {  // If no doctors are available.
            JOptionPane.showMessageDialog(this, "No doctors available.");  // Show a message indicating no doctors are available.
            return;  // Exit the method early since no doctors can be selected.
        }
    
        // Prepare the list of doctor names for the combo box.
        String[] doctorOptions = new String[availableDoctors.size()];
        for (int i = 0; i < availableDoctors.size(); i++) {
            doctorOptions[i] = availableDoctors.get(i).getFirstName();  // Assuming Doctor has a getFirstName() method for display.
        }
        JComboBox<String> doctorComboBox = new JComboBox<>(doctorOptions);  // Create a JComboBox for doctor selection.
        walkInPanel.add(new JLabel("Select a Doctor:"));  // Add a label above the combo box.
        walkInPanel.add(doctorComboBox);  // Add the combo box to the panel.
    
        // 3. Appointment Type Panel
        String[] appointmentTypes = {"General Consultation", "Surgery", "Follow-Up"};  // Define appointment types.
        JComboBox<String> appointmentTypeComboBox = new JComboBox<>(appointmentTypes);  // Create a combo box for appointment type selection.
        walkInPanel.add(new JLabel("Select Appointment Type:"));  // Add a label for appointment type.
        walkInPanel.add(appointmentTypeComboBox);  // Add the combo box for appointment type to the panel.
    
        // 4. Appointment Duration Panel
        String[] durations = {"30 minutes", "1 hour"};  // Define available appointment durations.
        JComboBox<String> durationComboBox = new JComboBox<>(durations);  // Create a combo box for duration selection.
        walkInPanel.add(new JLabel("Select Duration:"));  // Add a label for duration.
        walkInPanel.add(durationComboBox);  // Add the duration combo box to the panel.
    
        // 5. Appointment Description Panel
        JTextField descriptionField = new JTextField(20);  // Create a text field for entering the appointment description.
        walkInPanel.add(new JLabel("Description of Appointment:"));  // Add a label for the description field.
        walkInPanel.add(descriptionField);  // Add the description text field to the panel.
    
        // Confirm or Cancel
        int option = JOptionPane.showConfirmDialog(this, walkInPanel,  // Show the walk-in form inside a dialog.
                                                    "Walk-In Appointment for " + selectedPatient.getFirstName() + " " + selectedPatient.getLastName(),
                                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);  // Provide OK/Cancel options.
    
        if (option == JOptionPane.OK_OPTION) {  // If the user clicks "OK" to confirm the form.
            // Gather all input data
            String selectedDoctorName = (String) doctorComboBox.getSelectedItem();  // Get the selected doctor name from the combo box.
            if (selectedDoctorName == null || selectedDoctorName.isEmpty()) {  // If no doctor was selected.
                JOptionPane.showMessageDialog(this, "Please select a valid doctor.");  // Show an error message.
                return;  // Exit if no doctor is selected.
            }
    
            // Find the corresponding Doctor object based on the selected name.
            Doctor selectedDoctor = null;
            for (Doctor doctor : availableDoctors) {
                if (doctor.getFirstName().equals(selectedDoctorName)) {  // Match the selected name with available doctors.
                    selectedDoctor = doctor;
                    break;  // Exit the loop once a match is found.
                }
            }
    
            if (selectedDoctor == null) {  // If the doctor was not found (which should be rare given the selection).
                JOptionPane.showMessageDialog(this, "Invalid doctor selected.");
                return;  // Exit if the selected doctor is invalid.
            }
    
            // Handle appointment type selection
            String selectedAppointmentType = (String) appointmentTypeComboBox.getSelectedItem();  // Get the selected appointment type.
            switch (selectedAppointmentType) {
                case "General Consultation":
                    selectedAppointmentType = "general";  // Map the selected type to a database-friendly string.
                    break;
                case "Surgery":
                    selectedAppointmentType = "surgery";
                    break;
                case "Follow-Up":
                    selectedAppointmentType = "follow";
                    break;
            }
            String selectedDurationStr = (String) durationComboBox.getSelectedItem();  // Get the selected duration.
            Duration selectedDuration = selectedDurationStr.equals("30 minutes") ? Duration.ofMinutes(30) : Duration.ofHours(1);  // Map to Duration object.
            String description = descriptionField.getText();  // Get the description entered by the user.
    
            // Check doctor availability (this may involve checking current appointments or schedule).
            LocalDateTime currentDateTime = LocalDateTime.now();  // Get the current date and time for the appointment.
            Hospital hospital = Hospital.getInstance();  // Get the current hospital instance (singleton pattern).
            String preAppointmentInstructions = "";  // Placeholder for pre-appointment instructions, if any.
    
            // Book the appointment via the patient controller.
            boolean bookingRes = patientController.bookAppointment(
                selectedPatient,  // Pass the selected patient.
                selectedDoctor,  // Pass the selected doctor object (not just the name).
                selectedAppointmentType,  // Pass the selected appointment type.
                description,  // Pass the appointment description.
                currentDateTime,  // Set the appointment time as current.
                hospital,  // Pass the hospital where the appointment is being booked.
                selectedDuration,  // Set the duration of the appointment.
                preAppointmentInstructions  // Include any pre-appointment instructions.
            );
    
            // Check result of the booking and show a confirmation or failure message.
            if (bookingRes) {  // If the appointment was successfully booked.
                // Immediately update the appointment status to "Ongoing" for the walk-in patient.
                Appointment walkInAppointment = selectedPatient.getAppointments().get(selectedPatient.getAppointments().size() - 1);  // Get the most recent appointment.
                walkInAppointment.updateStatus("Ongoing");  // Update the status to "Ongoing".
    
                JOptionPane.showMessageDialog(this, "Walk-In Confirmed! Status updated to 'Ongoing' for " + selectedPatient.getFirstName() + ".");
                System.out.println("Booking Successful for " + selectedPatient.getFirstName());  // Log the successful booking.
            } else {  // If the booking failed.
                JOptionPane.showMessageDialog(this, "Appointment Could Not Be Created!");
                System.out.println("Booking Failed for " + selectedPatient.getFirstName());  // Log the failure.
            }
    
            // Dispose of the current window and return to the starting screen.
            dispose();  // Close the current window.
            new StartingScreen(patientController).setVisible(true);  // Show the starting screen again.
        } else {
            // If the user clicks "Cancel" or closes the dialog, return to the starting screen without making changes.
            dispose();  // Close the current window.
            new StartingScreen(patientController).setVisible(true);  // Show the starting screen again.
        }
    }
}    