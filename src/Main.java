import api.models.*;
import api.models.exceptions.*;
import java.time.Duration;
import java.time.LocalDateTime;
public class Main {
    
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
            childrensHospital.addDoctor(doctor3);
            childrensHospital.addDoctor(doctor4);   
        } catch (DoctorManagementException e) {
            System.out.println("Doctor already at that hospital");
        }

        Patient p1 = new Patient("Ryan","Chwiecko","rchwiec@uwo.ca", "123-456-7891","P2345","",doctor2,"");
        Patient p2 = new Patient("Sonia","Sharma","sshar@uwo.ca", "987-654-3211","P23456","",doctor3,"");
        try {
            childrensHospital.addPatient(p1);
            childrensHospital.addPatient(p2);
        } catch (PatientManagementException e) {
            System.out.println("Patient already at hospital");
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration thirtyMinutes = Duration.ofMinutes(30);
        recep1.makeAppointment(p2, doctor4, "general", "Consultation with Dr. Smith", currentDateTime, childrensHospital, thirtyMinutes, "Drink water prior to appointment, no caffeine");
    
        
    }
}

