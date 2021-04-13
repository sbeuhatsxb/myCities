package entities;

public class Architect {
    private int id;
    private String label;

    public Architect(){
	};

    public Architect(int idArch, String label) {
        this.id = idArch;
        this.label = label;
    }

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


}
