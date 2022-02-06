package cz.osu.kip.form;

import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.*;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class TreeViewWindow {
    private JTree tree;
    java.util.List<FolderLevel> folders = new ArrayList<>();

    public TreeViewWindow(File filePath) {
        JFrame frame = new JFrame();


        if (filePath.exists()) {
            File[] directories = getDirectories(filePath);
            makeLevelsRecursively(filePath, folders, directories);
//            Collections.sort(folders);
            for (FolderLevel folder:folders) {
                System.out.println(folder);
            }
        }



        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (FolderLevel fl:folders) {
            panel.add(fl.getjCheckBox());
            if (fl.getLevel() == 1){
                fl.getjCheckBox().setVisible(true);
            }else {
                fl.getjCheckBox().setVisible(false);
            }
        }



        JBScrollPane scrollPane = new JBScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 10, 300, 440);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(300, 450));
        contentPane.add(scrollPane);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        frame.setSize(300, 450);
        frame.setVisible(true);
        frame.show();

    }

    private void makeLevelsRecursively(File filePath, List<FolderLevel> folders, File[] directories) {
        for (File dir : directories) {
            File[] files = getDirectories(dir);
            if (files != null && files.length>0){
                for (File file:files) {
                    makeLevelsRecursively(filePath, folders, files);
                }
            }
            int folderLevel = 0;
            if (dir.toString().contains("/")) {
                String[] oldPaths = filePath.toString().split("/");
                String[] paths = dir.toString().split("/");
                folderLevel = paths.length - oldPaths.length;
            } else if (dir.toString().contains("\\")) {
                String[] oldPaths = filePath.toString().split("\\\\");
                String[] paths = dir.toString().split("\\\\");
                folderLevel = paths.length - oldPaths.length;
            }
            folders.add(new FolderLevel(dir.getName(), dir, folderLevel));
        }
    }

    @Nullable
    private File[] getDirectories(File filePath) {
        File[] directories = filePath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File current) {
                return current.isDirectory();
            }
        });
        return directories;
    }
}
