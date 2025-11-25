package org.example;

import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AutoReply {
    @Getter
    private JPanel panel1;
    private JTable table1;


    private void createUI() {
        JFrame frame = new JFrame("Editable JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // 欄位名稱
        String[] columnNames = {"接收", "回傳"};

        // 原始資料
        Object[][] data = {
                {"a", "Roy"},
                {"b", "Allen"},
                {"c", "Danny"},
                {"d", "Mark"},
                {"e", "John"}
        };

        // 可編輯的 Model（全部可編輯）
        JTable table = getJTable(data, columnNames);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        table.getDefaultEditor(Object.class).addCellEditorListener(new javax.swing.event.CellEditorListener() {

            @Override
            public void editingStopped(javax.swing.event.ChangeEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if (column != 0) return; // 只檢查接收欄
                Object value = table.getValueAt(row, column);
                System.out.println("Edit finished: " + value);
                String key = table.getValueAt(row, column).toString().trim();

                // 不能空白
                if (key.isEmpty()) {
                    JOptionPane.showMessageDialog(table, "接收欄不能為空!");
                    table.setValueAt("?", row, column);
                    return;
                }

                // 不能重複
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (i == row) continue;
                    if (key.equals(table.getValueAt(i, 0))) {
                        JOptionPane.showMessageDialog(table, "接收欄不能重複!");
                        table.setValueAt("?", row, column);
                        return;
                    }
                }
            }

            @Override
            public void editingCanceled(javax.swing.event.ChangeEvent e) {
                System.out.println("Edit canceled");
            }
        });

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }

        // 放入 ScrollPane
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static JTable getJTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;  // 每個欄位都能修改
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // 目前兩欄都用字串即可
                return String.class;
            }
        };

        JTable table = new JTable(model);

        // 監聽資料修改
        model.addTableModelListener(e -> {
            if (e.getColumn() < 0) return;

            int row = e.getFirstRow();
            int column = e.getColumn();
            Object newKey = model.getValueAt(row, 0);
            Object newValue = model.getValueAt(row, column);
            System.out.println("Row " + row + ", Column " + column +
                    " changed to: " + newKey);
            System.out.println("Row " + row + ", Column " + column +
                    " changed to: " + newValue);

            // 如果你要根據「接收」欄決定怎麼處理「回傳」欄，就在這裡做
            String rev = model.getValueAt(row, 0).toString();
            String reply = model.getValueAt(row, 1).toString();
            ReplyMap replyMap = new ReplyMap();
            replyMap.addReply(rev, reply);
            // e.g. TODO: 根據 rev / reply 去呼叫 SerialPortToolkit 寫資料
        });

        table.setFillsViewportHeight(true);
        return table;
    }
}
