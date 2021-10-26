package lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewFileClass {

    public static void makeNewFile(String targetString) throws IOException {
        File target = new File(targetString);
        controlePath(target);

        createFile(target);

        System.out.println("New file was created.");
    }

    private static void controlePath(File target) {
        if (!target.exists()){
            throw new IllegalArgumentException(String.format("Cannot find path: '%s'", target));
        }
        if (!target.isDirectory()){
            throw new IllegalArgumentException(String.format("Cannot make file to file %s, need a directory", target));
        }
    }

    private static void createFile(File target) {
        try {
            Files.createFile(target.toPath().resolve("newFile.txt"));
            System.out.println(String.format("File \t\t'%s' is made.\n", target.toPath().getFileName()));
        }catch (FileAlreadyExistsException e){
            throw new RuntimeException("File " + target + " already exists.", e);
        }catch (IOException e) {
            throw new RuntimeException("Cannot make a file to target path.");
        }
    }
}
