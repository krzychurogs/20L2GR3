package application;

import java.util.Date;
/**
 * Klasa Pomocnicza wykorzystyana w tabeli zajetych pokoi w Recepcjoniscie
 */
public class MenuItem
{
	public int roomNumber;
	public int numberOfSeats;
	public String lvl;
	public Date dates;
	public Date enddate;
	
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public Date getDates() {
		return dates;
	}
	public void setDates(Date dates) {
		this.dates = dates;
	}
	public MenuItem(int roomNumber, int numberOfSeats, String lvl, Date dates, Date enddate) {
		super();
		this.roomNumber = roomNumber;
		this.numberOfSeats = numberOfSeats;
		this.lvl = lvl;
		this.dates = dates;
		this.enddate = enddate;
	}
	
																												
	

	
	
}