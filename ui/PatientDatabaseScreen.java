import javax.swing.*;

public class PatientDatabaseScreen extends JFrame {
    public PatientDatabaseScreen() {
        setTitle("Access Patient Database");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("This is the Access Patient Database screen.");
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        new PatientDatabaseScreen();
    }
}
