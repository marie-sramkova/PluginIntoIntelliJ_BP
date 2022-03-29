package cz.osu.kip.form;

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
import java.util.List;

public class TreeViewWindow extends JFrame {
    private java.util.List<FolderLevel> folders = new ArrayList<>();
    private boolean isSubmitted = false;

    public TreeViewWindow(File filePath){
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

    public boolean isSubmitted() {
        return isSubmitted;
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
        setVisible(true);
        show();
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
            fl.getjCheckBox().addActionListener(makeListenerForEachCheckBox(fl, panel, frame, newPanel, fl.getjCheckBox()));
        }
        return panel;
    }

    private ActionListener makeListenerForEachCheckBox(FolderLevel folderLevel, JPanel panel, JFrame frame, JPanel newJpanel, JCheckBox checkBox) {
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
                                fl.getjCheckBox().addActionListener(makeListenerForEachCheckBox(fl, panel, frame, newPanel, fl.getjCheckBox()));
                                panel.revalidate();
                                panel.repaint();
                                frame.pack();
                            }
                        }
                    }
                } else {
                    for (FolderLevel folderLevel : folders) {
                        for (FolderLevel newFolderLevel : newFolders) {
                            if (folderLevel.getUrl().toString().equals(newFolderLevel.getUrl().toString())) {
                                folderLevel.getjCheckBox().setSelected(false);
                                folderLevel.getjCheckBox().setVisible(false);
                            } else if (folderLevel.getUrl().toString().contains(newFolderLevel.getUrl().toString())) {
                                folderLevel.getjCheckBox().setSelected(false);
                                folderLevel.getjCheckBox().setVisible(false);
                            }
                        }
                    }
                    if (!newFolders.isEmpty()) {
                        for (FolderLevel fl : newFolders) {
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

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

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
        panel.add(cancelButton);
        return panel;
    }
}
