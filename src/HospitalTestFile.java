import api.models.*;  // Import all models from the 'api.models' package (e.g., Hospital, Doctor, Patient)
import api.models.exceptions.*;  // Import custom exceptions for doctor and patient management
import org.junit.jupiter.api.BeforeEach;  // Import the annotation to run setup code before each test
import org.junit.jupiter.api.Test;  // Import the annotation to define a test method

import static org.junit.jupiter.api.Assertions.*;  // Import assertion methods from JUnit to validate test results

import java.util.List;  // Import List for managing collections of doctors and patients

public class HospitalTestFile {  // Define the test class for testing the Hospital class

    private Hospital hospital;  // Declare a Hospital object for testing
    private Doctor doctor1;  // Declare a Doctor object (doctor1)
    private Doctor doctor2;  // Declare a Doctor object (doctor2)
    private Patient patient1;  // Declare a Patient object (patient1)
    private Patient patient2;  // Declare a Patient object (patient2)

    @BeforeEach  // This method will run before each test method to set up common objects
    public void setUp() {
        // Initialize the hospital instance as a singleton
        hospital = Hospital.getInstance();

        // Create Doctor objects with test data
        doctor1 = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        doctor2 = new Doctor("Jane", "Smith", "janesmith@example.com", "0987654321", "D002", "Pediatrics", 10, true);

        // Create Patient objects and associate them with doctors
        patient1 = new Patient("Ryan", "Chwiecko", "rchwiec@uwo.ca", "123-456-7891", "P2345", "", doctor1, null);
        patient2 = new Patient("Sarah", "Williams", "sarahw@uwo.ca", "987-654-3210", "P2346", "", doctor2, null);
    }

    @Test  // Marks this method as a test method
    public void testAddDoctor() throws DoctorManagementException {
        // Add a doctor to the hospital
        hospital.addDoctor(doctor1);

        // Get the list of doctors from the hospital
        List<Doctor> doctors = hospital.getDoctors();
        
        // Assert that the hospital has one doctor now and the added doctor is in the list
        assertEquals(1, doctors.size(), "There should be one doctor in the hospital.");
        assertTrue(doctors.contains(doctor1), "Doctor should be present in the hospital.");
    }

    @Test  // Marks this method as a test method
    public void testAddDuplicateDoctorThrowsException() {
        // Try adding the same doctor twice
        try {
            hospital.addDoctor(doctor1);
            hospital.addDoctor(doctor1);  // Trying to add the same doctor again
            fail("Expected DoctorManagementException to be thrown.");
        } catch (DoctorManagementException e) {
            // If exception is thrown, assert the message
            assertEquals("Doctor already exists", e.getMessage());
        }
    }

    @Test  // Marks this method as a test method
    public void testAddPatient() throws PatientManagementException {
        // Add a patient to the hospital
        hospital.addPatient(patient1);

        // Get the list of patients from the hospital
        List<Patient> patients = hospital.getPatients();
        
        // Assert that the hospital has one patient now and the added patient is in the list
        assertEquals(1, patients.size(), "There should be one patient in the hospital system.");
        assertTrue(patients.contains(patient1), "Patient should be present in the hospital system.");
    }

    @Test  // Marks this method as a test method
    public void testAddDuplicatePatientThrowsException() {
        // Try adding the same patient twice
        try {
            hospital.addPatient(patient1);
            hospital.addPatient(patient1);  // Trying to add the same patient again
            fail("Expected PatientManagementException to be thrown.");
        } catch (PatientManagementException e) {
            // If exception is thrown, assert the message
            assertEquals("Patient already exists", e.getMessage());
        }
    }

    @Test  // Marks this method as a test method
    public void testCheckInPatient() throws PatientManagementException {
        // Check-in a patient
        hospital.checkPatientIn(patient1);

        // Verify that the patient is in the "patientsPresent" list
        assertTrue(hospital.getPatientsPresent().contains(patient1), "Patient should be checked in.");
    }

    @Test  // Marks this method as a test method
    public void testCheckInPatientAlreadyCheckedInThrowsException() {
        try {
            // Check-in the patient once
            hospital.checkPatientIn(patient1);
            // Try to check-in the same patient again
            hospital.checkPatientIn(patient1);  // Should throw an exception
            fail("Expected PatientManagementException to be thrown.");
        } catch (PatientManagementException e) {
            // Assert the exception message
            assertEquals("Patient already checked in", e.getMessage());
        }
    }

    @Test  // Marks this method as a test method
    public void testCheckOutPatient() throws PatientManagementException {
        // First check-in the patient
        hospital.checkPatientIn(patient1);

        // Check-out the patient
        hospital.checkPatientOut(patient1);

        // Verify that the patient is no longer in the "patientsPresent" list
        assertFalse(hospital.getPatientsPresent().contains(patient1), "Patient should be checked out.");
    }

    @Test  // Marks this method as a test method
    public void testCheckOutPatientNotCheckedInThrowsException() {
        try {
            // Try to check-out a patient who is not checked in
            hospital.checkPatientOut(patient1);  // Patient is not checked in yet
            fail("Expected PatientManagementException to be thrown.");
        } catch (PatientManagementException e) {
            // Assert the exception message
            assertEquals("Patient was never checked in", e.getMessage());
        }
    }

    @Test  // Marks this method as a test method
    public void testCheckCapacity() {
        // Initially the capacity should be true (room for more patients)
        assertTrue(hospital.checkCapacity(), "Hospital should have capacity for more patients.");

        // After adding a patient, the hospital should still have capacity
        try {
            hospital.checkPatientIn(patient1);
        } catch (PatientManagementException e) {
            e.printStackTrace();  // Handle exception if needed
        }

        // Assert that there is still capacity
        assertTrue(hospital.checkCapacity(), "Hospital should still have capacity for more patients.");

        // Simulate a full hospital by setting capacity to 0
        hospital.setCapacity(0);  // Set capacity to 0
        assertFalse(hospital.checkCapacity(), "Hospital should be at full capacity.");
    }

    @Test  // Marks this method as a test method
    public void testSingletonPattern() {
        // Get two instances of the hospital
        Hospital hospital1 = Hospital.getInstance();
        Hospital hospital2 = Hospital.getInstance();

        // Assert that both instances are the same (singleton pattern)
        assertSame(hospital1, hospital2, "Hospital instances should be the same.");
    }
}

