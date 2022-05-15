package cz.osu.kip.appLogic;

import cz.osu.kip.view.ClassToShowOptionDialogsWithTimer;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileController {
    public static List<String> loadFileToLines(String fileName) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(fileName));
            return lines;
        } catch (IOException e) {
            System.out.println("Nepodařilo se načíst soubor " + fileName);
            return null;
        }
    }

    public static void saveToFile(String fileName, String text) {
        if (!new File(fileName).isDirectory()) {
            try {
                FileWriter writer;
                if (new File(fileName).exists()) {
                    int dialogResult = JOptionPane.showConfirmDialog (null, "The file " + fileName + " already exists. Would you like to overwrite the file?","Warning",JOptionPane.YES_NO_CANCEL_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        writer = new FileWriter(fileName, false);
                        writer.append(text);
                        writer.close();
                    }
                }else{
                    writer = new FileWriter(fileName, true);
                    writer.append(text);
                    writer.close();
                }
            } catch (IOException e) {
                ClassToShowOptionDialogsWithTimer.showOptionDialogWithTimer("An error occurred while saving.", 2);
            }
        }
    }
}
