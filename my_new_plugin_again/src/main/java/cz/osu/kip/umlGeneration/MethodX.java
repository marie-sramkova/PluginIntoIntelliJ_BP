package cz.osu.kip.umlGeneration;

import java.util.List;

public class MethodX {
    private String status;
    private String returningType;
    private String name;
    private List<InputParameterX> inputParameterXES;
    private boolean staticStatus;

    public MethodX(String status, String returningType, String name, List<InputParameterX> inputParameterXES, boolean isStatic) {
        this.status = status;
        this.returningType = returningType;
        this.name = name;
        this.inputParameterXES = inputParameterXES;
        this.staticStatus = isStatic;
    }

    public String getStatus() {
        return status;
    }

    public String getReturningType() {
        return returningType;
    }

    public String getName() {
        return name;
    }

    public List<InputParameterX> getInputParameters() {
        return inputParameterXES;
    }

    public boolean isStaticStatus() {
        return staticStatus;
    }

    public String convertToUmlFormat() {
        StringBuilder sb = new StringBuilder();
        if (getStatus().equals("private")) {
            sb.append("- ");
        } else if (getStatus().equals("public")) {
            sb.append("+ ");
        } else if (getStatus().equals("protected")) {
            sb.append("# ");
        }
        if (isStaticStatus()) {
            sb.append("<<static>> ");
        }
        sb.append(getName()).append("(");
        if (getInputParameters() != null) {
            for (int i = 0; i < getInputParameters().size(); i++) {
                sb.append(inputParameterXES.get(i).convertToUmlFormat());
                if (i < getInputParameters().size() - 1) {
                    sb.append(", ");
                }
            }
        }
        if (!getReturningType().isEmpty()){
            sb.append(") : ").append(getReturningType()).append("\n");
        }else{
            sb.append(")").append("\n");
        }
        return sb.toString();
    }
}
