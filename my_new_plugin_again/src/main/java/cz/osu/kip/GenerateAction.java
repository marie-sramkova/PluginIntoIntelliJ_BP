package cz.osu.kip;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GenerateAction extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("Starting");

        try {
            Bublanina bublanina = new Bublanina();
            double d = bublanina.getDynamic();
            System.out.println("Dynamic je " + d);
        } catch (Throwable ex) {
            System.out.println("Failed dynamic. " + ex.getMessage() + " // " + ex.getClass().getSimpleName());
        }

        try {
            double d = Bublanina.getStatic();
            System.out.println("Static je " + d);
        } catch (Throwable ex) {
            System.out.println("Failed static. " + ex.getMessage() + " // " + ex.getClass().getSimpleName());
        }

        System.out.println("... done for the third time.");






        String target = JOptionPane.showInputDialog("Type here the absolute target path:");
        File file = new File(target);

        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            try {
                NewFileClass.makeNewFile(target);
            }catch (Exception ex) {
                throw new RuntimeException(String.format("Cannot make a new file to: '%s'", target));
            }
        } finally {
            Thread.currentThread().setContextClassLoader(current);
        }
    }
}
