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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
