package cz.osu.kip.view.mainForm;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UmlFormWindow extends JFrame {
    private static Project currentProject;
    private static File filePath;
    private  MainFormWindowItems mainFormWindowItems;
    private MainFormWindowPanels mainFormWindowPanels;
    private SubmitStateForFormWindow submitState = SubmitStateForFormWindow.CANCEL;

    public UmlFormWindow(Project currentProject, File filePath){
        this.currentProject = currentProject;
        this.filePath = filePath;
        mainFormWindowItems = new MainFormWindowItems(filePath, this);
        mainFormWindowPanels = new MainFormWindowPanels(currentProject, filePath, mainFormWindowItems);
        showFormWindow();
    }

    public UmlFormWindow(Project currentProject, File filePath, MainFormWindowItems mainFormWindowItems){
        this.currentProject = currentProject;
        this.filePath = filePath;
        this.mainFormWindowItems = mainFormWindowItems;
        mainFormWindowPanels = new MainFormWindowPanels(currentProject, filePath, mainFormWindowItems);
        showFormWindow();
    }

    public static File getFilePath() {
        return filePath;
    }

    public MainFormWindowItems getMainFormWindowItems() {
        return mainFormWindowItems;
    }

    public SubmitStateForFormWindow getSubmitState() {
        return submitState;
    }

    public void showFormWindow() {
        JPanel contentPane = mainFormWindowPanels.getScrollablePanel();

        setContentPane(contentPane);
        getContentPane().add(createButtonPanel(), BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JButton okButton = new JButton("Generate all");
        JButton cancelButton = new JButton("Cancel");
        JButton umlGenerationButton = new JButton("Generate only uml diagram");
        JButton configGenerationButton = new JButton("Generate only config file");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitState = SubmitStateForFormWindow.ALL;
                dispose();
            }
        });

        umlGenerationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitState = SubmitStateForFormWindow.ONLY_UML;
                dispose();
            }
        });

        configGenerationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitState = SubmitStateForFormWindow.ONLY_CONFIG;
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitState = SubmitStateForFormWindow.CANCEL;
                dispose();
            }
        });

        panel.add(okButton);
        panel.add(umlGenerationButton);
        panel.add(configGenerationButton);
        panel.add(cancelButton);
        return panel;
    }

    public void refresh(){
        mainFormWindowPanels.getContentPanel().revalidate();
        mainFormWindowPanels.getContentPanel().repaint();
        pack();
    }
}
