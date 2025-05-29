package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AllTableView extends JFrame {
    private JComboBox<String> tableSelector;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    Map<String, String> tableMap = new HashMap<>();

    public AllTableView() {
        setTitle("전체 테이블 보기");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tableMap.put("대여회사","CampingCarCompany");
        tableMap.put("캠핑카","CampingCar");
        tableMap.put("자체정비","MaintenanceRecord");
        tableMap.put("외부정비","ExternalRepair");
        tableMap.put("부품","Part");
        tableMap.put("회원","Customer");
        tableMap.put("공급사","RepairShop");
        tableMap.put("예약","Rental");
        tableMap.put("정비소","RepairShop");
        tableMap.put("직원","Staff");

        tableSelector = new JComboBox<>(new String[]{
                "대여회사", "캠핑카", "자체정비", "외부정비", "부품", "공급사", "회원", "예약", "직원"
        });

        JButton loadBtn = new JButton("조회");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("테이블 선택:"));
        topPanel.add(tableSelector);
        topPanel.add(loadBtn);

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

        loadBtn.addActionListener(e -> {
            String tableName = (String)tableMap.get(tableSelector.getSelectedItem());
            if (tableName != null) {
                try {
                    loadRealData(tableName); // 백엔드 있으면 이거 실행됨
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println(tableName);
                    System.out.println("⚠️ AdminDAO를 사용할 수 없음. mock 데이터로 대체");
                    loadMockData(tableName);
                }
            }
        });
    }

    private void loadRealData(String tableName) throws Exception {
        String daoClassName = "dao." + tableName + "Dao";
        Class<?> daoClass = Class.forName(daoClassName);
        Object daoInstance = daoClass.getDeclaredConstructor().newInstance();

        // 👇 getColumnNames 메서드가 있다면
        Vector<String> columnNames = (Vector<String>) daoClass
                .getMethod("getColumnNames")
                .invoke(daoInstance);

        Vector<Vector<Object>> data = (Vector<Vector<Object>>) daoClass
                .getMethod("getTableData")
                .invoke(daoInstance);

        tableModel.setDataVector(data, columnNames);
    }

    // 👉 백엔드 없을 때 쓰는 mock 데이터
    private void loadMockData(String tableName) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        switch (tableName) {
            case "대여회사":
                columnNames.add("회사ID");
                columnNames.add("회사명");
                columnNames.add("전화번호");
                data.add(new Vector<>(java.util.List.of("C001", "캠핑코리아", "010-1234-5678")));
                data.add(new Vector<>(java.util.List.of("C002", "카라반월드", "010-9876-5432")));
                break;

            case "회원":
                columnNames.add("회원ID");
                columnNames.add("이름");
                columnNames.add("이메일");
                data.add(new Vector<>(java.util.List.of("user01", "홍길동", "hong@gmail.com")));
                data.add(new Vector<>(java.util.List.of("user02", "김철수", "kim@naver.com")));
                break;

            default:
                columnNames.add("컬럼1");
                columnNames.add("컬럼2");
                data.add(new Vector<>(java.util.List.of("예시1", "값1")));
                data.add(new Vector<>(java.util.List.of("예시2", "값2")));
        }

        tableModel.setDataVector(data, columnNames);
    }
}
