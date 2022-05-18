package cz.osu.kip.appLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileExplorer {
    public static List<File> getJavaFiles(List<File> folderUrls) {
        if (folderUrls.isEmpty())
            return null;
        List<File> files = new ArrayList<>();
        for (File url : folderUrls) {
            if (url.isDirectory()) {
                File[] srcFiles = url.listFiles(f -> f.isFile() && f.toString().endsWith(".java"));
                files.addAll(Arrays.asList(srcFiles));
            }
        }
        return files;
    }

    public static List<File> getConfigFiles(List<File> folderUrls) {
        if (folderUrls.isEmpty())
            return null;
        List<File> files = new ArrayList<>();
        for (File url : folderUrls) {
            if (url.isDirectory()) {
                File[] srcFiles = url.listFiles(f -> f.isFile() && f.toString().endsWith(".myuml"));
                files.addAll(Arrays.asList(srcFiles));
            }
        }
        return files;
    }
}
