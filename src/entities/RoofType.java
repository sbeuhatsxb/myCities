package entities;

public class RoofType {
	
	private int id;
	private String label;
	
	//----------Constructeur---------
	public RoofType(int idRoofT, String label) {
		this.id=idRoofT;
		this.label=label;
		
		
		
	}
	//-------------------------------
	

	//-----------Getters&Setters-----	
	public int getIdRoofT() {
		return id;
	}

	public void setIdRoofT(int idRoofT) {
		this.id = idRoofT;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	//-------------------------------
}
