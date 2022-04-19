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
}
