package Admin;

import dao.CampingCarDao;
import dao.ExternalRepairDao;
import dao.MaintenanceRecordDao;
import dto.ExternalRepairDetail;
import dto.MaintenanceRecordDetail;
import entitiy.CampingCar;
import service.CampingCarService;
import service.ExternalRepairService;
import service.MaintenanceRecordService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CamperRepairView extends JFrame {
    private JComboBox<CampingCar> camperSelector;
    private JTable internalTable, externalTable;
    private final CampingCarService campingCarService = new CampingCarService(new CampingCarDao());
    private final MaintenanceRecordService maintenanceRecordService = new MaintenanceRecordService(new MaintenanceRecordDao());
    private final ExternalRepairService externalRepairService = new ExternalRepairService(new ExternalRepairDao());
    private List<MaintenanceRecordDetail> maintenanceRecordDetails = new ArrayList<>();
    private List<ExternalRepairDetail> externalRepairDetails = new ArrayList<>();

    public CamperRepairView() {
        setTitle("캠핑카 정비 정보");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        List<CampingCar> all = campingCarService.findAll();
        camperSelector = new JComboBox<>(new Vector<>(all));
        JButton loadBtn = new JButton("불러오기");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("캠핑카 선택:"));
        topPanel.add(camperSelector);
        topPanel.add(loadBtn);

        DefaultTableModel internalModel = new DefaultTableModel(
                new Object[]{"정비ID", "정비일자", "소요시간", "부품명", "재고"}, 0
        );
        internalTable = new JTable(internalModel);

        DefaultTableModel externalModel = new DefaultTableModel(
                new Object[]{"정비ID", "정비일자", "비용", "정비소명"}, 0
        );
        externalTable = new JTable(externalModel);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(new JScrollPane(internalTable));
        centerPanel.add(new JScrollPane(externalTable));

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);

        loadBtn.addActionListener(e -> {
            CampingCar selectedCar = (CampingCar) camperSelector.getSelectedItem();
            if (selectedCar == null) return;

            maintenanceRecordDetails = maintenanceRecordService.findMaintenanceRecordDetailsByCarId(selectedCar.getId());
            internalModel.setRowCount(0);
            for (MaintenanceRecordDetail record : maintenanceRecordDetails) {
                internalModel.addRow(new Object[]{
                        record.getMaintenanceId(),     // 정비 ID
                        record.getMaintenanceDate(),   // 정비 일자
                        record.getDurationMinutes(),   // 소요 시간
                        record.getPartName(),          // 부품명
                        record.getStockQuantity()      // 재고 수량
                });
            }
            externalRepairDetails = externalRepairService.findExternalRepairDetailsByCarId(selectedCar.getId());
            externalModel.setRowCount(0);
            for (ExternalRepairDetail detail : externalRepairDetails) {
                externalModel.addRow(new Object[]{
                        detail.getRepairId(),
                        detail.getRepairDate(),
                        detail.getRepairCost(),
                        detail.getShopName()
                });
            }
        });

        internalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = internalTable.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < maintenanceRecordDetails.size()) {
                    MaintenanceRecordDetail record = maintenanceRecordDetails.get(row);
                    String message = "부품명: " + record.getPartName() + "\n"
                            + "재고: " + record.getStockQuantity() + "개\n"
                            + "단가: " + record.getUnitPrice() + "원\n"
                            + "입고일: " + record.getArrivalDate() + "\n"
                            + "공급사: " + record.getSupplierName();
                    JOptionPane.showMessageDialog(null, message, "부품 상세 정보", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        externalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = externalTable.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < externalRepairDetails.size()) {
                    ExternalRepairDetail detail = externalRepairDetails.get(row);
                    String message = "정비ID: " + detail.getRepairId() + "\n"
                            + "정비일자: " + detail.getRepairDate() + "\n"
                            + "비용: " + detail.getRepairCost() + "\n"
                            + "정비소: " + detail.getShopName() + "\n"
                            + "주소: " + detail.getAddress() + "\n"
                            + "전화: " + detail.getPhone();
                    JOptionPane.showMessageDialog(null, message, "정비소 정보", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
