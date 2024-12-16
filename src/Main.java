import api.models.*;
import api.models.exceptions.*;
import java.time.Duration;
import java.time.LocalDateTime;
import ui.UiMain; // 

public class Main {
    public static Receptionist recep1;
    
    public static void main(String[] args) {
        // Creation of hospital
        Hospital childrensHospital = new Hospital("Playground Street", "General", 10000);

        // Creation of receptionist
        recep1 = new Receptionist("Bill", "Reid", "example@gmail.com", "123-456-7891", "123", "Night", childrensHospital);

        // Creation of doctor objects
        Doctor doctor1 = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        Doctor doctor2 = new Doctor("Jane", "Smith", "janesmith@example.com", "0987654321", "D002", "Neurology", 10, true);
        Doctor doctor3 = new Doctor("Emily", "Johnson", "emilyjohnson@example.com", "1122334455", "D003", "Orthopedics", 8, false);
        Doctor doctor4 = new Doctor("Michael", "Brown", "michaelbrown@example.com", "5566778899", "D004", "Pediatrics", 12, true);
        Doctor doctor5 = new Doctor("Laura", "Adams", "lauraadams@example.com", "1231231234", "D005", "Dermatology", 20, true);
        Doctor doctor6 = new Doctor("Carlos", "Garcia", "cgarcia@example.com", "9876543212", "D006", "Oncology", 18, true);
        Doctor doctor7 = new Doctor("Aisha", "Khan", "aishakhan@example.com", "5554443332", "D007", "Endocrinology", 22, false);

        // Add doctors to the hospital (catching exceptions for duplicates)
        try {
            childrensHospital.addDoctor(doctor1);
            childrensHospital.addDoctor(doctor2);
            childrensHospital.addDoctor(doctor3);
            childrensHospital.addDoctor(doctor4);
            childrensHospital.addDoctor(doctor5);
            childrensHospital.addDoctor(doctor6);
            childrensHospital.addDoctor(doctor7);
        } catch (DoctorManagementException e) {
            System.out.println("One of the doctors is already at the hospital.");
        }

        // Creation of patient objects
        Patient p1 = new Patient("Ryan","Chwiecko","rchwiec@uwo.ca", "123-456-7891","P2345","",doctor2,"");
        Patient p2 = new Patient("Sonia","Sharma","sshar@uwo.ca", "987-654-3211","P23456","",doctor3,"");
        Patient p3 = new Patient("Mark", "Taylor", "marktaylor@example.com", "234-567-8910", "P3333", "", doctor1, "");
        Patient p4 = new Patient("Lisa", "Chen", "lisachen@example.com", "345-678-9012", "P4444", "", doctor5, "");
        Patient p5 = new Patient("Sam", "Williams", "swilliams@example.com", "456-789-0123", "P5555", "", doctor6, "");
        Patient p6 = new Patient("Dana", "Lee", "danalee@example.com", "567-890-1234", "P6666", "", doctor4, "");
        Patient p7 = new Patient("Ahmed", "Hussein", "ahussein@example.com", "678-901-2345", "P7777", "", doctor7, "");
        Patient p8 = new Patient("Nina", "Rodriguez", "ninarod@example.com", "789-012-3456", "P8888", "", doctor2, "");
        Patient p9 = new Patient("Oliver", "Smith", "oliversmith@example.com", "890-123-4567", "P9999", "", doctor3, "");
        Patient p10 = new Patient("Bella", "Clark", "bellaclark@example.com", "901-234-5678", "P1010", "", doctor1, "");

        // Add patients to the hospital (catching exceptions for duplicates)
        try {
            childrensHospital.addPatient(p1);
            childrensHospital.addPatient(p2);
            childrensHospital.addPatient(p3);
            childrensHospital.addPatient(p4);
            childrensHospital.addPatient(p5);
            childrensHospital.addPatient(p6);
            childrensHospital.addPatient(p7);
            childrensHospital.addPatient(p8);
            childrensHospital.addPatient(p9);
            childrensHospital.addPatient(p10);
        } catch (PatientManagementException e) {
            System.out.println("One of the patients is already at the hospital.");
        }

        // Make a few appointments
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration thirtyMinutes = Duration.ofMinutes(30);

        recep1.makeAppointment(p2, doctor4, "general", "Consultation with Dr. Brown", currentDateTime, childrensHospital, thirtyMinutes, "Drink water prior to appointment, no caffeine");
        recep1.makeAppointment(p3, doctor1, "general", "Routine Heart Checkup", currentDateTime.plusHours(1), childrensHospital, thirtyMinutes, "Arrive 15 minutes early");
        recep1.makeAppointment(p5, doctor6, "surgery", "Oncology Follow-up", currentDateTime.plusDays(1), childrensHospital, thirtyMinutes, "Fast for 8 hours before");
        recep1.makeAppointment(p8, doctor2, "general", "Neurology Consultation", currentDateTime.plusDays(2).plusHours(2), childrensHospital, thirtyMinutes, "Bring previous MRI scans");
        recep1.makeAppointment(p10, doctor1, "general", "Cardiology Follow-up", currentDateTime.plusDays(3).plusHours(3), childrensHospital, thirtyMinutes, "No special instructions");

        UiMain ui_of_program = new UiMain();
        ui_of_program.receiveInformation(recep1);
    }
}


