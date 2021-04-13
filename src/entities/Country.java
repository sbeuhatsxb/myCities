package entities;

public class Country {

    private int id;
    private String label;

    public Country() {
    }

    public Country(int idCountry, String label) {
        this.id = idCountry;
        this.label = label;
    }

    public int getIdCountry() {
        return id;
    }

    public void setIdCountry(int idCountry) {
        this.id = idCountry;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
