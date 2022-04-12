package cz.osu.kip;

import cz.osu.kip.form.FolderLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileExplorer {
    public static List<File> getJavaFiles(List<FolderLevel> folderLevels){
        if (folderLevels.isEmpty())
            return null;
        List<File> files = new ArrayList<>();
        for (FolderLevel folderLevel:folderLevels) {
            if (folderLevel.getUrl().isDirectory()){
                File[] srcFiles = folderLevel.getUrl().listFiles(f -> f.isFile() && f.toString().endsWith(".java"));
                files.addAll(Arrays.asList(srcFiles));
            }
        }
        return files;
    }
}
