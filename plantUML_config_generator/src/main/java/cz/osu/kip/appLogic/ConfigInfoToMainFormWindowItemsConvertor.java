package cz.osu.kip.appLogic;

import com.intellij.openapi.project.Project;
import cz.osu.kip.view.mainForm.FolderLevel;
import cz.osu.kip.view.mainForm.UmlFormWindow;
import cz.osu.kip.view.mainForm.MainFormWindowItems;
import cz.osu.kip.view.mainForm.PackagesTreeViewWindow;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigInfoToMainFormWindowItemsConvertor {

    public static MainFormWindowItems convert(ConfigInfo configInfo, Project rootProject, File filePath) throws PackageFormException {
        UmlFormWindow umlFormWindow = new UmlFormWindow(rootProject, filePath);
        MainFormWindowItems mainFormWindowItems = new MainFormWindowItems(filePath, umlFormWindow);
        umlFormWindow.dispose();
        convertUMLTargetDestination(configInfo, mainFormWindowItems, filePath);
        convertConfigTargetDestination(configInfo, mainFormWindowItems, filePath);
        String initialURL = configInfo.getInitialUrl();
        convertPackagesToTreeViewWindow(configInfo, mainFormWindowItems, initialURL);
        mainFormWindowItems.getButtonToShowSelectedPackages().setVisible(true);

        mainFormWindowItems.setClassesCheckBox(configInfo.isClasses());
        mainFormWindowItems.setInterfacesCheckBox(configInfo.isInterfaces());
        mainFormWindowItems.setPublicForClassCheckBox(configInfo.isPublicClasses());
        mainFormWindowItems.setDefaultForClassCheckBox(configInfo.isDefaultClasses());
        mainFormWindowItems.setPublicForInterfaceCheckBox(configInfo.isPublicInterfaces());
        mainFormWindowItems.setDefaultForInterfaceCheckBox(configInfo.isDefaultInterfaces());
        mainFormWindowItems.setCheckBoxForClassAttributes(configInfo.isAttributesForClasses());
        mainFormWindowItems.setCheckBoxForClassMethods(configInfo.isMethodsForClasses());
        mainFormWindowItems.setCheckBoxForInnerClasses(configInfo.isInnerClasses());
        mainFormWindowItems.setCheckBoxForPrivateClassAttributes(configInfo.isPrivateAttributesForClasses());
        mainFormWindowItems.setCheckBoxForPublicClassAttributes(configInfo.isPublicAttributesForClasses());
        mainFormWindowItems.setCheckBoxForProtectedClassAttributes(configInfo.isProtectedAttributesForClasses());
        mainFormWindowItems.setCheckBoxForInternalClassAttributes(configInfo.isInternalAttributesForClasses());
        mainFormWindowItems.setCheckBoxForPrivateClassMethods(configInfo.isPrivateMethodsForClasses());
        mainFormWindowItems.setCheckBoxForPublicClassMethods(configInfo.isPublicMethodsForClasses());
        mainFormWindowItems.setCheckBoxForProtectedClassMethods(configInfo.isProtectedMethodsForClasses());
        mainFormWindowItems.setCheckBoxForInternalClassMethods(configInfo.isInternalMethodsForClasses());
        mainFormWindowItems.setCheckBoxForInterfaceAttributes(configInfo.isAttributesForInterfaces());
        mainFormWindowItems.setCheckBoxForInterfaceMethods(configInfo.isMethodsForInterfaces());
        return mainFormWindowItems;
    }

    private static void convertPackagesToTreeViewWindow(ConfigInfo configInfo, MainFormWindowItems mainFormWindowItems, String initialURL) throws PackageFormException {
        initialURL = getCorrectInitialURL(initialURL);
        List<FolderLevel> folders = makeFolderLevelFromEachPackage(configInfo, initialURL);
        PackagesTreeViewWindow packagesTreeViewWindow = new PackagesTreeViewWindow(folders, initialURL);
        mainFormWindowItems.setOwnPackages(packagesTreeViewWindow, initialURL);
        packagesTreeViewWindow.dispose();
    }

    private static List<FolderLevel> makeFolderLevelFromEachPackage(ConfigInfo configInfo, String initialURL) {
        List<FolderLevel> folders = new ArrayList<>();
        for (String url : configInfo.getPackages()) {
            File file = new File(url);
            String folderName = "";
            int folderLevel = 0;
            if (file.toString().contains("/")) {
                String[] oldPaths = initialURL.split("/");
                String[] paths = file.toString().split("/");
                folderLevel = paths.length - oldPaths.length + 1;
                folderName = file.toString().substring(file.toString().lastIndexOf("/") + 1);
            } else if (file.toString().contains("\\")) {
                String[] oldPaths = initialURL.split("\\\\");
                String[] paths = file.toString().split("\\\\");
                folderLevel = paths.length - oldPaths.length + 1;
                folderName = file.toString().substring(file.toString().lastIndexOf("\\") + 1);
            }
            FolderLevel fl = new FolderLevel(folderName, file, folderLevel, initialURL);
            fl.getjCheckBox().setSelected(true);
            folders.add(fl);
        }
        return folders;
    }

    @NotNull
    private static String getCorrectInitialURL(String initialURL) {
        if (initialURL.contains("/")) {
            if (initialURL.substring(initialURL.lastIndexOf("/")).contains(".")) {
                initialURL = initialURL.toString().substring(0, initialURL.toString().lastIndexOf("/"));
            }
        } else if (initialURL.contains("\\")) {
            if (initialURL.substring(initialURL.lastIndexOf("\\")).contains(".")) {
                initialURL = initialURL.substring(0, initialURL.lastIndexOf("\\"));
            }
        }
        return initialURL;
    }

    private static void convertConfigTargetDestination(ConfigInfo configInfo, MainFormWindowItems mainFormWindowItems, File filePath) {
        if (filePath.toPath().resolve("PlantUmlFiles").toFile().toString()
                .equals(configInfo.getConfigTargetDestination())) {
            mainFormWindowItems.setDefaultConfigTargetDestination();
        } else {
            mainFormWindowItems.setOwnConfigTargetDestination(configInfo.getConfigTargetDestination());
        }
    }

    private static void convertUMLTargetDestination(ConfigInfo configInfo, MainFormWindowItems mainFormWindowItems, File filePath) {
        if (filePath.toPath().resolve("PlantUmlFiles").toFile().toString()
                .equals(configInfo.getUmlTargetDestination())) {
            mainFormWindowItems.setDefaultUMLTargetDestination();
        } else {
            mainFormWindowItems.setOwnUMLTargetDestination(configInfo.getUmlTargetDestination());
        }
    }
}
