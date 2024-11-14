import javax.swing.*;

public class CheckInPatientScreen extends JFrame {
    public CheckInPatientScreen() {
        setTitle("Check-in Patient");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("This is the Check-in Patient screen.");
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CheckInPatientScreen();
    }
}
