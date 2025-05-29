package User;

import javax.swing.*;
import java.awt.*;

public class ReservationChangeDatePanel extends JPanel {
    public ReservationChangeDatePanel() {
        setLayout(new BorderLayout());
        add(new JLabel("📆 예약 일정 변경", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
