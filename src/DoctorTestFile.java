import api.models.*;  // Import all the models from the 'api.models' package
import org.junit.jupiter.api.BeforeEach;  // Import the annotation to run setup before each test
import org.junit.jupiter.api.Test;  // Import the annotation to define a test method
import static org.junit.jupiter.api.Assertions.*;  // Import assertion methods from JUnit to validate test results

import java.time.Duration;  // Import the Duration class to represent time intervals
import java.time.LocalDateTime;  // Import LocalDateTime to represent a specific date and time
import java.util.ArrayList;  // Import ArrayList to manage collections of patients

public class DoctorTestFile {  // Define the test class for testing the Doctor class

    private Doctor doctor;  // Declare a Doctor object
    private Patient patient;  // Declare a Patient object
    private Receptionist receptionist;  // Declare a Receptionist object
    private Appointment appointment;  // Declare an Appointment object
    private boolean appointmentAvailable;  // Declare a flag to check if the appointment is available

    @BeforeEach  // This method will run before each test method
    public void setUp() {
        // Create a Doctor object with required attributes
        doctor = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);

        // Create a Receptionist object with necessary information
        receptionist = new Receptionist("Bill", "Reid", "example@gmail.com", "123-456-7891", "123", "Night");

        // Create a Patient object with necessary details, associating it with the created doctor
        patient = new Patient("Ryan", "Chwiecko", "rchwiec@uwo.ca", "123-456-7891", "P2345", "", doctor, new ArrayList<>());

        // Create a LocalDateTime object for the appointment date set 1 hour from now
        LocalDateTime appointmentDate = LocalDateTime.now().plusHours(1);  // 1 hour from now

        // Create a Duration object for the appointment duration (30 minutes)
        Duration duration = Duration.ofMinutes(30);

        // Try to make an appointment using the receptionist, associating it with the patient and doctor
        appointmentAvailable = receptionist.makeAppointment(patient, doctor, "General", "Heart Checkup", appointmentDate, null, duration, "Arrive 15 minutes early");
        // Note: An anonymous subclass of Appointment is used for testing purposes, but it's using the Appointment constructor
    }
    

    @Test  // Marks this method as a test method
    public void testScheduleAppointment() {
        // Schedule the appointment using the doctor object
        doctor.scheduleAppointment(appointment);

        // Check that the doctor has one appointment scheduled and assert that it is true
        assertEquals(1, doctor.getAppointments().size(), "Doctor should have one scheduled appointment.");
    }

    @Test  // Marks this method as a test method
    public void testDoctorAvailability() {
        // Test if the doctor is available for a new appointment that does not overlap with existing appointments
        LocalDateTime newAppointmentTime = LocalDateTime.now().plusHours(3); // 1 hour from now
        Duration duration = Duration.ofMinutes(30);  // Define the duration of the new appointment
        assertTrue(doctor.isAvailable(newAppointmentTime, duration), "Doctor should be available for a new appointment.");

        // Test if the doctor is NOT available for an overlapping appointment (this one overlaps with the earlier appointment)
        LocalDateTime overlappingAppointmentTime = LocalDateTime.now().plusHours(1).plusMinutes(15); // Overlaps with the existing appointment
        assertFalse(doctor.isAvailable(overlappingAppointmentTime, duration), "Doctor should NOT be available for an overlapping appointment.");
    }

    @Test  // Marks this method as a test method
    public void testGetSpecialization() {
        // Check the specialization of the doctor and assert that it matches the expected value
        assertEquals("Cardiology", doctor.getSpecialization(), "Doctor's specialization should be 'Cardiology'.");
    }

    @Test  // Marks this method as a test method
    public void testGetRole() {
        // Check the role of the doctor and assert that it matches the expected value
        assertEquals("Doctor", doctor.getRole(), "The role of the person should be 'Doctor'.");
    }

    @Test  // Marks this method as a test method
    public void testGetPatients() {
        // Check if the doctor has no patients initially
        assertTrue(doctor.getPatients().isEmpty(), "Doctor should have no patients initially.");

        // Add the patient to the doctor's list of patients
        doctor.getPatients().add(patient);

        // Check if the doctor now has the patient in their list of patients
        assertEquals(1, doctor.getPatients().size(), "Doctor should have one patient.");
    }

    @Test  // Marks this method as a test method
    public void testGetAppointments() {
        // Schedule the appointment using the doctor object
        doctor.scheduleAppointment(appointment);

        // Check if the doctor has one appointment scheduled
        assertEquals(1, doctor.getAppointments().size(), "Doctor should have one scheduled appointment.");
    }
}

