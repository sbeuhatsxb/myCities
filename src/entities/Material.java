package entities;

public class Material {
	private int id;
	private String label;

	//----------Constructeur---------
	public Material(int idMaterial, String label) {
		this.id=idMaterial;
		this.label=label;
	
	}
	//-------------------------------
	
	//-----------Getters&Setters-----
	public int getIdMaterial() {
		return id;
	}

	public void setIdMaterial(int idMaterial) {
		this.id = idMaterial;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	//-------------------------------
}
