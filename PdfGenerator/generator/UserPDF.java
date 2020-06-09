package generator;



public class UserPDF {
	
	
	int id;
	String name;
	String surname;
	String login;
	String password;
	

	


	

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
	
	

	public UserPDF(String name, String surname, String login, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
	}
	

	

	public UserPDF() {
		super();
	}
	
	public UserPDF(int id, String name, String surname, String login, String password) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;

	}

	public UserPDF(int id, String name, String surname, String login) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;

	}

	

	@Override
	public String toString() {
		return name;
	}


	public void createUserPdf(int id, String name, String surname, String login) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
	}
	
	
	
}

