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

        FormWindow.showFormWindow();
    }
}
