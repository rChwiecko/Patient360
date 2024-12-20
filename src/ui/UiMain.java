package ui;  // Declare the package where this class resides

import api.controller.PatientController;  // Import the PatientController class from the API package
import api.models.Receptionist;  // Import the Receptionist class from the models package

public class UiMain {  // Define the UiMain class, which contains the entry point for initializing the UI

    // Constructor doesn't need to recursively instantiate a new UiMain
    public UiMain() {  // Define the constructor for the UiMain class
        // Constructor can be empty or perform other setup if necessary
    }

    // Corrected method name and added appropriate parameter
    public void receiveInformation(Receptionist receptionist) {  // Define the receiveInformation method, which takes a Receptionist object as a parameter
        // Create a PatientController object
        PatientController controller = new PatientController(receptionist);  // Create a new PatientController object and pass the receptionist to it

        // Create an instance of LoginFrame and pass the controller to it
        LoginFrame myFrame = new LoginFrame(controller);  // Instantiate a LoginFrame object, passing the controller as a parameter
        myFrame.initialize(); // Assuming LoginFrame is a GUI class that initializes the login screen
    }
}



