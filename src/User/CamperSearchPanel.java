package User;

import entitiy.CampingCar;
import service.CampingCarService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CamperSearchPanel extends JPanel {

    private final CampingCarService campingCarService;

    public CamperSearchPanel(CampingCarService campingCarService) {
        this.campingCarService = campingCarService;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🚐 캠핑카 조회 및 대여 가능 일자 보기", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        viewCars(); // 캠핑카 리스트 출력
    }

    public void viewCars() {
        List<CampingCar> campingCars = campingCarService.findAll();

        String[] columnNames = {
                "ID", "회사 ID", "차량 이름", "차량 번호판", "정원",
                "이미지 URL", "설명", "렌트 가격", "등록일"
        };

        String[][] data = new String[campingCars.size()][columnNames.length];

        for (int i = 0; i < campingCars.size(); i++) {
            CampingCar car = campingCars.get(i);
            data[i][0] = String.valueOf(car.getId());
            data[i][1] = String.valueOf(car.getCompanyId());
            data[i][2] = car.getCarName();
            data[i][3] = car.getLicensePlate();
            data[i][4] = String.valueOf(car.getCapacity());
            data[i][5] = car.getImageUrl();
            data[i][6] = car.getDescription();
            data[i][7] = car.getRentalPrice().toString();
            data[i][8] = car.getRegistrationDate().toString();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
