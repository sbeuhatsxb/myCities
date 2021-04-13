package entities;

public class Frame {
	private int id;
	private String label;
	
	//----------Constructeur---------
	public Frame(int idFrame, String label) {
		this.id=idFrame;
		this.label=label;
	}
	//-------------------------------
	
	//-----------Getters&Setters-----
	public int getIdFrame() {
		return id;
	}

	public void setIdFrame(int idFrame) {
		this.id = idFrame;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
	//-------------------------------
}
