package ui;

import api.controller.PatientController;
import api.models.Receptionist;

public class UiMain {

    // Constructor doesn't need to recursively instantiate a new UiMain
    public UiMain() {
        // Constructor can be empty or perform other setup if necessary
    }

    // Corrected method name and added appropriate parameter
    public void receiveInformation(Receptionist receptionist) {
        // Create a PatientController object
        PatientController controller = new PatientController(receptionist);

        // Create an instance of LoginFrame and pass the controller to it
        LoginFrame myFrame = new LoginFrame(controller);
        myFrame.initialize(); // Assuming LoginFrame is a GUI class that initializes the login screen
    }
}


