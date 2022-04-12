package cz.osu.kip.umlGeneration;

class AttributeX {
    private String status;
    private String type;
    private String name;
    private boolean staticStatus;

    public AttributeX(String status, String type, String name, boolean isStatic) {
        this.status = status;
        this.type = type;
        this.name = name;
        this.staticStatus = isStatic;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return staticStatus;
    }

    public String convertToUmlFormat() {
        StringBuilder sb = new StringBuilder();
        if (this.getStatus().equals("private")) {
            sb.append("- ");
        } else if (this.getStatus().equals("public")) {
            sb.append("+ ");
        } else if (this.getStatus().equals("protected")) {
            sb.append("# ");
        }
        if (isStatic()) {
            sb.append("<<static>> ");
        }
        sb.append(getName()).append(" : ").append(getType()).append("\n");
        return sb.toString();
    }
}
