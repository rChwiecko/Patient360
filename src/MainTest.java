import api.models.*;
import api.models.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private Hospital childrensHospital;
    private Receptionist recep1;
    private Doctor doctor1, doctor2, doctor3;
    private Patient patient1, patient2, patient3;

    @BeforeEach
    public void setUp() {
        // Setup a new hospital instance (Singleton)
        childrensHospital = Hospital.getInstance();

        // Create a Receptionist object with required parameters
        recep1 = new Receptionist("John", "Doe", "johndoe@example.com", "1234567890", "R001", "day");

        // Setup Doctor objects
        doctor1 = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        doctor2 = new Doctor("Jane", "Smith", "janesmith@example.com", "0987654321", "D002", "Neurology", 10, true);
        doctor3 = new Doctor("Emily", "Johnson", "emilyjohnson@example.com", "1122334455", "D003", "Orthopedics", 8, false);

        // Setup Patient objects
        patient1 = new Patient("Ryan", "Chwiecko", "rchwiec@uwo.ca", "123-456-7891", "P2345", "", doctor2, new ArrayList<>());
        patient2 = new Patient("Sonia", "Sharma", "sshar@uwo.ca", "987-654-3211", "P23456", "", doctor3, new ArrayList<>());
        patient3 = new Patient("Mark", "Taylor", "marktaylor@example.com", "234-567-8910", "P3333", "", doctor1, new ArrayList<>());

        // Add doctors to the hospital if not already added
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

            // Add patients to the hospital if not already added
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
            fail("Error adding doctor or patient: " + e.getMessage());
        }
    }

    @Test
    public void testAddDoctor() {
        // Ensure doctor is added to the hospital
        assertTrue(childrensHospital.getDoctors().contains(doctor1));
        assertTrue(childrensHospital.getDoctors().contains(doctor2));
        assertTrue(childrensHospital.getDoctors().contains(doctor3));
    }

    @Test
    public void testAddPatient() {
        // Ensure patient is added to the hospital
        assertTrue(childrensHospital.getPatients().contains(patient1));
        assertTrue(childrensHospital.getPatients().contains(patient2));
        assertTrue(childrensHospital.getPatients().contains(patient3));
    }

    @Test
    public void testMakeAppointment() {
        // Setup test parameters
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration thirtyMinutes = Duration.ofMinutes(30);

        // Directly call the makeAppointment method to simulate an appointment
        boolean appointmentMade = recep1.makeAppointment(patient1, doctor2, "general", "Neurology Checkup", currentDateTime.plusHours(1), childrensHospital, thirtyMinutes, "Bring MRI scans");

        // Verify the appointment was successfully made
        assertTrue(appointmentMade);
    }

    @Test
    public void testAppointmentWithException() {
        // Test adding duplicate doctor or patient
        try {
            childrensHospital.addDoctor(doctor1);  // Should throw exception since doctor1 is already added
            fail("Expected DoctorManagementException to be thrown");
        } catch (DoctorManagementException e) {
            assertEquals("Doctor already exists", e.getMessage());
        }

        try {
            childrensHospital.addPatient(patient1);  // Should throw exception since patient1 is already added
            fail("Expected PatientManagementException to be thrown");
        } catch (PatientManagementException e) {
            assertEquals("Patient already exists", e.getMessage());
        }
    }
}
