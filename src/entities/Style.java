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

    public int getIdStyle() {
        return id;
    }

    public void setIdStyle(int idStyle) {
        this.id = idStyle;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
