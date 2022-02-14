package cz.osu.kip;

import cz.osu.kip.form.FolderLevel;
import cz.osu.kip.form.MainFormWindowItems;

import java.io.File;
import java.util.List;

public class ConfigInfo {
    private String umlTargetDestination;
    private String configTargetDestination;
    private List<File> packages;
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














    public ConfigInfo(MainFormWindowItems mainFormWindowItems) {
        //folderLevels = mainFormWindowItems.getTreeViewWindow().getFolders();
    }
}
