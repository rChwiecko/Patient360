package Patient360.backend.api.models;
import java.time.Duration;
import java.time.LocalDateTime;
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

    public void managePatientRecord(Patient patient, String additionalRecord, Prescription newPrescription) {
        if (additionalRecord != null){
            patient.updateMedicalRecord(additionalRecord);
        }
        if (newPrescription != null){
            patient.addPrescription(newPrescription);
        }

    }

    public void checkInPatient(Patient patient, Appointment appointment) {
        if (patient != null){
            patient.checkIn();
            if (this.getLocation() != null){
                this.getLocation().checkPatientIn(patient);
            }
            //appointment is considered optional because walk in appointments are possible
            if (appointment != null){
                appointment.updateStatus("in progress");
            }
            
        }
    }

    public void checkOutPatient(Patient patient, Appointment appointment) {
        if (patient != null){
            patient.checkOut();
            if (this.getLocation() != null){
                try{
                    this.getLocation().checkPatientOut(patient);
                }catch
            }
            //appointment is considered optional because walk in appointments are possible
            if (appointment != null){
                appointment.updateStatus("complete");
            }
        }
    }

    public String getWorkShift() {
        return workShift;
    }

    public Hospital getLocation(){
        return this.recepHospital;
    }

    public void changeLocation(Hospital newHospital) {
        this.recepHospital = newHospital;
    }

    //returns false if the creation was not successful (ie, doctor was not available)
    public boolean makeAppointment(Patient patient, Doctor doctor, String appointmentType, String description, LocalDateTime date, Hospital location, Duration appointmentDuration, String preAppointmentInstructions){
        if (doctor.isAvailable(date, appointmentDuration)){
            switch (appointmentType){
                case "general" -> {
                    GeneralConsultation newAppointmentGeneral = new GeneralConsultation(patient, doctor, description, date, location, preAppointmentInstructions, Duration.ofHours(1));
                    patient.bookAppointment(newAppointmentGeneral);
                    doctor.scheduleAppointment(newAppointmentGeneral);
                }
                case "follow" -> {
                    FollowUp newAppointmentFollow = new FollowUp(patient, doctor, description, date, location, preAppointmentInstructions, Duration.ofHours(1));
                    patient.bookAppointment(newAppointmentFollow);
                    doctor.scheduleAppointment(newAppointmentFollow);
                }
                case "surgery" -> {
                    Surgery newAppointmentSurgery = new Surgery(patient, doctor, description, date, location, preAppointmentInstructions, Duration.ofHours(1));
                    patient.bookAppointment(newAppointmentSurgery);
                    doctor.scheduleAppointment(newAppointmentSurgery);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
//String firstName, String lastName, String email, String phoneNum, String ID, String medicalRecordNum, Doctor doctor, String medicalRecord
    public void addPatient(String firstName, String lastName, String phoneNumber, String email, Doctor doctor) {
        Patient newPaitient = new Patient(firstName, lastName, email, phoneNumber, "12", "3", doctor, "");
        this.getLocation().addPatient(newPaitient);
        //if new patient, no appointment exists yet
        this.checkInPatient(newPaitient, null);
    }
}
