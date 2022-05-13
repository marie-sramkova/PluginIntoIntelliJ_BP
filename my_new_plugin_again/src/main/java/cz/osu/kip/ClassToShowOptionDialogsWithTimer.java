package cz.osu.kip;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassToShowOptionDialogsWithTimer {

    public static void showOptionDialogWithTimer(String text, int seconds){
        JLabel messageLabel = new JLabel("<html><body><p style='width: 300px;'>"+text+"</p></body></html>");
        Timer timer = new Timer((seconds * 1000),
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        SwingUtilities.getWindowAncestor(messageLabel).dispose();
                    }
                });
        timer.setRepeats(false);
        timer.start();
        JOptionPane.showOptionDialog(null, messageLabel, "Notification", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
    }
}
