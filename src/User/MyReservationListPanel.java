package User;

import javax.swing.*;
import java.awt.*;

public class MyReservationListPanel extends JPanel {
    public MyReservationListPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("📋 나의 예약 전체 보기", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
