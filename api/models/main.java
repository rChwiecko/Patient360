import java.time.Duration;
import java.time.LocalDateTime;
public class main {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        //creation of hospital
        Hospital childrensHospital = new Hospital("plaeground stret", "general", 10000);
        //creation of receptionist
        Receptionist recep1 = new Receptionist("Bill", "Reid", "example@gmai.com", "123-456-7891", "123", "night", childrensHospital);
        //creation of doctor objects
        Doctor doctor1 = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        Doctor doctor2 = new Doctor("Jane", "Smith", "janesmith@example.com", "0987654321", "D002", "Neurology", 10, true);
        Doctor doctor3 = new Doctor("Emily", "Johnson", "emilyjohnson@example.com", "1122334455", "D003", "Orthopedics", 8, false);
        Doctor doctor4 = new Doctor("Michael", "Brown", "michaelbrown@example.com", "5566778899", "D004", "Pediatrics", 12, true);
        //will catch an exception because it attends to add the same doctor twice
        try {
            childrensHospital.addDoctor(doctor1);
            childrensHospital.addDoctor(doctor2);
            childrensHospital.addDoctor(doctor2);   
        } catch (DoctorManagementException e) {
            System.out.println("Doctor already at that hospital");
        }

        //Functionality 1: adding a patient
        Patient p1 = new Patient("Ryan","Chwiecko","rchwiec@uwo.ca", "123-456-7891","P2345","",doctor2,"");
        Patient p2 = new Patient("Sonia","Sharma","sshar@uwo.ca", "987-654-3211","P23456","",doctor3,"");
        try {
            childrensHospital.addPatient(p1);
            childrensHospital.addPatient(p2);
            childrensHospital.addPatient(p2);
        } catch (PatientManagementException e) {
            System.out.println("Patient already at hospital");
        }
        /*
        Patient patient, Doctor doctor, String description, LocalDateTime date, Hospital location, String preAppointmentInstructions, Duration appointmentDuration
         */
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration thirtyMinutes = Duration.ofMinutes(30);
        
        //Functionality 2: receptionist being able to cancel and make new appointments and give pre appointment instructions
        recep1.makeAppointment(p2, doctor4, "general", "Consultation with Dr. Smith", currentDateTime, childrensHospital, thirtyMinutes, "Drink water prior to appointment, no caffeine");
        recep1.checkInPatient(p2, null);
        System.out.println(doctor4.getAppointments().get(0).getLocation());   //will output the location of doctor 4's first appointment
        try {
            recep1.cancelAppointment(doctor4.getAppointments().get(0));
        } catch (AppointmentException e) {
            System.out.println("Appointment doesnt exist");
        }
        System.out.println(doctor4.getAppointments());  //will be empty because the appointment was cancelled

        //functionality 3: updating patient records
        Prescription newPrescription = new Prescription("123", p2, "Tyloneol", "1 Pill", "Twice daily", "2024/11/25", "2025/01/01", "Take one in the morning, and one before bed", 2);
        recep1.managePatientRecord(p2, null, newPrescription);   //can leave second param null to only add a new prescription
        recep1.managePatientRecord(p2, "Patient gets frequent headaches", null);   //leave third param null to only add additional record 
        System.out.println(p2.getCurrentMedications());
        System.out.println(p2.getMedicalRecord());
    }
}

