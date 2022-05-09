package cz.osu.kip;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import cz.osu.kip.configForm.ConfigFormWindow;
import cz.osu.kip.configForm.SubmitStateForConfigFormWindow;
import cz.osu.kip.mainForm.FolderLevel;
import cz.osu.kip.mainForm.MainFormWindowItems;
import cz.osu.kip.mainForm.SubmitStateForFormWindow;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TopMenuAction extends DumbAwareAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Project rootProject = e.getProject();
        System.out.println(rootProject.getBasePath());
        List<File> dirs = getSubdirs(new File(rootProject.getBasePath()));
        List<File> configFiles = FileExplorer.getConfigFiles(dirs);

        ConfigFormWindow configFormWindow = new ConfigFormWindow(configFiles, new File(rootProject.getBasePath()));
        configFormWindow.show();

        configFormWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                List<File> files = new ArrayList<>();
                if (configFormWindow.getSubmitState() != SubmitStateForConfigFormWindow.CANCEL) {
                    for (FolderLevel fl : configFormWindow.getConfigFiles()) {
                        if (fl.getjCheckBox().isSelected()) {
                            files.add(fl.getUrl());
                        }
                    }
                }
                switch (configFormWindow.getSubmitState()) {
                    case GENERATE_UML_DIAGRAM:
                        for (File filePath : files) {
                            MainFormWindowItems mainFormWindowItems = Generator.getDataFromFile(rootProject, filePath);
                            ConfigInfo configInfo = new ConfigInfo(mainFormWindowItems);
                            Generator.createUmlFile(mainFormWindowItems, configInfo);
                        }
                        break;
                    case DELETE:
                        for (File filePath : files) {
                            if (filePath.exists() && filePath.isFile()) {
                                try {
                                    Files.deleteIfExists(filePath.toPath());
                                    //todo: are you sure?
                                } catch (IOException ex) {
                                    Messages.showMessageDialog(null ,"Chyba pri odstranovani souboru " + filePath.toString(), Messages.getInformationIcon());
                                }
                            }
                        }
                        break;
                    case CANCEL:
                        break;
                }
            }
        });


//        String path = FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString();
//        System.out.println(path);
//        String text = null;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(path));
//            text = reader.readLine();
//            System.out.println(text);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            Gson gson = new Gson();
//            JsonParser parser = new JsonParser();
//            JsonObject object = (JsonObject) parser.parse(text);
//            ConfigInfo configInfo = gson.fromJson(object, ConfigInfo.class);
//            System.out.println(configInfo.toString());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

//        JSONParser parser = new JSONParser();
//        try {
//            JSONObject json = (JSONObject) parser.parse(text);
//
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        }

//        Read more: https://www.java67.com/2016/10/3-ways-to-convert-string-to-json-object-in-java.html#ixzz7Ou64fw8z
//        FormWindow.showFormWindow();
    }

    private List<File> getSubdirs(File file) {
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
}
