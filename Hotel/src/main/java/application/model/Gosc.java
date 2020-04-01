package application.model;

/**
 * Autor:Damian
 *
 * Gosc
 */

public class Gosc {



    private long id;
    private String imie;
    private String nazwisko;
    private String email;
    private String adres;
    private String panstwo;
    private String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPanstwo() {
        return Panstwo;
    }

    public void setPanstwo(String Panstwo) {
        this.Panstwo = Panstwo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
