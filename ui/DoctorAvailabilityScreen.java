import javax.swing.*;

public class DoctorAvailabilityScreen extends JFrame {
    public DoctorAvailabilityScreen() {
        setTitle("View Doctor Availability");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("This is the Doctor Availability screen.");
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        new DoctorAvailabilityScreen();
    }
}
