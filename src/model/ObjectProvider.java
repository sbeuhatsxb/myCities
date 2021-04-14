package model;

import entities.Building;
import entities.City;
import entities.Favlist;
import entities.User;
import resources.Env;

import java.util.List;

public class ObjectProvider {

    private DataHandler database;

    public User getUser(int userId){
        database = new DataHandler();
        List<Object> userGetter = database.getOne(userId, Env.USER);
        User user = (User) userGetter.get(0);
        return user;
    }

    public Favlist getFavlist(int userId){
        database = new DataHandler();
        List<Object> favlists = database.getFilteredIntByColumn(Env.FAVLIST, userId, "id_user");
        Favlist favlist = (Favlist) favlists.get(0);
        return favlist;
    }

    public Building getBuildingById(int buildingId){
        database = new DataHandler();
        List<Object> buildingGetter = database.getOne(buildingId, Env.BUILDING);
        Building building = (Building) buildingGetter.get(0);
        return building;
    }

    public List<Object> getAllBuildings(){
        database = new DataHandler();
        List<Object> buildings = database.getAll(Env.BUILDING);
        return buildings;
    }

    public List<Object> getAllCities(){
        database = new DataHandler();
        List<Object> cities = database.getAll(Env.CITY);
        return cities;
    }

    public List<Object> getBuildingsByCity(City city){
        database = new DataHandler();
        int cityId = city.getId();
        List<Object> buildings = database.getFilteredIntByColumn(Env.BUILDING, cityId, "id_city");

        return buildings;
    }


}
