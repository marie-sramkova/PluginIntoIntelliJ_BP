package cz.osu.kip;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.project.Project;
import cz.osu.kip.mainForm.FolderLevel;
import cz.osu.kip.mainForm.MainFormWindowItems;
import cz.osu.kip.umlGeneration.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static void createUmlFile(MainFormWindowItems mainFormWindowItems, ConfigInfo configInfo) {
        List<File> newFolders = new ArrayList<>();
        if (mainFormWindowItems.getTreeViewWindow() != null && mainFormWindowItems.getTreeViewWindow().getFolders() != null) {
            for (FolderLevel fl : mainFormWindowItems.getTreeViewWindow().getFolders()) {
                if (fl.getjCheckBox().isSelected())
                    newFolders.add(fl.getUrl());
            }
        }else{
            for (String pakage:configInfo.getPackages()) {
                newFolders.add(new File(pakage));
            }
        }
        List<File> files = FileExplorer.getJavaFiles(newFolders);
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n\n");
        List<PackageX> packageXES = getPackageXES(files);
        for (PackageX packageX : packageXES) {
            String text = UmlFilter.getTextByConfigInfo(configInfo, packageX);
            sb.append(text);
        }
        sb.append("@enduml");
        FileController.saveToFile(configInfo.getUmlTargetDestination(), sb.toString());
    }

    public static void createConfigFile(ConfigInfo configInfo) {
        JSONObject jsonObject = new JSONObject(configInfo);
        String configInfo2 = jsonObject.toString(4);

        FileController.saveToFile(configInfo.getConfigTargetDestination(), configInfo2);
    }

    @NotNull
    public static List<PackageX> getPackageXES(List<File> files) {
        List<PackageX> packageXES = new ArrayList<>();
        for (File file : files) {
            PackageX packageX = getPackageXFromFile(file);
            if (packageXES.size() == 0) {
                packageXES.add(packageX);
                continue;
            }
            boolean alreadyExist = false;
            for (PackageX packageXInList : packageXES) {
                if (packageX.getName().equals(packageXInList.getName())) {
                    for (ClassX classX : packageX.getClassXES()) {
                        packageXInList.addClassX(classX);
                        alreadyExist = true;
                    }
                }
            }
            if (alreadyExist == false) {
                packageXES.add(packageX);
            }
        }
        return packageXES;
    }

    public static PackageX getPackageXFromFile(File fileInput) {
        List<String> lines = FileController.loadFileToLines(fileInput.getPath());
        PackageX packageX = DividingToClassUtil.divideFromLines(lines);
        return packageX;
    }

    public static MainFormWindowItems getDataFromFile(Project rootProject, File filePath) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath.getPath()));
            String line;
            while((line = reader.readLine()) != null){
                text.append(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(text.toString());
            ConfigInfo configInfo = gson.fromJson(object, ConfigInfo.class);
            MainFormWindowItems mainFormWindowItems = ConfigInfoToMainFormWindowItemsConvertor.convert(configInfo, rootProject, filePath);
            return mainFormWindowItems;
        } catch (Exception ex) {
            int input = JOptionPane.showConfirmDialog(null,
                    "Incorrect file or impossible to load the content of the file.", "Chyba", JOptionPane.DEFAULT_OPTION);
            return null;
        }
    }
}
