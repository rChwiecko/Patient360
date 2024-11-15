
import java.time.Duration;
import java.time.LocalDateTime;
public class main {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        Hospital childrensHospital = new Hospital("plaeground stret", "general", 10000);
        Receptionist sonia = new Receptionist("Sonia", "Sharma", "idk@gmai.com", "idk", "123", "night", childrensHospital);
        Doctor doctor1 = new Doctor("John", "Doe", "johndoe@example.com", "1234567890", "D001", "Cardiology", 15, true);
        Doctor doctor2 = new Doctor("Jane", "Smith", "janesmith@example.com", "0987654321", "D002", "Neurology", 10, true);
        Doctor doctor3 = new Doctor("Emily", "Johnson", "emilyjohnson@example.com", "1122334455", "D003", "Orthopedics", 8, false);
        Doctor doctor4 = new Doctor("Michael", "Brown", "michaelbrown@example.com", "5566778899", "D004", "Pediatrics", 12, true);
        try {
            childrensHospital.addDoctor(doctor1);
            childrensHospital.addDoctor(doctor2);
            childrensHospital.addDoctor(doctor3);   
        } catch (DoctorManagementException e) {
            System.out.println("Doctor already at that hospital");
        }

        Patient p2 = new Patient("alex", "dang", "alex@gmail", "2223", "1234", "1", doctor4, null);
        try {
            sonia.addPatient("Ryan", "Chwiecko", "123-456-6789", "example@gmail", doctor2);        
        } catch (PatientManagementException e) {
            System.out.println("Patient already there");
        }
    
        Patient p1 = childrensHospital.getPatientsPresent().get(0);
        boolean a1Result = sonia.makeAppointment(p1, doctor4, "surgery", "ear checkup", LocalDateTime.of(2024, 11, 15, 10, 30), childrensHospital, Duration.ofHours(1), "clean ears");
        System.out.println(doctor4.getAppointments().get(0).getAppointmentType());
        System.out.println("before: "+doctor4.getAppointments());
        try {
            sonia.cancelAppointment(doctor4.getAppointments().get(0));
        } catch (AppointmentException e) {
            System.out.println("Appointment doesnt exist");
        }
        System.out.println("new: "+doctor4.getAppointments());

        // boolean a2Result = sonia.makeAppointment(p2, doctor4, "general", "ear checkup", LocalDateTime.of(2024, 11, 15, 14, 00), childrensHospital, Duration.ofMinutes(30), "clean ears");
        // if (a1Result){
        //     System.out.println("Patient 1's appointment was booked");
        // }
        // else {
        //     System.out.println("Doctor was not available for patient 1");
        // }

        // if (a2Result){
        //     System.out.println("Patient 2's appointment was booked");
        // }
        // else {
        //     System.out.println("Doctor was not available for patient 2");
        // }

    }
}

