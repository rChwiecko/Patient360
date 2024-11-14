import javax.swing.*;

public class BookAppointmentScreen extends JFrame 
{
    public BookAppointmentScreen() {
        setTitle("Book an Appointment");
        setSize(1900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("This is the Book Appointment screen.");
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        new BookAppointmentScreen();
    }
}
