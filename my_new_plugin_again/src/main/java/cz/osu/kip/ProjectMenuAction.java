package cz.osu.kip;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import cz.osu.kip.form.FormWindow;
import cz.osu.kip.form.MainFormWindowItems;
import cz.osu.kip.form.SubmitStateForFormWindow;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;

public class ProjectMenuAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        e.getActionManager().getId(this);
        Project rootProject = e.getProject();
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        File filePath = new File(file.getPath());
        System.out.println(rootProject);
        System.out.println(filePath);
        if (filePath.isDirectory()){
            showNewForm(rootProject, filePath, null);
        }else{
            getDataFromFile(rootProject, filePath);
        }


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

    private void getDataFromFile(Project rootProject, File filePath) {
        String text = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath.getPath()));
            text = reader.readLine();
            System.out.println(text);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(text);
            ConfigInfo configInfo = gson.fromJson(object, ConfigInfo.class);
            System.out.println(configInfo.toString());
            MainFormWindowItems mainFormWindowItems = ConfigInfoToMainFormWindowItemsConvertor.convert(configInfo, rootProject, filePath);
            showNewForm(rootProject, filePath, mainFormWindowItems);
        } catch (Exception ex) {
            int input = JOptionPane.showConfirmDialog(null,
                    "Incorrect file or impossible to load the content of the file.", "Chyba", JOptionPane.DEFAULT_OPTION);
        }
    }

    private void showNewForm(Project rootProject, File filePath, MainFormWindowItems mainFormWindowItems) {
        FormWindow formWindow;
        if (mainFormWindowItems == null){
            formWindow = new FormWindow(rootProject, filePath);
        }else{
            formWindow = new FormWindow(rootProject, filePath, mainFormWindowItems);
        }
        formWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                System.out.println(formWindow.getSubmitState());
                switch (formWindow.getSubmitState()){
                    case ALL:
                        ConfigInfo configInfo = new ConfigInfo(formWindow.getMainFormWindowItems());

                        var configInfo2 = new JSONObject(configInfo);

                        System.out.println(configInfo2);

                        try {
                            FileWriter writer = new FileWriter(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles.myuml").toFile().toString());
                            writer.write(configInfo2.toString());
                            writer.close();
                        } catch (IOException ex) {
                            System.out.println("chyba json");
                            ex.printStackTrace();
                        }
                        break;
                    case ONLY_UML:
                        break;
                    case ONLY_CONFIG:
                        break;
                    case CANCEL:
                        break;
                }
//                if(!formWindow.getSubmitState().equals(SubmitStateForFormWindow.CANCEL)){
//                    ConfigInfo configInfo = new ConfigInfo(formWindow.getMainFormWindowItems());
//
//                    var configInfo2 = new JSONObject(configInfo);
//
//                    System.out.println(configInfo2);
//
//                    try {
//                        FileWriter writer = new FileWriter(FormWindow.getFilePath().toPath().resolve("PlantUmlFiles.myuml").toFile().toString());
//                        writer.write(configInfo2.toString());
//                        writer.close();
//                    } catch (IOException ex) {
//                        System.out.println("chyba json");
//                        ex.printStackTrace();
//                    }
//                }
            }
        });
    }
}
