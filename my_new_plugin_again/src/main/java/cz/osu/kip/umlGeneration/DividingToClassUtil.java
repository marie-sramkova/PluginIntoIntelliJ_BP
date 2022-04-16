package cz.osu.kip.umlGeneration;

import java.util.ArrayList;
import java.util.List;

public class DividingToClassUtil {

    public static PackageX divideFromLines(List<String> lines) {
        List<ClassX> classXES = new ArrayList<>();
        top_iteration:
        for (int i = 1; i < lines.size(); i++) {
            List<String> linesOfOneClass = new ArrayList<>();
            if (lines.get(i).startsWith("interface") || lines.get(i).startsWith("public interface") || lines.get(i).startsWith("class") || lines.get(i).startsWith("public class")) {
                linesOfOneClass.add(lines.get(i));
                bottom_iteration:
                for (int j = i + 1; j < lines.size(); j++) {
                    linesOfOneClass.add(lines.get(j));
                    if (lines.get(j).trim().startsWith("}")) {
                        ClassX classX = addClassX(linesOfOneClass);
                        classXES.add(classX);
                        break bottom_iteration;
                    }
                }
            }
        }
        String nameOfPackage = lines.get(0).substring(8, lines.get(0).length() - 1);
        PackageX packageX = new PackageX(nameOfPackage, classXES);
        return packageX;
    }

    private static ClassX addClassX(List<String> lines) {
        String type = getClassType(lines.get(0));
        if (type != null) {
            String name = getNameOfClass(lines.get(0));
            String tmp = lines.get(0).substring((type.length() + name.length()) + 2);
            boolean extendsStatus = false, implementsStatus = false;
            String extendedClass = null;
            List<String> implementedInterfaces = null;
            if (tmp.startsWith("{")) {
            } else {
                if (tmp.contains("extends")) {
                    extendsStatus = true;
                    extendedClass = getExtendedClass(tmp);
                }
                if (type.equals("class")) {
                    if (tmp.contains("implements")) {
                        implementsStatus = true;
                        implementedInterfaces = getImplementedInterface(tmp);
                    }
                }
            }
            List<String> others = lines;
            others.remove(0);
            others.remove(lines.size() - 1);

            List<AttributeX> attributeXES = new ArrayList<>();
            List<MethodX> methodXES = new ArrayList<>();
            if (type.equals("interface")) {
                for (int i = 0; i < others.size(); i++) {
                    if (others.get(i).contains("=")) {
                        attributeXES.add(getAttributeFromLine(others.get(i)));
                    } else if (others.get(i).isBlank()) {
                        continue;
                    } else {
                        methodXES.add(getMethodsFromList(others.get(i)));
                    }
                }
            } else if (type.equals("class")) {
                int skip = 0;
                for (int i = 0; i < others.size(); i++) {
                    if (skip > 0){
                        if (others.get(i).contains("{"))
                            skip = skip + 1;
                        if (others.get(i).contains("}"))
                            skip = skip - 1;
                        continue;
                    }
                    if (others.get(i).isBlank()) {
                        continue;
                    }
                    String startingIndex = others.get(i).trim();
                    if (startingIndex.startsWith("}") || startingIndex.startsWith("@")) {
                        continue;
                    } else if (others.get(i).contains("{")) {
                        methodXES.add(getMethodsFromList(others.get(i)));
                        skip = skip + 1;
                    } else {
                        attributeXES.add(getAttributeFromLine(others.get(i)));
                    }
                }
            }

            ClassX classX = new ClassX(name, type, extendsStatus, extendedClass, implementsStatus, implementedInterfaces, attributeXES, methodXES);
            return classX;
        } else return null;
    }

    private static AttributeX getAttributeFromLine(String line) {
        String tmp = line;
        tmp = tmp.trim();
        String status = getStatus(tmp);
        tmp = tmp.substring(status.length());
        boolean isStatic = false;
        if (line.contains(" static ")) {
            isStatic = true;
            tmp = tmp.substring(7);
        }
        String type = getAttributeType(tmp.trim());
        tmp = tmp.substring(type.length() + 1).trim();
        String name = getAttributeName(tmp);

        AttributeX attributeX = new AttributeX(status, type, name, isStatic);
        return attributeX;
    }

    private static String getAttributeName(String line) {
        int lastIndex;
        if (line.contains(" ")) {
            lastIndex = line.indexOf(" ");
        } else {
            lastIndex = line.indexOf(";");
        }
        String name = line.substring(0, lastIndex);
        return name;
    }

    private static String getAttributeType(String line) {
        int lastIndex = (line.indexOf(" "));
        String type = line.substring(0, lastIndex);
        return type;
    }

    private static String getClassType(String line) {
        if (line.startsWith("interface") || line.startsWith("public interface"))
            return "interface";
        else if (line.startsWith("class") || line.startsWith("public class"))
            return "class";
        else return null;
    }

