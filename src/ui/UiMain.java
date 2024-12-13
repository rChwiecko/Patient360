package ui;

import api.controller.PatientController;
import api.models.Hospital;


public class UiMain 
{
    /* 
    void recieveInformation (Hospital hospital)
    {
        // create a patient controller object 
        PatientController controller = new PatientController(hospital);

        //controller.

        // double loop doctor list first loop iterating through list 
        // second loop will create each block (time slot) in increments of 30 minutes 
        // blue available, grey not available 

    }*/

    public static void main(String[] args) 
    {
        launchLogin();
    }
    public static void launchLogin() 
    {
        LoginFrame myFrame = new LoginFrame();
        myFrame.initialize();
    } 

    public static void launchStartingScreen() 
    {
        new StartingScreen();
    }

    public static void launchCheckInPatientScreen() {
        new CheckInPatientScreen();
    }

    public static void launchBookAppointmentScreen() {
        new BookAppointmentScreen();
    }

    public static void launchDoctorAvailabilityScreen() {
        new DoctorAvailabilityScreen();
    }

    
}

/*
 * package ui;

public class MainUIController {
    public static void main(String[] args) {
        launchStartingScreen(); // Entry point
    }

    public static void launchCheckInPatientScreen() {
        new CheckInPatientScreen();
    }

    public static void launchStartingScreen() {
        // Implement the logic to show your starting screen.
        System.out.println("Starting screen placeholder.");
        // Example: new StartingScreen();
    }

    public static void launchBookAppointmentScreen() {
        new BookAppointmentScreen();
    }

    public static void launchDoctorAvailabilityScreen() {
        new DoctorAvailabilityScreen();
    }
}

 */
