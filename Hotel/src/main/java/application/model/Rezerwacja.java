package application.model;
/**
 * Autor:Damian
 *
 * Rezerwacja
 */
public class Rezerwacja {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="RESERVATION_ID")
    private long id;
    @Column(name="ROOM_ID")
    private long pokojId;
    @Column(name="GUEST_ID")
    private long goscId;
    @Column(name="REZERWATION_DATE")
    private Date data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long pokojId) {
        this.pokojId = pokojId;
    }

    public long getGuestId() {
        return goscId;
    }

    public void setGuestId(long guestId) {
        this.goscId = goscId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

