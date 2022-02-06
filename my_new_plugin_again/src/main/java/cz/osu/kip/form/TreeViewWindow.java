package cz.osu.kip.form;

import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class TreeViewWindow {
    private JTree tree;
    java.util.List<FolderLevel> folders = new ArrayList<>();
    List<ActionListener> actionListeners = new ArrayList<>();

    public TreeViewWindow(File filePath) {
        JFrame frame = new JFrame();


        if (filePath.exists()) {
            File[] directories = getDirectories(filePath);
            List<FolderLevel> firstFolders = makeLevels(filePath, directories);
            folders.addAll(firstFolders);
//            Collections.sort(folders);
            for (FolderLevel folder : folders) {
                System.out.println(folder);
            }
        }


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (FolderLevel fl : folders) {
            panel.add(fl.getjCheckBox());
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
            panel.add(newPanel);
            fl.getjCheckBox().setVisible(true);
            fl.getjCheckBox().addActionListener(makeListenerForEachCheckBox(fl, panel, frame, newPanel));
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

    private ActionListener makeListenerForEachCheckBox(FolderLevel folderLevel, JPanel panel, JFrame frame, JPanel newJpanel) {
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                File[] directories = getDirectories(folderLevel.getUrl());
                List<FolderLevel> newFolders = makeLevels(FormWindow.getFilePath(), directories);
                if (folderLevel.getjCheckBox().isSelected()){
                    for (FolderLevel fl:newFolders) {
                        for (FolderLevel folderLevel:folders) {
                            if (folderLevel.getUrl().equals(fl.getUrl())){
                                folderLevel.getjCheckBox().setSelected(true);
                                folderLevel.getjCheckBox().setVisible(true);
                            }else {
                                folders.add(fl);
                                newJpanel.add(fl.getjCheckBox());
                                fl.getjCheckBox().setVisible(true);
                                JPanel newPanel = new JPanel();
                                newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
                                newJpanel.add(newPanel);
                                fl.getjCheckBox().addActionListener(makeListenerForEachCheckBox(fl, panel, frame, newPanel));
                                panel.revalidate();
                                panel.repaint();
                                frame.pack();
                            }
                        }
                    }
                }else{
                    for (FolderLevel folderLevel:folders) {
                        for (FolderLevel newFolderLevel:newFolders) {
                            System.out.println();
                            if (folderLevel.getUrl().toString().equals(newFolderLevel.getUrl().toString())){
                                folderLevel.getjCheckBox().setSelected(false);
                                folderLevel.getjCheckBox().setVisible(false);
                            }else if(folderLevel.getUrl().toString().contains(newFolderLevel.getUrl().toString())){
                                folderLevel.getjCheckBox().setSelected(false);
                                folderLevel.getjCheckBox().setVisible(false);
                            }
                        }
                    }
                    if (!newFolders.isEmpty()){
                        for (FolderLevel fl:newFolders) {
                            fl.getjCheckBox().setSelected(false);
                            fl.getjCheckBox().setVisible(false);
                        }
                    }
                }
            }
        };
        folderLevel.getjCheckBox();
        return listener;
    }

    private List<FolderLevel> makeLevels(File filePath, File[] directories) {
        List<FolderLevel> newFolderLevels = new ArrayList<>();
        for (File dir : directories) {
            File[] files = getDirectories(dir);
//            if (files != null && files.length>0){
//                for (File file:files) {
//                    makeLevelsRecursively(filePath, folders, files);
//                }
//            }
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
            newFolderLevels.add(new FolderLevel(dir.getName(), dir, folderLevel));
        }
        return newFolderLevels;
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
