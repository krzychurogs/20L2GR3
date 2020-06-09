package generator;
import java.util.List;




public class Job {


	private int id;
	

	private String name;

	private List<UserPDF> user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserPDF> getUser() {
		return user;
	}

	public void setUser(List<UserPDF> user) {
		this.user = user;
	}

	public Job(int id, String name, List<UserPDF> user) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
	}

	public Job() {
		super();
	}
	
	@Override
	public String toString() {
		return  name+"" ;
	}	
	
}
