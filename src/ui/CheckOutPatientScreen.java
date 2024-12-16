package ui;

import api.controller.PatientController;
import api.models.Appointment;
import api.models.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class CheckOutPatientScreen extends JFrame {
    private JLabel username;

    private static final int SCREEN_WIDTH = 1900;
    private static final int SCREEN_HEIGHT = 650;

    private PatientController patientController;

    public CheckOutPatientScreen(PatientController patientController) {
        this.patientController = patientController;

        setTitle("Check-out Patient");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Select Appointment to Check-Out");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        // List Panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Fetch all appointments with "Ongoing" status
        List<Appointment> ongoingAppointments = patientController.getPatients().stream()
                .flatMap(patient -> patient.getAppointments().stream())
                .filter(appointment -> "Ongoing".equals(appointment.getAppointmentStatus()))
                .collect(Collectors.toList());

        DefaultListModel<String> appointmentListModel = new DefaultListModel<>();
        for (Appointment appointment : ongoingAppointments) {
            String displayText = "Patient: " + appointment.getPatient().getFirstName() + " " +
                    appointment.getPatient().getLastName() + " | Date: " + appointment.getDate() +
                    " | Type: " + appointment.getAppointmentType();
            appointmentListModel.addElement(displayText);
        }

        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(appointmentList);

        JButton completeButton = new JButton("Mark as Completed");
        completeButton.setEnabled(false);

        // Enable button only if an appointment is selected
        appointmentList.addListSelectionListener(e -> completeButton.setEnabled(appointmentList.getSelectedIndex() != -1));

        // Action to mark appointment as completed
        completeButton.addActionListener(e -> {
            int selectedIndex = appointmentList.getSelectedIndex();
            if (selectedIndex != -1) {
                Appointment selectedAppointment = ongoingAppointments.get(selectedIndex);

                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Do you want to mark this appointment as completed?",
                        "Confirm Completion", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    selectedAppointment.updateStatus("Completed");
                    JOptionPane.showMessageDialog(this, "Appointment marked as completed.");

                    // Refresh the list after marking as completed
                    appointmentListModel.remove(selectedIndex);
                    ongoingAppointments.remove(selectedIndex);
                }
            }
        });

        listPanel.add(scrollPane);
        listPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        listPanel.add(completeButton);
        mainPanel.add(listPanel);

        // Back Button to navigate to Starting Screen
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen(patientController);
        });
        mainPanel.add(backButton);

        add(mainPanel);
        setVisible(true);
    }
}
