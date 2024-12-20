package ui;

import api.controller.PatientController;
import api.models.Doctor;
import api.models.Patient;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PatientDatabaseScreen extends JFrame {
    private final PatientController patientController; // Declare the PatientController to handle patient-related
                                                       // operations
    private Patient selectedPatient; // Declare a variable to hold the selected patient

    // Constructor to set up the Patient Database Screen
    public PatientDatabaseScreen(PatientController patientController) {
        this.patientController = patientController; // Initialize patientController

        // Set up the frame properties
        setTitle("Access Patient Database"); // Set the title of the window
        setSize(1900, 650); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define the behavior when closing the window

        // Create the main panel to hold other components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        mainPanel.setBackground(Color.WHITE); // Set the background color to white

        // Create a panel to hold the logo
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(null); // No layout manager for absolute positioning
        logoPanel.setBackground(Color.WHITE); // Set the background color to white

        // Load and scale the logo image
        ImageIcon image1 = new ImageIcon("ui/imgs/Patient360Logo.png"); // Load the logo image
        Image scaledImage = image1.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH); // Scale the image
        image1 = new ImageIcon(scaledImage); // Update the ImageIcon with the scaled image

        JLabel imageLabel = new JLabel(image1); // Create a label to hold the logo image
        imageLabel.setBounds(20, 20, image1.getIconWidth(), image1.getIconHeight()); // Set the position and size of the
                                                                                     // image

        logoPanel.add(imageLabel); // Add the logo to the logo panel
        mainPanel.add(logoPanel); // Add the logo panel to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing between components

        // Create buttons for "Add a Patient" and "Modify a Patient"
        JButton addPatientButton = new JButton("Add a Patient"); // Create a button to add a patient
        addPatientButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font of the button text
        addPatientButton.setPreferredSize(new Dimension(200, 50)); // Set the preferred size of the button
        addPatientButton.addActionListener(e -> { // Add an action listener to handle button click
            dispose(); // Close the current window
            new AddPatientScreen(); // Open the Add Patient screen
        });

        JButton modifyPatientButton = new JButton("Modify a Patient"); // Create a button to modify a patient
        modifyPatientButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font of the button text
        modifyPatientButton.setPreferredSize(new Dimension(200, 50)); // Set the preferred size of the button
        modifyPatientButton.addActionListener(e -> { // Add an action listener to handle button click
            Patient patient = selectPatient(); // Select a patient
            if (patient != null) {
                dispose(); // Close the current window
                new ModifyPatientScreen(patient); // Open the Modify Patient screen with the selected patient
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout()); // Use FlowLayout to arrange the buttons
        buttonPanel.setBackground(Color.WHITE); // Set the background color to white
        buttonPanel.add(addPatientButton); // Add the Add Patient button
        buttonPanel.add(modifyPatientButton); // Add the Modify Patient button

        mainPanel.add(buttonPanel); // Add the button panel to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing between components

        // Create a back button to return to the starting screen
        JButton backButton = new JButton("Back"); // Create the Back button
        backButton.setPreferredSize(new Dimension(150, 50)); // Set the preferred size of the button
        backButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Set the font of the button text
        backButton.addActionListener(e -> { // Add an action listener to handle button click
            dispose(); // Close the current window
            new StartingScreen(patientController); // Open the Starting Screen
        });

        // Create a panel for the back button
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align the button to the left
        backButtonPanel.setBackground(Color.WHITE); // Set the background color to white
        backButtonPanel.add(backButton); // Add the back button to the panel

        mainPanel.add(backButtonPanel); // Add the back button panel to the main panel

        // Add the main panel to the content pane
        getContentPane().add(mainPanel);
        setVisible(true); // Make the window visible
    }

    // Method to select a patient from the list
    private Patient selectPatient() {
        // Fetch all patients from the patientController
        List<Patient> patients = patientController.getPatients();

        // Convert the list of patients to an array of strings to display their names
        String[] patientNames = patients.stream()
                .map(patient -> patient.getFirstName() + " " + patient.getLastName()) // Format patient names
                .toArray(String[]::new); // Convert the list to an array of strings

        // Create a JList to display patient names
        JList<String> patientList = new JList<>(patientNames); // Pass the array of names to JList
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one selection

        // Display a confirmation dialog to select a patient
        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(patientList), // Wrap the patient list in a JScrollPane for scrolling
                "Select a Patient", // Title of the dialog
                JOptionPane.OK_CANCEL_OPTION, // OK and Cancel buttons
                JOptionPane.PLAIN_MESSAGE // Message type
        );

        // If the user clicked OK and selected a patient
        if (option == JOptionPane.OK_OPTION && patientList.getSelectedIndex() >= 0) {
            int selectedIndex = patientList.getSelectedIndex(); // Get the index of the selected patient
            Patient chosen = patients.get(selectedIndex); // Retrieve the selected patient from the list

            // Confirm the patient selection
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Select patient " + chosen.getFirstName() + " " + chosen.getLastName() + "?",
                    "Confirm Patient", // Title of the confirmation dialog
                    JOptionPane.YES_NO_OPTION, // Yes and No buttons
                    JOptionPane.QUESTION_MESSAGE // Message type
            );

            if (confirm == JOptionPane.YES_OPTION) { // If the user confirmed the selection
                this.selectedPatient = chosen; // Store the selected patient
                return chosen; // Return the selected patient
            }
        }
        return null; // Return null if no patient is selected
    }

    // Nested class for Add Patient screen
    private class AddPatientScreen extends JFrame {
        private JTextField firstNameField; // Declare a field for first name
        private JTextField lastNameField; // Declare a field for last name
        private JTextField emailField; // Declare a field for email
        private JTextField phoneField; // Declare a field for phone number
        private JComboBox<Doctor> doctorDropdown; // Declare a dropdown for selecting a doctor

        // Constructor for Add Patient screen
        public AddPatientScreen() {
            setTitle("Add a Patient"); // Set the window title
            setSize(500, 400); // Set the window size
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window on exit
            setLocationRelativeTo(null); // Center the window on the screen

            // Create a panel to hold input fields and buttons
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

            // Add first name input field
            panel.add(new JLabel("First Name:"));
            firstNameField = new JTextField(20); // Create a text field for the first name
            panel.add(firstNameField); // Add the text field to the panel
            panel.add(Box.createVerticalStrut(10)); // Add spacing between components

            // Add last name input field
            panel.add(new JLabel("Last Name:"));
            lastNameField = new JTextField(20); // Create a text field for the last name
            panel.add(lastNameField); // Add the text field to the panel
            panel.add(Box.createVerticalStrut(10)); // Add spacing

            // Add email input field
            panel.add(new JLabel("Email:"));
            emailField = new JTextField(20); // Create a text field for email
            panel.add(emailField); // Add the text field to the panel
            panel.add(Box.createVerticalStrut(10)); // Add spacing

            // Add phone number input field
            panel.add(new JLabel("Phone Number:"));
            phoneField = new JTextField(20); // Create a text field for phone number
            panel.add(phoneField); // Add the text field to the panel
            panel.add(Box.createVerticalStrut(10)); // Add spacing

            // Add doctor dropdown field
            panel.add(new JLabel("Assign a Doctor:"));
            List<Doctor> doctors = patientController.getDoctors(); // Get the list of doctors
            doctorDropdown = new JComboBox<>(doctors.toArray(new Doctor[0])); // Populate the dropdown with doctors

            // Set a custom renderer for displaying doctor names
            doctorDropdown.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof Doctor) {
                        Doctor doctor = (Doctor) value; // Cast the value to Doctor
                        value = doctor.getFirstName() + " " + doctor.getLastName(); // Display doctor's full name
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); // Return
                                                                                                             // the
                                                                                                             // renderer
                }
            });

            panel.add(doctorDropdown); // Add the doctor dropdown to the panel
            panel.add(Box.createVerticalStrut(20)); // Add spacing

            // Create and add the Add Patient button
            JButton addButton = new JButton("Add Patient");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
            addButton.addActionListener(e -> addPatientAction()); // Add action listener to handle button click

            // Create and add the Cancel button
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
            cancelButton.addActionListener(e -> { // Handle cancel action
                dispose(); // Close the current window
                new PatientDatabaseScreen(patientController); // Return to the Patient Database screen
            });

            // Create a button panel to hold the buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Use FlowLayout
            buttonPanel.add(addButton); // Add the Add Patient button
            buttonPanel.add(cancelButton); // Add the Cancel button

            panel.add(Box.createVerticalStrut(10)); // Add spacing
            panel.add(buttonPanel); // Add the button panel

            getContentPane().add(panel); // Add the main panel to the content pane
            setVisible(true); // Make the window visible
        }

        // Method to handle adding a patient
        private void addPatientAction() {
            // Retrieve input values
            String firstName = firstNameField.getText().trim(); // Get the first name
            String lastName = lastNameField.getText().trim(); // Get the last name
            String email = emailField.getText().trim(); // Get the email
            String phone = phoneField.getText().trim(); // Get the phone number
            Doctor selectedDoctor = (Doctor) doctorDropdown.getSelectedItem(); // Get the selected doctor

            // Validate the inputs
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()
                    || selectedDoctor == null) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE); // Show
                                                                                                                        // error
                                                                                                                        // message
                return;
            }

            // Check if phone number contains only digits
            if (!phone.matches("\\d+")) { // Validate phone number to contain only digits
                JOptionPane.showMessageDialog(this, "Phone number must contain only numeric values.", "Error",
                        JOptionPane.ERROR_MESSAGE); // Show error message
                return;
            }

            // Call the controller to add the patient
            boolean success = patientController.addPatient(firstName, lastName, email, phone, selectedDoctor);

            if (success) {
                JOptionPane.showMessageDialog(this, "Patient added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE); // Show success message
                dispose(); // Close the window
                new StartingScreen(patientController); // Return to the starting screen
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.", "Error", JOptionPane.ERROR_MESSAGE); // Show
                                                                                                                   // failure
                                                                                                                   // message
            }
        }
    }

    // Blank screen for Modify Patient functionality
    private class ModifyPatientScreen extends JFrame {
        public ModifyPatientScreen(Patient patient) {
            setTitle("Modify Patient: " + patient.getFirstName() + " " + patient.getLastName()); // Set the title of the
                                                                                                 // window with
                                                                                                 // patient's name
            setSize(500, 350); // Adjusted height for the back button
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window when user closes the frame
            setLocationRelativeTo(null); // Center the window on the screen

            // Main panel to contain all components
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use vertical BoxLayout for stacking components
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Set padding for the panel

            // Welcome label with patient's name
            JLabel titleLabel = new JLabel("Modify Patient: " + patient.getFirstName() + " " + patient.getLastName(),
                    SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size for the label
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
            panel.add(titleLabel); // Add title label to the panel
            panel.add(Box.createVerticalStrut(20)); // Add some vertical space after the title

            // Add Prescription Button
            JButton addPrescriptionButton = new JButton("Add Prescription"); // Create button for adding prescription
            addPrescriptionButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
            addPrescriptionButton.setPreferredSize(new Dimension(200, 40)); // Set button size
            addPrescriptionButton.addActionListener(e -> new AddPrescriptionScreen(patient)); // Open Add Prescription
                                                                                              // screen on click
            panel.add(addPrescriptionButton); // Add button to the panel
            panel.add(Box.createVerticalStrut(20)); // Add vertical spacing

            // Add Additional Information Button
            JButton addInfoButton = new JButton("Add Additional Information"); // Create button for adding info
            addInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
            addInfoButton.setPreferredSize(new Dimension(200, 40)); // Set button size
            addInfoButton.addActionListener(e -> new AddInformationScreen(patient)); // Open Add Information screen on
                                                                                     // click
            panel.add(addInfoButton); // Add button to the panel
            panel.add(Box.createVerticalStrut(20)); // Add vertical spacing

            // Back Button
            JButton backButton = new JButton("Back"); // Create back button
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
            backButton.setPreferredSize(new Dimension(150, 40)); // Set button size
            backButton.addActionListener(e -> {
                dispose(); // Close current window
                new PatientDatabaseScreen(patientController); // Return to the Patient Database screen
            });
            panel.add(backButton); // Add back button to the panel
            panel.add(Box.createVerticalStrut(20)); // Add vertical spacing

            // Add the panel to the frame
            getContentPane().add(panel); // Add the panel with all buttons and labels to the frame
            setVisible(true); // Make the frame visible
        }

        // Placeholder for Add Prescription Screen
        private class AddPrescriptionScreen extends JFrame {
            private JTextField medicationNameField; // Text field for medication name
            private JTextField dosageField; // Text field for dosage
            private JTextField frequencyField; // Text field for frequency
            private JTextField prescriptionDateField; // Text field for prescription date
            private JTextField expiryDateField; // Text field for expiry date
            private JTextField instructionsField; // Text field for instructions
            private JTextField refillCountField; // Text field for refill count

            public AddPrescriptionScreen(Patient patient) {
                setTitle("Add Prescription for: " + patient.getFirstName() + " " + patient.getLastName()); // Set the
                                                                                                           // title with
                                                                                                           // patient's
                                                                                                           // name
                setSize(600, 600); // Set size of the window
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window when user closes the frame
                setLocationRelativeTo(null); // Center the window on the screen

                // Main panel for the form
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use vertical BoxLayout for stacking
                                                                         // components
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Set padding for the panel

                // Title label for prescription
                JLabel titleLabel = new JLabel(
                        "Prescription for: " + patient.getFirstName() + " " + patient.getLastName(),
                        SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size for the title
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
                panel.add(titleLabel); // Add the title label to the panel
                panel.add(Box.createVerticalStrut(20)); // Add vertical space after the title

                // Medication Name field
                panel.add(new JLabel("Medication Name:")); // Add label for medication name
                medicationNameField = new JTextField(20); // Create text field for medication name
                panel.add(medicationNameField); // Add text field to the panel
                panel.add(Box.createVerticalStrut(10)); // Add vertical space

                // Dosage field
                panel.add(new JLabel("Dosage:")); // Add label for dosage
                dosageField = new JTextField(20); // Create text field for dosage
                panel.add(dosageField); // Add text field to the panel
                panel.add(Box.createVerticalStrut(10)); // Add vertical space

                // Frequency field
                panel.add(new JLabel("Frequency:")); // Add label for frequency
                frequencyField = new JTextField(20); // Create text field for frequency
                panel.add(frequencyField); // Add text field to the panel
                panel.add(Box.createVerticalStrut(10)); // Add vertical space

                // Prescription Date field
                panel.add(new JLabel("Prescription Date (YYYY-MM-DD):")); // Add label for prescription date
                prescriptionDateField = new JTextField(20); // Create text field for prescription date
                panel.add(prescriptionDateField); // Add text field to the panel
                panel.add(Box.createVerticalStrut(10)); // Add vertical space

                // Expiry Date field
                panel.add(new JLabel("Expiry Date (YYYY-MM-DD):")); // Add label for expiry date
                expiryDateField = new JTextField(20); // Create text field for expiry date
                panel.add(expiryDateField); // Add text field to the panel
                panel.add(Box.createVerticalStrut(10)); // Add vertical space

                // Instructions field
                panel.add(new JLabel("Instructions:")); // Add label for instructions
                instructionsField = new JTextField(20); // Create text field for instructions
                panel.add(instructionsField); // Add text field to the panel
                panel.add(Box.createVerticalStrut(10)); // Add vertical space

                // Refill Count field
                panel.add(new JLabel("Refill Count:")); // Add label for refill count
                refillCountField = new JTextField(20); // Create text field for refill count
                panel.add(refillCountField); // Add text field to the panel
                panel.add(Box.createVerticalStrut(20)); // Add vertical space

                // Buttons for actions
                JButton addButton = new JButton("Add Prescription"); // Create button to add prescription
                addButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
                addButton.addActionListener(e -> {
                    addPrescriptionAction(patient); // Call method to add prescription data
                });

                JButton cancelButton = new JButton("Cancel"); // Create cancel button
                cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
                cancelButton.addActionListener(e -> {
                    dispose(); // Close the current window
                });

                JPanel buttonPanel = new JPanel(new FlowLayout()); // Create a panel for the buttons
                buttonPanel.add(addButton); // Add addButton to the button panel
                buttonPanel.add(cancelButton); // Add cancelButton to the button panel

                panel.add(buttonPanel); // Add the button panel to the main panel

                getContentPane().add(panel); // Add the main panel to the content pane
                setVisible(true); // Make the frame visible
            }

            private void addPrescriptionAction(Patient patient) {
                // Retrieve input values from the form
                String medicationName = medicationNameField.getText().trim();
                String dosage = dosageField.getText().trim();
                String frequency = frequencyField.getText().trim();
                String prescriptionDate = prescriptionDateField.getText().trim();
                String expiryDate = expiryDateField.getText().trim();
                String instructions = instructionsField.getText().trim();
                String refillCountStr = refillCountField.getText().trim();

                // Validate the inputs
                if (medicationName.isEmpty() || dosage.isEmpty() || frequency.isEmpty() ||
                        prescriptionDate.isEmpty() || expiryDate.isEmpty() ||
                        instructions.isEmpty() || refillCountStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int refillCount;
                try {
                    refillCount = Integer.parseInt(refillCountStr); // Parse refill count as an integer
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Refill count must be a numeric value.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Display the prescription data
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
            private JTextArea newInfoTextArea; // Text area for new information
            private DefaultListModel<String> recordListModel; // Model for the medical record list
            private JList<String> recordList; // List to display medical records

            public AddInformationScreen(Patient patient) {
                setTitle("Add Information for: " + patient.getFirstName() + " " + patient.getLastName()); // Set the
                                                                                                          // title
                setSize(500, 400); // Set size of the window
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the window when user closes the frame
                setLocationRelativeTo(null); // Center the window on the screen

                // Main panel for the form
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout()); // Use BorderLayout for panel
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set padding for the panel

                // Title label for the medical record
                JLabel titleLabel = new JLabel(
                        "Medical Record for: " + patient.getFirstName() + " " + patient.getLastName());
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the title
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the title
                mainPanel.add(titleLabel, BorderLayout.NORTH); // Add title label to the top of the panel

                // Text area to add new information
                newInfoTextArea = new JTextArea(5, 20); // Create a text area for new information
                JScrollPane scrollPane = new JScrollPane(newInfoTextArea); // Add scrolling for text area
                mainPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane with text area to the center

                // Button panel for save/cancel options
                JPanel buttonPanel = new JPanel();
                JButton saveButton = new JButton("Save"); // Create button to save new information
                saveButton.addActionListener(e -> {
                    String newInfo = newInfoTextArea.getText().trim(); // Get the text from the text area
                    if (newInfo.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter information to save.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Add new information to the patient's medical record list
                        recordListModel.addElement(newInfo);
                        newInfoTextArea.setText(""); // Clear the text area after saving the information
                    }
                });

                JButton cancelButton = new JButton("Cancel"); // Create cancel button
                cancelButton.addActionListener(e -> dispose()); // Close the window when clicked
                buttonPanel.add(saveButton); // Add save button to the button panel
                buttonPanel.add(cancelButton); // Add cancel button to the button panel
                mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom of the frame

                // Create the list to display medical records
                recordListModel = new DefaultListModel<>();
                recordList = new JList<>(recordListModel);
                JScrollPane listScrollPane = new JScrollPane(recordList); // Add scrolling for the list
                mainPanel.add(listScrollPane, BorderLayout.EAST); // Add list to the east side of the panel

                getContentPane().add(mainPanel); // Add the main panel to the content pane
                setVisible(true); // Make the frame visible
            }
        }

        // Placeholder for Add Additional Information Screen
        private class AddInformationScreen extends JFrame {
            private JTextArea newInfoTextArea; // Text area for entering new information
            private DefaultListModel<String> recordListModel; // Model for the medical record list
            private JList<String> recordList; // List to display existing medical records

            public AddInformationScreen(Patient patient) {
                setTitle("Add Information for: " + patient.getFirstName() + " " + patient.getLastName()); // Set the
                                                                                                          // title of
                                                                                                          // the window
                                                                                                          // with
                                                                                                          // patient's
                                                                                                          // name
                setSize(500, 400); // Set the size of the window (500px width, 400px height)
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window when user closes the frame
                setLocationRelativeTo(null); // Center the window on the screen

                // Main panel to hold the components
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout()); // Use BorderLayout for the panel
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set padding for the panel

                // Title Label displaying the patient's name
                JLabel titleLabel = new JLabel(
                        "Medical Record for: " + patient.getFirstName() + " " + patient.getLastName());
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set the font style and size for the title
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the label
                mainPanel.add(titleLabel, BorderLayout.NORTH); // Add the title label to the top of the panel

                // Medical Record List section
                recordListModel = new DefaultListModel<>(); // Create a DefaultListModel to hold medical records
                // Add existing records to the list model
                for (String record : patient.getMedicalRecord()) {
                    recordListModel.addElement(record); // Add each existing record from the patient's medical record
                }
                recordList = new JList<>(recordListModel); // Create a JList with the list model
                recordList.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font style and size for the list
                JScrollPane scrollPane = new JScrollPane(recordList); // Add scroll functionality to the list
                scrollPane.setBorder(BorderFactory.createTitledBorder("Existing Medical Record")); // Add border and
                                                                                                   // title for the
                                                                                                   // scroll pane
                mainPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the panel

                // Panel for adding new information
                JPanel addInfoPanel = new JPanel(); // Create a new panel for adding new information
                addInfoPanel.setLayout(new BoxLayout(addInfoPanel, BoxLayout.Y_AXIS)); // Use BoxLayout to stack
                                                                                       // components vertically
                addInfoPanel.setBorder(BorderFactory.createTitledBorder("Add New Information")); // Set a border with a
                                                                                                 // title

                // Text area for entering new medical information
                newInfoTextArea = new JTextArea(4, 30); // Create a text area with 4 rows and 30 columns
                newInfoTextArea.setWrapStyleWord(true); // Enable word wrapping in the text area
                newInfoTextArea.setLineWrap(true); // Enable line wrapping in the text area
                JScrollPane textAreaScrollPane = new JScrollPane(newInfoTextArea, // Add scroll functionality to the
                                                                                  // text area
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, // Add vertical scrollbar if necessary
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar

                addInfoPanel.add(new JLabel("Enter new medical record information:")); // Add a label above the text
                                                                                       // area
                addInfoPanel.add(Box.createVerticalStrut(10)); // Add some vertical space
                addInfoPanel.add(textAreaScrollPane); // Add the scrollable text area to the panel
                addInfoPanel.add(Box.createVerticalStrut(10)); // Add some vertical space

                // Buttons for adding and closing information
                JButton addButton = new JButton("Add Information"); // Create a button for adding new information
                addButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
                // Add action listener to the button
                addButton.addActionListener(e -> {
                    addNewInformation(patient); // Call the addNewInformation method when the button is clicked
                    dispose(); // Close the current window
                    new StartingScreen(patientController); // Open the starting screen after closing
                });

                JButton closeButton = new JButton("Close"); // Create a button to close the window
                closeButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
                closeButton.addActionListener(e -> dispose()); // Close the window when the button is clicked

                addInfoPanel.add(addButton); // Add the addButton to the panel
                addInfoPanel.add(Box.createVerticalStrut(10)); // Add vertical space between buttons
                addInfoPanel.add(closeButton); // Add the closeButton to the panel

                mainPanel.add(addInfoPanel, BorderLayout.SOUTH); // Add the addInfoPanel to the bottom of the main panel

                // Add the main panel to the frame
                getContentPane().add(mainPanel);
                setVisible(true); // Make the frame visible
            }

            // Method to add new information to the patient's medical record
            private void addNewInformation(Patient patient) {
                String newInfo = newInfoTextArea.getText().trim(); // Get the new information entered by the user
                if (newInfo.isEmpty()) { // If the new information is empty
                    JOptionPane.showMessageDialog(this, "Please enter valid information.", "Error",
                            JOptionPane.ERROR_MESSAGE); // Show error message
                    return; // Exit the method
                }

                // Add the new information to the patient's medical record list
                patient.getMedicalRecord().add(newInfo);

                // Update the list model to reflect the newly added information
                recordListModel.addElement(newInfo);
                newInfoTextArea.setText(""); // Clear the text area after adding information

                // Show a success message
                JOptionPane.showMessageDialog(this, "Information added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}