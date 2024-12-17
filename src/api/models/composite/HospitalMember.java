package api.models.composite;

/**
 * interface for the composite design pattern, used to abstract the storage of patients doctors and receptionists
 */
public interface HospitalMember {
    String getFirstName();
    String getLastName();
    String getRole();
}