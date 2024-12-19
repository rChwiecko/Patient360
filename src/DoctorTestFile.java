import api.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DoctorTestFile {

    private Doctor doctor;
    private Patient patient;
    private Receptionist receptionist;
    private Appointment appointment;
    private boolean appointmentAvailable;

    @BeforeEach
    public void setUp() {
        // Create a Doctor object
        doctor = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        receptionist = new Receptionist("Bill", "Reid", "example@gmail.com", "123-456-7891", "123", "Night");

        // Create a Patient object
        patient = new Patient("Ryan", "Chwiecko", "rchwiec@uwo.ca", "123-456-7891", "P2345", "", doctor, new ArrayList<>());

        // Create a concrete Appointment for testing (use Appointment constructor as it is)
        LocalDateTime appointmentDate = LocalDateTime.now().plusHours(1);  // 1 hour from now
        Duration duration = Duration.ofMinutes(30);

        // Using the existing Appointment class (which we can't instantiate directly)
        appointmentAvailable = receptionist.makeAppointment(patient, doctor, "General", "Heart Checkup", appointmentDate, null,  duration, "Arrive 15 minutes early");
            // Anonymous subclass of Appointment for testing purposes
            // No new functionality, just use Appointment's constructor
        };
    

    @Test
    public void testScheduleAppointment() {
        // Schedule the appointment
        doctor.scheduleAppointment(appointment);

        // Check that the doctor has one appointment scheduled
        assertEquals(1, doctor.getAppointments().size(), "Doctor should have one scheduled appointment.");
    }

    @Test
    public void testDoctorAvailability() {
        // Test if the doctor is available for a new appointment that does not overlap
        LocalDateTime newAppointmentTime = LocalDateTime.now().plusHours(3); // 1 hours from now
        Duration duration = Duration.ofMinutes(30);
        assertTrue(doctor.isAvailable(newAppointmentTime, duration), "Doctor should be available for a new appointment.");

        // Test if the doctor is NOT available for an overlapping appointment
        LocalDateTime overlappingAppointmentTime = LocalDateTime.now().plusHours(1).plusMinutes(15); // Overlaps with the existing appointment
        assertFalse(doctor.isAvailable(overlappingAppointmentTime, duration), "Doctor should NOT be available for an overlapping appointment.");
    }

    @Test
    public void testGetSpecialization() {
        // Check the specialization of the doctor
        assertEquals("Cardiology", doctor.getSpecialization(), "Doctor's specialization should be 'Cardiology'.");
    }

    @Test
    public void testGetRole() {
        // Check the role of the doctor
        assertEquals("Doctor", doctor.getRole(), "The role of the person should be 'Doctor'.");
    }

    @Test
    public void testGetPatients() {
        // Check if the doctor has no patients initially
        assertTrue(doctor.getPatients().isEmpty(), "Doctor should have no patients initially.");

        // Add the patient to the doctor's list
        doctor.getPatients().add(patient);

        // Check if the doctor now has the patient
        assertEquals(1, doctor.getPatients().size(), "Doctor should have one patient.");
    }

    @Test
    public void testGetAppointments() {
        // Schedule the appointment
        doctor.scheduleAppointment(appointment);

        // Check if the doctor has one appointment scheduled
        assertEquals(1, doctor.getAppointments().size(), "Doctor should have one scheduled appointment.");
    }
}
