package Patient360.backend.api.models;

abstract class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String appointmentType;
    private String description;
    private String date;
    private String location;

    // Constructor
    public Appointment(Patient patient, Doctor doctor, String appointmentType, String description, String date, String location) {
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

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
