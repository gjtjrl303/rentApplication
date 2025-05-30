package view.User;

import entitiy.CampingCar;
import entitiy.Rental;
import service.CampingCarService;
import service.RentalService;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CamperSearchPanel extends JPanel {

    private final JTabbedPane tabbedPane;
    private final CampingCarService campingCarService;
    private final RentalService rentalService;
    private final Long customerId;
    private final String licenseNumber;
    private final UserView userView;
    private JScrollPane scrollPane;

    public CamperSearchPanel(CampingCarService campingCarService, JTabbedPane tabbedPane,
                             RentalService rentalService, Long customerId, String licenseNumber, UserView userView) {
        this.campingCarService = campingCarService;
        this.tabbedPane = tabbedPane;
        this.rentalService = rentalService;
        this.customerId = customerId;
        this.licenseNumber = licenseNumber;
        this.userView = userView;

        setLayout(new BorderLayout());
        JLabel title = new JLabel("🚐 캠핑카 조회 및 대여 가능 일자 보기", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        viewCars();
    }

    private void viewCars() {
        if (scrollPane != null) {
            remove(scrollPane);
        }

        List<CampingCar> campingCars = campingCarService.findAll();
        List<Rental> allRentals = rentalService.findAll();

        String[] columnNames = {
                "회사명", "차량 이름", "차량 번호판", "정원",
                "이미지 URL", "설명", "렌트 가격", "등록일",
                "예약불가일자", // 버튼 셀
                "대여 시작일", "대여 기간(일)", "추가 용품", "예약"
        };

        Object[][] data = new Object[campingCars.size()][columnNames.length];

        // 차량별 예약불가일자(오늘 이후만) Set 만들기
        List<Set<LocalDate>> reservedDatesList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < campingCars.size(); i++) {
            CampingCar car = campingCars.get(i);
            Set<LocalDate> reserved = new TreeSet<>();
            for (Rental r : allRentals) {
                if (r.getCarId().equals(car.getId()) && r.getRentalStartDate() != null) {
                    LocalDate start = r.getRentalStartDate();
                    for (int d = 0; d < r.getRentalDurationDays(); d++) {
                        LocalDate date = start.plusDays(d);
                        if (!date.isBefore(today)) reserved.add(date);
                    }
                }
            }
            reservedDatesList.add(reserved);

            data[i][0] = car.getCompanyName();
            data[i][1] = car.getCarName();
            data[i][2] = car.getLicensePlate();
            data[i][3] = car.getCapacity();
            data[i][4] = car.getImageUrl();
            data[i][5] = car.getDescription();
            data[i][6] = car.getRentalPrice();
            data[i][7] = car.getRegistrationDate();
            data[i][8] = "보기"; // 버튼으로 표시
            data[i][9] = "";
            data[i][10] = "";
            data[i][11] = "";
            data[i][12] = "예약하기";
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override public boolean isCellEditable(int r, int c) {
                // 예약불가일자(8), 대여시작일(9), 기간(10), 추가용품(11), 예약(12)
                return c == 8 || c == 9 || c == 10 || c == 11 || c == 12;
            }
        };

        JTable table = new JTable(model);

        // 예약불가일자 버튼
        table.getColumn("예약불가일자").setCellRenderer(new ButtonRenderer("보기"));
        table.getColumn("예약불가일자").setCellEditor(
                new NotAvailableDateButtonEditor(
                        new JCheckBox(), campingCars, reservedDatesList
                )
        );

        // 예약 버튼
        table.getColumn("예약").setCellRenderer(new ButtonRenderer("예약하기"));
        table.getColumn("예약").setCellEditor(
                new ButtonEditor(new JCheckBox(), campingCars, reservedDatesList, model, rentalService, customerId, licenseNumber, this, userView)
        );

        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // ButtonRenderer: 텍스트가 버튼으로 보이게
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) { setText(text); }
        @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
            return this;
        }
    }

    // 예약불가일자 버튼 클릭 시 전체 날짜 다이얼로그로 보여줌
    private static class NotAvailableDateButtonEditor extends DefaultCellEditor {
        private final JButton button = new JButton("보기");
        private final List<CampingCar> cars;
        private final List<Set<LocalDate>> reservedDatesList;
        private int selectedRow;

        public NotAvailableDateButtonEditor(JCheckBox checkBox, List<CampingCar> cars, List<Set<LocalDate>> reservedDatesList) {
            super(checkBox);
            this.cars = cars;
            this.reservedDatesList = reservedDatesList;

            button.addActionListener(e -> {
                Set<LocalDate> dates = reservedDatesList.get(selectedRow);
                CampingCar car = cars.get(selectedRow);
                showNotAvailableDatesDialog(dates, car.getCarName());
                fireEditingStopped();
            });
        }
        @Override
        public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int row, int col) {
            selectedRow = row;
            return button;
        }
        private void showNotAvailableDatesDialog(Set<LocalDate> dates, String carName) {
            JDialog dialog = new JDialog((Frame) null, carName + " 예약불가일자", true);
            dialog.setSize(350, 400);
            dialog.setLocationRelativeTo(null);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            if (dates.isEmpty()) {
                textArea.setText("예약 불가일자가 없습니다! (모든 날짜 예약 가능)");
            } else {
                dates.stream().sorted().forEach(date -> textArea.append(date.toString() + "\n"));
            }
            dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

            JButton closeButton = new JButton("닫기");
            closeButton.addActionListener(ev -> dialog.dispose());
            dialog.add(closeButton, BorderLayout.SOUTH);

            dialog.setVisible(true);
        }
    }

    // 예약 버튼
    private static class ButtonEditor extends DefaultCellEditor {
        private final JButton button = new JButton("예약하기");
        private final List<CampingCar> cars;
        private final List<Set<LocalDate>> reservedDatesList;
        private final DefaultTableModel model;
        private final RentalService rentalService;
        private final Long customerId;
        private final String licenseNumber;
        private final CamperSearchPanel parentPanel;
        private final UserView userView;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, List<CampingCar> cars, List<Set<LocalDate>> reservedDatesList, DefaultTableModel model,
                            RentalService rentalService, Long customerId, String licenseNumber,
                            CamperSearchPanel parentPanel, UserView userView) {
            super(checkBox);
            this.cars = cars;
            this.reservedDatesList = reservedDatesList;
            this.model = model;
            this.rentalService = rentalService;
            this.customerId = customerId;
            this.licenseNumber = licenseNumber;
            this.parentPanel = parentPanel;
            this.userView = userView;

            button.addActionListener(e -> {
                CampingCar car = cars.get(selectedRow);
                Set<LocalDate> reserved = reservedDatesList.get(selectedRow);

                String startDate = String.valueOf(model.getValueAt(selectedRow, 9)).trim();
                String durationStr = String.valueOf(model.getValueAt(selectedRow, 10)).trim();
                String additionalItems = String.valueOf(model.getValueAt(selectedRow, 11)).trim();

                try {
                    int days = Integer.parseInt(durationStr);
                    if (days < 1) throw new NumberFormatException("대여 기간은 1일 이상이어야 합니다.");
                    LocalDate rentalStartDate = LocalDate.parse(startDate);

                    // (1) 오늘 이전 날짜 예약 불가
                    if (!rentalStartDate.isAfter(LocalDate.now().minusDays(1))) {
                        JOptionPane.showMessageDialog(button, "오늘 이후 날짜만 예약할 수 있습니다!", "예약 불가", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // (2) 예약기간 중 일부라도 이미 예약된 날짜면 불가
                    boolean overlapped = false;
                    for (int d = 0; d < days; d++) {
                        if (reserved.contains(rentalStartDate.plusDays(d))) {
                            overlapped = true; break;
                        }
                    }
                    if (overlapped) {
                        JOptionPane.showMessageDialog(button, "해당 기간 중 이미 예약된 날짜가 있습니다!", "예약 불가", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 정상적으로 예약 진행
                    BigDecimal pricePerDay = car.getRentalPrice();
                    BigDecimal totalFee = pricePerDay.multiply(BigDecimal.valueOf(days));
                    LocalDate paymentDueDate = rentalStartDate.plusDays(days);

                    Rental rental = new Rental();
                    rental.setCarId(car.getId());
                    rental.setCustomerId(customerId);
                    rental.setCompanyId(car.getCompanyId());
                    rental.setLicenseNumber(licenseNumber);
                    rental.setRentalStartDate(rentalStartDate);
                    rental.setRentalDurationDays(days);
                    rental.setTotalFee(totalFee);
                    rental.setPaymentDueDate(paymentDueDate);
                    rental.setAdditionalItems(additionalItems);

                    rentalService.save(rental);

                    JOptionPane.showMessageDialog(button, "예약 완료!", "알림", JOptionPane.INFORMATION_MESSAGE);
                    userView.refreshAllTabs();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(button, "입력값 오류 또는 예약 실패: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int row, int col) {
            selectedRow = row;
            return button;
        }
    }
}
