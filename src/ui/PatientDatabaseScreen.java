package ui;


import api.controller.PatientController;
import api.models.Patient;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PatientDatabaseScreen extends JFrame {
    // Declare patientController as a class field
    private PatientController patientController;
    private Patient selectedPatient;

    public PatientDatabaseScreen(PatientController patientController) {
        this.patientController = patientController;
        // Set up the frame
        Patient currPatient = selectPatient();
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

        // Create a label for the Patient Database screen
        JLabel label = new JLabel("This is the Access Patient Database screen.");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(label);

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            dispose();
            new StartingScreen(patientController);  // Go back to the starting screen
        });

        // Add back button at the bottom
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);

        mainPanel.add(backButtonPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
    /* 
    public static void main(String[] args) {
        new PatientDatabaseScreen();
    }*/
    private Patient selectPatient() {
        // Fetch all patients
        List<Patient> patients = patientController.getPatients();

        // Convert the list of patients to an array of strings for display (e.g., using patient's name)
        // Adjust getName() or any other method to display patients as desired
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

            // If user clicked YES, set the selectedPatient variable
            if (confirm == JOptionPane.YES_OPTION) {
                this.selectedPatient = chosen;
                return selectedPatient;
                // selectedPatient now holds the chosen patient's object
            }
        }return selectPatient();
    }
}

// manage patient record screen, access list of patrients and add prescription, fill out some fields for it and save 

