package cz.osu.kip;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import cz.osu.kip.ConfigInfo;
import cz.osu.kip.form.FormWindow;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class TopMenuAction extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("old generating");

        String path = FormWindow.getFilePath().toPath().resolve("PlantUmlFiles").toFile().toString();
        System.out.println(path);
        String text = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            text = reader.readLine();
            System.out.println(text);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(text);
            ConfigInfo configInfo = gson.fromJson(object, ConfigInfo.class);
            System.out.println(configInfo.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
}
