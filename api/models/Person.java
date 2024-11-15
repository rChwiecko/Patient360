

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String ID;

    // Constructor
    public Person(String firstName, String lastName, String email, String phoneNum, String ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.ID = ID;
    }

    // Methods
    
    /** 
     * @return String
     */
    public String getRole() {
        // Implementation
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getID() {
        return ID;
    }
}
