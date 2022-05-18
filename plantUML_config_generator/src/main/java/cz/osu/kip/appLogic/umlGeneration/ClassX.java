package cz.osu.kip.appLogic.umlGeneration;

import java.util.List;

public class ClassX {
    private String name;
    private String type;
    private boolean isPublic;
    private boolean extendStatus;
    private List<String> extendedClassesX;
    private boolean implementStatus;
    private List<String> implementedInterface;
    private List<AttributeX> attributeXES;
    private List<MethodX> methodXES;
    private List<ClassX> innerClassesX;

    public ClassX(String name, String type, boolean isPublic, boolean extendStatus, List<String> extendedClassesX, boolean implementStatus, List<String> implementedInterface, List<AttributeX> attributeXES, List<MethodX> methodXES) {
        this.name = name;
        this.type = type;
        this.isPublic = isPublic;
        this.extendStatus = extendStatus;
        this.extendedClassesX = extendedClassesX;
        this.implementStatus = implementStatus;
        this.implementedInterface = implementedInterface;
        this.attributeXES = attributeXES;
        this.methodXES = methodXES;
    }

    public void addInnerClassesX(List<ClassX> innerClassesX) {
        this.innerClassesX = innerClassesX;
    }

    public List<ClassX> getInnerClassesX() {
        return innerClassesX;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isExtendStatus() {
        return extendStatus;
    }

    public List<String> getExtendedClassesX() {
        return extendedClassesX;
    }

    public boolean isImplementStatus() {
        return implementStatus;
    }

    public List<String> getImplementedInterface() {
        return implementedInterface;
    }

    public List<AttributeX> getAttributes() {
        return attributeXES;
    }

    public List<MethodX> getMethods() {
        return methodXES;
    }
}
