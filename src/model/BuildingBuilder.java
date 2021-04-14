package model;

public class BuildingBuilder {

    private String name;
    private int windows;
    private int year;
    private int city;
    private int frame;
    private int material;
    private int type;
    private int roofType;
    private int style;
    private String description;
    private String image;
    private int architect;

    public BuildingBuilder(String name, int windows, int year, int city, int frame, int material, int type, int roofType, int style, String description, String image, int architect) {
        this.name = name;
        this.windows = windows;
        this.year = year;
        this.city = city;
        this.frame = frame;
        this.material = material;
        this.type = type;
        this.roofType = roofType;
        this.style = style;
        this.description = description;
        this.image = image;
        this.architect = architect;
    }

    public String getName() {
        return name;
    }

    public int getWindows() {
        return windows;
    }

    public int getYear() {
        return year;
    }

    public int getCity() {
        return city;
    }

    public int getFrame() {
        return frame;
    }

    public int getMaterial() {
        return material;
    }

    public int getType() {
        return type;
    }

    public int getRoofType() {
        return roofType;
    }

    public int getStyle() {
        return style;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getArchitect() {
        return architect;
    }
}
