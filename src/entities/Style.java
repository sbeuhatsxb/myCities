package entities;

public class Style {

    private int id;
    private String label;

    public Style() {
    }

    public Style(int idStyle, String label) {
        this.id = idStyle;
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
