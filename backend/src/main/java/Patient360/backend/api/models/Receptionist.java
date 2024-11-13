package Patient360.backend.api.models;
import java.util.ArrayList;
import java.util.List;

public class Receptionist extends Person {
    private String workShift;
    private List<Appointment> handledAppointments;
    private Hospital recepHospital;
    // Constructor
    public Receptionist(String firstName, String lastName, String email, String phoneNum, String ID,
                        String workShift, Hospital hospital) {
        super(firstName, lastName, email, phoneNum, ID);
        this.workShift = workShift;
        this.recepHospital = hospital;
        this.handledAppointments = new ArrayList<>();
    }

    // Methods
    public void cancelAppointment(Appointment appointment) {
        handledAppointments.remove(appointment);

        // Remove from relevant doctor's appointment list
        if (appointment.getDoctor() != null) {
            appointment.getDoctor().getAppointments().remove(appointment);
        }

        // Remove from relevant patient's appointment list
        if (appointment.getPatient() != null) {
            appointment.getPatient().getAppointments().remove(appointment);
        }
    }

    public void managePatientRecord(Patient patient) {
        // Implementation
    }

    public void checkInPatient(Patient patient) {
        // Implementation
    }

    public void checkOutPatient(Patient patient) {
        // Implementation
    }

    public String getWorkShift() {
        return workShift;
    }

    public void changeLocation(String newLocation) {
        this.location = newLocation;
    }

    public void addPatient(String firstName, String lastName, String phoneNumber, String email) {
        // Implementation
    }
}
