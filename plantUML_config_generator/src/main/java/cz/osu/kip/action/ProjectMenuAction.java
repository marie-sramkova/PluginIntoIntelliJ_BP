package cz.osu.kip.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import cz.osu.kip.appLogic.ConfigInfo;
import cz.osu.kip.appLogic.Generator;
import cz.osu.kip.appLogic.PackageFormException;
import cz.osu.kip.view.ClassToShowOptionDialogsWithTimer;
import cz.osu.kip.view.mainForm.UmlFormWindow;
import cz.osu.kip.view.mainForm.MainFormWindowItems;
import cz.osu.kip.view.mainForm.SubmitStateForFormWindow;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ProjectMenuAction extends AnAction {
    private ConfigInfo configInfo;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        e.getActionManager().getId(this);
        Project rootProject = e.getProject();
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        File filePath = new File(file.getPath());
        System.out.println(rootProject);
        System.out.println(filePath);
        if (filePath.isDirectory()) {
            UmlFormWindow umlFormWindow = showNewForm(rootProject, filePath, null);
        } else {
            MainFormWindowItems mainFormWindowItems = null;
            try{
//                mainFormWindowItems = Generator.getDataFromFile(rootProject, filePath);
//                UmlFormWindow umlFormWindow = showNewForm(rootProject, filePath, mainFormWindowItems);
//                mainFormWindowItems.setUmlFormWindow(umlFormWindow);

                mainFormWindowItems = Generator.getDataFromFile(rootProject, filePath);
                if (mainFormWindowItems != null) {
                    UmlFormWindow umlFormWindow = showNewForm(rootProject, filePath, mainFormWindowItems);
                }
            }
            catch (PackageFormException ex){
                ClassToShowOptionDialogsWithTimer.showOptionDialogWithTimer("An error occurred while processing packages.", 2);
            }
        }
    }

    private UmlFormWindow showNewForm(Project rootProject, File filePath, MainFormWindowItems mainFormWindowItems) {
        UmlFormWindow umlFormWindow;
        if (mainFormWindowItems == null) {
            umlFormWindow = new UmlFormWindow(rootProject, filePath);
        } else {
            umlFormWindow = new UmlFormWindow(rootProject, filePath, mainFormWindowItems);
        }
        umlFormWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (umlFormWindow.getSubmitState() != SubmitStateForFormWindow.CANCEL) {
                    configInfo = new ConfigInfo(umlFormWindow.getMainFormWindowItems());
                }
                switch (umlFormWindow.getSubmitState()) {
                    case ALL:
                        Generator.createConfigFile(configInfo);
                        Generator.createUmlFile(umlFormWindow.getMainFormWindowItems(), configInfo);
                        break;
                    case ONLY_UML:
                        Generator.createUmlFile(umlFormWindow.getMainFormWindowItems(), configInfo);
                        break;
                    case ONLY_CONFIG:
                        Generator.createConfigFile(configInfo);
                        break;
                    case CANCEL:
                        break;
                }
            }
        });
        return umlFormWindow;
    }
}
