package cz.osu.kip;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import cz.osu.kip.configForm.ConfigFormWindow;
import cz.osu.kip.configForm.SubmitStateForConfigFormWindow;
import cz.osu.kip.mainForm.FolderLevel;
import cz.osu.kip.mainForm.MainFormWindowItems;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TopMenuAction extends DumbAwareAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Project rootProject = e.getProject();
        System.out.println(rootProject.getBasePath());
        List<File> dirs = getSubdirs(new File(rootProject.getBasePath()));
        List<File> configFiles = FileExplorer.getConfigFiles(dirs);

        ConfigFormWindow configFormWindow = new ConfigFormWindow(configFiles, new File(rootProject.getBasePath()));
        configFormWindow.show();

        configFormWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                List<File> files = new ArrayList<>();
                if (configFormWindow.getSubmitState() != SubmitStateForConfigFormWindow.CANCEL) {
                    for (FolderLevel fl : configFormWindow.getConfigFiles()) {
                        if (fl.getjCheckBox().isSelected()) {
                            files.add(fl.getUrl());
                        }
                    }
                }
                switch (configFormWindow.getSubmitState()) {
                    case GENERATE_UML_DIAGRAM:
                        for (File filePath : files) {
                            MainFormWindowItems mainFormWindowItems = Generator.getDataFromFile(rootProject, filePath);
                            ConfigInfo configInfo = new ConfigInfo(mainFormWindowItems);
                            Generator.createUmlFile(mainFormWindowItems, configInfo);
                        }
                        break;
                    case DELETE:
                        for (File filePath : files) {
                            if (filePath.exists() && filePath.isFile()) {
                                try {
                                    int dialogResult = JOptionPane.showConfirmDialog (null, "Would yout like to delete file / files?","Warning",JOptionPane.YES_NO_CANCEL_OPTION);
                                    if(dialogResult == JOptionPane.YES_OPTION){
                                        Files.deleteIfExists(filePath.toPath());
                                        optionDialog("File was / files were successfully deleted.");
                                    }else {
                                        configFormWindow.show();
                                    }
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null ,"Cannot delete the file " + filePath.toString());
                                }
                            }
                        }
                        break;
                    case CANCEL:
                        break;
                }
            }
        });
    }

    private static void optionDialog(String text){
        JLabel messageLabel = new JLabel("<html><body><p style='width: 300px;'>"+text+"</p></body></html>");
        Timer timer = new Timer(2000,
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        SwingUtilities.getWindowAncestor(messageLabel).dispose();
                    }
                });
        timer.setRepeats(false);
        timer.start();
        JOptionPane.showOptionDialog(null, messageLabel, "Notification", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
    }

    private List<File> getSubdirs(File file) {
        List<File> subdirs = Arrays.asList(Objects.requireNonNull(file.listFiles(new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory();
            }
        })));
        subdirs = new ArrayList<>(subdirs);

        List<File> deepSubdirs = new ArrayList<File>();
        for (File subdir : subdirs) {
            deepSubdirs.addAll(getSubdirs(subdir));
        }
        subdirs.addAll(deepSubdirs);
        return subdirs;
    }
}
