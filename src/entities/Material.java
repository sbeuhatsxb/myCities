package entities;

public class Material {
    private int id;
    private String label;

    public Material() {
    }

    public Material(int idMaterial, String label) {
        this.id = idMaterial;
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
