package ui;

import api.controller.PatientController;  // Importing the PatientController class to manage patient-related operations.
import api.models.Appointment;  // Importing the Appointment model class to work with appointments.
import api.models.Patient;  // Importing the Patient model class to work with patient details.

import javax.swing.*;  // Importing Swing components for creating the GUI (e.g., JList, JPanel, JOptionPane).
import java.awt.*;  // Importing AWT classes for GUI-related functionalities like Font and Layout.
import java.util.List;  // Importing List interface for handling lists of objects.
import java.util.Map;  // Importing Map interface for storing key-value pairs (e.g., mapping appointment details to appointment objects).
import java.util.HashMap;  // Importing HashMap class to implement the Map interface and store appointments.
import java.util.stream.Collectors;  // Importing Collectors for stream operations like filtering and collecting appointments.


public class CheckOutPatientScreen extends JFrame {  
    // CheckOutPatientScreen extends JFrame to create a window for patient check-out functionality
    private static DefaultListModel<String> appointmentModel = new DefaultListModel<>();  
    // A DefaultListModel to store the appointment details that will be displayed in a list
    private static final int SCREEN_WIDTH = 1900;  
    // Constant for setting the width of the screen
    private static final int SCREEN_HEIGHT = 650;  
    // Constant for setting the height of the screen

    private PatientController patientController;  
    // Declare a reference to the PatientController for managing patients
    private Map<String, Appointment> appointmentMap = new HashMap<>();  
    // A map to associate appointment details with the actual Appointment object

    public CheckOutPatientScreen(PatientController patientController) {  
        // Constructor for initializing the CheckOutPatientScreen with a PatientController
        this.patientController = patientController;  
        // Initialize the patientController with the provided PatientController

        // Get list of ongoing appointments and populate the model and map
        List<Appointment> ongoingAppointments = patientController.getPatients().stream()  
            // Stream through all the patients
            .flatMap(patient -> patient.getAppointments().stream())  
            // Flatten the list of appointments for each patient into a single stream
            .filter(appointment -> "Ongoing".equals(appointment.getAppointmentStatus()))  
            // Filter the appointments to include only the ongoing ones
            .collect(Collectors.toList());  
            // Collect the ongoing appointments into a list

        if (ongoingAppointments.isEmpty()) {  
            // If there are no ongoing appointments, show a message and return to the starting screen
            JOptionPane.showMessageDialog(this, "No ongoing appointments available.");  
            // Show a dialog to inform the user that no ongoing appointments are available
            dispose();  
            // Close the current CheckOutPatientScreen window
            new StartingScreen(patientController).setVisible(true);  
            // Open the StartingScreen window
        } else {  
            // If there are ongoing appointments, proceed to populate the list and display the dialog
            appointmentModel.clear();  
            // Clear any existing data in the appointment model
            for (Appointment appointment : ongoingAppointments) {  
                // Loop through each ongoing appointment
                String appointmentDetails = "Patient: " + appointment.getPatient().getFirstName() + " " +  
                        appointment.getPatient().getLastName() + " | Date: " + appointment.getDate() +  
                        " | Type: " + appointment.getAppointmentType();  
                // Prepare a string of appointment details to display (patient name, date, and type)
                appointmentModel.addElement(appointmentDetails);  
                // Add the appointment details string to the list model
                appointmentMap.put(appointmentDetails, appointment);  
                // Put the appointment details in the map, with the details string as the key and the appointment as the value
            }

            showAppointmentListDialog();  
            // Show the dialog with the list of ongoing appointments
        }
    }

    private void showAppointmentListDialog() {  
        // Method to display a dialog showing the list of ongoing appointments
        JList<String> appointmentList = new JList<>(appointmentModel);  
        // Create a JList with the appointment details from the appointment model
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        // Set the selection mode to single selection, allowing only one item to be selected
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 16));  
        // Set the font of the list to Arial with a size of 16

        int option = JOptionPane.showConfirmDialog(  
                this,  
                new JScrollPane(appointmentList),  
                "Select an Ongoing Appointment to Check-Out",  
                JOptionPane.OK_CANCEL_OPTION,  
                JOptionPane.PLAIN_MESSAGE  
        );  
        // Show a confirmation dialog with the appointment list in a scroll pane. User can select one appointment and click OK or Cancel

        if (option == JOptionPane.CANCEL_OPTION) {  
            // If the user clicks "Cancel" in the dialog
            dispose();  
            // Close the current CheckOutPatientScreen window
            new StartingScreen(patientController).setVisible(true);  
            // Open the StartingScreen window
            return;  
            // Exit the method
        }

        String selectedAppointmentDetails = appointmentList.getSelectedValue();  
        // Get the details of the selected appointment from the list
        if (selectedAppointmentDetails != null) {  
            // If an appointment is selected
            Appointment selectedAppointment = appointmentMap.get(selectedAppointmentDetails);  
            // Get the Appointment object corresponding to the selected details from the map
            if (selectedAppointment != null) {  
                // If the selected appointment exists
                int confirm = JOptionPane.showConfirmDialog(  
                        this,  
                        "Confirm check-out for appointment:\n" + selectedAppointmentDetails,  
                        "Confirm Appointment Check-Out",  
                        JOptionPane.YES_NO_OPTION  
                );  
                // Show a confirmation dialog asking the user to confirm the check-out for the selected appointment

                if (confirm == JOptionPane.YES_OPTION) {  
                    // If the user clicks "Yes"
                    selectedAppointment.updateStatus("Completed");  
                    // Update the status of the selected appointment to "Completed"
                    JOptionPane.showMessageDialog(this,  
                            "Appointment status updated to 'Completed' for " + selectedAppointment.getPatient().getFirstName() + ".");  
                    // Show a message dialog indicating that the appointment status has been updated
                    dispose();  
                    // Close the current CheckOutPatientScreen window
                    new StartingScreen(patientController).setVisible(true);  
                    // Open the StartingScreen window after the update
                }
            }
        }
    }
}

