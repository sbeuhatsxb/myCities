package entities;

public class Style {
	
	private int id;
	private String label;
	
	//----------Constructeur---------
	public Style (int idStyle, String label) {
		this.id=idStyle;
		this.label=label;
	}

	public int getIdStyle() {
		return id;
	}
	//-------------------------------
	
	//-----------Getters&Setters-----
	public void setIdStyle(int idStyle) {
		this.id = idStyle;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	//-------------------------------

}
