package application;

import java.util.Arrays;
/**
 * Klasa pomocnicza wykorzystywana w tableView w kontrolerze Recepcjonisty
 */
public class Item {

	Rooms number;
	long count;
	public Item(Rooms number, long count) {
		super();
		this.number = number;
		this.count = count;
	}
	public Rooms getNumber() {
		return number;
	}
	public void setNumber(Rooms number) {
		this.number = number;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Item [number=" + number + ", count=" + count + "]";
	}
	


	
	


}