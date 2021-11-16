package cz.osu.kip;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FormWindow {

    public static void showFormWindow(){
//        String src = "D:\sramk\Documents\vysoka_skola\bakalarka\temp\backup";
//        String trg = "D:\sramk\Documents\vysoka_skola\2_rocnik\2_semestr\7OPR2\projects\cviceni12\slozka2";


        JTextField source = new JTextField(20);
        JTextField target = new JTextField(20);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("source:"));
        myPanel.add(source);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("target:"));
        myPanel.add(target);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Fill absolute locations", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (!source.getText().isEmpty() && !target.getText().isEmpty()) {
                BackupProvider backupProvider = new BackupProvider();
                backupProvider.doBackup(source.getText(), target.getText());
            } else {
                System.out.println("failed");
            }
        } else {
            System.out.println("canceled");
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
    }

}
