package entities;

public class City {
    private int id;
    private Country country;
    private String label;

    public City() {
    }

    public City(int id, Country country, String label) {
        this.id = id;
        this.country = country;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
