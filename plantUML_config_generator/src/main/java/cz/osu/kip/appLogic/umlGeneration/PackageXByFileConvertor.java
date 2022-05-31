package cz.osu.kip.appLogic.umlGeneration;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageXByFileConvertor {

    public static PackageX getPackageXWithClassesFromLines(List<String> lines) {
        List<ClassX> classXES = new ArrayList<>();
        top_iteration:
        for (int i = 1; i < lines.size(); i++) {
            if ((lines.get(i).trim().startsWith("interface ") || lines.get(i).trim().startsWith("public interface ")
                    || lines.get(i).trim().startsWith("class ") || lines.get(i).trim().startsWith("public class ") )
                    && !lines.get(i).contains(" abstract ")) {
                ClassX newClass = getClassX(lines, i);
                classXES.add(newClass);
            }
        }
        String nameOfPackage = lines.get(0).substring(8, lines.get(0).length() - 1);
        PackageX packageX = new PackageX(nameOfPackage, classXES);
        return packageX;
    }

    private static ClassX getClassX(List<String> lines, int i) {
        List<String> linesOfOneClass = new ArrayList<>();
        ClassX classX = null;
        linesOfOneClass.add(lines.get(i));
        int countOfNestingOfParenthesis = 1;
        int countOfNestingOfInnerClasses = 0;
        List<ClassX> innerClassesX = new ArrayList<>();
        boolean stillComment = false;
        int x = 0;
        classX = getClassXFromLines(lines, i, linesOfOneClass, classX, countOfNestingOfParenthesis, countOfNestingOfInnerClasses, innerClassesX, stillComment);

        if (!innerClassesX.isEmpty()) {
            classX.addInnerClassesX(innerClassesX);
        }
        return classX;
    }

    @Nullable
    private static ClassX getClassXFromLines(List<String> lines, int i, List<String> linesOfOneClass, ClassX classX, int countOfNestingOfParenthesis, int countOfNestingOfInnerClasses, List<ClassX> innerClassesX, boolean stillComment) {
        bottom_iteration:
        for (int j = i + 1; j < lines.size(); j++) {
            String line = lines.get(j);
            //region solving comments
            if (line.trim().startsWith("//")) {
                continue bottom_iteration;
            }
            if (stillComment == true) {
                if (line.contains("*/")
                        && (getCountOfFoundStringInStringNotInQuotation(line.substring(0, line.indexOf("*/")), "\"") % 2) == 0
                        && (getCountOfFoundStringInStringNotInQuotation(line.substring(0, line.indexOf("*/")), "'") % 2) == 0) {
                    stillComment = false;
                    int endIndex = line.indexOf("*/");
                    line = line.substring(endIndex + 2);
                    if (line.isBlank()) {
                        continue bottom_iteration;
                    }
                } else {
                    continue bottom_iteration;
                }
            }
            while (line.contains("/*")
                    && (getCountOfFoundStringInStringNotInQuotation(line.substring(0, line.indexOf("/*")), "\"") % 2) == 0
                    && (getCountOfFoundStringInStringNotInQuotation(line.substring(0, line.indexOf("/*")), "'") % 2) == 0) {
                int startIndex = line.indexOf("/*");
                if (line.contains("*/")
                        && (getCountOfFoundStringInStringNotInQuotation(line.substring(0, line.indexOf("*/")), "\"") % 2) == 0
                        && (getCountOfFoundStringInStringNotInQuotation(line.substring(0, line.indexOf("*/")), "'") % 2) == 0) {
                    StringBuilder sb = new StringBuilder();
                    int endIndex = line.indexOf("*/") + 2;
                    line = sb.append(line.substring(0, startIndex)).append(line.substring(endIndex)).toString();
                } else {
                    line = line.substring(0, startIndex);
                    stillComment = true;
                }
                if (line.isBlank()) {
                    continue bottom_iteration;
                }
            }
            //endregion
            if (line.trim().startsWith("interface ") || line.trim().startsWith("public interface ") || line.trim().startsWith("class ") || lines.get(j).trim().startsWith("public class ")) {
                countOfNestingOfInnerClasses = countOfNestingOfInnerClasses + 1;
                innerClassesX.add(getClassX(lines, j));
            }
            //region controlCountOfNestingOfInnerClasses
            if (countOfNestingOfInnerClasses > 0) {
                countOfNestingOfInnerClasses = processNewParenthesis(countOfNestingOfParenthesis, line);
                if (countOfNestingOfInnerClasses > innerClassesX.size()) {
                    continue bottom_iteration;
                } else {
                    countOfNestingOfInnerClasses = 0;
                }
            }
            //endregion
            countOfNestingOfParenthesis = processNewParenthesis(countOfNestingOfParenthesis, line);
            if (countOfNestingOfParenthesis <= 0) {
                classX = addClassX(linesOfOneClass);
                break bottom_iteration;
            }
            linesOfOneClass.add(line);
        }
        return classX;
    }

    private static int processNewParenthesis(int countOfNestingOfParenthesis, String line) {
        if (line.contains("{")) {
            int countOfNewOpeningParenthesis = getCountOfFoundStringInStringNotInQuotation(line, "{");
            countOfNestingOfParenthesis = countOfNestingOfParenthesis + countOfNewOpeningParenthesis;
        }
        if (line.contains("}")) {
            int countOfNewClosingParenthesis = getCountOfFoundStringInStringNotInQuotation(line, "}");
            countOfNestingOfParenthesis = countOfNestingOfParenthesis - countOfNewClosingParenthesis;
        }
        return countOfNestingOfParenthesis;
    }

    private static int getCountOfFoundStringInStringNotInQuotation(String line, String foundString) {
        int countNotInQuotation = 0;
        if (line.contains(foundString)) {
            int countOfFoundString = StringUtils.countMatches(line, foundString);
            if (line.contains("\"") || line.contains("'")) {
                String tmp;
                int endIndex = line.indexOf(foundString);
                for (int i = 0; i < countOfFoundString; i++) {
                    tmp = line.substring(0, endIndex);
                    int countOfDoubleQuotationMarks = StringUtils.countMatches(tmp, "\"");
                    int countOfSimpleQuotationMarks = StringUtils.countMatches(tmp, "'");
                    if (countOfDoubleQuotationMarks % 2 == 0 && countOfSimpleQuotationMarks % 2 == 0) {
                        countNotInQuotation = countNotInQuotation + 1;
                    }
                    endIndex = line.substring(endIndex + 1).indexOf(foundString);
                }
            } else {
                return countOfFoundString;
            }
        }
        return countNotInQuotation;
    }

    private static ClassX addClassX(List<String> lines) {
        String type = getClassType(lines.get(0));
        boolean isPublic = getIfClassIsPublic(lines.get(0));
        if (type != null) {
            String name = getNameOfClass(lines.get(0));
            String tmp = lines.get(0).trim().substring((type.length() + name.length()) + 1).trim();
            if (isPublic && lines.get(0).contains("public")) {
                tmp = tmp.substring(7).trim();
            }
            boolean extendsStatus = false, implementsStatus = false;
            List<String> extendedClassesX = null;
            List<String> implementedInterfaces = null;
            if (!tmp.startsWith("{")) {
                if (tmp.contains("extends")
                        && getCountOfFoundStringInStringNotInQuotation(tmp, "extends") > 0) {
                    extendsStatus = true;
                    extendedClassesX = getExtendedClassXes(tmp);
                }
                if (type.equals("class")) {
                    if (tmp.contains("implements")
                            && getCountOfFoundStringInStringNotInQuotation(tmp, "implements") > 0) {
                        implementsStatus = true;
                        implementedInterfaces = getImplementedInterface(tmp);
                    }
                }
            }
            List<String> others = lines;
            others.remove(0);

            List<AttributeX> attributeXES = new ArrayList<>();
            List<MethodX> methodXES = new ArrayList<>();
            if (type.equals("interface")) {
                for (int i = 0; i < others.size(); i++) {
                    if (others.get(i).contains("=")) {
                        attributeXES.add(getAttributeFromLine(others.get(i)));
                    } else if (others.get(i).isBlank()) {
                        continue;
                    } else {
                        MethodX methodX = getMethodsFromList(others.get(i));
                        if (methodX != null){
                            methodXES.add(methodX);
                        }
                    }
                }
            } else if (type.equals("class")) {
                int skip = 0;
                for (int i = 0; i < others.size(); i++) {
                    if (skip > 0) {
                        skip = processNewParenthesis(skip, others.get(i));
                        continue;
                    }
                    if (others.get(i).isBlank()) {
                        continue;
                    }
                    String trimmedLine = others.get(i).trim();
                    if (trimmedLine.startsWith("}") || trimmedLine.startsWith("@")) {
                        continue;
                    } else if (others.get(i).contains("{")) {
                        MethodX methodX = getMethodsFromList(others.get(i));
                        if (methodX != null){
                            methodXES.add(methodX);
                        }
                        skip = skip + 1;
                    } else {
                        try {
                            AttributeX attributeX = getAttributeFromLine(others.get(i));
                            attributeXES.add(attributeX);
                        } catch (Exception ex) {
                            //not an attribute
                        }
                    }
                }
            }
            ClassX classX = new ClassX(name, type, isPublic, extendsStatus, extendedClassesX, implementsStatus, implementedInterfaces, attributeXES, methodXES);
            return classX;
        } else return null;
    }

    private static boolean getIfClassIsPublic(String line) {
        if (line.trim().startsWith("interface") || line.trim().startsWith("class")) {
            return false;
        } else if(line.trim().startsWith("public interface") || line.trim().startsWith("public class")) {
            return true;
        } else return false;
    }

    private static AttributeX getAttributeFromLine(String line) {
        String tmp = line;
        tmp = tmp.trim();
        String status = getStatus(tmp);
        if (tmp.startsWith(status)) {
            tmp = tmp.substring(status.length());
        }
        boolean isStatic = false;
        if (line.contains(" static ")
                && getCountOfFoundStringInStringNotInQuotation(tmp, "static") > 0) {
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
        int lastIndex = line.length();
        if (line.matches(".*[^A-Za-z0-9].*")) {

            String patternStr = "[^A-Za-z0-9]";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                lastIndex = matcher.start();
            }
        }
        String name = line.substring(0, lastIndex);
        return name;
    }

    private static String getAttributeType(String line) {
        line = line.trim();
        int lastIndex = (line.indexOf(" "));
        String type = line.substring(0, lastIndex);
        return type;
    }

    private static String getClassType(String line) {
        if (line.trim().startsWith("interface") || line.trim().startsWith("public interface"))
            return "interface";
        else if (line.trim().startsWith("class") || line.trim().startsWith("public class"))
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
        implementedInterface = tmp.trim();
        implementedInterfaces.add(implementedInterface);

        return implementedInterfaces;
    }

    private static List<String> getExtendedClassXes(String tmp) {
        int startIndex = tmp.lastIndexOf("extends");
        String extendedClass = tmp.substring(startIndex + 8);
        int lastIndex = extendedClass.length() - 1;
        if (extendedClass.contains("implements")
                && getCountOfFoundStringInStringNotInQuotation(extendedClass, "implements") > 0) {
            lastIndex = extendedClass.indexOf("implements");
        } else {
            lastIndex = extendedClass.indexOf("{");
        }
        extendedClass = extendedClass.substring(0, lastIndex).trim();
        List<String> extendedClassesX = new ArrayList<>();
        while (extendedClass.contains(",")
                && getCountOfFoundStringInStringNotInQuotation(extendedClass, ",") > 0) {
            lastIndex = extendedClass.indexOf(",");
            String extendedClassx = extendedClass.substring(0, lastIndex);
            extendedClassesX.add(extendedClassx);
            extendedClass = extendedClass.substring(lastIndex + 2);
        }
        extendedClass = extendedClass.trim();
        extendedClassesX.add(extendedClass);

        return extendedClassesX;
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
        if (line.startsWith("public")) {
            line = line.substring(7);
        }
        line = line.trim();
        String name;
        if (line.startsWith("interface")) {
            int startIndex = line.lastIndexOf("interface");
            name = line.substring(startIndex + 10);
        } else {
            int startIndex = line.lastIndexOf("class");
            name = line.substring(startIndex + 6);
        }
        int lastIndex = (!name.contains(" ")) ? name.indexOf("{") : name.indexOf(" ");
        if (lastIndex == -1) {
            return "";
        }
        name = name.substring(0, lastIndex);

        return name;
    }

    private static MethodX getMethodsFromList(String line) {
        String status = null;
        boolean isStatic = false;
        String returningType = null;
        String name = null;
        List<InputParameterX> inputParameterXES = null;
        try {
            String tmp = line.trim();
            status = getStatus(tmp);
            if (line.contains(status)) {
                tmp = tmp.substring(status.length()).trim();
            }
            isStatic = false;
            if (line.contains(" static ")
                    && getCountOfFoundStringInStringNotInQuotation(tmp, "static") > 0
            ) {
                isStatic = true;
                tmp = tmp.substring(7);
            }
            returningType = getReturningType(tmp);
            tmp = tmp.substring(returningType.length()).trim();
            name = "";
            if (!tmp.startsWith("(")) {
                name = getNameOfMethod(tmp);
                tmp = tmp.substring(name.length());
            }
            tmp = tmp.substring(1).trim();
            inputParameterXES = null;
            if (!tmp.startsWith(")")) {
                inputParameterXES = getInputParameters(tmp);
            }
        } catch (Exception e) {
            return null;
        }
        if (name.equals("") && !returningType.isEmpty()) {
            MethodX methodX = new MethodX(status, name, returningType, inputParameterXES, isStatic);
            return methodX;
        }
        MethodX methodX = new MethodX(status, returningType, name, inputParameterXES, isStatic);

        return methodX;
    }

    private static List<InputParameterX> getInputParameters(String line) {
        List<InputParameterX> inputParameterXES;

        if (line.contains(",")) {
            inputParameterXES = getListOfMutlipleInputParameters(line);
        } else {
            inputParameterXES = getListWithOneInputParameter(line);
        }
        return inputParameterXES;
    }

    private static List<InputParameterX> getListWithOneInputParameter(String line) {
        List<InputParameterX> inputParameterXES = new ArrayList<>();
        String inputParameterName;
        String inputParameterType;
        int lastIndex = (line.indexOf(" "));
        inputParameterType = line.substring(0, lastIndex);
        if (line.contains(")")){
            lastIndex = line.indexOf(")");
        }
        inputParameterName = line.substring(inputParameterType.length() + 1, lastIndex);

        InputParameterX inputParameterX = new InputParameterX(inputParameterType, inputParameterName);
        inputParameterXES.add(inputParameterX);
        return inputParameterXES;
    }

    private static List<InputParameterX> getListOfMutlipleInputParameters(String line) {
        List<InputParameterX> inputParameterXES = new ArrayList<>();
        String inputParameterName;
        String inputParameterType;
        String[] arrayOfParameters;
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
        if (line.substring(0, lastIndex).contains("(")) {
            lastIndex = line.indexOf("(");
        }
        String returningType = line.substring(0, lastIndex);
        return returningType;
    }

    private static String getStatus(String line) {
        int lastIndex = (line.indexOf(" "));
        String status = line.substring(0, lastIndex);
        if (!(status.equals("private") || status.equals("protected") || status.equals("public"))) {
            status = "internal";
        }
        return status;
    }
}
