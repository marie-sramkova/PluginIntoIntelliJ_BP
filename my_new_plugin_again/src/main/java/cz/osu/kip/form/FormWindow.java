package cz.osu.kip.form;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FormWindow extends JFrame {
    private static Project currentProject;
    private static File filePath;
    private  MainFormWindowItems mainFormWindowItems;
    private MainFormWindowPanels mainFormWindowPanels;
    private boolean isSubmitted = false;

    public FormWindow(Project currentProject, File filePath){
        this.currentProject = currentProject;
        this.filePath = filePath;
        mainFormWindowItems = new MainFormWindowItems(filePath);
        mainFormWindowPanels = new MainFormWindowPanels(currentProject, filePath, mainFormWindowItems);
//        okButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                FormWindow.super.dispose();
//            }
//        });
        showFormWindow();
    }

    public FormWindow(Project currentProject, File filePath, MainFormWindowItems mainFormWindowItems){
        this.currentProject = currentProject;
        this.filePath = filePath;
        this.mainFormWindowItems = mainFormWindowItems;
        mainFormWindowPanels = new MainFormWindowPanels(currentProject, filePath, mainFormWindowItems);
        showFormWindow();
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    public static File getFilePath() {
        return filePath;
    }

    public MainFormWindowItems getMainFormWindowItems() {
        return mainFormWindowItems;
    }

    public MainFormWindowPanels getMainFormWindowPanels() {
        return mainFormWindowPanels;
    }

    public boolean isSubmitted() {
        return isSubmitted;
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

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        JButton submitButton = new JButton("Submit");
        submitButton.setEnabled(false);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isSubmitted = true;
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isSubmitted = false;
                dispose();
            }
        });

        panel.add(okButton);
        panel.add(submitButton);
        panel.add(cancelButton);
        return panel;
    }



//        String target = JOptionPane.showInputDialog("Type here the absolute target path:");
//        File file = new File(target.getText());
//
//        ClassLoader current = Thread.currentThread().getContextClassLoader();
//        try {
//            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
//            try {
//                NewFileClass.makeNewFile(target.getText());
//            }catch (Exception ex) {
//                throw new RuntimeException(String.format("Cannot make a new file to: '%s'", target.getText()));
//            }
//        } finally {
//            Thread.currentThread().setContextClassLoader(current);
//        }
//    }

}
