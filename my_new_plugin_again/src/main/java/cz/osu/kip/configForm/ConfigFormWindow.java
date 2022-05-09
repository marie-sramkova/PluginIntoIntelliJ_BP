package cz.osu.kip.configForm;

import com.intellij.ui.components.JBScrollPane;
import cz.osu.kip.mainForm.FolderLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigFormWindow extends JFrame {

    private JPanel scrollablePanel;
    private JPanel contentPanel;
    List<FolderLevel> configFiles;
    File rootProject;
    private SubmitStateForConfigFormWindow submitState = SubmitStateForConfigFormWindow.CANCEL;

    public ConfigFormWindow(List<File> configFiles, File rootProject){
        this.rootProject = rootProject;
        this.configFiles = convertConfigFilesToFolderLevels(configFiles);
        makeFrame();
        show();
//        showFormWindow();
    }

    public List<FolderLevel> getConfigFiles() {
        return configFiles;
    }

    public SubmitStateForConfigFormWindow getSubmitState() {
        return submitState;
    }

    private List<FolderLevel> convertConfigFilesToFolderLevels(List<File> configFiles) {
        List<FolderLevel> files = new ArrayList<>();
        for (File file:configFiles) {
            String name = file.toString().substring(rootProject.toString().length());
            FolderLevel fl = new FolderLevel(name, file, 0, rootProject.toString());
            files.add(fl);
        }
        return files;
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

    private void makeFrame() {
        contentPanel = makeContentPanel();

        JBScrollPane scrollPane = new JBScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 10, 485, 200);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 220));
        contentPane.add(scrollPane);
        //setContentPane(contentPane);
        getContentPane().add(contentPane, BorderLayout.PAGE_START);
        getContentPane().add(createButtonPanel(), BorderLayout.PAGE_END);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
    }

    private JPanel makeContentPanel() {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        for (FolderLevel fl:configFiles) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            panel.add(fl.getjCheckBox());
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
            newPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            panel.add(newPanel);
            fl.getjCheckBox().setVisible(true);
            myPanel.add(panel);
        }
        return myPanel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JButton umlDiagramGenerationButton = new JButton("Generate uml diagram");
        JButton cancelButton = new JButton("Cancel");
        JButton deleteButton = new JButton("Delete");

        umlDiagramGenerationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitState = SubmitStateForConfigFormWindow.GENERATE_UML_DIAGRAM;
                dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitState = SubmitStateForConfigFormWindow.DELETE;
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitState = SubmitStateForConfigFormWindow.CANCEL;
                dispose();
            }
        });

        panel.add(umlDiagramGenerationButton);
        panel.add(deleteButton);
        panel.add(cancelButton);
        return panel;
    }
}
