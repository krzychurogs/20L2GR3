package application;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	int id;
	
	@Column(name = "Name")
	String name;
	@Column(name = "Surname")
	String surname;
	@Column(name = "login")
	String login;
	
	@Column(name = "password")
	String password;
	

	@ManyToOne
    private Job job;
	
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
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
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public User() {
		super();
	}

	public User(String name, String surname, String login, String password, Job job) {
		super();
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
		this.job = job;
	}

	@Override
	public String toString() {
		return name;
	}

	
	
	
	
}

