package User;

import javax.swing.*;
import java.awt.*;

public class ReservationDeletePanel extends JPanel {
    public ReservationDeletePanel() {
        setLayout(new BorderLayout());
        add(new JLabel("❌ 예약 삭제 기능", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
