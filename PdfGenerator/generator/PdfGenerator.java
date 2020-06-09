package generator;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfGenerator {
	
	public void userListPdf(List<UserPDF>userList,List<ReservationPdf>reservationList,float profit) {
		Document document=new Document();
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("C:/raport.pdf"));
			 PdfPTable table = new PdfPTable(3);
			 table.setSpacingBefore(13);
			 table.setSpacingAfter(13);

			 PdfPCell cell;
		     cell = new PdfPCell(new Phrase("Uzytkownicy"));
		     cell.setColspan(3);
		     table.addCell(cell);
		     cell = new PdfPCell(new Phrase("Imie"));
		     table.addCell(cell);
		     cell = new PdfPCell(new Phrase("Nazwisko"));
		     table.addCell(cell);
		     cell = new PdfPCell(new Phrase("Login"));
		     table.addCell(cell);
			document.open();
			document.add(new Paragraph("Laczny Zysk: "+profit));
			for(int i=0;i<userList.size();i++) {
				 	table.addCell(userList.get(i).name);
		            table.addCell(userList.get(i).surname);
		            table.addCell(userList.get(i).login);
			}
			 PdfPTable secondTable = new PdfPTable(5);
			 PdfPCell celltwo;
			 celltwo = new PdfPCell(new Phrase("Goscie"));
			 celltwo.setColspan(5);
		     secondTable.addCell(celltwo);
		     celltwo = new PdfPCell(new Phrase("Imie"));
		     secondTable.addCell(celltwo);
		     celltwo = new PdfPCell(new Phrase("Nazwisko"));
		     secondTable.addCell(celltwo);
		     celltwo = new PdfPCell(new Phrase("Rachunek"));
		     secondTable.addCell(celltwo);
		     celltwo = new PdfPCell(new Phrase("Data zapisania"));
		     secondTable.addCell(celltwo);
		     celltwo = new PdfPCell(new Phrase("Data wypisania"));
		     secondTable.addCell(celltwo);
		     document.add(table);
		     document.add(new Paragraph(""));	
		     document.add(new Paragraph(""));
			for(int i=0;i<reservationList.size();i++) {
				
				secondTable.addCell(reservationList.get(i).name);
				secondTable.addCell(reservationList.get(i).surname);
				secondTable.addCell(Float.toString(reservationList.get(i).totalbill));
				secondTable.addCell(reservationList.get(i).begginingDate);
				secondTable.addCell(reservationList.get(i).endDate);
			}
			document.add(secondTable);
		}
		catch(Exception e) {
			System.out.println(e);
			
		}
		document.close();
		System.out.println("system dodal");
	}

}
