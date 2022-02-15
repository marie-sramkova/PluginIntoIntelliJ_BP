package cz.osu.kip;

import com.google.gson.Gson;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import cz.osu.kip.form.FolderLevel;
import cz.osu.kip.form.FormWindow;
import cz.osu.kip.form.MainFormWindowItems;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectMenuAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        e.getActionManager().getId(this);
        Project rootProject = e.getProject();
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        File filePath = new File(file.getPath());
        FormWindow formWindow = new FormWindow(rootProject, filePath);
        formWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                System.out.println(formWindow.isSubmitted());
                if(formWindow.isSubmitted()){
                    ConfigInfo configInfo = new ConfigInfo(formWindow.getMainFormWindowItems());

                    var configInfo2 = new JSONObject(configInfo);

                    System.out.println(configInfo2);

                    try {
                        FileWriter writer = new FileWriter(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString());
                        writer.write(configInfo2.toString());
                        writer.close();
                    } catch (IOException ex) {
                        System.out.println("chyba json");
                        ex.printStackTrace();
                    }


//                    Gson gson = new Gson();
//                    try {
//                        gson.toJson(configInfo, new FileWriter(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString()));
//                    } catch (IOException ex) {
//                        System.out.println("chyba json");
//                        ex.printStackTrace();
//                    }

                }
            }
        });


//        Project currentProject = e.getProject();
//        StringBuffer dlgMsg = new StringBuffer(e.getPresentation().getText() + " Selected!");
//        String dlgTitle = e.getPresentation().getDescription();
//        // If an element is selected in the editor, add info about it.
//        Navigatable nav = e.getData(CommonDataKeys.NAVIGATABLE);
//        if (nav != null) {
//            dlgMsg.append(String.format("\nSelected Element: %s", nav.toString()));
//        }
//        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }
}
