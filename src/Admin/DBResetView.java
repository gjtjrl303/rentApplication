package Admin;

import service.DBResetService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBResetView extends JFrame {

    private final DBResetService dbResetService = new DBResetService();
    public DBResetView() {
        setTitle("데이터베이스 초기화");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 창만 닫히게

        JLabel label = new JLabel("정말로 모든 데이터를 초기화하시겠습니까?");
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton resetBtn = new JButton("초기화 실행");
        JButton cancelBtn = new JButton("취소");

        JPanel btnPanel = new JPanel();
        btnPanel.add(resetBtn);
        btnPanel.add(cancelBtn);

        add(label, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // TODO: 백엔드 DAO 연결
        resetBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbResetService.init();
                JOptionPane.showMessageDialog(null, "데이터베이스 초기화가 완료되었습니다.");
                dispose(); // 창 닫기
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
