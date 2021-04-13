package entities;

public class Role {
    private int id;
    private String label;

    public Role() {
    }

    public Role(int idRole, String label) {
        this.id = idRole;
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
