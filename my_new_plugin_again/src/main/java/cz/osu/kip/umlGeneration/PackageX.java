package cz.osu.kip.umlGeneration;

import java.util.List;

public class PackageX {
    private String name;
    private List<ClassX> classXES;

    public PackageX(String name, List<ClassX> classXES) {
        this.name = name;
        this.classXES = classXES;
    }

    public String getName() {
        return name;
    }

    public List<ClassX> getClassXES() {
        return classXES;
    }

    public void addClassX(ClassX classX){
        if (!classXES.contains(classX)){
            this.classXES.add(classX);
        }
    }

    public String convertToUmlFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(getName()).append("{\n\n");
        for (ClassX classX : getClassXES()) {
            if (classX != null)
                sb.append(classX.convertToUmlFormatClasses());
        }

        for (ClassX classX : getClassXES()) {
            if (classX != null)
                sb.append(classX.convertToUmlFormatAssociations());
        }

        sb.append("\n}\n\n");
        return sb.toString();
    }
}
