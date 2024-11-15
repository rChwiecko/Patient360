import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import composite.HospitalMember;

public class Receptionist extends Person implements HospitalMember{
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

    @Override
    public String getRole(){
        return "Receptionist";
    }

    
    /** 
     * @param patient
     * @param doctor
     * @return boolean
     */
    public boolean changeDoctor(Patient patient, Doctor doctor){
        //prevent null pointer exception
        if (patient == null){
            return false;
        }
        try {
            patient.setDoctor(doctor);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * remove appointment from doctor and patient attributes
     * @param appointment
     */
    public void cancelAppointment(Appointment appointment) throws AppointmentException{
        if (appointment == null){
            throw new AppointmentException("Empty appointment");
        }
        
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

    
    public boolean checkInPatient(Patient patient, Appointment appointment) {
        if (patient != null){
            patient.checkIn();
            if (this.getLocation() != null){
                try{
                    this.getLocation().checkPatientIn(patient);
                }catch (PatientManagementException e){
                    return false;
                }
            }
            //appointment is considered optional because walk in appointments are possible
            if (appointment != null){
                appointment.updateStatus("in progress");
            }return true;
            
        }else{
            return false;
        }
    }

    public boolean checkOutPatient(Patient patient, Appointment appointment) {
        if (patient != null){
            patient.checkOut();
            if (this.getLocation() != null){
                try{
                    this.getLocation().checkPatientOut(patient);
                }catch(PatientManagementException e){
                    return false;
                }
            }
            if (appointment != null){
                appointment.updateStatus("complete");
            }return true;
        }
        return false;
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
            AppointmentFactory factory;
            switch (appointmentType.toLowerCase()) {
                case "general" -> factory = new GeneralConsultationFactory();
                case "follow" -> factory = new FollowUpFactory();
                case "surgery" -> factory = new SurgeryFactory();
                default -> throw new IllegalArgumentException("Unknown appointment type: " + appointmentType);
            }
            Appointment appointment = factory.createAppointment(patient, doctor, description, date, location, preAppointmentInstructions, appointmentDuration);

            // Book and schedule the appointment
            patient.bookAppointment(appointment);
            doctor.scheduleAppointment(appointment);
        
            return true;
        }
        else{
            return false;
        }
    }
//String firstName, String lastName, String email, String phoneNum, String ID, String medicalRecordNum, Doctor doctor, String medicalRecord
    public void addPatient(String firstName, String lastName, String phoneNumber, String email, Doctor doctor) throws PatientManagementException{
        Patient newPaitient = new Patient(firstName, lastName, email, phoneNumber, "12", "3", doctor, "");
        //check to see if patient is already a member of the hospital
        for (Patient patient: this.getLocation().getPatients()){
            if (patient.getFirstName().equals(newPaitient.getFirstName()) && patient.getLastName().equals(newPaitient.getLastName())){
                throw new PatientManagementException("Patient already exists");
            }
        }
        this.getLocation().addPatient(newPaitient);
        this.checkInPatient(newPaitient, null);
    }
}
