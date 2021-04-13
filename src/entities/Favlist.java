package entities;

import java.util.List;

public class Favlist {
    private int idUser;
    private List<Building> buildings;

    public Favlist() {
    }

    public Favlist(int idUser, List<Building> buildings) {
        this.idUser = idUser;
        this.buildings = buildings;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
