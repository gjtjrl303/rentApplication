package view.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class CustomQueryView extends JFrame {
    private JTextArea queryInputArea;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public CustomQueryView() {
        setTitle("임의 SELECT 실행");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 질의 입력 영역
        queryInputArea = new JTextArea(5, 70);
        queryInputArea.setText("-- 여기에 SELECT 문을 입력하세요");
        JScrollPane inputScroll = new JScrollPane(queryInputArea);

        JButton executeBtn = new JButton("실행");
        JButton exampleBtn = new JButton("예시 보기");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("SQL 입력"));
        topPanel.add(inputScroll, BorderLayout.CENTER);
        topPanel.add(executeBtn, BorderLayout.EAST);

        // 결과 테이블
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        JScrollPane resultScroll = new JScrollPane(resultTable);

        add(topPanel, BorderLayout.NORTH);
        add(resultScroll, BorderLayout.CENTER);
        add(exampleBtn, BorderLayout.SOUTH);

        setVisible(true);

        // 🔹 실행 버튼 동작 (현재는 더미 응답)
        executeBtn.addActionListener(e -> {
            String query = queryInputArea.getText().trim();
            if (!query.toLowerCase().startsWith("select")) {
                JOptionPane.showMessageDialog(this, "SELECT 문만 입력 가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 나중에 DAO에서 실제 실행하도록 수정
            Vector<String> columnNames = new Vector<>();
            columnNames.add("부품명");
            columnNames.add("총 사용량");

            Vector<Vector<Object>> data = new Vector<>();
            data.add(new Vector<>(java.util.List.of("타이어", 12)));
            data.add(new Vector<>(java.util.List.of("브레이크패드", 8)));

            tableModel.setDataVector(data, columnNames);
        });

        // 🔹 예시 버튼 동작
        exampleBtn.addActionListener(e -> {
            String examples = """
            -- 예시 1: 부품별 총 사용 시간 (4개 테이블 조인 + 그룹핑)
            SELECT p.부품명, SUM(j.정비시간) AS 총정비시간
            FROM 자체정비 j
            JOIN 부품 p ON j.부품ID = p.부품ID
            JOIN 캠핑카 c ON j.캠핑카ID = c.캠핑카ID
            JOIN 대여회사 h ON c.회사ID = h.회사ID
            GROUP BY p.부품명;

            -- 예시 2: 정비소별 평균 정비비용
            SELECT s.정비소명, AVG(o.비용) AS 평균비용
            FROM 외부정비 o
            JOIN 정비소 s ON o.정비소ID = s.정비소ID
            JOIN 캠핑카 c ON o.캠핑카ID = c.캠핑카ID
            GROUP BY s.정비소명;

            -- 예시 3: 회원별 총 대여일수
            SELECT m.회원명, SUM(DATEDIFF(r.반납일, r.대여일)) AS 총이용일수
            FROM 예약 r
            JOIN 회원 m ON r.회원ID = m.회원ID
            JOIN 캠핑카 c ON r.캠핑카ID = c.캠핑카ID
            JOIN 대여회사 h ON c.회사ID = h.회사ID
            GROUP BY m.회원명;
            """;
            JTextArea exampleArea = new JTextArea(examples, 15, 70);
            exampleArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(exampleArea);
            JOptionPane.showMessageDialog(this, scrollPane, "SELECT 예시 3가지", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
