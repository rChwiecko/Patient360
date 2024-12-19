import api.models.*;
import api.models.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class HospitalTestFile {

    private Hospital hospital;
    private Doctor doctor1;
    private Doctor doctor2;
    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    public void setUp() {
        // Initialize the hospital instance
        hospital = Hospital.getInstance();

        // Create Doctor objects
        doctor1 = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        doctor2 = new Doctor("Jane", "Smith", "janesmith@example.com", "0987654321", "D002", "Pediatrics", 10, true);

        // Create Patient objects
        patient1 = new Patient("Ryan", "Chwiecko", "rchwiec@uwo.ca", "123-456-7891", "P2345", "", doctor1, null);
        patient2 = new Patient("Sarah", "Williams", "sarahw@uwo.ca", "987-654-3210", "P2346", "", doctor2, null);
    }

    @Test
    public void testAddDoctor() throws DoctorManagementException {
        // Add a doctor to the hospital
        hospital.addDoctor(doctor1);

        // Check that the doctor is added to the hospital's doctor list
        List<Doctor> doctors = hospital.getDoctors();
        assertEquals(1, doctors.size(), "There should be one doctor in the hospital.");
        assertTrue(doctors.contains(doctor1), "Doctor should be present in the hospital.");
    }

    @Test
    public void testAddDuplicateDoctorThrowsException() {
        // Add a doctor to the hospital
        try {
            hospital.addDoctor(doctor1);
            hospital.addDoctor(doctor1);  // Trying to add the same doctor again
            fail("Expected DoctorManagementException to be thrown.");
        } catch (DoctorManagementException e) {
            assertEquals("Doctor already exists", e.getMessage());
        }
    }

    @Test
    public void testAddPatient() throws PatientManagementException {
        // Add a patient to the hospital
        hospital.addPatient(patient1);

        // Check that the patient is added to the hospital's list of patients
        List<Patient> patients = hospital.getPatients();
        assertEquals(1, patients.size(), "There should be one patient in the hospital system.");
        assertTrue(patients.contains(patient1), "Patient should be present in the hospital system.");
    }

    @Test
    public void testAddDuplicatePatientThrowsException() {
        // Add a patient to the hospital
        try {
            hospital.addPatient(patient1);
            hospital.addPatient(patient1);  // Trying to add the same patient again
            fail("Expected PatientManagementException to be thrown.");
        } catch (PatientManagementException e) {
            assertEquals("Patient already exists", e.getMessage());
        }
    }

    @Test
    public void testCheckInPatient() throws PatientManagementException {
        // Check-in a patient
        hospital.checkPatientIn(patient1);

        // Verify that the patient is in the "patientsPresent" list
        assertTrue(hospital.getPatientsPresent().contains(patient1), "Patient should be checked in.");
    }

    @Test
    public void testCheckInPatientAlreadyCheckedInThrowsException() {
        try {
            hospital.checkPatientIn(patient1);
            hospital.checkPatientIn(patient1);  // Trying to check in the same patient again
            fail("Expected PatientManagementException to be thrown.");
        } catch (PatientManagementException e) {
            assertEquals("Patient already checked in", e.getMessage());
        }
    }

    @Test
    public void testCheckOutPatient() throws PatientManagementException {
        // Check-in a patient first
        hospital.checkPatientIn(patient1);

        // Check-out the patient
        hospital.checkPatientOut(patient1);

        // Verify that the patient is no longer in the "patientsPresent" list
        assertFalse(hospital.getPatientsPresent().contains(patient1), "Patient should be checked out.");
    }

    @Test
    public void testCheckOutPatientNotCheckedInThrowsException() {
        try {
            hospital.checkPatientOut(patient1);  // Patient is not checked in yet
            fail("Expected PatientManagementException to be thrown.");
        } catch (PatientManagementException e) {
            assertEquals("Patient was never checked in", e.getMessage());
        }
    }

    @Test
    public void testCheckCapacity() {
        // Initially the capacity should be 0
        assertTrue(hospital.checkCapacity(), "Hospital should have capacity for more patients.");

        // Adding patients should increase the capacity
        try {
            hospital.checkPatientIn(patient1);
        } catch (PatientManagementException e) {
            e.printStackTrace();
        }

        assertTrue(hospital.checkCapacity(), "Hospital should still have capacity for more patients.");

        // If the hospital is full, capacity should return false
        // Note: We simulate the situation where the hospital would be full by checking if capacity is reached.
        // In this simplified test case, we are assuming the capacity will not be affected too much, but ideally, you should set a max limit.

        hospital.setCapacity(0);  // Set capacity to 0
        assertFalse(hospital.checkCapacity(), "Hospital should be at full capacity.");
    }

    @Test
    public void testSingletonPattern() {
        Hospital hospital1 = Hospital.getInstance();
        Hospital hospital2 = Hospital.getInstance();

        // Check that both instances are the same (singleton pattern)
        assertSame(hospital1, hospital2, "Hospital instances should be the same.");
    }
}
