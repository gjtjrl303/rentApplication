package User;

import javax.swing.*;
import java.awt.*;

public class ReservationChangeCamperPanel extends JPanel {
    public ReservationChangeCamperPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("🔁 예약된 캠핑카 변경", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
