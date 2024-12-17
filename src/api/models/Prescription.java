package api.models;

/**
 * Represents a prescription assigned to a patient for medication.
 */
public class Prescription {
    private String prescriptionId;    // Unique ID for the prescription
    private Patient patient;          // Patient to whom the prescription is assigned
    private String medicationName;    // Name of the medication
    private String dosage;            // Dosage of the medication (e.g., 500mg)
    private String frequency;         // Frequency of medication intake (e.g., twice daily)
    private String prescriptionDate;  // Date when the prescription was issued
    private String expiryDate;        // Expiry date of the prescription
    private String instructions;      // Additional instructions for the patient
    private int refillCount;          // Number of refills allowed for the prescription

    /**
     * Constructor to initialize all fields of the Prescription class.
     *
     * @param prescriptionId   Unique identifier for the prescription
     * @param patient          The patient associated with this prescription
     * @param medicationName   Name of the medication
     * @param dosage           Dosage amount for the medication
     * @param frequency        Frequency of medication intake
     * @param prescriptionDate Date the prescription was issued
     * @param expiryDate       Expiry date of the prescription
     * @param instructions     Additional instructions for taking the medication
     * @param refillCount      Number of refills allowed for the prescription
     */
    public Prescription(String prescriptionId, Patient patient, String medicationName, String dosage, String frequency,
                        String prescriptionDate, String expiryDate, String instructions, int refillCount) {
        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.prescriptionDate = prescriptionDate;
        this.expiryDate = expiryDate;
        this.instructions = instructions;
        this.refillCount = refillCount;
    }

    // Getters

    /**
     * Gets the unique prescription ID.
     * @return Prescription ID
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * Gets the patient assigned to this prescription.
     * @return Patient object
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Gets the name of the prescribed medication.
     * @return Medication name
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Gets the dosage for the medication.
     * @return Dosage (e.g., "500mg")
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Gets the frequency of medication intake.
     * @return Frequency (e.g., "twice daily")
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Gets the date the prescription was issued.
     * @return Prescription date
     */
    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    /**
     * Gets the expiry date of the prescription.
     * @return Expiry date
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Gets any additional instructions provided with the prescription.
     * @return Instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Gets the number of refills allowed for this prescription.
     * @return Number of refills
     */
    public int getRefillCount() {
        return refillCount;
    }

    // Methods to be implemented

    /**
     * Checks if the prescription has expired based on the expiry date.
     *
     * @return true if the prescription has expired, false otherwise
     */
    public boolean isExpired() {
        // Logic to check if current date is past the expiry date
        return false; // To be implemented
    }

    /**
     * Determines whether the prescription can still be refilled.
     *
     * @return true if refills are available, false otherwise
     */
    public boolean canRefill() {
        // Logic to check refillCount > 0
        return false; // To be implemented
    }

    /**
     * Provides a summary of the prescription details.
     *
     * @return A formatted string containing prescription details
     */
    public String getPrescriptionDetails() {
        // Format details like medication name, dosage, frequency, etc.
        return null;
    }
}