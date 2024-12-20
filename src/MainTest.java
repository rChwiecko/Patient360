import api.models.*;  // Import all models from the 'api.models' package (e.g., Hospital, Doctor, Patient)
import api.models.exceptions.*;  // Import custom exceptions for doctor and patient management
import org.junit.jupiter.api.BeforeEach;  // Import the annotation for setup method before each test
import org.junit.jupiter.api.Test;  // Import the annotation for test methods
import java.time.Duration;  // Import Duration to handle time intervals (e.g., appointment duration)
import java.time.LocalDateTime;  // Import LocalDateTime for handling date and time
import java.util.ArrayList;  // Import ArrayList to store patients

import static org.junit.jupiter.api.Assertions.*;  // Import static methods for assertions (e.g., assertTrue, assertEquals)

public class MainTest {  // MainTest class containing test cases for the hospital system

    private Hospital childrensHospital;  // Hospital object to be tested
    private Receptionist recep1;  // Receptionist object to be tested
    private Doctor doctor1, doctor2, doctor3;  // Doctor objects for testing
    private Patient patient1, patient2, patient3;  // Patient objects for testing

    @BeforeEach  // Setup method to initialize objects before each test
    public void setUp() {
        // Setup a new hospital instance (Singleton pattern ensures only one instance)
        childrensHospital = Hospital.getInstance();

        // Create a Receptionist object with required parameters (name, email, contact, etc.)
        recep1 = new Receptionist("John", "Doe", "johndoe@example.com", "1234567890", "R001", "day");

        // Setup Doctor objects with personal details, specialization, experience, and availability
        doctor1 = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        doctor2 = new Doctor("Jane", "Smith", "janesmith@example.com", "0987654321", "D002", "Neurology", 10, true);
        doctor3 = new Doctor("Emily", "Johnson", "emilyjohnson@example.com", "1122334455", "D003", "Orthopedics", 8, false);

        // Setup Patient objects with personal details, assigned doctor, and an empty list of appointments
        patient1 = new Patient("Ryan", "Chwiecko", "rchwiec@uwo.ca", "123-456-7891", "P2345", "", doctor2, new ArrayList<>());
        patient2 = new Patient("Sonia", "Sharma", "sshar@uwo.ca", "987-654-3211", "P23456", "", doctor3, new ArrayList<>());
        patient3 = new Patient("Mark", "Taylor", "marktaylor@example.com", "234-567-8910", "P3333", "", doctor1, new ArrayList<>());

        // Add doctors to the hospital if they are not already added
        try {
            if (!childrensHospital.getDoctors().contains(doctor1)) {
                childrensHospital.addDoctor(doctor1);
            }
            if (!childrensHospital.getDoctors().contains(doctor2)) {
                childrensHospital.addDoctor(doctor2);
            }
            if (!childrensHospital.getDoctors().contains(doctor3)) {
                childrensHospital.addDoctor(doctor3);
            }

            // Add patients to the hospital if they are not already added
            if (!childrensHospital.getPatients().contains(patient1)) {
                childrensHospital.addPatient(patient1);
            }
            if (!childrensHospital.getPatients().contains(patient2)) {
                childrensHospital.addPatient(patient2);
            }
            if (!childrensHospital.getPatients().contains(patient3)) {
                childrensHospital.addPatient(patient3);
            }
        } catch (DoctorManagementException | PatientManagementException e) {
            fail("Error adding doctor or patient: " + e.getMessage());  // If any error occurs, fail the test with a message
        }
    }

    @Test  // Test method for adding doctors to the hospital
    public void testAddDoctor() {
        // Ensure that all doctors have been successfully added to the hospital
        assertTrue(childrensHospital.getDoctors().contains(doctor1));
        assertTrue(childrensHospital.getDoctors().contains(doctor2));
        assertTrue(childrensHospital.getDoctors().contains(doctor3));
    }

    @Test  // Test method for adding patients to the hospital
    public void testAddPatient() {
        // Ensure that all patients have been successfully added to the hospital
        assertTrue(childrensHospital.getPatients().contains(patient1));
        assertTrue(childrensHospital.getPatients().contains(patient2));
        assertTrue(childrensHospital.getPatients().contains(patient3));
    }

    @Test  // Test method for making an appointment
    public void testMakeAppointment() {
        // Setup test parameters: Current time and appointment duration (30 minutes)
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration thirtyMinutes = Duration.ofMinutes(30);

        // Simulate an appointment creation using the makeAppointment method
        boolean appointmentMade = recep1.makeAppointment(patient1, doctor2, "general", "Neurology Checkup", currentDateTime.plusHours(1), childrensHospital, thirtyMinutes, "Bring MRI scans");

        // Verify that the appointment was successfully made
        assertTrue(appointmentMade);
    }

    @Test  // Test method for handling exceptions when adding duplicate doctors or patients
    public void testAppointmentWithException() {
        // Test adding a doctor that already exists (should throw exception)
        try {
            childrensHospital.addDoctor(doctor1);  // Should throw exception since doctor1 is already added
            fail("Expected DoctorManagementException to be thrown");  // Fail the test if no exception is thrown
        } catch (DoctorManagementException e) {
            // Verify that the exception message is as expected
            assertEquals("Doctor already exists", e.getMessage());
        }

        // Test adding a patient that already exists (should throw exception)
        try {
            childrensHospital.addPatient(patient1);  // Should throw exception since patient1 is already added
            fail("Expected PatientManagementException to be thrown");  // Fail the test if no exception is thrown
        } catch (PatientManagementException e) {
            // Verify that the exception message is as expected
            assertEquals("Patient already exists", e.getMessage());
        }
    }
}

