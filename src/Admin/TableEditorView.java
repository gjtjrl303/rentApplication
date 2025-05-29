package Admin;

import dao.DeleteDao;
import dao.InsertDao;
import dao.UpdateDao;
import service.DeleteService;
import service.InsertService;
import service.UpdateService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Map;

public class TableEditorView extends JFrame {

    /* ─────────── UI 컴포넌트 ─────────── */
    private final JComboBox<String> actionSelector;
    private final JTextArea  inputArea   = new JTextArea(10, 60);
    private final JLabel     guideLabel  = new JLabel();
    private final JButton    executeBtn  = new JButton("실행");
    private final InsertService insertService = new InsertService(new InsertDao());
    private final UpdateService updateService = new UpdateService(new UpdateDao());
    private final DeleteService deleteService = new DeleteService(new DeleteDao());

    /* ─────────── 예시 문구 모음 ─────────── */
    private static final Map<String, String> EXAMPLE_MAP = Map.of(
            "INSERT",
            """
            -- 예시) CampingCarCompany 테이블
            INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email)
            VALUES ('캠핑천국', '서울특별시 송파구 올림픽로 88', '02-123-4567', '김철수', 'camping@naver.com');
            """,

            "UPDATE (조건식)",
            """
            -- 예시) Staff 테이블
            UPDATE Staff
            SET monthly_salary = 3900000.00, num_dependents = 2
            WHERE name = '김유진' AND department = '총무부';
            """,

            "DELETE (조건식)",
            """
            -- 예시) Part 테이블
            DELETE FROM Part
            WHERE part_name = '타이어 16인치' AND arrival_date < '2025-01-01';
            """
    );

    public TableEditorView() {
        super("테이블 입력/삭제/변경");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(780, 540);
        setLocationRelativeTo(null);

        actionSelector = new JComboBox<>(new String[]{
                "INSERT", "UPDATE (조건식)", "DELETE (조건식)"
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        top.add(new JLabel("테이블:"));
        top.add(Box.createHorizontalStrut(20));
        top.add(new JLabel("동작:"));
        top.add(actionSelector);

        /* ▌중앙 – 가이드 + 입력 영역 ⌂ */
        guideLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        guideLabel.setForeground(Color.GRAY);
        guideLabel.setBorder(new EmptyBorder(0, 3, 4, 0));

        inputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        inputArea.setLineWrap(true);
        inputArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JScrollPane scroll = new JScrollPane(inputArea);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(0, 12, 0, 12));
        center.add(guideLabel, BorderLayout.NORTH);
        center.add(scroll,  BorderLayout.CENTER);

        /* ▌하단 – 실행 버튼 ⌂ */
        executeBtn.setPreferredSize(new Dimension(100, 34));
        JPanel bottom = new JPanel();
        bottom.setBorder(new EmptyBorder(10, 0, 15, 0));
        bottom.add(executeBtn);

        /* ▌프레임 레이아웃 ⌂ */
        add(top,    BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        /* ▌액션별 예시 문구 초기화 & 리스너 ⌂ */
        updateGuide();
        actionSelector.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) updateGuide();
        });

        /* ▌실행 버튼 리스너 ⌂ */
        executeBtn.addActionListener(e -> {
            String action = (String) actionSelector.getSelectedItem();
            String text   = inputArea.getText().trim();

            boolean isValid = switch (action) {
                case "INSERT" -> text.contains(",");
                case "UPDATE (조건식)" -> text.toLowerCase().contains("set") && text.toLowerCase().contains("where");
                case "DELETE (조건식)" -> text.toLowerCase().contains("where");
                default -> false;
            };

            if (!isValid) {
                JOptionPane.showMessageDialog(this,
                        "입력한 내용이 SQL 문 형식에 맞지 않습니다.\n예시를 참고해주세요.",
                        "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int result = switch (action) {
                    case "INSERT" -> insertService.insert(text);
                    case "UPDATE (조건식)" -> updateService.update(text);
                    case "DELETE (조건식)" -> deleteService.delete(text);
                    default -> throw new IllegalArgumentException("알 수 없는 동작입니다.");
                };

                JOptionPane.showMessageDialog(this,
                        result + "개의 행이 영향을 받았습니다.", "실행 결과", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "오류 발생: " + ex.getMessage() + ex.getCause(), "에러", JOptionPane.ERROR_MESSAGE);
            }
        });

        /* ▌마지막 세팅 ⌂ */
        setVisible(true);
    }

    /** 동작 선택에 따른 가이드/placeholder 업데이트 */
    private void updateGuide() {
        String key = (String) actionSelector.getSelectedItem();
        guideLabel.setText("입력 예시 (" + key + ")");
        inputArea.setText(EXAMPLE_MAP.getOrDefault(key, ""));
    }

    /* ─────────── 테스트용 main ─────────── */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TableEditorView::new);
    }
}
