package view.Admin;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JFrame {
    public AdminView() {
        setTitle("관리자 페이지");
        setSize(1000, 700); // 넓이 1000px, 높이 700px
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 버튼 생성
        JButton initBtn = new JButton("데이터베이스 초기화");
        initBtn.addActionListener(e -> new DBResetView());

        JButton manageBtn = new JButton("테이블 입력/삭제/변경");
        manageBtn.addActionListener(e -> new TableEditorView());

        JButton viewAllBtn = new JButton("전체 테이블 보기");
        viewAllBtn.addActionListener(e -> new AllTableView());

        JButton camperInfoBtn = new JButton("캠핑카 정비 및 부품 정보");
        camperInfoBtn.addActionListener(e -> new CamperRepairView());

        JButton customQueryBtn = new JButton("임의 SELECT 실행");
        customQueryBtn.addActionListener(e -> new CustomQueryView());
        // 레이아웃 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.add(initBtn);
        panel.add(manageBtn);
        panel.add(viewAllBtn);
        panel.add(camperInfoBtn);
        panel.add(customQueryBtn);

        add(panel);
        setVisible(true);

        // TODO: 버튼 액션 리스너 등록 (각 기능별 View 연결)
    }
}
