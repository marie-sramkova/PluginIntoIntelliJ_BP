package cz.osu.kip.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import cz.osu.kip.view.ClassToShowOptionDialogsWithTimer;
import cz.osu.kip.appLogic.ConfigInfo;
import cz.osu.kip.appLogic.FileExplorer;
import cz.osu.kip.appLogic.Generator;
import cz.osu.kip.view.configForm.ConfigFormWindow;
import cz.osu.kip.view.configForm.SubmitStateForConfigFormWindow;
import cz.osu.kip.view.mainForm.FolderLevel;
import cz.osu.kip.view.mainForm.MainFormWindowItems;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
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
                                        ClassToShowOptionDialogsWithTimer.showOptionDialogWithTimer("File was / files were successfully deleted.", 2);
                                    }else {
                                        configFormWindow.show();
                                    }
                                } catch (IOException ex) {
                                    ClassToShowOptionDialogsWithTimer.showOptionDialogWithTimer("Cannot delete the file " + filePath.toString() + ".", 2);
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
