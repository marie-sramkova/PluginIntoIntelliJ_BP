package cz.osu.kip;

import com.intellij.openapi.project.Project;
import cz.osu.kip.form.FormWindow;
import cz.osu.kip.form.MainFormWindowItems;

import java.io.File;

public class ConfigInfoToMainFormWindowItemsConvertor {

    public static MainFormWindowItems convert(ConfigInfo configInfo, Project rootProject, File filePath) {
        MainFormWindowItems mainFormWindowItems = new MainFormWindowItems(filePath);
        convertUMLTargetDestination(configInfo, mainFormWindowItems, filePath);
        convertConfigTargetDestination(configInfo, mainFormWindowItems, filePath);
        mainFormWindowItems.setOwnPackages(); //TODO: výpis

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
