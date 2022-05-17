package cz.osu.kip.view.mainForm;

import com.intellij.ui.components.JBScrollPane;
import cz.osu.kip.view.ClassToShowOptionDialogsWithTimer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PackagesTreeViewWindow extends JFrame {
    private java.util.List<FolderLevel> folders = new ArrayList<>();
    private boolean wasCanceled = false;
    private String initialUrl;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public PackagesTreeViewWindow(PackagesTreeViewWindow newPackagesTreeViewWindow, String initialUrl) {
        this.initialUrl = initialUrl;
        for (FolderLevel fl : newPackagesTreeViewWindow.getFolders()) {
            FolderLevel folderLevel = new FolderLevel(fl, initialUrl);
            this.folders.add(folderLevel);
        }
        Collections.sort(folders, new FolderLevelComparator());
        makeFrame();
        show();
    }

    public PackagesTreeViewWindow(List<FolderLevel> newFolders, String initialUrl) {
        List<File> dirs = new ArrayList<>();
        this.initialUrl = initialUrl;
        if (newFolders == null || newFolders.size() == 0){
            folders.add(new FolderLevel(new File(initialUrl).getName(), new File(initialUrl), 1, initialUrl));
        }else {
            for (FolderLevel fl : newFolders) {
                FolderLevel folderLevel = new FolderLevel(fl, initialUrl);
                this.folders.add(folderLevel);

                File[] subdirsForSelectedFolders = new File[0];

                subdirsForSelectedFolders = getDirectories(fl.getUrl());
                try {
                    dirs.addAll(Arrays.asList(subdirsForSelectedFolders));
                } catch (Exception e) {
                    ClassToShowOptionDialogsWithTimer.showOptionDialogWithTimer("An error occurred while processing package " + fl.getUrl() + ".", 2);
                    return;
                }
            }
            dirs.addAll(Arrays.asList(getDirectories(new File(initialUrl))));
            List<FolderLevel> firstFolders = makeLevels(new File(initialUrl), dirs.toArray(new File[dirs.size()]));
            for (FolderLevel firstFolder : firstFolders) {
                boolean foldersIncludeFirstFolder = false;
                for (FolderLevel fl : folders) {
                    if (fl.getUrl().equals(firstFolder.getUrl())) {
                        foldersIncludeFirstFolder = true;
                    }
                }
                if (!foldersIncludeFirstFolder) {
                    this.folders.add(firstFolder);
                }
            }
            Collections.sort(folders, new FolderLevelComparator());
        }
        makeFrame();
        show();
    }

    public PackagesTreeViewWindow(File filePath) {
        if (filePath.exists()) {
            folders.add(new FolderLevel(filePath.getName(), filePath, 1, filePath.getAbsolutePath()));
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
        getContentPane().add(contentPane, BorderLayout.PAGE_START);
        getContentPane().add(createButtonPanel(), BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 500);
        pack();
    }

    @NotNull
    private JPanel makeContentPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (FolderLevel fl : folders) {
            JPanel mainCheckBoxPanel = new JPanel();
            mainCheckBoxPanel.setLayout(new BoxLayout(mainCheckBoxPanel, BoxLayout.Y_AXIS));
            int leftBorderSize = (fl.getLevel() * 10) - 10;
            mainCheckBoxPanel.setBorder(BorderFactory.createEmptyBorder(0, leftBorderSize, 0, 0));
            mainCheckBoxPanel.add(fl.getjCheckBox());
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
            newPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            mainCheckBoxPanel.add(newPanel);
            fl.getjCheckBox().setVisible(true);
            fl.getjCheckBox().addActionListener(makeListenerForCheckBox(fl, panel, frame, newPanel));
            panel.add(mainCheckBoxPanel);
        }
        return panel;
    }

    private ActionListener makeListenerForCheckBox(FolderLevel folderLevel, JPanel panel, JFrame frame, JPanel newJpanel) {
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                File[] directories = getDirectories(folderLevel.getUrl());
                List<FolderLevel> newFolders;
                if (initialUrl != null) {
                    newFolders = makeLevels(new File(initialUrl), directories);
                } else {
                    newFolders = makeLevels(UmlFormWindow.getFilePath(), directories);
                }
                if (folderLevel.getjCheckBox().isSelected()) {
                    top_iteration:
                    for (FolderLevel fl : newFolders) {
                        boolean foldersContainsNewFolderLvl = false;
                        bottom_iteration:
                        for (FolderLevel folderLvl : folders) {
                            if (folderLvl.getUrl().toString().equals(fl.getUrl().toString())) {
                                foldersContainsNewFolderLvl = true;
                                folderLvl.getjCheckBox().setSelected(true);
                                folderLvl.getjCheckBox().setVisible(true);
                            }
                        }
                        if (foldersContainsNewFolderLvl == false){
                            folders.add(fl);
                            addToPanel(fl, newJpanel, panel, frame);
                        }
                    }
                } else {
                    for (FolderLevel newFolderLevel : newFolders) {
                        for (FolderLevel fl : folders) {
                            if (fl.getUrl().toString().contains(newFolderLevel.getUrl().toString())) {
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

    private void addToPanel(FolderLevel fl, JPanel newJpanel, JPanel panel, JFrame frame) {
        newJpanel.add(fl.getjCheckBox());
        fl.getjCheckBox().setVisible(true);
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        newJpanel.add(newPanel);
        fl.getjCheckBox().addActionListener(makeListenerForCheckBox(fl, panel, frame, newPanel));
        panel.revalidate();
        panel.repaint();
        frame.pack();
    }

    private List<FolderLevel> makeLevels(File filePath, File[] directories) {
        filePath = getDirectoryPathWithoutFile(filePath);
        List<FolderLevel> newFolderLevels = new ArrayList<>();
        for (File dir : directories) {
            int folderLevel = 0;
            if (dir.toString().contains("/")) {
                String[] oldPaths = filePath.toString().split("/");
                String[] paths = dir.toString().split("/");
                folderLevel = paths.length - oldPaths.length + 1;
            } else if (dir.toString().contains("\\")) {
                String[] oldPaths = filePath.toString().split("\\\\");
                String[] paths = dir.toString().split("\\\\");
                folderLevel = paths.length - oldPaths.length + 1;
            }
            if (initialUrl != null) {
                newFolderLevels.add(new FolderLevel(dir.getName(), dir, folderLevel, initialUrl));
            } else {
                newFolderLevels.add(new FolderLevel(dir.getName(), dir, folderLevel, UmlFormWindow.getFilePath().toString()));
            }
        }
        return newFolderLevels;
    }

    @NotNull
    public File getDirectoryPathWithoutFile(File filePath) {
        if (filePath.toString().contains("/")) {
            if (filePath.toString().substring(filePath.toString().lastIndexOf("/")).contains(".")
            && !filePath.toString().substring(filePath.toString().lastIndexOf("/")+1).startsWith(".")) {
                filePath = new File(filePath.toString().substring(0, filePath.toString().lastIndexOf("/")));
            }
        } else if (filePath.toString().contains("\\")) {
            if (filePath.toString().substring(filePath.toString().lastIndexOf("\\")).contains(".")
            && !filePath.toString().substring(filePath.toString().lastIndexOf("\\")+1).startsWith(".")) {
                filePath = new File(filePath.toString().substring(0, filePath.toString().lastIndexOf("\\")));
            }
        }
        return filePath;
    }

    @Nullable
    private File[] getDirectories(File filePath) {
        filePath = getDirectoryPathWithoutFile(filePath);
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
