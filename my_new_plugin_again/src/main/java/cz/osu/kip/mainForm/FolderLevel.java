package cz.osu.kip.mainForm;

import com.intellij.ui.components.JBCheckBox;

import java.io.File;
import java.util.Comparator;

public class FolderLevel/* implements Comparable<FolderLevel>*/{
    private String name;
    private File url;
    private int level;
    private JBCheckBox jCheckBox;

    public FolderLevel(String name, File url, int level) {
        this.name = name;
        this.url = url;
        this.level = level;
        jCheckBox = new JBCheckBox(url.toString().substring(FormWindow.getFilePath().toString().length()));
    }

    public JBCheckBox getjCheckBox() {
        return jCheckBox;
    }

    public void setjCheckBox(JBCheckBox jCheckBox) {
        this.jCheckBox = jCheckBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getUrl() {
        return url;
    }

    public void setUrl(File url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "FolderLevel{" +
                "name='" + name + '\'' +
                ", url=" + url +
                ", level=" + level +
                '}';
    }

//    @Override
//    public int compareTo(@NotNull FolderLevel o) {
//        int i = Integer.compare(this.level, o.level);
//        if (i != 0) return i;
//
//        return url.compareTo(o.url);
//    }
}


class FolderLevelComparator implements Comparator<FolderLevel>
{
    @Override
    public int compare(FolderLevel fl1, FolderLevel fl2) {
        return fl1.getUrl().compareTo(fl2.getUrl());
    }
}