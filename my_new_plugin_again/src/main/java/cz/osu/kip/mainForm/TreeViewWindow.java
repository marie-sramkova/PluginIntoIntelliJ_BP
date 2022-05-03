package cz.osu.kip.mainForm;

import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TreeViewWindow extends JFrame {
    private java.util.List<FolderLevel> folders = new ArrayList<>();
    //    private java.util.List<FolderLevel> oldFolders = new ArrayList<>();
    private boolean wasCanceled = false;

//    @Override
//    public void show() {
//        if (wasCanceled){
//            folders = oldFolders;
//            makeFrame();
//            wasCanceled = false;
//        }
//        super.show();
//    }

    public TreeViewWindow(TreeViewWindow newTreeViewWindow) {
        folders = newTreeViewWindow.getFolders();
        Collections.sort(folders, new FolderLevelComparator());
        makeFrame();
        show();
    }

    public TreeViewWindow(File filePath) {
        if (filePath.exists()) {
            File[] directories = getDirectories(filePath);
            List<FolderLevel> firstFolders = makeLevels(filePath, directories);
            folders.addAll(firstFolders);
        }
        makeFrame();
        show();
    }

    public List<FolderLevel> getFolders() {
        return folders;
    }

    private void makeFrame() {
        JPanel panel = makeContentPanel(this);

        JBScrollPane scrollPane = new JBScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 10, 300, 400);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(300, 410));
        contentPane.add(scrollPane);
        //setContentPane(contentPane);
        getContentPane().add(contentPane, BorderLayout.PAGE_START);
        getContentPane().add(createButtonPanel(), BorderLayout.PAGE_END);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 500);
    }

    @NotNull
    private JPanel makeContentPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (FolderLevel fl : folders) {
            panel.add(fl.getjCheckBox());
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
            newPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            panel.add(newPanel);
            fl.getjCheckBox().setVisible(true);
            fl.getjCheckBox().addActionListener(makeListenerForEachCheckBox(fl, panel, frame, newPanel));
        }
        return panel;
    }

    private ActionListener makeListenerForEachCheckBox(FolderLevel folderLevel, JPanel panel, JFrame frame, JPanel newJpanel) {
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                File[] directories = getDirectories(folderLevel.getUrl());
                List<FolderLevel> newFolders = makeLevels(FormWindow.getFilePath(), directories);
                if (folderLevel.getjCheckBox().isSelected()) {
                    for (FolderLevel fl : newFolders) {
                        for (FolderLevel folderLevel : folders) {
                            if (folderLevel.getUrl().toString().equals(fl.getUrl().toString())) {
                                folderLevel.getjCheckBox().setSelected(true);
                                folderLevel.getjCheckBox().setVisible(true);
                            } else {
                                folders.add(fl);
                                newJpanel.add(fl.getjCheckBox());
                                fl.getjCheckBox().setVisible(true);
                                JPanel newPanel = new JPanel();
                                newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
                                newPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                                newJpanel.add(newPanel);
                                fl.getjCheckBox().addActionListener(makeListenerForEachCheckBox(fl, panel, frame, newPanel));
                                panel.revalidate();
                                panel.repaint();
                                frame.pack();
                            }
                        }
                    }
                } else {
                    for (FolderLevel newFolderLevel : newFolders) {
                        for (FolderLevel fl:folders) {
                            if (fl.getUrl().toString().contains(newFolderLevel.getUrl().toString())){
                                fl.getjCheckBox().setSelected(false);
                                fl.getjCheckBox().setVisible(false);
                            }
                        }
                        folders.removeIf(fl -> (fl.getUrl().toString().contains(newFolderLevel.getUrl().toString())));
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

    public boolean isWasCanceled() {
        return wasCanceled;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wasCanceled = false;
                //folders.removeIf(fl -> !fl.getjCheckBox().isSelected() && fl.getLevel() != 1);
//                oldFolders = new ArrayList<>(folders);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wasCanceled = true;
                dispose();
            }
        });

        panel.add(okButton);
        panel.add(cancelButton);
        return panel;
    }
}
