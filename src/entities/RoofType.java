package entities;

public class RoofType {

    private int id;
    private String label;

    public RoofType() {
    }

    public RoofType(int idRoofT, String label) {
        this.id = idRoofT;
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
