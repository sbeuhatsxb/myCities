package entities;

public class Building {

    private int id, windows, year;
    private int idArch, idCity, idFrame, idMat, idRoofT, idStyle;
    private String image;

    public Building() {
    }

    public Building(int idArch, int idBuilding, int idCity, int idFrame, int idMat, int idRoofT, int idStyle, int windows, int year, String image) {
        this.idArch = idArch;
        this.id = idBuilding;
        this.idCity = idCity;
        this.idFrame = idFrame;
        this.idMat = idMat;
        this.idRoofT = idRoofT;
        this.idStyle = idStyle;
        this.windows = windows;
        this.year = year;
        this.image = image;
    }
    public int getIdBuilding() {
        return id;
    }

    public void setIdBuilding(int idBuilding) {
        this.id = idBuilding;
    }

    public int getWindows() {
        return windows;
    }

    public void setWindows(int windows) {
        this.windows = windows;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIdArch() {
        return idArch;
    }

    public void setIdArch(int idArch) {
        this.idArch = idArch;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getIdFrame() {
        return idFrame;
    }

    public void setIdFrame(int idFrame) {
        this.idFrame = idFrame;
    }

    public int getIdMat() {
        return idMat;
    }

    public void setIdMat(int idMat) {
        this.idMat = idMat;
    }

    public int getIdRoofT() {
        return idRoofT;
    }

    public void setIdRoofT(int idRoofT) {
        this.idRoofT = idRoofT;
    }

    public int getIdStyle() {
        return idStyle;
    }

    public void setIdStyle(int idStyle) {
        this.idStyle = idStyle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
