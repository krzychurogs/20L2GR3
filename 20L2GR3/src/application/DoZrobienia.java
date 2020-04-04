package application.model;
/**
 * Autor:Damian
 *
 * Rezerwacja
 * Klasa pozwala na generowanie zadań do zrobienia przez obsługę hotelu
 */
public class DoZrobienia {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "zadanie")
    private String zadanie;

    public DoZrobienia() {

    }

    public DoZrobienia(String zadanie ){

        this.zadanie = zadanie;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getZadanie() {

        return zadanie;
    }

    public void setZadanie(String zadanie) {

        this.zadanie = zadanie;
    }

    public String toString() {

        return "[DoZrobienia: id = " + id + " ,zadanie = " + zadanie +" ]";
    }
}
