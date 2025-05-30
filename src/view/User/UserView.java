    package view.User;

    import dao.CampingCarDao;
    import dao.RentalDao;
    import entitiy.Customer;
    import service.CampingCarService;
    import service.RentalService;

    import javax.swing.*;
    import java.awt.*;

    public class UserView extends JFrame {
        private final Customer loginUser;
        private final JTabbedPane tabbedPane;

        public UserView(Customer loginUser) {
            this.loginUser = loginUser;
            setTitle("회원 페이지 - 캠핑카 예약 시스템");
            setSize(1000, 700);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            tabbedPane = new JTabbedPane();

            // ★ 패널 생성 시 this 전달
            tabbedPane.add("캠핑카 조회 및 예약하기",
                    new CamperSearchPanel(
                            new CampingCarService(new CampingCarDao()),
                            tabbedPane,
                            new RentalService(new RentalDao()),
                            loginUser.getId(),
                            loginUser.getLicenseNumber(),
                            this)
            );
            tabbedPane.add("나의 예약 조회",
                    new MyReservationListPanel(loginUser.getId(), this)
            );
            tabbedPane.add("정비 의뢰", new RepairRequestPanel(loginUser.getId()));  // ✅ 수정

            add(tabbedPane);
            setVisible(true);

            // 로그인 정보 알림
            JOptionPane.showMessageDialog(this,
                    "환영합니다, " + loginUser.getName() + "님!\n" +
                            "아이디: " + loginUser.getUsername() +
                            "\n라이선스: " + loginUser.getLicenseNumber());
        }

        // ★★★ 전체 탭 새로고침 메서드
        public void refreshAllTabs() {
            tabbedPane.setComponentAt(0, new CamperSearchPanel(
                    new CampingCarService(new CampingCarDao()),
                    tabbedPane,
                    new RentalService(new RentalDao()),
                    loginUser.getId(),
                    loginUser.getLicenseNumber(),
                    this
            ));
            tabbedPane.setComponentAt(1, new MyReservationListPanel(loginUser.getId(), this));
            // 정비 의뢰 탭 등 필요시 추가
        }
    }
