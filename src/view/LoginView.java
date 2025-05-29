package view;

import Admin.AdminView;
import dao.CustomerDao;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JRadioButton adminRadio, userRadio;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JPanel formPanel;

    public LoginView() {
        setTitle("캠핑카 예약 시스템 로그인");
        setSize(460, 380);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 중앙 정렬
        setResizable(false); // 창 크기 고정

        // 제목
        JLabel title = new JLabel("로그인");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // 라디오 버튼
        adminRadio = new JRadioButton("관리자");
        userRadio = new JRadioButton("회원");
        ButtonGroup group = new ButtonGroup();
        group.add(adminRadio);
        group.add(userRadio);
        adminRadio.setSelected(true);

        JPanel radioPanel = new JPanel();
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        radioPanel.add(adminRadio);
        radioPanel.add(Box.createHorizontalStrut(20));
        radioPanel.add(userRadio);

        // 아이디 행
        JPanel idRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("아이디:");
        idLabel.setPreferredSize(new Dimension(70, 30));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 30));
        idRow.add(idLabel);
        idRow.add(usernameField);

        // 비밀번호 행
        JPanel pwRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setPreferredSize(new Dimension(70, 30));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 30));
        pwRow.add(pwLabel);
        pwRow.add(passwordField);

        // 입력창 묶음
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.add(idRow);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(pwRow);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(400, 100));
        formPanel.setVisible(false);

        // 로그인 버튼
        loginBtn = new JButton("로그인");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setPreferredSize(new Dimension(120, 30));

        // 메인 레이아웃
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(radioPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(loginBtn);

        add(mainPanel);

        // 관리자 선택 시 입력창 숨기기
        adminRadio.addActionListener(e -> {
            formPanel.setVisible(false);
            formPanel.revalidate();
            formPanel.repaint();
        });

        // 회원 선택 시 입력창 표시
        userRadio.addActionListener(e -> {
            formPanel.setVisible(true);
            formPanel.revalidate();
            formPanel.repaint();
        });

        // 로그인 로직
        loginBtn.addActionListener(e -> {
            if (adminRadio.isSelected()) {
                try {
//                    DBUtil.connect("root", "1234");
//                    JOptionPane.showMessageDialog(null, "관리자 로그인 성공");
                    JOptionPane.showMessageDialog(null, "관리자 로그인 성공");

                    // 관리자 화면으로 이동
                    dispose(); // 현재 로그인 창 닫기
                    new AdminView(); // 관리자 창 열기
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "DB 연결 실패: " + ex.getMessage());
                }
            } else {
                String id = usernameField.getText();
                String pw = new String(passwordField.getPassword());
                CustomerDao dao = new CustomerDao();
                if (dao.login(id, pw)) {
                    JOptionPane.showMessageDialog(null, "회원 로그인 성공");
                    dispose();
                    new view.User.UserView();
                    // TODO: UserView로 이동
                } else {
                    JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 틀렸습니다.");
                }
            }
        });

        setVisible(true);
    }
}
