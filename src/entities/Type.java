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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
