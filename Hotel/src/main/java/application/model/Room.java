package application.model;


/**
 * Autor:Damian
 *
 * Room Entity
 */


public class Room {
    @Id
    private long id;
    private String nazwa;
    private String numer;
    private String lozko;

    private boolean zajety;
    private List<Gosc> rezydenci;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return nazwa;
    }

    public void setName(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNumber() {
        return numer;
    }

    public void setNumber(String numer) {
        this.numer = numer;
    }

    public String getBedInfo() {
        return lozko;
    }

    public void setBedInfo(String lozko) {
        this.lozko = lozko;
    }
    public boolean getCzyZajety() {
        return CzyZajety;
    }

    public void setCzyZajety(boolean CzyZajety) {
        this.CzyZajety = CzyZajety;
    }

    public List<Gosc> rezydenci() {
        return rezydenci;
    }

    public void setRezydenci(List<Gosc> rezydenci) {
        this.rezydenci = rezydenci;
    }
}