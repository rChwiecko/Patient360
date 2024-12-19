package api.models.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import api.models.exceptions.PatientManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import api.models.*;
import api.models.exceptions.AppointmentException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistTest {

    private Receptionist receptionist;
    @Mock
    private Patient mockPatient;
    @Mock
    private Doctor mockDoctor;
    @Mock
    private Hospital mockHospital;
    @Mock
    private Appointment mockAppointment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock singleton hospital instance
        when(Hospital.getInstance()).thenReturn(mockHospital);

        // Initialize receptionist object
        receptionist = new Receptionist("John", "Doe", "john.doe@example.com", "1234567890", "R1", "day");
    }

    @Test
    void testAddPatient_Success() throws PatientManagementException {
        // Arrange
        when(mockHospital.getPatients()).thenReturn(new ArrayList<>());
        doNothing().when(mockHospital).addPatient(any(Patient.class));

        // Act
        receptionist.addPatient("Jane", "Doe", "9876543210", "jane.doe@example.com", mockDoctor);

        // Assert
        verify(mockHospital, times(1)).addPatient(any(Patient.class));
    }

    @Test
    void testAddPatient_AlreadyExists() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(mockPatient);
        when(mockHospital.getPatients()).thenReturn(patients);
        when(mockPatient.getFirstName()).thenReturn("Jane");
        when(mockPatient.getLastName()).thenReturn("Doe");

        // Act & Assert
        assertThrows(PatientManagementException.class, () ->
                receptionist.addPatient("Jane", "Doe", "9876543210", "jane.doe@example.com", mockDoctor));
    }

    @Test
    void testMakeAppointment_Success() {
        // Arrange
        LocalDateTime date = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(30);
        when(mockDoctor.isAvailable(date, duration)).thenReturn(true);
        when(mockHospital.getDoctors()).thenReturn(List.of(mockDoctor));
        doNothing().when(mockPatient).addObserver(any());
        doNothing().when(mockDoctor).scheduleAppointment(any(Appointment.class));
        doNothing().when(mockPatient).bookAppointment(any(Appointment.class));

        // Act
        boolean result = receptionist.makeAppointment(mockPatient, mockDoctor, "general",
                "Check-up", date, mockHospital, duration, "Arrive 10 mins early");

        // Assert
        assertTrue(result);
        verify(mockDoctor, times(1)).scheduleAppointment(any(Appointment.class));
        verify(mockPatient, times(1)).bookAppointment(any(Appointment.class));
    }

    @Test
    void testMakeAppointment_DoctorNotAvailable() {
        // Arrange
        LocalDateTime date = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(30);
        when(mockDoctor.isAvailable(date, duration)).thenReturn(false);

        // Act
        boolean result = receptionist.makeAppointment(mockPatient, mockDoctor, "general",
                "Check-up", date, mockHospital, duration, "Arrive 10 mins early");

        // Assert
        assertFalse(result);
        verify(mockDoctor, never()).scheduleAppointment(any(Appointment.class));
        verify(mockPatient, never()).bookAppointment(any(Appointment.class));
    }

    @Test
    void testCancelAppointment_Success() throws Exception {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(mockAppointment);
        when(mockAppointment.getDoctor()).thenReturn(mockDoctor);
        when(mockAppointment.getPatient()).thenReturn(mockPatient);
        when(mockDoctor.getAppointments()).thenReturn(appointments);
        when(mockPatient.getAppointments()).thenReturn(appointments);

        // Act
        receptionist.cancelAppointment(mockAppointment);

        // Assert
        verify(mockDoctor.getAppointments(), times(1)).remove(mockAppointment);
        verify(mockPatient.getAppointments(), times(1)).remove(mockAppointment);
    }

    @Test
    void testCancelAppointment_ThrowsException() {
        // Act & Assert
        assertThrows(AppointmentException.class, () -> receptionist.cancelAppointment(null));
    }

    @Test
    void testCheckInPatient_Success() {
        // Arrange
        doNothing().when(mockHospital).checkPatientIn(mockPatient);

        // Act
        boolean result = receptionist.checkInPatient(mockPatient, mockAppointment);

        // Assert
        assertTrue(result);
        verify(mockPatient, times(1)).checkIn();
        verify(mockHospital, times(1)).checkPatientIn(mockPatient);
    }

    @Test
    void testCheckOutPatient_Success() {
        // Arrange
        doNothing().when(mockHospital).checkPatientOut(mockPatient);

        // Act
        boolean result = receptionist.checkOutPatient(mockPatient, mockAppointment);

        // Assert
        assertTrue(result);
        verify(mockPatient, times(1)).checkOut();
        verify(mockHospital, times(1)).checkPatientOut(mockPatient);
    }

    @Test
    void testManagePatientRecord_AddRecordAndPrescription() {
        // Arrange
        String record = "Flu Diagnosis";
        Prescription mockPrescription = mock(Prescription.class);
        doNothing().when(mockPatient).updateMedicalRecord(record);
        doNothing().when(mockPatient).addPrescription(mockPrescription);

        // Act
        receptionist.managePatientRecord(mockPatient, record, mockPrescription);

        // Assert
        verify(mockPatient, times(1)).updateMedicalRecord(record);
        verify(mockPatient, times(1)).addPrescription(mockPrescription);
    }
}
