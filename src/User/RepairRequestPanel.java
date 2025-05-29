package User;

import javax.swing.*;
import java.awt.*;

public class RepairRequestPanel extends JPanel {
    public RepairRequestPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("🛠️ 정비소에 정비 의뢰", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
