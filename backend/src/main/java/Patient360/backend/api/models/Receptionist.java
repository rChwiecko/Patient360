package Patient360.backend.api.models;
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
                this.getLocation().checkPatientOut(patient);
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


    public void makeAppointment(Patient patient, Doctor doctor, String appointmentType, String description, LocalDateTime date, Hospital location){
        switch (appointmentType){
            case "general":
                GeneralConsultation newAppointmentGeneral = new GeneralConsultation(patient, doctor, description, date, location);
                patient.bookAppointment(newAppointmentGeneral);
                doctor.scheduleAppointment(newAppointmentGeneral);
                break;
            case "follow":
                FollowUp newAppointmentFollow = new FollowUp(patient, doctor, description, date, location);
                patient.bookAppointment(newAppointmentFollow);
                doctor.scheduleAppointment(newAppointmentFollow);
                break;
            case "surgery":
                Surgery newAppointmentSurgery = new Surgery(patient, doctor, description, date, location);
                patient.bookAppointment(newAppointmentSurgery);
                doctor.scheduleAppointment(newAppointmentSurgery);
                break;
        }
    }

    public void addPatient(String firstName, String lastName, String phoneNumber, String email, Doctor doctor) {
        this.getLocation().addPatient(new Patient(firstName, lastName, email, phoneNumber, firstName, lastName, phoneNumber, doctor, email));
    }
}
