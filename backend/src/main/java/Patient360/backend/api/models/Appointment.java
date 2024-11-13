package Patient360.backend.api.models;
import java.time.LocalDateTime;

abstract class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String appointmentType;
    private String description;
    private LocalDateTime date;
    private String location;

    // Constructor
    public Appointment(Patient patient, Doctor doctor, String appointmentType, String description, LocalDateTime date, String location) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentType = appointmentType;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    // Methods
    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
