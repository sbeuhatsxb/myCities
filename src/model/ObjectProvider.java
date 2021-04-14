package model;

import entities.*;
import resources.Env;

import java.util.List;

public class ObjectProvider {

    private DataHandler database;

    public User getUserById(int userId){
        database = new DataHandler();
        List<Object> userGetter = database.getOne(userId, Env.USER);
        if(userGetter.size() > 0) {
            User user = (User) userGetter.get(0);
            return user;
        }
        return null;
    }

    public Style getStyleById(int styleId){
        database = new DataHandler();
        List<Object> styleGetter = database.getOne(styleId, Env.STYLE);
        if(styleGetter.size() > 0) {
            Style style = (Style) styleGetter.get(0);
            return style;
        }
        return null;
    }

    public Type getTypeById(int typeId){
        database = new DataHandler();
        List<Object> typeGetter = database.getOne(typeId, Env.TYPE);
        if(typeGetter.size() > 0) {
            Type type = (Type) typeGetter.get(0);
            return type;
        }
        return null;
    }

    public Favlist getFavlist(int userId){
        database = new DataHandler();
        List<Object> favlists = database.getFilteredIntByColumn(Env.FAVLIST, userId, "id_user");
        if(favlists.size() > 0) {
            Favlist favlist = (Favlist) favlists.get(0);
            return favlist;
        }
        return null;
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

    public User getUserByLogin(String login){
        database = new DataHandler();
        List<Object> userList = database.getFilteredStringByColumn(Env.USER, login, "login");
        if(userList.size() > 0){
            User user = (User) userList.get(0);
            return user;
        }
        return null;
    }

    public List<Object> getBuildingsByCity(City city){
        database = new DataHandler();
        int cityId = city.getId();
        List<Object> buildings = database.getFilteredIntByColumn(Env.BUILDING, cityId, "id_city");

        return buildings;
    }

    public List<Object> getBuildingByYear(int year){
        database = new DataHandler();
        List<Object> buildings = database.getFilteredIntByColumn(Env.BUILDING, year, "year");

        return buildings;
    }

    public List<Object> getBuildingByStyle(Style style){
        database = new DataHandler();
        int styleId = style.getId();
        List<Object> buildings = database.getFilteredIntByColumn(Env.BUILDING, styleId, "id_style");

        return buildings;
    }

    public List<Object> getBuildingByType(Type type){
        database = new DataHandler();
        int typeId = type.getId();
        List<Object> buildings = database.getFilteredIntByColumn(Env.BUILDING, typeId, "id_type");

        return buildings;
    }
}
