package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckInPatientScreen extends JFrame {

    private static DefaultListModel<String> patientsModel = new DefaultListModel<>();

    public CheckInPatientScreen() {
        // Add some dummy patients for demonstration
        if (patientsModel.isEmpty()) {
            patientsModel.addElement("John Doe");
            patientsModel.addElement("Jane Smith");
            patientsModel.addElement("Alice Johnson");
        }

        setTitle("Check-in Patient");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Patient List");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(label);

        // Display patient list
        JList<String> patientList = new JList<>(patientsModel);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientList.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add a mouse listener to handle selection
        patientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click to select a patient
                    String selectedPatient = patientList.getSelectedValue();
                    if (selectedPatient != null) {
                        showCheckInOptions(selectedPatient);
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
            new StartingScreen(); // Navigate to StartingScreen
        });

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);
        mainPanel.add(backButtonPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void showCheckInOptions(String patientName) {
        JFrame optionsFrame = new JFrame("Check-in Options for " + patientName);
        optionsFrame.setSize(400, 300);
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        JButton walkInButton = new JButton("Walk-in");
        walkInButton.addActionListener(e -> {
            optionsFrame.dispose();
            showWalkInForm(patientName);
        });

        JButton appointmentButton = new JButton("Appointment");
        appointmentButton.addActionListener(e -> {
            optionsFrame.dispose();
            checkAppointment(patientName);
        });

        optionsPanel.add(new JLabel("Select Check-in Option:"));
        optionsPanel.add(walkInButton);
        optionsPanel.add(appointmentButton);

        optionsFrame.add(optionsPanel);
        optionsFrame.setVisible(true);
    }

    private void showWalkInForm(String patientName) {
        JFrame walkInFrame = new JFrame("Walk-in Details for " + patientName);
        walkInFrame.setSize(400, 300);
        walkInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel walkInPanel = new JPanel();
        walkInPanel.setLayout(new BoxLayout(walkInPanel, BoxLayout.Y_AXIS));

        JTextField reasonField = new JTextField();
        reasonField.setPreferredSize(new Dimension(250, 30));
        walkInPanel.add(new JLabel("Reason for Visit:"));
        walkInPanel.add(reasonField);

        String[] doctors = {"Dr. Smith", "Dr. Johnson", "Dr. Williams", "Dr. Brown"};
        JComboBox<String> doctorDropdown = new JComboBox<>(doctors);
        walkInPanel.add(new JLabel("Select Doctor:"));
        walkInPanel.add(doctorDropdown);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String reason = reasonField.getText();
            String selectedDoctor = (String) doctorDropdown.getSelectedItem();

            if (!reason.isEmpty() && selectedDoctor != null) {
                JOptionPane.showMessageDialog(walkInFrame, "Walk-in saved for " + patientName + " with " + selectedDoctor + ": " + reason);
                walkInFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(walkInFrame, "Please fill all fields.");
            }
        });

        walkInPanel.add(saveButton);
        walkInFrame.add(walkInPanel);
        walkInFrame.setVisible(true);
    }

    private void checkAppointment(String patientName) {
        int confirm = JOptionPane.showConfirmDialog(this, "Confirm check-in for " + patientName + "?", "Check Appointment", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Appointment status for " + patientName + " is now 'Ongoing'.");
        }
    }
    /* 
    public static void main(String[] args) {
        new CheckInPatientScreen();
    }*/
}


