package generator;

import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {

    	PdfGenerator generator =new PdfGenerator();
    	List<UserPDF> userpdfList =new ArrayList<UserPDF>();

    		 UserPDF newuser=new UserPDF(1,"lul","lol","wtf");
    		 UserPDF dwauser=new UserPDF(1,"lul","lol","wtf");
    		 userpdfList.add(newuser);
    		 userpdfList.add(dwauser);
    		
    	//generator.userListPdf(userpdfList);	    
	}

}
