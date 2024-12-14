package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class CheckInPatientScreen extends JFrame {

    private static DefaultListModel<String> patientsModel = new DefaultListModel<>();
    private static final int SCREEN_WIDTH = 1900;
    private static final int SCREEN_HEIGHT = 650;

    private static final Map<String, List<String>> patientAppointments = new HashMap<>();

    static {
        patientAppointments.put("John Doe", Arrays.asList("2024-12-20 at 10:00 AM", "2024-12-25 at 2:00 PM"));
        patientAppointments.put("Jane Smith", Collections.singletonList("2024-12-18 at 1:00 PM"));
        patientAppointments.put("Alice Johnson", Arrays.asList("2024-12-19 at 9:30 AM", "2024-12-22 at 11:00 AM"));
    }

    public CheckInPatientScreen() {
        if (patientsModel.isEmpty()) {
            patientsModel.addElement("John Doe");
            patientsModel.addElement("Jane Smith");
            patientsModel.addElement("Alice Johnson");
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
                    String selectedPatient = patientList.getSelectedValue();
                    if (selectedPatient != null) {
                        dispose(); // Close current screen
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
            new StartingScreen(); 
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
        optionsFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        JLabel appointmentsLabel = new JLabel("Future Appointments:");
        appointmentsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        optionsPanel.add(appointmentsLabel);

        List<String> appointments = patientAppointments.getOrDefault(patientName, new ArrayList<>());
        DefaultListModel<String> appointmentListModel = new DefaultListModel<>();
        appointments.forEach(appointmentListModel::addElement);

        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentList);
        appointmentScrollPane.setPreferredSize(new Dimension(500, 200));
        optionsPanel.add(appointmentScrollPane);

        JButton walkInButton = new JButton("Walk-in");
        walkInButton.addActionListener(e -> {
            optionsFrame.dispose();
            showWalkInForm(patientName);
        });

        JButton appointmentButton = new JButton("Appointment");
        appointmentButton.addActionListener(e -> {
            String selectedAppointment = appointmentList.getSelectedValue();
            if (selectedAppointment != null) {
                int confirm = JOptionPane.showConfirmDialog(optionsFrame, "Confirm check-in for " + patientName + " on " + selectedAppointment + "?", "Check Appointment", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(optionsFrame, "Appointment status for " + patientName + " is now 'Ongoing'.");
                    optionsFrame.dispose();
                    new CheckInPatientScreen();
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

    private void showWalkInForm(String patientName) {
    JFrame walkInFrame = new JFrame("Walk-in Details for " + patientName);
    walkInFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
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

    String[] appointmentTypes = {"Surgery", "Follow-up", "General Consultation"};
    JComboBox<String> appointmentTypeDropdown = new JComboBox<>(appointmentTypes);
    walkInPanel.add(new JLabel("Appointment Type:"));
    walkInPanel.add(appointmentTypeDropdown);

    JTextField locationField = new JTextField();
    locationField.setPreferredSize(new Dimension(250, 30));
    walkInPanel.add(new JLabel("Location:"));
    walkInPanel.add(locationField);

    JTextField durationField = new JTextField();
    durationField.setPreferredSize(new Dimension(250, 30));
    walkInPanel.add(new JLabel("Duration (in minutes):"));
    walkInPanel.add(durationField);

    JTextField dateField = new JTextField();
    dateField.setPreferredSize(new Dimension(250, 30));
    walkInPanel.add(new JLabel("Appointment Date (YYYY-MM-DD):"));
    walkInPanel.add(dateField);

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
            JOptionPane.showMessageDialog(walkInFrame, "Walk-in saved for " + patientName + " with Dr. " + selectedDoctor + 
                ": " + appointmentType + " on " + appointmentDate + ".\nLocation: " + location + "\nDuration: " + duration + " mins");
            
            JOptionPane.showMessageDialog(walkInFrame, "Appointment status for " + patientName + " is now 'Ongoing'.");
            
            walkInFrame.dispose(); 
            new CheckInPatientScreen(); 
        } else {
            JOptionPane.showMessageDialog(walkInFrame, "Please fill all fields.");
        }
    });

    walkInPanel.add(saveButton);
    walkInFrame.add(walkInPanel);
    walkInFrame.setVisible(true);
    
    }   

}
