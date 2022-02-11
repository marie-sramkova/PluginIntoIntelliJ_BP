package cz.osu.kip.form;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.io.File;

public class FormWindow extends JFrame {
    private static Project currentProject;
    private static File filePath;
    private MainFormWindowItems mainFormWindowItems;
    private MainFormWindowPanels mainFormWindowPanels;
    //private JFrame frame = new JFrame();

    public FormWindow(Project currentProject, File filePath){
        this.currentProject = currentProject;
        this.filePath = filePath;
        mainFormWindowItems = new MainFormWindowItems();
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

    public void showFormWindow() {
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

        setContentPane(contentPane);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                getValues();
            }
        });
    }



    public void getValues() {
        boolean checked = mainFormWindowItems.getOwnPackages().isSelected();
        if(mainFormWindowItems.getOwnPackages().isSelected()) {
            System.out.println("selected");
        } else if (!mainFormWindowItems.getOwnPackages().isSelected()) {
            System.out.println("notSelected");
        }
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
