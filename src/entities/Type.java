package entities;

public class Type {
    private int id;
    private String label;

    public Type() {
    }

    public Type(String label, int idType) {
        this.id = idType;
        this.label = label;
    }

    public int getIdType() {
        return id;
    }

    public void setIdType(int idType) {
        this.id = idType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
