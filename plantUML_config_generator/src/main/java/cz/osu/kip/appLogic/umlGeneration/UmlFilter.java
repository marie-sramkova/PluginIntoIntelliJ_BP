package cz.osu.kip.appLogic.umlGeneration;

import cz.osu.kip.appLogic.ConfigInfo;

public class UmlFilter {
    private static ConfigInfo configInfo;

    public static ConfigInfo getConfigInfo() {
        return configInfo;
    }

    public static String getTextByConfigInfo(ConfigInfo configInfo, PackageX packageX) {
        UmlFilter.configInfo = configInfo;
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageX.getName()).append("{\n\n");

        processClassesX(packageX, sb);
        processInterfaces(packageX, sb);

        return sb.toString();
    }

    private static void processInterfaces(PackageX packageX, StringBuilder sb) {
        if (getConfigInfo().isInterfaces()) {
            for (ClassX classX : packageX.getClassXES()) {
                if (classX != null && classX.getType().equals("interface")) {
                    String classXText = convertClassToUml(classX);
                    sb.append(classXText);
                    sb.append(convertToUmlFormatAssociations(classX));
                }
            }
        }
    }

    private static void processClassesX(PackageX packageX, StringBuilder sb) {
        if (getConfigInfo().isClasses()) {
            for (ClassX classX : packageX.getClassXES()) {
                if (classX != null && classX.getType().equals("class")) {
                    if ((getConfigInfo().isDefaultClasses() && !classX.isPublic()) || (getConfigInfo().isPublicClasses() && classX.isPublic())) {
                        String classXText = convertClassToUml(classX);
                        sb.append(classXText);
                        if (getConfigInfo().isInnerClasses()) {
                            if (classX.getInnerClassesX() != null && classX.getInnerClassesX().size() > 0) {
                                for (ClassX innerClassX : classX.getInnerClassesX()) {
                                    String innerClassXText = convertClassToUml(innerClassX);
                                    sb.append(innerClassXText);
                                }
                                sb.append(convertToUmlFormatNestedClassesXAssociations(classX));
                            }
                        }
                        sb.append(convertToUmlFormatAssociations(classX));
                    }
                }
            }
        }
    }

    private static String convertClassToUml(ClassX classX) {
        StringBuilder sb = new StringBuilder();
        sb.append(classX.getType()).append(" ").append(classX.getName()).append("{\n");
        convertAttributesForClassX(classX, sb);
        if (getConfigInfo().isAttributesForClasses() && getConfigInfo().isMethodsForClasses() && classX.getAttributes().size() != 0 && classX.getMethods().size() != 0) {
            sb.append("\n");
        }
        convertMethodsForClassX(classX, sb);

        sb.append("}\n\n");
        return sb.toString();
    }

    private static void convertAttributesForClassX(ClassX classX, StringBuilder sb) {
        if (getConfigInfo().isAttributesForClasses() && classX.getAttributes().size() != 0) {
            for (AttributeX attributeX : classX.getAttributes()) {
                if (getConfigInfo().isPrivateAttributesForClasses() && attributeX.getStatus().equals("private")) {
                    sb.append("- ");
                    if (attributeX.isStatic()) {
                        sb.append("<<static>> ");
                    }
                    sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                }
                if (getConfigInfo().isPublicAttributesForClasses() && attributeX.getStatus().equals("public")) {
                    sb.append("+ ");
                    if (attributeX.isStatic()) {
                        sb.append("<<static>> ");
                    }
                    sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                }
                if (getConfigInfo().isProtectedAttributesForClasses() && attributeX.getStatus().equals("protected")) {
                    sb.append("# ");
                    if (attributeX.isStatic()) {
                        sb.append("<<static>> ");
                    }
                    sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                }
                if (getConfigInfo().isInternalAttributesForClasses() && attributeX.getStatus().equals("internal")) {
                    sb.append("~ ");
                    if (attributeX.isStatic()) {
                        sb.append("<<static>> ");
                    }
                    sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                }
            }
        }
    }

    private static void convertMethodsForClassX(ClassX classX, StringBuilder sb) {
        if (getConfigInfo().isMethodsForClasses() && classX.getMethods().size() != 0) {
            for (MethodX methodX : classX.getMethods()) {
                if (getConfigInfo().isPrivateMethodsForClasses() && methodX.getStatus().equals("private")) {
                    sb.append("- ");
                    if (methodX.isStaticStatus()) {
                        sb.append("<<static>> ");
                    }
                    printMethodInfoToUml(sb, methodX);
                }
                if (getConfigInfo().isPublicMethodsForClasses() && methodX.getStatus().equals("public")) {
                    sb.append("+ ");
                    if (methodX.isStaticStatus()) {
                        sb.append("<<static>> ");
                    }
                    printMethodInfoToUml(sb, methodX);
                }
                if (getConfigInfo().isProtectedMethodsForClasses() && methodX.getStatus().equals("protected")) {
                    sb.append("# ");
                    if (methodX.isStaticStatus()) {
                        sb.append("<<static>> ");
                    }
                    printMethodInfoToUml(sb, methodX);
                }
                if (getConfigInfo().isInternalMethodsForClasses() && methodX.getStatus().equals("internal")) {
                    sb.append("~ ");
                    if (methodX.isStaticStatus()) {
                        sb.append("<<static>> ");
                    }
                    printMethodInfoToUml(sb, methodX);
                }
            }
        }
    }

    private static void printMethodInfoToUml(StringBuilder sb, MethodX methodX) {
        sb.append(methodX.getName()).append("(");
        if (methodX.getInputParameters() != null) {
            for (int i = 0; i < methodX.getInputParameters().size(); i++) {
                sb.append(convertInputParametersToUmlFormat(methodX.getInputParameters().get(i)));
                if (i < methodX.getInputParameters().size() - 1) {
                    sb.append(", ");
                }
            }
        }
        if (!methodX.getReturningType().isEmpty()) {
            sb.append(") : ").append(methodX.getReturningType()).append("\n");
        } else {
            sb.append(")").append("\n");
        }
    }

    public static String convertToUmlFormatAssociations(ClassX classX) {
        StringBuilder sb = new StringBuilder();
        if (classX.isExtendStatus()) {
            if (classX.getExtendedClassesX() != null && classX.getExtendedClassesX().size() != 0) {
                for (String extendedClassX : classX.getExtendedClassesX()) {
                    sb.append(extendedClassX).append(" <|.. ").append(classX.getName()).append("\n");
                }
            }
        }
        if (classX.isImplementStatus()) {
            if (classX.getImplementedInterface() != null && classX.getImplementedInterface().size() != 0) {
                for (String implementedInterface : classX.getImplementedInterface()) {
                    sb.append(implementedInterface).append(" <|.. ").append(classX.getName()).append("\n");
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    public static String convertToUmlFormatNestedClassesXAssociations(ClassX classX) {
        StringBuilder sb = new StringBuilder();
        if (classX.getInnerClassesX() != null && classX.getInnerClassesX().size() > 0) {
            for (ClassX innerClassX : classX.getInnerClassesX()) {
                sb.append(classX.getName()).append(" --> \"many\" ").append(innerClassX.getName()).append(" : +nested\n\n");
            }
        }
        return sb.toString();
    }

    public static String convertInputParametersToUmlFormat(InputParameterX inputParameterX) {
        StringBuilder sb = new StringBuilder();
        sb.append(inputParameterX.getName()).append(" : ").append(inputParameterX.getType());
        return sb.toString();
    }
}
