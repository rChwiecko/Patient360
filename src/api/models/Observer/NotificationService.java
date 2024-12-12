package api.models.Observer;
public class NotificationService implements Observer {
    @Override
    public void update(String message) {
        // Logic to send notification (e.g., email or SMS)
        System.out.println("Notification to patient: " + message);
    }
}