package entities;

public class Type {
	private int id;
	private String label;
	
	//----------Constructeur---------
	public Type(String label, int idType) {
		this.id=idType;
		this.label=label;
	}
	//-------------------------------
	
	//-----------Getters&Setters-----
	public int getIdType() {
		return id;
	}

	public void setIdType(int idType) {
		this.id = idType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	//-------------------------------

}
