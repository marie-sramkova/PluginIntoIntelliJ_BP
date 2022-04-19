package cz.osu.kip.umlGeneration;

import cz.osu.kip.ConfigInfo;

public class UmlFilter {
    private static ConfigInfo configInfo;

    public static ConfigInfo getConfigInfo() {
        return configInfo;
    }

    public static String getTextByConfigInfo(ConfigInfo configInfo, PackageX packageX) {
        UmlFilter.configInfo = configInfo;
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageX.getName()).append("{\n\n");

        if (getConfigInfo().isClasses() == true) {
            for (ClassX classX : packageX.getClassXES()) {
                if (classX != null && classX.getType().equals("class")) {
                    String classXText = convertClassToUml(classX);
                    sb.append(classXText);
                    sb.append(classX.convertToUmlFormatAssociations());
                }
            }
        }

        if (getConfigInfo().isInterfaces() == true) {
            for (ClassX classX : packageX.getClassXES()) {
                if (classX != null && classX.getType().equals("interface")) {
                    String classXText = convertInterfaceToUml(classX);
                    sb.append(classXText);
                    sb.append(classX.convertToUmlFormatAssociations());
                }
            }
        }


        sb.append("\n}\n\n");
        return sb.toString();
    }

    private static String convertClassToUml(ClassX classX) {
        StringBuilder sb = new StringBuilder();
        sb.append(classX.getType()).append(" ").append(classX.getName()).append("{\n");
        if (getConfigInfo().isAttributesForClasses() == true) {
            if (classX.getAttributes().size() != 0) {
                for (AttributeX attributeX : classX.getAttributes()) {
                    if (getConfigInfo().isPrivateAttributesForClasses() == true) {
                        if (attributeX.getStatus().equals("private")) {
                            sb.append("- ");
                            if (attributeX.isStatic()) {
                                sb.append("<<static>> ");
                            }
                            sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                        }
                    }
                    if (getConfigInfo().isPublicAttributesForClasses() == true) {
                        if (attributeX.getStatus().equals("public")) {
                            sb.append("+ ");
                            if (attributeX.isStatic()) {
                                sb.append("<<static>> ");
                            }
                            sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                        }
                    }
                    if (getConfigInfo().isProtectedAttributesForClasses() == true) {
                        if (attributeX.getStatus().equals("protected")) {
                            sb.append("# ");
                            if (attributeX.isStatic()) {
                                sb.append("<<static>> ");
                            }
                            sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                        }
                    }
                    if (getConfigInfo().isInternalAttributesForClasses() == true) {
                        if (attributeX.getStatus().equals("internal")) {
                            sb.append("~ ");
                            if (attributeX.isStatic()) {
                                sb.append("<<static>> ");
                            }
                            sb.append(attributeX.getName()).append(" : ").append(attributeX.getType()).append("\n");
                        }
                    }
                }
            }
        }
        if (getConfigInfo().isAttributesForClasses() && getConfigInfo().isMethodsForClasses() && classX.getAttributes().size() != 0 && classX.getMethods().size() != 0) {
            sb.append("\n");
        }
        if (classX.getMethods().size() != 0) {
            for (MethodX methodX : classX.getMethods()) {
                sb.append(methodX.convertToUmlFormat());
            }
        }
        sb.append("}\n\n");
        return sb.toString();
    }

    private static String convertInterfaceToUml(ClassX classX) {
        StringBuilder sb = new StringBuilder();
        sb.append(classX.getType()).append(" ").append(classX.getName()).append("{\n");
        if (classX.getAttributes().size() != 0) {
            for (AttributeX attributeX : classX.getAttributes()) {
                sb.append(attributeX.convertToUmlFormat());
            }
        }
        if (classX.getAttributes().size() != 0 && classX.getMethods().size() != 0) {
            sb.append("\n");
        }
        if (classX.getMethods().size() != 0) {
            for (MethodX methodX : classX.getMethods()) {
                sb.append(methodX.convertToUmlFormat());
            }
        }
        sb.append("}\n\n");
        return sb.toString();
    }
}
