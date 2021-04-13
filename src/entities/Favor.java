package entities;

public class Favor {
	private int idUser;
	private int[] buildings;
	
	//----------Constructeur---------
	public Favor(int idUser, int[] buildings) {
		this.idUser=idUser;
		this.buildings=buildings;
	}
	//-------------------------------
	
	//-----------Getters&Setters-----
	
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int[] getBuildings() {
		return buildings;
	}

	public void setBuildings(int[] buildings) {
		this.buildings = buildings;
	}
	
	
	
	
	//-------------------------------
	
	

}
