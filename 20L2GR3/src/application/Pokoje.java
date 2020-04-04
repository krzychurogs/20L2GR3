package application;

import java.sql.Date;

public class Pokoje {
	int liczba_osob_na_pokoj;
	int nr_pokoju;
	String lvl;
	Date data;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getLiczba_osob_na_pokoj() {
		return liczba_osob_na_pokoj;
	}
	public void setLiczba_osob_na_pokoj(int liczba_osob_na_pokoj) {
		this.liczba_osob_na_pokoj = liczba_osob_na_pokoj;
	}
	public int getNr_pokoju() {
		return nr_pokoju;
	}
	public void setNr_pokoju(int nr_pokoju) {
		this.nr_pokoju = nr_pokoju;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public Pokoje(int liczba_osob_na_pokoj, int nr_pokoju, String lvl) {
		super();
		this.liczba_osob_na_pokoj = liczba_osob_na_pokoj;
		this.nr_pokoju = nr_pokoju;
		this.lvl = lvl;
	}
	
	
}
