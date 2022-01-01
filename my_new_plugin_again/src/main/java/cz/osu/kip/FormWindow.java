package cz.osu.kip;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FormWindow extends JFrame {

    public void showFormWindow(Project currentProject, File filePath){
        JFrame frame = new JFrame();
        JPanel panel = makeContentPanel(currentProject, filePath);
        JPanel contentPane = makeScrollablePanel(panel);


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


        System.out.println(panel.getComponent(0).getAccessibleContext());
        System.out.println(panel.getInputContext().getLocale());
    }

    @NotNull
    private JPanel makeScrollablePanel(JPanel panel) {
        JBScrollPane scrollPane = new JBScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 400, 400);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 500));
        contentPane.add(scrollPane);
        return contentPane;
    }

    @NotNull
    private JPanel makeContentPanel(Project currentProject, File filePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 4));

//        String src = "D:\sramk\Documents\vysoka_skola\bakalarka\temp\backup";
//        String trg = "D:\sramk\Documents\vysoka_skola\2_rocnik\2_semestr\7OPR2\projects\cviceni12\slozka2";

        System.out.println(filePath.toString());

        JTextField source = new JTextField(20);
        JTextField target = new JTextField(20);
        JCheckBox privateCheckBox = new JCheckBox();
        JCheckBoxMenuItem publicCheckBoxMenuItem = new JCheckBoxMenuItem("ahoj");
        JColorChooser colorChooser = new JColorChooser();
        List<String> text = new ArrayList<>(Arrays.asList("private", "public", "protected"));
        JComboBox<String> comboBox = new ComboBox(text.toArray());
        JFileChooser defaultTargetFile = new JFileChooser();
        defaultTargetFile.setCurrentDirectory(new File(filePath.toPath().toFile().toString()));
        //  Nefunkční - dopracovat
        System.out.println(new File(filePath.toPath().resolve("PlantUmlFiles").toFile().toString()));

        panel.add(new JLabel("source:"));
        panel.add(source);
        panel.add(new JLabel("target:"));
        panel.add(target);
        panel.add(privateCheckBox);
        panel.add(publicCheckBoxMenuItem);
        panel.add(colorChooser);
        panel.add(comboBox);
//        myPanel4.add(defaultSourceFile);
        panel.add(defaultTargetFile);

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
