package model;

import entities.Building;
import entities.City;
import entities.Favlist;
import entities.User;
import resources.Env;

import java.util.List;

public class ObjectProvider {

    private DataHandler database;

    public User getUserById(int userId){
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

    public City getCityById(int cityId){
        database = new DataHandler();
        List<Object> cities = database.getFilteredIntByColumn(Env.CITY, cityId, "id_city");
        City city = (City) cities.get(0);
        return city;
    }

    public List<Object> getBuildingsByCity(City city){
        database = new DataHandler();
        int cityId = city.getId();
        List<Object> buildings = database.getFilteredIntByColumn(Env.BUILDING, cityId, "id_city");

        return buildings;
    }

    public User getUserByLogin(String login){
        database = new DataHandler();
        List<Object> userList = database.getFilteredStringByColumn(Env.USER, login, "login");
        User user = (User) userList.get(0);
        return user;
    }


}
