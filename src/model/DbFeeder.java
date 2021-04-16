package model;

import entities.Building;
import entities.User;

public class DbFeeder {

    private DataHandler database;

    public void addNewBuilding(Building building){
        database = new DataHandler();
        database.insertNewBuilding(building);
    }

    public void deleteBuilding(Building building){
        database = new DataHandler();
        database.deleteBuilding(building);
    }

    public boolean addToFavlist(Building building, User user){
        database = new DataHandler();
        if(!database.checkIfFavlistIsAlreadySet(building.getId(), user.getId())){
            database.insertNewFavlist(building, user);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeFromFavlist(Building building, User user){
        database = new DataHandler();
        if(database.checkIfFavlistIsAlreadySet(building.getId(), user.getId())){
            database.removeFromFavlist(building, user);
            return true;
        } else {
            return false;
        }
    }



}
