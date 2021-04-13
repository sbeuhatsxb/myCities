package entities;

public class Architect {
	private  int id;
	private String  label;
	
	
	//----------Constructeur---------
	public Architect(int idArch, String label) {
		this.id=idArch;
		this.label=label;
		
	}
	//-------------------------------
	
	//-----------Getters&Setters-----
	public int getIdArch() {
		return id;
	}

	public void setIdArch(int idArch) {
		this.id = idArch;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	//-------------------------------
	

}
