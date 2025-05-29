package User;

import javax.swing.*;
import java.awt.*;

public class ReservationRegisterPanel extends JPanel {
    public ReservationRegisterPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("📅 캠핑카 대여 등록", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
