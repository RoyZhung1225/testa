package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements LogOutPut {


    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton 開啟關閉Button;
    private JButton port設定Button;
    private JButton 自動回傳Button;
    private JCheckBox HEXCheckBox;
    private JCheckBox stringCheckBox;
    private JButton 發送Button1;
    private JButton 清除Button;
    private JTextArea log;
    private JTextArea textArea1;

    CmdManager cmdManager;

    public JPanel getPanel() {
        return panel1;
    }

    public GUI(CmdManager cmdManager){
        this.cmdManager = cmdManager;
        自動回傳Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoReply autoReply = new AutoReply();
                JFrame frame = new JFrame("AutoReply");
                frame.setContentPane(autoReply.getPanel1());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }


    @Override
    public void log(String msg) {
        SwingUtilities.invokeLater(() -> {
            log.append(msg + "\n");
        });
    }
}
