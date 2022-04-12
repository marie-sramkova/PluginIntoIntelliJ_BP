package cz.osu.kip.umlGeneration;

import cz.osu.kip.form.FormWindow;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileController {
    public static List<String> loadFileToLines(String fileName) {
        System.out.println(fileName);
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
        System.out.println("saveToFile method in FileController:" + fileName);
        try {
//            Files.writeString(Path.of(fileName), text);
            FileWriter writer = new FileWriter(fileName, true);
            writer.append(text);
            writer.close();
        } catch (IOException e) {
            System.out.println("Nepodařilo se načíst soubor " + fileName);
        }
    }
}
