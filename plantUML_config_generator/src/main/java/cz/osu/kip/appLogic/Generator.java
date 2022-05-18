package cz.osu.kip.appLogic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.project.Project;
import cz.osu.kip.view.ClassToShowOptionDialogsWithTimer;
import cz.osu.kip.view.mainForm.FolderLevel;
import cz.osu.kip.view.mainForm.MainFormWindowItems;
import cz.osu.kip.appLogic.umlGeneration.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static void createUmlFile(MainFormWindowItems mainFormWindowItems, ConfigInfo configInfo) {
        List<File> newFolders = getFolders(mainFormWindowItems, configInfo);
        List<File> files = FileExplorer.getJavaFiles(newFolders);
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n\n");
        List<PackageX> packageXES = getPackageXESFromFiles(files);
        if (packageXES != null) {
            for (PackageX packageX : packageXES) {
                String text = UmlFilter.getTextByConfigInfo(configInfo, packageX);
                sb.append(text);
            }
        }
        sb.append("@enduml");
        FileController.saveToFile(configInfo.getUmlTargetDestination(), sb.toString());
    }

    public static void createConfigFile(ConfigInfo configInfo) {
        JSONObject jsonObject = new JSONObject(configInfo);
        String configInfo2 = jsonObject.toString(4);

        FileController.saveToFile(configInfo.getConfigTargetDestination(), configInfo2);
    }

    public static List<PackageX> getPackageXESFromFiles(List<File> files) {
        List<PackageX> packageXES = new ArrayList<>();
        if (files == null) {
            return null;
        }
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
        PackageX packageX = DividingToClassUtil.getPackageXWithClassesFromLines(lines);
        return packageX;
    }

    public static MainFormWindowItems getDataFromFile(Project rootProject, File filePath) throws PackageFormException {
        StringBuilder text = new StringBuilder();
        List<String> lines = FileController.loadFileToLines(filePath.getAbsolutePath().toString());
        for (String line : lines) {
            text.append(line);
        }
        try {
            ConfigInfo configInfo = convertTextToConfigInfoByGson(text);
            MainFormWindowItems mainFormWindowItems = ConfigInfoToMainFormWindowItemsConvertor.convert(configInfo, rootProject, filePath);
            return mainFormWindowItems;
        } catch (PackageFormException ex) {
            throw new PackageFormException();
        } catch (Exception ex) {
            int input = JOptionPane.showConfirmDialog(null,
                    "Incorrect file or impossible to load the content of the file.", "Error", JOptionPane.DEFAULT_OPTION);
            return null;
        }
    }

    private static ConfigInfo convertTextToConfigInfoByGson(StringBuilder text) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(text.toString());
        ConfigInfo configInfo = gson.fromJson(object, ConfigInfo.class);
        return configInfo;
    }

    @NotNull
    private static List<File> getFolders(MainFormWindowItems mainFormWindowItems, ConfigInfo configInfo) {
        List<File> newFolders = new ArrayList<>();
        if (mainFormWindowItems.getTreeViewWindow() != null && mainFormWindowItems.getTreeViewWindow().getFolders() != null) {
            for (FolderLevel fl : mainFormWindowItems.getTreeViewWindow().getFolders()) {
                if (fl.getjCheckBox().isSelected())
                    newFolders.add(fl.getUrl());
            }
        } else {
            for (String pakage : configInfo.getPackages()) {
                newFolders.add(new File(pakage));
            }
        }
        return newFolders;
    }
}
