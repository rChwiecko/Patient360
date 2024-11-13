package Patient360.backend.api.models;
import java.time.LocalDateTime;

abstract class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String appointmentType;
    private String description;
    private LocalDateTime date;
    private Hospital location;
    private String status;
    private String preAppointmentInstructions;

    // Constructor
    public Appointment(Patient patient, Doctor doctor, String appointmentType, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentType = appointmentType;
        this.description = description;
        this.date = date;
        this.location = location;
        this.status = "incomplete";
        this.preAppointmentInstructions = preAppointmentInstructions;
        
        //fill in with email logic for pre instructions
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

    public void setDate(LocalDateTime newDate){
        this.date = newDate;
    }

    public Hospital getLocation() {
        return location;
    }

    public String getAppointmentStatus(){
        return this.status;
    }

    public void updateStatus(String newStatus){
        this.status = newStatus;
    }

    public void completeAppointment(){
        this.updateStatus("complete");
    }

    public void reschedule(LocalDateTime newDate){
         this.setDate(newDate);
    }

    public String getPreAppointmentInstructions(){
        return this.preAppointmentInstructions;
    }
}
