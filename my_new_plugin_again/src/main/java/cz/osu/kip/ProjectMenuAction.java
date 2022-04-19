package cz.osu.kip;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import cz.osu.kip.form.FolderLevel;
import cz.osu.kip.form.FormWindow;
import cz.osu.kip.form.MainFormWindowItems;
import cz.osu.kip.umlGeneration.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
                switch (formWindow.getSubmitState()){
                    case ALL:
//                        checkIfUmlFileExistsOrCreate(formWindow);
                        createConfigFile(formWindow);
                        if(formWindow.getMainFormWindowItems().getTreeViewWindow() != null && formWindow.getMainFormWindowItems().getTreeViewWindow().getFolders() != null) {
                            List<FolderLevel> newFolderLevels = new ArrayList<>();
                            for (FolderLevel fl : formWindow.getMainFormWindowItems().getTreeViewWindow().getFolders()) {
                                if (fl.getjCheckBox().isSelected())
                                    newFolderLevels.add(fl);
                            }
                            List<File> files = FileExplorer.getJavaFiles(/*formWindow.getMainFormWindowItems().getTreeViewWindow().getFolders()*/ newFolderLevels);
                            StringBuilder sb = new StringBuilder();
                            sb.append("@startuml\n\n");
                            List<PackageX> packageXES = getPackageXES(files);
                            for (PackageX packageX:packageXES) {
                                String text = UmlFilter.getTextByConfigInfo(configInfo, packageX);
                                sb.append(text);
                            }
                            sb.append("@enduml");
                            FileController.saveToFile(configInfo.getUmlTargetDestination(), sb.toString());
                        }
                        break;
                    case ONLY_UML:
                        break;
                    case ONLY_CONFIG:
                        createConfigFile(formWindow);
                        break;
                    case CANCEL:
                        break;
                }
            }
        });
    }

    private void checkIfUmlFileExistsOrCreate(FormWindow formWindow) {
        if (!formWindow.getMainFormWindowItems().getDefaultUMLTargetDestination().isSelected()){
            if (!new File(formWindow.getMainFormWindowItems().getDefaultUMLTargetFile().getSelectedFile().getAbsolutePath()).exists()){
                try {
                    FileWriter writer = new FileWriter(formWindow.getMainFormWindowItems().getDefaultUMLTargetFile().getSelectedFile().getAbsolutePath());
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @NotNull
    private List<PackageX> getPackageXES(List<File> files) {
        List<PackageX> packageXES = new ArrayList<>();
        for (File file: files) {
            PackageX packageX = getPackageXFromFile(file);
            if (packageXES.size() == 0){
                packageXES.add(packageX);
                continue;
            }
            boolean alreadyExist = false;
            for (PackageX packageXInList:packageXES) {
                if (packageX.getName().equals(packageXInList.getName())){
                    for (ClassX classX:packageX.getClassXES()) {
                        packageXInList.addClassX(classX);
                        alreadyExist = true;
                    }
                }
            }
            if (alreadyExist == false){
                packageXES.add(packageX);
            }
        }
        return packageXES;
    }

    private PackageX getPackageXFromFile(File fileInput) {
        List<String> lines = FileController.loadFileToLines(fileInput.getPath());
        PackageX packageX = DividingToClassUtil.divideFromLines(lines);
        return packageX;
    }

    private void createConfigFile(FormWindow formWindow) {
        configInfo = new ConfigInfo(formWindow.getMainFormWindowItems());

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
    }
}
