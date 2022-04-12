package cz.osu.kip.umlGeneration;

public class InputParameterX {
    private String type;
    private String name;

    public InputParameterX(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String convertToUmlFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append(" : ").append(getType());
        return sb.toString();
    }
}
