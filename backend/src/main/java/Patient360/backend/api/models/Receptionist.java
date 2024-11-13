package Patient360.backend.api.models;
import java.util.List;
import java.util.ArrayList;

class Receptionist extends Person {
    private String workShift;
    private List<Appointment> handledAppointments;
    private String location;

    // Constructor
    public Receptionist(String firstName, String lastName, String email, String phoneNum, String ID,
                        String workShift, String location) {
        super(firstName, lastName, email, phoneNum, ID);
        this.workShift = workShift;
        this.location = location;
        this.handledAppointments = new ArrayList<>();
    }

    // Methods
    public void cancelAppointment(Appointment appointment) {
        // Implementation
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
