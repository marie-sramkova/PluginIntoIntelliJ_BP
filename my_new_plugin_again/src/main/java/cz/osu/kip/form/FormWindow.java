package cz.osu.kip.form;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class FormWindow extends JFrame {
    private static Project currentProject;
    private static File filePath;
    private static MainFormWindowItems mainFormWindowItems;
    private static MainFormWindowPanels mainFormWindowPanels;

    public FormWindow(Project currentProject, File filePath){
        this.currentProject = currentProject;
        this.filePath = filePath;
        mainFormWindowItems = new MainFormWindowItems();
        mainFormWindowPanels = new MainFormWindowPanels(currentProject, filePath, mainFormWindowItems);
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    public static File getFilePath() {
        return filePath;
    }

    public static MainFormWindowItems getMainFormWindowItems() {
        return mainFormWindowItems;
    }

    public static MainFormWindowPanels getMainFormWindowPanels() {
        return mainFormWindowPanels;
    }

    public void showFormWindow() {
        JFrame frame = new JFrame();
        JPanel contentPane = mainFormWindowPanels.getScrollablePanel();

//        int result = JOptionPane.showConfirmDialog(null, panel,
//                "Fill", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//            if (!source.getText().isEmpty() && !target.getText().isEmpty()) {
////                BackupProvider backupProvider = new BackupProvider();
////                backupProvider.doBackup(source.getText(), target.getText());
//                System.out.println(source.getText());
//                System.out.println(target.getText());
//                System.out.println(privateCheckBox);
//                System.out.println(publicCheckBoxMenuItem.getState());
//                System.out.println(colorChooser.getColor());
//                System.out.println(comboBox.getSelectedItem());
////                System.out.println(defaultSourceFile);
//                System.out.println(defaultTargetFile.getSelectedFile());
//            } else {
//                System.out.println("failed");
//            }
//        } else {
//            System.out.println("canceled");
//        }

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                getValues();
            }
        });
    }

    private void getValues() {
        boolean checked = mainFormWindowItems.getPrivateCheckBox().isSelected();
        if(mainFormWindowItems.getPrivateCheckBox().isSelected()) {
            System.out.println("selected");
        } else if (!mainFormWindowItems.getPrivateCheckBox().isSelected()) {
            System.out.println("notSelected");
        }
        System.out.println(mainFormWindowItems.getPrivateCheckBox().isSelected());
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