    private static List<String> getImplementedInterface(String tmp) {
        int startIndex = tmp.lastIndexOf("implements");
        int lastIndex = tmp.indexOf("{");
        List<String> implementedInterfaces = new ArrayList<>();
        tmp = tmp.substring(startIndex + 11, lastIndex);
        String implementedInterface;
        while (tmp.contains(",")) {
            lastIndex = tmp.indexOf(",");
            implementedInterface = tmp.substring(0, lastIndex);
            implementedInterfaces.add(implementedInterface);
            tmp = tmp.substring(lastIndex + 2);
        }
        implementedInterface = tmp.substring(0, tmp.length() - 1);
        implementedInterfaces.add(implementedInterface);

        return implementedInterfaces;
    }

    private static String getExtendedClass(String tmp) {
        int startIndex = tmp.lastIndexOf("extends");
        String extendedClass = tmp.substring(startIndex + 8);
        int lastIndex = extendedClass.indexOf(" ");
        String lastChar = extendedClass.substring(0, lastIndex);
        if (lastChar.contains(",")) {
            lastChar = extendedClass.substring(0, lastIndex - 1);
        }
        extendedClass = lastChar;

        return extendedClass;
    }

    private static String getNameOfClass(String line) {
        String name;
        if (line.startsWith("interface")) {
            int startIndex = line.lastIndexOf("interface");
            name = line.substring(startIndex + 10);
        } else {
            int startIndex = line.lastIndexOf("class");
            name = line.substring(startIndex + 6);
        }
        int lastIndex = name.indexOf(" ");
        name = name.substring(0, lastIndex);

        return name;
    }

    private static MethodX getMethodsFromList(String line) {
        String tmp = line.trim();
        String status = getStatus(tmp);
        if (line.contains(status)) {
            tmp = tmp.substring(status.length()).trim();
        }
        boolean isStatic = false;
        if (line.contains(" static ")) {
            isStatic = true;
            tmp = tmp.substring(7);
        }
        String returningType = getReturningType(tmp);
        tmp = tmp.substring(returningType.length()).trim();
        String name = "";
        if (!tmp.startsWith("(")){
            name = getNameOfMethod(tmp);
            tmp = tmp.substring(name.length());
        }
        tmp = tmp.substring(1).trim();
        List<InputParameterX> inputParameterXES = null;
        if (!tmp.startsWith(")")) {
            inputParameterXES = getInputParameters(tmp);
        }
        if (name.equals("") && !returningType.isEmpty()){
            MethodX methodX = new MethodX(status, name, returningType, inputParameterXES, isStatic);
            return methodX;
        }
        MethodX methodX = new MethodX(status, returningType, name, inputParameterXES, isStatic);

        return methodX;
    }

    private static List<InputParameterX> getInputParameters(String line) {
        List<InputParameterX> inputParameterXES = new ArrayList<>();
        int places = line.length() - line.replaceAll(",", "").length();
        String[] arrayOfParameters = new String[places + 1];

        String inputParameterType;
        String inputParameterName;
        if (line.contains(",")) {
            arrayOfParameters = line.split(", ");
            for (int i = 0; i < arrayOfParameters.length; i++) {
                int lastIndex = arrayOfParameters[i].indexOf(" ");
                inputParameterType = arrayOfParameters[i].substring(0, lastIndex);
                if (arrayOfParameters[i].contains(")")) {
                    lastIndex = arrayOfParameters[i].indexOf(")");
                    inputParameterName = arrayOfParameters[i].substring(inputParameterType.length() + 1, lastIndex);
                } else {
                    inputParameterName = arrayOfParameters[i].substring(inputParameterType.length() + 1);
                }
                InputParameterX inputParameterX = new InputParameterX(inputParameterType, inputParameterName);
                inputParameterXES.add(inputParameterX);
            }
        } else {
            int lastIndex = (line.indexOf(" "));
            inputParameterType = line.substring(0, lastIndex);
            lastIndex = line.indexOf(")");
            inputParameterName = line.substring(inputParameterType.length() + 1, lastIndex);

            InputParameterX inputParameterX = new InputParameterX(inputParameterType, inputParameterName);
            inputParameterXES.add(inputParameterX);
        }
        return inputParameterXES;
    }

    private static String getNameOfMethod(String line) {
        int lastIndex = (line.indexOf("("));
        String name = line.substring(0, lastIndex);
        return name;
    }

    private static String getReturningType(String line) {
        int lastIndex;
        lastIndex = (line.indexOf(" "));
        if (line.substring(0, lastIndex).contains("(")){
            lastIndex = line.indexOf("(");
        }
        String returningType = line.substring(0, lastIndex);
        return returningType;
    }

    private static String getStatus(String line) {
        int lastIndex = (line.indexOf(" "));
        String status = line.substring(0, lastIndex);
        if (!(status.equals("private") || status.equals("protected"))) {
            status = "public";
        }
        return status;
    }
}
