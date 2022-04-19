package cz.osu.kip.umlGeneration;

import java.util.List;

public class ClassX {
    private String name;
    private String type;
    private boolean isPublic;
    private boolean extendStatus;
    private String extendedClass;
    private boolean implementStatus;
    private List<String> implementedInterface;
    private List<AttributeX> attributeXES;
    private List<MethodX> methodXES;

    public ClassX(String name, String type, boolean isPublic, boolean extendStatus, String extendedClass, boolean implementStatus, List<String> implementedInterface, List<AttributeX> attributeXES, List<MethodX> methodXES) {
        this.name = name;
        this.type = type;
        this.isPublic = isPublic;
        this.extendStatus = extendStatus;
        this.extendedClass = extendedClass;
        this.implementStatus = implementStatus;
        this.implementedInterface = implementedInterface;
        this.attributeXES = attributeXES;
        this.methodXES = methodXES;
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

    public String getExtendedClass() {
        return extendedClass;
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

    public String convertToUmlFormatClasses() {
        StringBuilder sb = new StringBuilder();
        sb.append(getType()).append(" ").append(getName()).append("{\n");
        if (getAttributes().size() != 0) {
            for (AttributeX attributeX : getAttributes()) {
                sb.append(attributeX.convertToUmlFormat());
            }
        }
        if (getAttributes().size() != 0 && getMethods().size() != 0) {
            sb.append("\n");
        }
        if (getMethods().size() != 0) {
            for (MethodX methodX : getMethods()) {
                sb.append(methodX.convertToUmlFormat());
            }
        }
        sb.append("}\n\n");
        return sb.toString();
    }

    public String convertToUmlFormatAssociations() {
        StringBuilder sb = new StringBuilder();
        if (isExtendStatus()) {
            if (getExtendedClass() != null) {
                sb.append(getName()).append(" --|> ").append(getExtendedClass()).append("\n");
            }
        }
        if (isImplementStatus()) {
            if (getImplementedInterface().size() != 0) {
                for (String implementedInterface : getImplementedInterface()) {
                    sb.append(getName()).append(" ..|> ").append(implementedInterface).append("\n");
                }
            }
        }
        return sb.toString();
    }

}
