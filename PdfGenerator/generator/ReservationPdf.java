package generator;

public class ReservationPdf {
		String name;
		String surname;
		float totalbill;
		String begginingDate;
		String endDate;
		
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
		public float getTotalbill() {
			return totalbill;
		}
		public void setTotalbill(float totalbill) {
			this.totalbill = totalbill;
		}
		public String getBegginingDate() {
			return begginingDate;
		}
		public void setBegginingDate(String begginingDate) {
			this.begginingDate = begginingDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public ReservationPdf(String name, String surname, float totalbill, String begginingDate, String endDate) {
			super();
			this.name = name;
			this.surname = surname;
			this.totalbill = totalbill;
			this.begginingDate = begginingDate;
			this.endDate = endDate;
		}
		
}
