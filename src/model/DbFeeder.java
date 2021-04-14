package model;

import entities.Building;
import entities.User;

public class DbFeeder {

    private DataHandler database;

    public void addNewBuilding(Building building){
        database = new DataHandler();
        database.insertNewBuilding(building);
    }

    public void addToFavlist(Building building, User user){
        database = new DataHandler();
        if(!database.checkIfFavlistIsAlreadySet(building.getId(), user.getId())){
            database.insertNewFavlist(building, user);
        } else {
            System.out.println("FavList already recorded");
        }
    }




}