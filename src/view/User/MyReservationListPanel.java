package view.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import entitiy.Rental;
import entitiy.CampingCar;
import service.RentalService;
import service.CampingCarService;
import dao.RentalDao;
import dao.CampingCarDao;

public class MyReservationListPanel extends JPanel {
    private final Long customerId;
    private final UserView userView;

    public MyReservationListPanel(Long customerId, UserView userView) {
        this.customerId = customerId;
        this.userView = userView;
        setLayout(new BorderLayout());

        // 1. 서비스 준비
        RentalService rentalService = new RentalService(new RentalDao());
        CampingCarService campingCarService = new CampingCarService(new CampingCarDao());

        List<CampingCar> allCars = campingCarService.findAllIncludingRented();

        // 2. 모든 차량과 내 예약 조회
        List<Rental> myRentals = rentalService.findByCustomerId(customerId);

        // 3. 차량 ID → CampingCar 객체 맵핑
        Map<Long, CampingCar> carIdMap = new HashMap<>();
        for (CampingCar car : allCars) {
            carIdMap.put(car.getId(), car); // 반드시 getId()!
        }

        // 4. 콤보박스용 차량 이름+ID 배열
        String[] carNamesWithId = allCars.stream()
                .map(car -> car.getCarName() + " (" + car.getId() + ")")
                .toArray(String[]::new);

        // 5. 테이블 데이터 채우기
        String[] columnNames = {"예약ID", "차량", "대여시작", "대여기간(일)", "총요금", "추가용품", "수정", "취소"};
        Object[][] data = new Object[myRentals.size()][columnNames.length];
        for (int i = 0; i < myRentals.size(); i++) {
            Rental r = myRentals.get(i);
            CampingCar car = carIdMap.get(r.getCarId());
            String carName = (car != null) ? car.getCarName() : "알수없음";
            Long carId = r.getCarId();
            data[i][0] = String.valueOf(r.getId());
            data[i][1] = carName + " (" + carId + ")";
            data[i][2] = r.getRentalStartDate() == null ? "" : r.getRentalStartDate().toString();
            data[i][3] = String.valueOf(r.getRentalDurationDays());
            data[i][4] = r.getTotalFee() == null ? "" : r.getTotalFee().toString();
            data[i][5] = r.getAdditionalItems() == null ? "" : r.getAdditionalItems();
            data[i][6] = "수정";
            data[i][7] = "취소";
        }

        // 6. 모델 및 테이블 생성
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // 차량, 대여시작, 기간, 추가용품, 수정/취소 버튼만 편집 가능
                return col == 1 || col == 2 || col == 3 || col == 5 || col == 6 || col == 7;
            }
        };

        JTable table = new JTable(model);

        // 차량 이름+ID 콤보박스 에디터
        TableColumn carColumn = table.getColumnModel().getColumn(1);
        carColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(carNamesWithId)));

        table.getColumn("수정").setCellRenderer(new ButtonRenderer("수정"));
        table.getColumn("수정").setCellEditor(new UpdateButtonEditor(
                new JCheckBox(), rentalService, model, this, userView, carIdMap, customerId));
        table.getColumn("취소").setCellRenderer(new ButtonRenderer("취소"));
        table.getColumn("취소").setCellEditor(new CancelButtonEditor(
                new JCheckBox(), rentalService, model, this, userView));

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // 공통 버튼 렌더러
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) { setText(text); }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // 수정 버튼 (차량, 날짜, 기간, 추가용품 변경)
    private static class UpdateButtonEditor extends DefaultCellEditor {
        private final JButton button = new JButton("수정");
        private final RentalService rentalService;
        private final DefaultTableModel model;
        private final MyReservationListPanel parentPanel;
        private final UserView userView;
        private final Map<Long, CampingCar> carIdMap;
        private final Long customerId;
        private int selectedRow;

        public UpdateButtonEditor(JCheckBox checkBox, RentalService rentalService, DefaultTableModel model,
                                  MyReservationListPanel parentPanel, UserView userView,
                                  Map<Long, CampingCar> carIdMap, Long customerId) {
            super(checkBox);
            this.rentalService = rentalService;
            this.model = model;
            this.parentPanel = parentPanel;
            this.userView = userView;
            this.carIdMap = carIdMap;
            this.customerId = customerId;

            button.addActionListener(e -> {
                try {
                    Long rentalId = Long.parseLong(model.getValueAt(selectedRow, 0).toString());
                    String carNameWithId = model.getValueAt(selectedRow, 1).toString();
                    Long newCarId = parseCarIdFromCombo(carNameWithId);
                    String startDate = model.getValueAt(selectedRow, 2).toString();
                    String durationStr = model.getValueAt(selectedRow, 3).toString();
                    String additionalItems = model.getValueAt(selectedRow, 5).toString();

                    int days = Integer.parseInt(durationStr);
                    if (days < 1) throw new NumberFormatException("대여 기간은 1일 이상이어야 합니다.");

                    // 1. 날짜 겹침 체크
                    java.time.LocalDate newStart = java.time.LocalDate.parse(startDate);
                    java.time.LocalDate newEnd = newStart.plusDays(days - 1);

                    List<Rental> allRentals = rentalService.findAll();

                    boolean overlap = false;
                    for (Rental other : allRentals) {
                        // 자기 자신의 기존 예약은 무시
                        if (other.getId().equals(rentalId)) continue;
                        // 같은 차량 예약만 검사
                        if (!other.getCarId().equals(newCarId)) continue;
                        if (other.getRentalStartDate() == null) continue;

                        java.time.LocalDate otherStart = other.getRentalStartDate();
                        java.time.LocalDate otherEnd = otherStart.plusDays(other.getRentalDurationDays() - 1);

                        // 두 기간이 겹치는지 체크
                        if (!(newEnd.isBefore(otherStart) || newStart.isAfter(otherEnd))) {
                            overlap = true;
                            break;
                        }
                    }

                    if (overlap) {
                        JOptionPane.showMessageDialog(button, "선택한 차량의 해당 기간에는 이미 다른 예약이 있습니다.", "예약 겹침", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // 2. 겹침이 없으면 실제 수정 진행
                    rentalService.updateRentalCar(rentalId, newCarId, startDate, days, additionalItems);

                    JOptionPane.showMessageDialog(button, "예약 정보가 수정되었습니다.");
                    userView.refreshAllTabs();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(button, "입력값 오류 또는 수정 실패: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                }
                fireEditingStopped();
            });
        }

        private Long parseCarIdFromCombo(String carNameWithId) {
            int start = carNameWithId.lastIndexOf("(");
            int end = carNameWithId.lastIndexOf(")");
            if (start >= 0 && end > start) {
                String idStr = carNameWithId.substring(start + 1, end).trim();
                return Long.parseLong(idStr);
            }
            throw new IllegalArgumentException("차량ID 추출 실패: " + carNameWithId);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            return button;
        }
    }

    // 취소 버튼 에디터
    private static class CancelButtonEditor extends DefaultCellEditor {
        private final JButton button = new JButton("취소");
        private final RentalService rentalService;
        private final DefaultTableModel model;
        private final MyReservationListPanel parentPanel;
        private final UserView userView;
        private int selectedRow;

        public CancelButtonEditor(JCheckBox checkBox, RentalService rentalService, DefaultTableModel model,
                                  MyReservationListPanel parentPanel, UserView userView) {
            super(checkBox);
            this.rentalService = rentalService;
            this.model = model;
            this.parentPanel = parentPanel;
            this.userView = userView;

            button.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(button, "정말로 예약을 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    Long rentalId = Long.parseLong(model.getValueAt(selectedRow, 0).toString());
                    rentalService.delete(rentalId);
                    JOptionPane.showMessageDialog(button, "예약이 취소되었습니다.");
                    userView.refreshAllTabs();
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            return button;
        }
    }
}
