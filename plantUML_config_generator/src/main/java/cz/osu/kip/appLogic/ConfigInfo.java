package cz.osu.kip.appLogic;

import cz.osu.kip.appLogic.umlGeneration.PackageXByFileConvertor;
import cz.osu.kip.view.mainForm.FolderLevel;
import cz.osu.kip.view.mainForm.MainFormWindowItems;
import cz.osu.kip.view.mainForm.UmlFormWindow;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigInfo {
    private String initialUrl = UmlFormWindow.getFilePath().toString();
    private String umlTargetDestination;
    private String configTargetDestination;
    private List<String> packages = new ArrayList<>();
    private boolean classes;
    private boolean publicClasses;
    private boolean defaultClasses;
    private boolean attributesForClasses;
    private boolean methodsForClasses;
    private boolean innerClasses;
    private boolean publicAttributesForClasses;
    private boolean privateAttributesForClasses;
    private boolean protectedAttributesForClasses;
    private boolean internalAttributesForClasses;
    private boolean publicMethodsForClasses;
    private boolean privateMethodsForClasses;
    private boolean protectedMethodsForClasses;
    private boolean internalMethodsForClasses;
    private boolean interfaces;
    private boolean publicInterfaces;
    private boolean defaultInterfaces;
    private boolean attributesForInterfaces;
    private boolean methodsForInterfaces;

    public ConfigInfo(MainFormWindowItems mainFormWindowItems) {
        processUmlTargetDestination(mainFormWindowItems);
        processConfigTargetDestination(mainFormWindowItems);
        processPackages(mainFormWindowItems);
        classes = mainFormWindowItems.getClassesCheckBox().isSelected();
        publicClasses = mainFormWindowItems.getPublicForClassCheckBox().isSelected();
        defaultClasses = mainFormWindowItems.getDefaultForClassCheckBox().isSelected();
        attributesForClasses = mainFormWindowItems.getCheckBoxForClassAttributes().isSelected();
        methodsForClasses = mainFormWindowItems.getCheckBoxForClassMethods().isSelected();
        innerClasses = mainFormWindowItems.getCheckBoxForInnerClasses().isSelected();
        publicAttributesForClasses = mainFormWindowItems.getCheckBoxForPublicClassAttributes().isSelected();
        privateAttributesForClasses = mainFormWindowItems.getCheckBoxForPrivateClassAttributes().isSelected();
        protectedAttributesForClasses = mainFormWindowItems.getCheckBoxForProtectedClassAttributes().isSelected();
        internalAttributesForClasses = mainFormWindowItems.getCheckBoxForInternalClassAttributes().isSelected();
        publicMethodsForClasses = mainFormWindowItems.getCheckBoxForPublicClassMethods().isSelected();
        privateMethodsForClasses = mainFormWindowItems.getCheckBoxForPrivateClassMethods().isSelected();
        protectedMethodsForClasses = mainFormWindowItems.getCheckBoxForProtectedClassMethods().isSelected();
        internalMethodsForClasses = mainFormWindowItems.getCheckBoxForInternalClassMethods().isSelected();
        interfaces = mainFormWindowItems.getInterfacesCheckBox().isSelected();
        publicInterfaces = mainFormWindowItems.getPublicForInterfaceCheckBox().isSelected();
        defaultInterfaces = mainFormWindowItems.getDefaultForInterfaceCheckBox().isSelected();
        attributesForInterfaces = mainFormWindowItems.getCheckBoxForInterfaceAttributes().isSelected();
        methodsForInterfaces = mainFormWindowItems.getCheckBoxForInterfaceMethods().isSelected();
    }

    private void processPackages(MainFormWindowItems mainFormWindowItems) {
        if (mainFormWindowItems.getAllPackages().isSelected()) {
            packages.add(UmlFormWindow.getFilePath().toString());
            List<File> subdirs = getSubdirs(UmlFormWindow.getFilePath());
            for (File file : subdirs) {
                packages.add(file.getAbsolutePath());
            }
        } else if (mainFormWindowItems.getTreeViewWindow() != null && mainFormWindowItems.getTreeViewWindow().getFolders() != null) {
            for (FolderLevel fl : mainFormWindowItems.getTreeViewWindow().getFolders()) {
                if (fl.getjCheckBox().isSelected())
                    packages.add(fl.getUrl().toString());
            }
        }
    }

    private void processConfigTargetDestination(MainFormWindowItems mainFormWindowItems) {
        StringBuilder stringBuilder;
        if (mainFormWindowItems.getDefaultConfigTargetDestination().isSelected()) {
            configTargetDestination = UmlFormWindow.getFilePath().toPath().resolve("PlantUmlConfigFile.myuml").toFile().toString();
        } else {
            stringBuilder = new StringBuilder();
            try {
                stringBuilder.append(mainFormWindowItems.getDefaultConfigTargetFile().getSelectedFile().getAbsolutePath());
                if (!stringBuilder.toString().endsWith(".myuml")) {
                    stringBuilder.append(".myuml");
                }
            } catch (Exception e) {
                int input = JOptionPane.showConfirmDialog(null,
                        "You selected incorrect file or you didn't confirm selection. The default location: " + mainFormWindowItems.getDefaultUMLTargetDestinationDesc().getText() + " was set.", "Error", JOptionPane.DEFAULT_OPTION);
                stringBuilder.append(UmlFormWindow.getFilePath().toPath().resolve("PlantUmlConfigFile.myuml").toFile());
            }
            configTargetDestination = stringBuilder.toString();
        }
    }

    private void processUmlTargetDestination(MainFormWindowItems mainFormWindowItems) {
        StringBuilder stringBuilder;
        if (mainFormWindowItems.getDefaultUMLTargetDestination().isSelected()) {
            umlTargetDestination = UmlFormWindow.getFilePath().toPath().resolve("PlantUmlFile.puml").toFile().toString();
        } else {
            stringBuilder = new StringBuilder();
            try {
                stringBuilder.append(mainFormWindowItems.getDefaultUMLTargetFile().getSelectedFile().getAbsolutePath());
                if (!stringBuilder.toString().endsWith(".puml")) {
                    stringBuilder.append(".puml");
                }
            } catch (Exception e) {
                int input = JOptionPane.showConfirmDialog(null,
                        "You selected incorrect file or you didn't confirm selection. The default location: " + mainFormWindowItems.getDefaultUMLTargetDestinationDesc().getText() + " was set.", "Error", JOptionPane.DEFAULT_OPTION);
                stringBuilder.append(UmlFormWindow.getFilePath().toPath().resolve("PlantUmlFile.puml").toFile());
            }
            umlTargetDestination = stringBuilder.toString();
        }
    }

    private List<File> getSubdirs(File file) {
        if(!file.isDirectory()){
            file = new File(ConfigInfoToMainFormWindowItemsConvertor.getCorrectInitialURL(file.getAbsolutePath()));
        }
        List<File> subdirs = Arrays.asList(Objects.requireNonNull(file.listFiles(new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory();
            }
        })));
        subdirs = new ArrayList<File>(subdirs);

        List<File> deepSubdirs = new ArrayList<File>();
        for (File subdir : subdirs) {
            deepSubdirs.addAll(getSubdirs(subdir));
        }
        subdirs.addAll(deepSubdirs);
        return subdirs;
    }

    public String getInitialUrl() {
        return initialUrl;
    }

    public String getUmlTargetDestination() {
        return umlTargetDestination;
    }

    public String getConfigTargetDestination() {
        return configTargetDestination;
    }

    public List<String> getPackages() {
        return packages;
    }

    public boolean isClasses() {
        return classes;
    }

    public boolean isPublicClasses() {
        return publicClasses;
    }

    public boolean isDefaultClasses() {
        return defaultClasses;
    }

    public boolean isAttributesForClasses() {
        return attributesForClasses;
    }

    public boolean isMethodsForClasses() {
        return methodsForClasses;
    }

    public boolean isInnerClasses() {
        return innerClasses;
    }

    public boolean isPublicAttributesForClasses() {
        return publicAttributesForClasses;
    }

    public boolean isPrivateAttributesForClasses() {
        return privateAttributesForClasses;
    }

    public boolean isProtectedAttributesForClasses() {
        return protectedAttributesForClasses;
    }

    public boolean isInternalAttributesForClasses() {
        return internalAttributesForClasses;
    }

    public boolean isPublicMethodsForClasses() {
        return publicMethodsForClasses;
    }

    public boolean isPrivateMethodsForClasses() {
        return privateMethodsForClasses;
    }

    public boolean isProtectedMethodsForClasses() {
        return protectedMethodsForClasses;
    }

    public boolean isInternalMethodsForClasses() {
        return internalMethodsForClasses;
    }

    public boolean isInterfaces() {
        return interfaces;
    }

    public boolean isPublicInterfaces() {
        return publicInterfaces;
    }

    public boolean isDefaultInterfaces() {
        return defaultInterfaces;
    }

    public boolean isAttributesForInterfaces() {
        return attributesForInterfaces;
    }

    public boolean isMethodsForInterfaces() {
        return methodsForInterfaces;
    }

    @Override
    public String toString() {
        return "ConfigInfo{" +
                "initialUrl='" + initialUrl + '\'' +
                ", umlTargetDestination='" + umlTargetDestination + '\'' +
                ", configTargetDestination='" + configTargetDestination + '\'' +
                ", packages=" + packages +
                ", classes=" + classes +
                ", publicClasses=" + publicClasses +
                ", defaultClasses=" + defaultClasses +
                ", attributesForClasses=" + attributesForClasses +
                ", methodsForClasses=" + methodsForClasses +
                ", innerClasses=" + innerClasses +
                ", publicAttributesForClasses=" + publicAttributesForClasses +
                ", privateAttributesForClasses=" + privateAttributesForClasses +
                ", protectedAttributesForClasses=" + protectedAttributesForClasses +
                ", internalAttributesForClasses=" + internalAttributesForClasses +
                ", publicMethodsForClasses=" + publicMethodsForClasses +
                ", privateMethodsForClasses=" + privateMethodsForClasses +
                ", protectedMethodsForClasses=" + protectedMethodsForClasses +
                ", internalMethodsForClasses=" + internalMethodsForClasses +
                ", interfaces=" + interfaces +
                ", publicInterfaces=" + publicInterfaces +
                ", defaultInterfaces=" + defaultInterfaces +
                ", attributesForInterfaces=" + attributesForInterfaces +
                ", methodsForInterfaces=" + methodsForInterfaces +
                '}';
    }
}
