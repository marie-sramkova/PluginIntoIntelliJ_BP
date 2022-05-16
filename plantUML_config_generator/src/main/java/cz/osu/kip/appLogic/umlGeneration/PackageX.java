package cz.osu.kip.appLogic.umlGeneration;

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
}
