package view.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class CamperRepairView extends JFrame {
    private JComboBox<String> camperSelector;
    private JTable internalTable, externalTable;

    public CamperRepairView() {
        setTitle("캠핑카 정비 및 부품 정보");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        camperSelector = new JComboBox<>(new String[]{"캠핑카 A", "캠핑카 B", "캠핑카 C"});
        JButton loadBtn = new JButton("불러오기");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("캠핑카 선택:"));
        topPanel.add(camperSelector);
        topPanel.add(loadBtn);

        // 내부정비 테이블
        DefaultTableModel internalModel = new DefaultTableModel(
                new Object[]{"부품명", "정비일자", "정비시간"}, 0
        );
        internalTable = new JTable(internalModel);
        JScrollPane internalScroll = new JScrollPane(internalTable);

        // 외부정비 테이블
        DefaultTableModel externalModel = new DefaultTableModel(
                new Object[]{"정비소명", "정비일자", "비용"}, 0
        );
        externalTable = new JTable(externalModel);
        JScrollPane externalScroll = new JScrollPane(externalTable);

        // 레이아웃 구성
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(new JScrollPane(internalScroll));
        centerPanel.add(new JScrollPane(externalScroll));

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);

        // 버튼 클릭 시 더미 데이터로 테이블 채우기
        loadBtn.addActionListener(e -> {
            String selected = (String) camperSelector.getSelectedItem();

            // 내부정비 더미 데이터
            internalModel.setRowCount(0);
            internalModel.addRow(new Object[]{"엔진오일", "2024-05-01", "40분"});
            internalModel.addRow(new Object[]{"브레이크패드", "2024-05-15", "60분"});

            // 외부정비 더미 데이터
            externalModel.setRowCount(0);
            externalModel.addRow(new Object[]{"현대모터스", "2024-06-01", "120,000원"});
            externalModel.addRow(new Object[]{"기아센터", "2024-06-20", "90,000원"});
        });

        // 부품 클릭 시 팝업
        internalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = internalTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    String part = (String) internalTable.getValueAt(row, 0);
                    JOptionPane.showMessageDialog(null, part + "의 재고: 23개\n공급사: 현대부품상사");
                }
            }
        });

        // 정비소 클릭 시 팝업
        externalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = externalTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    String shop = (String) externalTable.getValueAt(row, 0);
                    JOptionPane.showMessageDialog(null, shop + " 정비소\n주소: 서울 강남구\n전화: 02-1234-5678");
                }
            }
        });
    }
}
