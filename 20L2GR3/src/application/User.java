package application;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	int id;
	
	@Column(name = "login")
	String login;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "job_id")
	int idJob;
	
	public User(int id, String login, String password, int idJob) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.idJob = idJob;
	}
	
	public User() {
		super();
		this.id=1;
		this.login="";
		this.password="";
		this.idJob=1;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIdJob() {
		return idJob;
	}
	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}
	
}
