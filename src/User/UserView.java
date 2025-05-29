package view.User;

import User.*;
import dao.CampingCarDao;
import service.CampingCarService;

import javax.swing.*;
import java.awt.*;

public class UserView extends JFrame {
    private JTabbedPane tabbedPane;

    public UserView() {
        setTitle("회원 페이지 - 캠핑카 예약 시스템");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // 🚐 캠핑카 목록 조회 + 대여 가능 일자 확인
        tabbedPane.add("캠핑카 조회", new CamperSearchPanel(new CampingCarService(new CampingCarDao())));

        // 📅 캠핑카 대여 등록
        tabbedPane.add("예약 등록", new ReservationRegisterPanel());

        // 📋 내가 예약한 목록 전체 보기
        tabbedPane.add("나의 예약 조회", new MyReservationListPanel());

        tabbedPane.add("예약 삭제", new ReservationDeletePanel());

        // 🔁 예약된 캠핑카 변경
        tabbedPane.add("캠핑카 변경", new ReservationChangeCamperPanel());

        // 📆 예약된 일정 변경
        tabbedPane.add("일정 변경", new ReservationChangeDatePanel());

        // 🛠️ 외부 정비소에 정비 요청
        tabbedPane.add("정비 의뢰", new RepairRequestPanel());

        add(tabbedPane);

        setVisible(true);
    }
}
