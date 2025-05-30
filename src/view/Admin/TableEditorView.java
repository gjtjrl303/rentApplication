package view.Admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Map;

public class TableEditorView extends JFrame {

    /* ─────────── UI 컴포넌트 ─────────── */
    private final JComboBox<String> tableSelector;
    private final JComboBox<String> actionSelector;
    private final JTextArea  inputArea   = new JTextArea(10, 60);
    private final JLabel     guideLabel  = new JLabel();
    private final JButton    executeBtn  = new JButton("실행");

    /* ─────────── 예시 문구 모음 ─────────── */
    private static final Map<String, String> EXAMPLE_MAP = Map.of(
            "INSERT",
            """
            -- 예시) 캠핑카 테이블
            column1, column2, column3
            값1,     값2,     값3
            """,
            "UPDATE (조건식)",
            """
            -- 예시) 외부정비 테이블
            SET column1 = 새값
            WHERE id = 123
            """,
            "DELETE (조건식)",
            """
            -- 예시) 부품 테이블
            WHERE column1 = 'abc' AND column2 > 3
            """
    );

    public TableEditorView() {
        super("테이블 입력/삭제/변경");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(780, 540);
        setLocationRelativeTo(null);

        /* ▌상단 – 테이블·액션 선택 ⌂ */
        tableSelector = new JComboBox<>(new String[]{
                "대여회사", "캠핑카", "자체정비", "외부정비", "부품", "공급사"
        });
        actionSelector = new JComboBox<>(new String[]{
                "INSERT", "UPDATE (조건식)", "DELETE (조건식)"
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        top.add(new JLabel("테이블:"));
        top.add(tableSelector);
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
            String table  = (String) tableSelector.getSelectedItem();
            String action = (String) actionSelector.getSelectedItem();
            String text   = inputArea.getText().trim();

            // TODO: DAO 호출
            JOptionPane.showMessageDialog(this,
                    "테이블: " + table + "\n동작: " + action + "\n--------------\n" + text,
                    "확인", JOptionPane.INFORMATION_MESSAGE
            );
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
