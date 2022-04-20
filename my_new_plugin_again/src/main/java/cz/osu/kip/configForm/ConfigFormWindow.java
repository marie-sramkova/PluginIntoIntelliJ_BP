package cz.osu.kip.configForm;

import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

public class ConfigFormWindow extends JFrame {

    private JPanel scrollablePanel;
    private JPanel contentPanel;

    public ConfigFormWindow(){
        scrollablePanel = makeScrollablePanel();
        showFormWindow();
    }

    private void showFormWindow() {
        JPanel contentPane = getScrollablePanel();

        setContentPane(contentPane);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public JPanel getScrollablePanel() {
        return scrollablePanel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    private JPanel makeScrollablePanel() {
        contentPanel = makeContentPanel();

        JBScrollPane scrollPane = new JBScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setBounds(0, 10, 800, 790);
        JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(800, 800));
        contentPane.add(scrollPane);
        return contentPane;
    }

    private JPanel makeContentPanel() {
        JPanel myPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(myPanel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        myPanel.setLayout(groupLayout);
        return myPanel;
    }
}
