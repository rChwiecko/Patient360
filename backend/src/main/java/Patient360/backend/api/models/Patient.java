package Patient360.backend.api.models;
import java.util.List;
import java.util.ArrayList;

class Patient extends Person {
    private String medicalRecordNum;
    private String currentMedications;
    private List<Appointment> appointments;
    private Doctor doctor;
    private String medicalRecord;

    // Constructor
    public Patient(String firstName, String lastName, String email, String phoneNum, String ID, String medicalRecordNum, String currentMedications, Doctor doctor, String medicalRecord) {
        super(firstName, lastName, email, phoneNum, ID);
        this.medicalRecordNum = medicalRecordNum;
        this.currentMedications = currentMedications;
        this.doctor = doctor;
        this.medicalRecord = medicalRecord;
        this.appointments = new ArrayList<>();
    }

    // Methods
    public void bookAppointment(Appointment appointment) {
        // Implementation
    }

    public void addMedicalHistory(String record) {
        // Implementation
    }

    public String getCurrentMedications() {
        return currentMedications;
    }

    public Appointment getNextAppointment() {
        // Implementation
        return null;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }
}
