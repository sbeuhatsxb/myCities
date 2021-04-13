package entities;

public class City {
	private int id,idCountry;
	private String label;
	
	//----------Constructeur---------
	public City (int idCity, int idCountry, String label) {
		this.id=idCity;
		this.idCountry=idCountry;
		this.label=label;
		
	}
	//-------------------------------
	
	//-----------Getters&Setters-----
	public int getIdCity() {
		return id;
	}

	public void setIdCity(int idCity) {
		this.id = idCity;
	}

	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	//-------------------------------

}
