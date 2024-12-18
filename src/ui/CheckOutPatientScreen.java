package ui;

import api.controller.PatientController;
import api.models.Appointment;
import api.models.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CheckOutPatientScreen extends JFrame {
    private static DefaultListModel<String> appointmentModel = new DefaultListModel<>();
    private static final int SCREEN_WIDTH = 1900;
    private static final int SCREEN_HEIGHT = 650;

    private PatientController patientController;
    private Map<String, Appointment> appointmentMap = new HashMap<>();

    public CheckOutPatientScreen(PatientController patientController) {
        this.patientController = patientController;

        // Get list of ongoing appointments and populate the model and map
        List<Appointment> ongoingAppointments = patientController.getPatients().stream()
                .flatMap(patient -> patient.getAppointments().stream())
                .filter(appointment -> "Ongoing".equals(appointment.getAppointmentStatus()))
                .collect(Collectors.toList());

        if (ongoingAppointments.isEmpty()) {
            // If no ongoing appointments, show a message and return to the starting screen
            JOptionPane.showMessageDialog(this, "No ongoing appointments available.");
            dispose();
            new StartingScreen(patientController).setVisible(true); // Ensure the starting screen is visible
        } else {
            // Otherwise, populate the list and show the dialog
            appointmentModel.clear();
            for (Appointment appointment : ongoingAppointments) {
                String appointmentDetails = "Patient: " + appointment.getPatient().getFirstName() + " " +
                        appointment.getPatient().getLastName() + " | Date: " + appointment.getDate() +
                        " | Type: " + appointment.getAppointmentType();
                appointmentModel.addElement(appointmentDetails);
                appointmentMap.put(appointmentDetails, appointment);
            }

            showAppointmentListDialog();
        }
    }

    private void showAppointmentListDialog() {
        JList<String> appointmentList = new JList<>(appointmentModel);
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 16));

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(appointmentList),
                "Select an Ongoing Appointment to Check-Out",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.CANCEL_OPTION) {
            // If the user clicks "Cancel", dispose of the current frame and return to the previous screen.
            dispose();
            new StartingScreen(patientController).setVisible(true);  // Ensure it's made visible
            return;
        }

        String selectedAppointmentDetails = appointmentList.getSelectedValue();
        if (selectedAppointmentDetails != null) {
            Appointment selectedAppointment = appointmentMap.get(selectedAppointmentDetails);
            if (selectedAppointment != null) {
                // Show confirmation to mark the appointment as completed
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Confirm check-out for appointment:\n" + selectedAppointmentDetails,
                        "Confirm Appointment Check-Out",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    selectedAppointment.updateStatus("Completed");
                    JOptionPane.showMessageDialog(this,
                            "Appointment status updated to 'Completed' for " + selectedAppointment.getPatient().getFirstName() + ".");
                    // Dispose the current CheckOutPatientScreen and return to the StartingScreen
                    dispose();
                    new StartingScreen(patientController).setVisible(true);  // Return to the starting screen after confirmation
                }
            }
        }
    }
}
