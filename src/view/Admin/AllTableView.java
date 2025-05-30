package view.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class AllTableView extends JFrame {
    private JComboBox<String> tableSelector;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public AllTableView() {
        setTitle("전체 테이블 보기");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tableSelector = new JComboBox<>(new String[]{
                "대여회사", "캠핑카", "자체정비", "외부정비", "부품", "공급사", "회원", "예약", "정비소"
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
            String tableName = (String) tableSelector.getSelectedItem();
            if (tableName != null) {
                try {
                    loadRealData(tableName); // 백엔드 있으면 이거 실행됨
                } catch (Exception ex) {
                    System.out.println("⚠️ AdminDAO를 사용할 수 없음. mock 데이터로 대체");
                    loadMockData(tableName);
                }
            }
        });
    }

    // 👉 실제 백엔드 연결용 (백엔드가 있을 때 이 함수만 바꾸면 됨)
    private void loadRealData(String tableName) throws Exception {
        Class<?> daoClass = Class.forName("dao.AdminDAO");
        Object daoInstance = daoClass.getDeclaredConstructor().newInstance();

        Vector<String> columnNames = (Vector<String>) daoClass
                .getMethod("getColumnNames", String.class)
                .invoke(daoInstance, tableName);

        Vector<Vector<Object>> data = (Vector<Vector<Object>>) daoClass
                .getMethod("getTableData", String.class)
                .invoke(daoInstance, tableName);

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
