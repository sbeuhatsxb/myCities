package model;

import entities.Building;
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
        List<Object> favlists = database.getFilteredById(Env.FAVLIST, userId, "id_user");
        Favlist favlist = (Favlist) favlists.get(0);


        return favlist;
    }

    public Building getBuilding(int buildingId){
        List<Object> buildingGetter = database.getOne(buildingId, Env.BUILDING);
        Building building = (Building) buildingGetter.get(0);
        return building;
    }

    public List<Object> getAllBuildings(){
        List<Object> buildings = database.getAll(Env.BUILDING);
        return buildings;
    }

    public List<Object> getAllCities(){
        List<Object> cities = database.getAll(Env.CITY);
        return cities;
    }





}
