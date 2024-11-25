# Patient360

**Patient360** is an intuitive and efficient hospital management system designed to address inefficiencies in healthcare administration, particularly for receptionists. By consolidating critical tasks like appointment scheduling, patient record management, and doctor availability coordination into a single user-friendly platform, Patient360 streamlines operations and improves patient care delivery.

**It is currently a work in progress**

---

## Classes

### **Back End**

- **HospitalMember.java**: Base class for all members (e.g., patients, doctors, receptionists) in the hospital system.
- **Appointment.java**: Represents an appointment, including details like date, time, and associated members.
- **AppointmentFactory.java**: Factory class for creating appointment instances.
- **Doctor.java**: Represents a doctor with attributes such as specialty and availability.
- **FollowUp.java**: Extends Appointment to specifically handle follow-up appointments for patients.
- **GeneralConsultation.java**: Represents a general consultation appointment.
- **Hospital.java**: Core class for the hospital, managing patients, doctors, and overall capacity.
- **Patient.java**: Represents a patient with details like medical history and appointments.
- **Person.java**: Abstract class defining common attributes for individuals in the system.
- **Prescription.java**: Represents a prescription issued to a patient during their treatment.
- **Receptionist.java**: Represents a receptionist who manages scheduling and patient check-ins/check-outs.
- **Surgery.java**: Represents a surgical appointment with additional attributes like required equipment or operating room.

### **Exception Handling**

- **AppointmentException.java**: Exception thrown for issues related to appointment creation or management.
- **DoctorManagementException.java**: Handles exceptions specific to doctor-related operations (e.g., scheduling conflicts).
- **HospitalFullException.java**: Triggered when attempting to add a patient to a hospital at full capacity.
- **PatientManagementException.java**: Used for errors in patient operations (e.g., duplicate records, invalid check-ins).

---

### **Front End** *(to be fully implemented in the third deliverable)*

- **LoginFrame.java**: Receptionist has to verify their credentials to enter the system.
- **StartingScreen.java**: This is the main screen, guiding the receptionist on what task they would like to accomplish in the system.
- **BookAppointmentScreen.java**: UI when the receptionist has to book an appointment for the patient.
- **CheckInPatientScreen.java**: UI when the receptionist wants to check in a patient.
- **DoctorAvailability.java**: UI when the receptionist wants to check the doctor availability based on the patient's needs.
- **PatientDatabaseScreen.java**: UI of the patient regarding their information within the system.

---

## Implementation Details

This project is built as a **desktop application** using **JavaFX** and **Swing** for the user interface. The system is designed for efficiency, maintainability, and scalability, with a focus on delivering core functionalities.

### **Key Technologies**
- **JavaFX**: For building a responsive user interface.
- **Swing**: For additional UI components.
- **Java**: Core implementation language.

---

## Repository Structure

- **`/.vscode`**: Contains configuration files for the VS Code editor.
- **`/api`**: Contains all the back-end files.
- **`/ui`**: Contains all the front-end files for the user interface using JavaSwing and JavaFX.
- **`/.DS_Store`**: Metadata file (commonly auto-generated in macOS environments).
- **`/readme.md`**: The file containing details regarding the project.

---

## Running the Application

To run the application, follow these steps:

1. **Ensure your environment is set up**:
   - Make sure you have a Java Development Kit (JDK) installed (preferably version 8 or higher).
   - Set up your IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code) to recognize the project's structure and dependencies.

2. **Compile the project**:
   - Open the project in your IDE.
   - Build the project to ensure there are no compilation errors.

3. **Run the `main.java` file**:
   - Locate `main.java` in the project's file structure.
   - Right-click on `main.java` and select `Run` (or execute it through the command line if you're using a terminal).
   - Example command line execution:
     ```bash
     javac main.java
     java main
     ```

4. **Follow the prompts**:
   - After the application starts, follow the on-screen instructions to interact with the system.

5. **Notes**:
   - Ensure the application is connected to the correct UI files (if front-end components are included).
   - If running in an IDE, ensure that the project uses the correct configuration to run the main method.

Enjoy using **Patient360**!

