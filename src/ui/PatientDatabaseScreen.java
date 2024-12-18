package ui;

import api.controller.PatientController;
import api.models.Doctor;
import api.models.Patient;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PatientDatabaseScreen extends JFrame {
    private final PatientController patientController;
    private Patient selectedPatient;

    public PatientDatabaseScreen(PatientController patientController) {
        this.patientController = patientController;

        // Set up the frame
        setTitle("Access Patient Database");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // Create the logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(null);
        logoPanel.setBackground(Color.WHITE);

        // Load and scale the logo image
        ImageIcon image1 = new ImageIcon("ui/imgs/Patient360Logo.png");
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(image1);
        imageLabel.setBounds(20, 20, image1.getIconWidth(), image1.getIconHeight());

        logoPanel.add(imageLabel);
        mainPanel.add(logoPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Create buttons for "Add a Patient" and "Modify a Patient"
        JButton addPatientButton = new JButton("Add a Patient");
        addPatientButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addPatientButton.setPreferredSize(new Dimension(200, 50));
        addPatientButton.addActionListener(e -> {
            dispose(); // Close this window
            new AddPatientScreen(); // Open blank Add Patient screen
        });

        JButton modifyPatientButton = new JButton("Modify a Patient");
        modifyPatientButton.setFont(new Font("Arial", Font.PLAIN, 16));
        modifyPatientButton.setPreferredSize(new Dimension(200, 50));
        modifyPatientButton.addActionListener(e -> {
            Patient patient = selectPatient();
            if (patient != null) {
                dispose(); // Close this window
                new ModifyPatientScreen(patient); // Open blank Modify Patient screen
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addPatientButton);
        buttonPanel.add(modifyPatientButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen(patientController); // Go back to the starting screen
        });

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);

        mainPanel.add(backButtonPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private Patient selectPatient() {
        // Fetch all patients
        List<Patient> patients = patientController.getPatients();

        // Convert the list of patients to an array of strings for display
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

            if (confirm == JOptionPane.YES_OPTION) {
                this.selectedPatient = chosen;
                return chosen;
            }
        }
        return null; // Return null if no patient is selected
    }

    private class AddPatientScreen extends JFrame {
        private JTextField firstNameField;
        private JTextField lastNameField;
        private JTextField emailField;
        private JTextField phoneField;
        private JComboBox<Doctor> doctorDropdown;
    
        public AddPatientScreen() {
            setTitle("Add a Patient");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null); // Center the window
    
            // Main panel
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
            // First Name Field
            panel.add(new JLabel("First Name:"));
            firstNameField = new JTextField(20);
            panel.add(firstNameField);
            panel.add(Box.createVerticalStrut(10));
    
            // Last Name Field
            panel.add(new JLabel("Last Name:"));
            lastNameField = new JTextField(20);
            panel.add(lastNameField);
            panel.add(Box.createVerticalStrut(10));
    
            // Email Field
            panel.add(new JLabel("Email:"));
            emailField = new JTextField(20);
            panel.add(emailField);
            panel.add(Box.createVerticalStrut(10));
    
            // Phone Number Field
            panel.add(new JLabel("Phone Number:"));
            phoneField = new JTextField(20);
            panel.add(phoneField);
            panel.add(Box.createVerticalStrut(10));
    
            // Doctor Dropdown
            panel.add(new JLabel("Assign a Doctor:"));
            List<Doctor> doctors = patientController.getDoctors();
            doctorDropdown = new JComboBox<>(doctors.toArray(new Doctor[0])); // Populate dropdown with Doctor objects
    
            // Set a custom renderer to display the doctor's full name in the dropdown
            doctorDropdown.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof Doctor) {
                        Doctor doctor = (Doctor) value;
                        value = doctor.getFirstName() + " " + doctor.getLastName(); // Display full name
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });
    
            panel.add(doctorDropdown);
            panel.add(Box.createVerticalStrut(20));
            
            // Add Patient Button
            JButton addButton = new JButton("Add Patient");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addButton.addActionListener(e -> addPatientAction());

            // Cancel Button
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            cancelButton.addActionListener(e -> {
                dispose();
                new PatientDatabaseScreen(patientController);
            });

            // Place buttons in a separate panel with FlowLayout
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            buttonPanel.add(addButton);
            buttonPanel.add(cancelButton);

            panel.add(Box.createVerticalStrut(10)); // Add spacing
            panel.add(buttonPanel); // Add the button panel

    
            getContentPane().add(panel);
            setVisible(true);
        }
    
        private void addPatientAction() {
            // Retrieve input values
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            Doctor selectedDoctor = (Doctor) doctorDropdown.getSelectedItem(); // Get the selected Doctor object
    
            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || selectedDoctor == null) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Check if phone number is numeric
            if (!phone.matches("\\d+")) { // Ensures the phone number contains only digits
                JOptionPane.showMessageDialog(this, "Phone number must contain only numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Call patientController.addPatient
            boolean success = patientController.addPatient(firstName, lastName, email, phone, selectedDoctor);
    
            if (success) {
                JOptionPane.showMessageDialog(this, "Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the window
                new StartingScreen(patientController);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    

    // Blank screen for Modify Patient functionality
    private class ModifyPatientScreen extends JFrame {
        public ModifyPatientScreen(Patient patient) {
            setTitle("Modify Patient: " + patient.getFirstName() + " " + patient.getLastName());
            setSize(500, 350); // Adjusted height for the back button
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null); // Center the window
    
            // Main panel
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
            // Welcome label
            JLabel titleLabel = new JLabel("Modify Patient: " + patient.getFirstName() + " " + patient.getLastName(), SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(titleLabel);
            panel.add(Box.createVerticalStrut(20));
    
            // Add Prescription Button
            JButton addPrescriptionButton = new JButton("Add Prescription");
            addPrescriptionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addPrescriptionButton.setPreferredSize(new Dimension(200, 40));
            addPrescriptionButton.addActionListener(e -> new AddPrescriptionScreen(patient)); // Open Add Prescription
            panel.add(addPrescriptionButton);
            panel.add(Box.createVerticalStrut(20));
    
            // Add Additional Information Button
            JButton addInfoButton = new JButton("Add Additional Information");
            addInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addInfoButton.setPreferredSize(new Dimension(200, 40));
            addInfoButton.addActionListener(e -> new AddInformationScreen(patient)); // Open Additional Info
            panel.add(addInfoButton);
            panel.add(Box.createVerticalStrut(20));
    
            // Back Button
            JButton backButton = new JButton("Back");
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.setPreferredSize(new Dimension(150, 40));
            backButton.addActionListener(e -> {
                dispose();
                new PatientDatabaseScreen(patientController); // Return to Patient Database Screen
            });
            panel.add(backButton);
            panel.add(Box.createVerticalStrut(20));
    
            // Add the panel to the frame
            getContentPane().add(panel);
            setVisible(true);
        }

    
        // Placeholder for Add Prescription Screen
    private class AddPrescriptionScreen extends JFrame {
            private JTextField medicationNameField;
            private JTextField dosageField;
            private JTextField frequencyField;
            private JTextField prescriptionDateField;
            private JTextField expiryDateField;
            private JTextField instructionsField;
            private JTextField refillCountField;
        
            public AddPrescriptionScreen(Patient patient) {
                setTitle("Add Prescription for: " + patient.getFirstName() + " " + patient.getLastName());
                setSize(600, 600);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setLocationRelativeTo(null); // Center the window
        
                // Main panel
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
                // Patient Information
                JLabel titleLabel = new JLabel("Prescription for: " + patient.getFirstName() + " " + patient.getLastName(), SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(titleLabel);
                panel.add(Box.createVerticalStrut(20));
        
                // Medication Name
                panel.add(new JLabel("Medication Name:"));
                medicationNameField = new JTextField(20);
                panel.add(medicationNameField);
                panel.add(Box.createVerticalStrut(10));
        
                // Dosage
                panel.add(new JLabel("Dosage:"));
                dosageField = new JTextField(20);
                panel.add(dosageField);
                panel.add(Box.createVerticalStrut(10));
        
                // Frequency
                panel.add(new JLabel("Frequency:"));
                frequencyField = new JTextField(20);
                panel.add(frequencyField);
                panel.add(Box.createVerticalStrut(10));
        
                // Prescription Date
                panel.add(new JLabel("Prescription Date (YYYY-MM-DD):"));
                prescriptionDateField = new JTextField(20);
                panel.add(prescriptionDateField);
                panel.add(Box.createVerticalStrut(10));
        
                // Expiry Date
                panel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
                expiryDateField = new JTextField(20);
                panel.add(expiryDateField);
                panel.add(Box.createVerticalStrut(10));
        
                // Instructions
                panel.add(new JLabel("Instructions:"));
                instructionsField = new JTextField(20);
                panel.add(instructionsField);
                panel.add(Box.createVerticalStrut(10));
        
                // Refill Count
                panel.add(new JLabel("Refill Count:"));
                refillCountField = new JTextField(20);
                panel.add(refillCountField);
                panel.add(Box.createVerticalStrut(20));
        
                // Buttons
                JButton addButton = new JButton("Add Prescription");
                addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                addButton.addActionListener(e -> {
                    addPrescriptionAction(patient);
                });
        
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                cancelButton.addActionListener(e -> {
                    dispose();
                });
        
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.add(addButton);
                buttonPanel.add(cancelButton);
        
                panel.add(buttonPanel);
        
                getContentPane().add(panel);
                setVisible(true);
            }
        
            private void addPrescriptionAction(Patient patient) {
                // Retrieve input values
                String medicationName = medicationNameField.getText().trim();
                String dosage = dosageField.getText().trim();
                String frequency = frequencyField.getText().trim();
                String prescriptionDate = prescriptionDateField.getText().trim();
                String expiryDate = expiryDateField.getText().trim();
                String instructions = instructionsField.getText().trim();
                String refillCountStr = refillCountField.getText().trim();
        
                // Validate inputs
                if (medicationName.isEmpty() || dosage.isEmpty() || frequency.isEmpty() ||
                        prescriptionDate.isEmpty() || expiryDate.isEmpty() ||
                        instructions.isEmpty() || refillCountStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                int refillCount;
                try {
                    refillCount = Integer.parseInt(refillCountStr); // Validate refill count as an integer
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Refill count must be a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // For now, just display the prescription data
                JOptionPane.showMessageDialog(this,
                        "Prescription added successfully!\n" +
                                "Patient: " + patient.getFirstName() + " " + patient.getLastName() + "\n" +
                                "Medication: " + medicationName + "\n" +
                                "Dosage: " + dosage + "\n" +
                                "Frequency: " + frequency + "\n" +
                                "Prescription Date: " + prescriptionDate + "\n" +
                                "Expiry Date: " + expiryDate + "\n" +
                                "Instructions: " + instructions + "\n" +
                                "Refill Count: " + refillCount,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
        
                // Close the form after success
                dispose();
            }
        }
        
    
        // Placeholder for Add Additional Information Screen
        private class AddInformationScreen extends JFrame {
            private JTextArea newInfoTextArea;
            private DefaultListModel<String> recordListModel;
            private JList<String> recordList;
        
            public AddInformationScreen(Patient patient) {
                setTitle("Add Information for: " + patient.getFirstName() + " " + patient.getLastName());
                setSize(500, 400);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setLocationRelativeTo(null); // Center the window
        
                // Main panel
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                // Title Label
                JLabel titleLabel = new JLabel("Medical Record for: " + patient.getFirstName() + " " + patient.getLastName());
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(titleLabel, BorderLayout.NORTH);
        
                // Medical Record List
                recordListModel = new DefaultListModel<>();
                for (String record : patient.getMedicalRecord()) {
                    recordListModel.addElement(record); // Add each existing record to the list
                }
                recordList = new JList<>(recordListModel);
                recordList.setFont(new Font("Arial", Font.PLAIN, 14));
                JScrollPane scrollPane = new JScrollPane(recordList);
                scrollPane.setBorder(BorderFactory.createTitledBorder("Existing Medical Record"));
                mainPanel.add(scrollPane, BorderLayout.CENTER);
        
                // Panel for Adding New Information
                JPanel addInfoPanel = new JPanel();
                addInfoPanel.setLayout(new BoxLayout(addInfoPanel, BoxLayout.Y_AXIS));
                addInfoPanel.setBorder(BorderFactory.createTitledBorder("Add New Information"));
        
                newInfoTextArea = new JTextArea(4, 30);
                newInfoTextArea.setWrapStyleWord(true);
                newInfoTextArea.setLineWrap(true);
                JScrollPane textAreaScrollPane = new JScrollPane(newInfoTextArea,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
                addInfoPanel.add(new JLabel("Enter new medical record information:"));
                addInfoPanel.add(Box.createVerticalStrut(10));
                addInfoPanel.add(textAreaScrollPane);
                addInfoPanel.add(Box.createVerticalStrut(10));
        
                // Buttons
                JButton addButton = new JButton("Add Information");
                addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                addButton.addActionListener(e -> {
                    addNewInformation(patient);
                    dispose();
                    new StartingScreen(patientController);
                });
        
                JButton closeButton = new JButton("Close");
                closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                closeButton.addActionListener(e -> dispose());
        
                addInfoPanel.add(addButton);
                addInfoPanel.add(Box.createVerticalStrut(10));
                addInfoPanel.add(closeButton);
        
                mainPanel.add(addInfoPanel, BorderLayout.SOUTH);
        
                // Add main panel to frame
                getContentPane().add(mainPanel);
                setVisible(true);
            }
        
            private void addNewInformation(Patient patient) {
                String newInfo = newInfoTextArea.getText().trim();
                if (newInfo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter valid information.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Add new information to the patient's medical record
                patient.getMedicalRecord().add(newInfo);
        
                // Update the list model to reflect the new addition
                recordListModel.addElement(newInfo);
                newInfoTextArea.setText(""); // Clear the input box
        
                JOptionPane.showMessageDialog(this, "Information added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
    }
    
}
