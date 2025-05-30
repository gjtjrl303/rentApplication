package view.User;

import entitiy.CampingCar;
import entitiy.RepairShop;
import entitiy.ExternalRepair;
import service.CampingCarService;
import service.RepairShopService;
import service.ExternalRepairService;
import dao.CampingCarDao;
import dao.RepairShopDao;
import dao.ExternalRepairDao;
import service.RentalService;
import dao.RentalDao;
import entitiy.Rental;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RepairRequestPanel extends JPanel {
    public RepairRequestPanel(Long customerId) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🛠️ 외부 정비소에 정비 의뢰", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        CampingCarService carService = new CampingCarService(new CampingCarDao());
        RepairShopService shopService = new RepairShopService(new RepairShopDao());
        ExternalRepairService repairService = new ExternalRepairService(new ExternalRepairDao());
        RentalService rentalService = new RentalService(new RentalDao());

        List<Rental> myRentals = rentalService.findByCustomerId(customerId);
        List<RepairShop> repairShops = shopService.findAll();

        JComboBox<String> carCombo = new JComboBox<>();
        JComboBox<String> shopCombo = new JComboBox<>();
        JTextField detailField = new JTextField();
        JTextField dateField = new JTextField("2025-06-01");
        JTextField costField = new JTextField();

        for (Rental r : myRentals) {
            carCombo.addItem("[" + r.getCarId() + "] " + r.getRentalStartDate() + "부터 " + r.getRentalDurationDays() + "일");
        }

        for (RepairShop shop : repairShops) {
            shopCombo.addItem(shop.getName() + " (" + shop.getId() + ")");
        }

        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("대여한 캠핑카:"));
        formPanel.add(carCombo);
        formPanel.add(new JLabel("정비소 선택:"));
        formPanel.add(shopCombo);
        formPanel.add(new JLabel("정비 내용:"));
        formPanel.add(detailField);
        formPanel.add(new JLabel("정비 날짜 (yyyy-MM-dd):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("예상 비용:"));
        formPanel.add(costField);

        JButton submitBtn = new JButton("정비 요청 등록");

        submitBtn.addActionListener(e -> {
            try {
                int carIndex = carCombo.getSelectedIndex();
                int shopIndex = shopCombo.getSelectedIndex();

                if (carIndex < 0 || shopIndex < 0) {
                    JOptionPane.showMessageDialog(this, "캠핑카와 정비소를 모두 선택하세요.");
                    return;
                }

                Rental rental = myRentals.get(carIndex);
                RepairShop selectedShop = repairShops.get(shopIndex);

                ExternalRepair repair = new ExternalRepair(
                        null,
                        rental.getCarId(),
                        selectedShop.getId(),
                        rental.getCompanyId(),
                        rental.getLicenseNumber(),
                        detailField.getText(),
                        LocalDate.parse(dateField.getText()),
                        Double.parseDouble(costField.getText()),
                        LocalDate.parse(dateField.getText()).plusDays(14)
                );

                repairService.save(repair);
                JOptionPane.showMessageDialog(this, "정비 요청이 성공적으로 등록되었습니다.");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "정비 요청 등록 실패: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
    }
}
