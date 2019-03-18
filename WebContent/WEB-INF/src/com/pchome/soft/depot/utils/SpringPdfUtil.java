package com.pchome.soft.depot.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pchome.akbpfp.struts2.action.report.PdfDataBean;

public class SpringPdfUtil {


	private static BaseFont bf;
	private static Font ft12;
	private static Font ft14B;
	private static Font ft14;
	private static Font ft12CR;
	private static Font ft20B; 
	
	private static Log log = LogFactory.getLog(SpringPdfUtil.class);

	public SpringPdfUtil() throws DocumentException, IOException{

		bf = BaseFont.createFont("config/font/kaiu.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		ft12 = new Font(bf, 12,Font.NORMAL);
		ft12CR = new Font(bf, 12,Font.NORMAL, BaseColor.RED);
		ft14B = new Font(bf, 14,Font.BOLD);
		ft14 = new Font(bf, 14,Font.NORMAL);
		ft20B = new Font(bf, 20,Font.BOLD);


	}

	public byte[] getBillPayPdf(PdfDataBean pdfDataBean) throws DocumentException, MalformedURLException, IOException{

		ByteArrayOutputStream out=new ByteArrayOutputStream();

		Document pdfDocument=new Document();

		PdfWriter.getInstance(pdfDocument, out);

		pdfDocument.open();

		addTitle(pdfDocument,pdfDataBean);
		addContent(pdfDocument,pdfDataBean);
		//createTable(pdfDocument);
		//createTable(pdfDocument);
		addFoot(pdfDocument);

		pdfDocument.close();

		System.out.println("pdf write ok");


		return out.toByteArray();
	}
	
	public byte[] getBillInvoicePdf(PdfDataBean pdfDataBean) throws DocumentException, MalformedURLException, IOException{

		ByteArrayOutputStream out=new ByteArrayOutputStream();

		Document pdfDocument=new Document();

		PdfWriter.getInstance(pdfDocument, out);

		pdfDocument.open();

		addTitle(pdfDocument,pdfDataBean);
		addInvoiceContent(pdfDocument,pdfDataBean);
		addFoot(pdfDocument);

		pdfDocument.close();

		System.out.println("pdf write ok");


		return out.toByteArray();
	}


	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}


	private static void addContent(Document pdfDocument,PdfDataBean pdfDataBean) throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(preface, 2);
		preface.add(new Paragraph("報表日期:"+pdfDataBean.getDateRange(), ft12));
		preface.add(new Paragraph("帳戶名稱:"+pdfDataBean.getCustName(), ft12));
		preface.add(new Paragraph("統一編號:"+pdfDataBean.getTax(), ft12));
		addEmptyLine(preface, 1);

		preface.add(new Paragraph("帳戶明細:", ft14B));
		preface.add(new Paragraph("-------------------------------------------------------------------", ft14B));
		addEmptyLine(preface, 1);
		pdfDocument.add(preface);

		createTable(pdfDocument,pdfDataBean);

		preface = new Paragraph();
		preface.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("-------------------------------------------------------------------", ft14B));
		addEmptyLine(preface, 2);
		pdfDocument.add(preface);
	}
	
	
	private static void addInvoiceContent(Document pdfDocument,PdfDataBean pdfDataBean) throws DocumentException {
		
		NumberFormat formatter = new DecimalFormat("###,###,###,###");
		
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(preface, 2);
		preface.add(new Paragraph("報表日期:"+pdfDataBean.getDateRange(), ft12));
		preface.add(new Paragraph("帳戶名稱:"+pdfDataBean.getCustName(), ft12));
		preface.add(new Paragraph("統一編號:"+pdfDataBean.getTax(), ft12));
		addEmptyLine(preface, 1);

		preface.add(new Paragraph("帳戶明細:", ft14B));
		preface.add(new Paragraph("-------------------------------------------------------------------", ft14B));
		preface.add(new Paragraph("前一個月:帳戶餘額:NT$ "+formatter.format(Float.parseFloat(pdfDataBean.getPreMonthReminePrice()))+" 元", ft14B));
		addEmptyLine(preface, 1);
		pdfDocument.add(preface);

		createTable(pdfDocument,pdfDataBean);

		preface = new Paragraph();
		preface.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("-------------------------------------------------------------------", ft14B));
		preface.add(new Paragraph(pdfDataBean.getDateRange()+":帳戶總餘額:NT$ "+formatter.format(Float.parseFloat(pdfDataBean.getThisMonthReminePrice()))+" 元", ft14B));
		addEmptyLine(preface, 2);
		pdfDocument.add(preface);
	}


	private static String getCurrentFilePath(String path) throws FileNotFoundException {
		
	     if (StringUtils.isBlank(path)) {
	      throw new NullPointerException("path could not be null!!");
	     }

	     String temp = path;
	     if (path.indexOf("/") == 0) {
	      temp = path.substring(1);
	     }

	     URL url = SpringPdfUtil.class.getClassLoader().getResource(temp);
	     if (url == null) {
	      throw new FileNotFoundException("could not find path : " + path);
	     } else {

	     }
	     String currentPath = url.getPath();
	     if (StringUtils.isBlank(currentPath)) {
	      throw new NullPointerException("could not find path : " + path);
	     } else {
	      //for windwos path
	      currentPath = currentPath.replace("%20", " ");
	      return currentPath;
	     }
	}

	private static void addTitle(Document pdfDocument,PdfDataBean pdfDataBean) throws DocumentException, MalformedURLException, IOException {
		
		
		
		//File file = new File(getCurrentFilePath(pdfDataBean.getLogoPath()));
		//log.info(">>> exists  = "+file.exists());
		//log.info(">>> Absolute  = "+file.getAbsolutePath());
		//log.info(">>> Canonical  = "+file.getCanonicalPath());

		Image gif = Image.getInstance(getCurrentFilePath(pdfDataBean.getLogoPath()));		
		//log.info(">>> gif  = "+gif);
		pdfDocument.add(gif);
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.setAlignment(Element.ALIGN_LEFT);
		preface.add(new Paragraph(pdfDataBean.getPchomeTitle(), ft14));
		preface.add(new Paragraph(pdfDataBean.getPchomeAddress(), ft14));
		preface.add(new Paragraph(pdfDataBean.getPchomeTel(), ft14));
		pdfDocument.add(preface);

		preface = new Paragraph();
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 2);
		preface.add(new Paragraph(pdfDataBean.getReportTitle(), ft20B));
		pdfDocument.add(preface);

	}

	private static void addFoot(Document pdfDocument) throws DocumentException {

		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.setAlignment(Element.ALIGN_LEFT);
		preface.add(new Paragraph("備註", ft12CR));
		preface.add(new Paragraph("1)以上資料若有任何問題請洽客服中心", ft12CR));
		pdfDocument.add(preface);


	}

	private static void createTable(Document pdfDocument,PdfDataBean pdfDataBean) throws DocumentException {



		NumberFormat formatter = new DecimalFormat("###,###,###,###");
		
		PdfPTable table = new PdfPTable(7);

		//BaseColor cr=new BaseColor(255,0,0);
		table.setTotalWidth(100);
		//table.getDefaultCell().setBorder(1);
		table.getDefaultCell().setBorderWidth(1);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setPadding(2);

		//table.getDefaultCell().setBorderColor(cr);


	
		//table.setBorderColor(BaseColor.GRAY);
		//table.setPadding(4);
		//table.setSpacing(4);
		//table.setBorderWidth(1);
		
		String sa[]=pdfDataBean.getContentList().pollFirst().split(",");
		PdfPCell c1=null;
		for(String s:sa){
			if(StringUtils.isNotBlank(s)){
			
				if(NumberUtils.isNumber(s)){
					s=formatter.format(Float.parseFloat(s));
				}
				c1 = new PdfPCell(new Phrase(s,ft12));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setVerticalAlignment(Element.ALIGN_TOP);
				c1.setBorderWidth(1);
				c1.setPadding(5);
				table.addCell(c1);
			}
		}



		String content="";
		
		while((content=pdfDataBean.getContentList().pollFirst())!=null){
			
			sa=content.split(",");
			for(String s:sa){
				
				if(NumberUtils.isNumber(s)){
					s=formatter.format(Float.parseFloat(s));
				}
				//if(StringUtils.isNotBlank(s)){
					c1 = new PdfPCell(new Phrase(s,ft12));
					c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					c1.setVerticalAlignment(Element.ALIGN_CENTER);
					c1.setBorderWidth(1);
					c1.setPadding(5);
					
					table.addCell(c1);
					
				//}
			}
		}

		pdfDocument.add(table);



	}




	public byte[] getPdfFromString(String content){

		ByteArrayOutputStream out=new ByteArrayOutputStream();


		Document pdfDocument=new Document();

		BaseFont bf=null;
		Font font=null;

		try {
			//bf= BaseFont.createFont("MHei-Medium","UniCNS-UCS2-H",false);
			bf = BaseFont.createFont("config/font/kaiu.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


			font=new Font(bf,12);


			PdfWriter.getInstance(pdfDocument, out);

			pdfDocument.open();


			pdfDocument.add(new Paragraph(content.toString(),font));


			/*
			PdfPTable  table = new PdfPTable(2); 
			table.setWidthPercentage(100f);
			//設定每個欄位的寬度
			table.setWidths(new float[]{0.20f, 0.90f});

			PdfPCell title = new PdfPCell();
			//合併儲存格
			title.setColspan(2);
			title.addElement(new Phrase("Table's Title"));
			table.addCell(title);

			//設定第一個欄位的內容
			PdfPCell cell_1 = new PdfPCell();
			cell_1.addElement(new Phrase("Column 1"));
			table.addCell(cell_1);

			//設定第二個欄位的內容
			PdfPCell cell_2 = new PdfPCell();
			cell_2.addElement(new Phrase("Column 2"));
			table.addCell(cell_2);

			pdfDocument.add(table);

			 */

			pdfDocument.close();



		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



		return out.toByteArray();
	}

	public byte[] getPdfFromList(LinkedList<String> content){
		ByteArrayOutputStream out=new ByteArrayOutputStream();

		Document pdfDocument=new Document();

		BaseFont bf=null;
		Font font=null;

		try {
			//bf= BaseFont.createFont("MHei-Medium","UniCNS-UCS2-H",false);
			bf = BaseFont.createFont("config/font/kaiu.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


			font=new Font(bf,12);


			PdfWriter.getInstance(pdfDocument, out);

			pdfDocument.open();


			String data[]=null;

			LinkedList<LinkedList<String>> tableList=new LinkedList<LinkedList<String>>();
			LinkedList<String> inTableData;
			int tableSize=0;
			for(String s:content){

				if(s.indexOf(",")>0){
					s=s.substring(0,s.length()-1);
					inTableData=new LinkedList<String>();
					data=s.split(",");
					for(String ds:data){
						//if(StringUtils.isNotBlank(ds)){
						inTableData.addLast(ds);
						//}
					}
					tableSize=inTableData.size();
					tableList.addLast(inTableData);
				}else{
					pdfDocument.add(new Paragraph(s,font));
				}

			}



			PdfPTable table = new PdfPTable(tableSize); 
			table.setWidthPercentage(100f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);



			//設定每個欄位的寬度
			float[] tw=new float[tableSize];

			for(int x=0;x<tableSize;x++){
				tw[x]=1f;
			}


			table.setWidths(tw);


			//加粗表格边框 -- 若需要可使用 

			/*
		    Rectangle border = new Rectangle(0f, 0f);
            border.setBorderWidthLeft(0.5f);
            border.setBorderWidthRight(0.5f);
            border.setBorderColor(new BaseColor(Color.BLACK));
            border.setBorderWidthBottom(1f);
            border.setBorderWidthTop(0.5f);
			 */



			PdfPCell cell =null;

			for(LinkedList<String> id:tableList){
				for(String d:id){
					cell = new PdfPCell(new Paragraph(d,font));
					cell.setFixedHeight(15);
					//cell.cloneNonPositionParameters(border);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					//cell.addElement(new Paragraph(d,font));
					table.addCell(cell);
				}

			}
			/*
	        PdfPCell title = new PdfPCell();
			//合併儲存格
			title.setColspan(2);
			title.addElement(new Phrase("Table's Title"));
			table.addCell(title);

			//設定第一個欄位的內容
			PdfPCell cell_1 = new PdfPCell();
			cell_1.addElement(new Phrase("Column 1"));
			table.addCell(cell_1);

			//設定第二個欄位的內容
			PdfPCell cell_2 = new PdfPCell();
			cell_2.addElement(new Phrase("Column 2"));
			table.addCell(cell_2);
			 */
			pdfDocument.add(table);



			pdfDocument.close();

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return out.toByteArray();
	}


	public static void main(String arg[]){



		SpringPdfUtil springPdfUtil;
		try {
			springPdfUtil = new SpringPdfUtil();


			String content="nico";

			//byte[] pdfarray=springPdfUtil.getBillPayPdf(content);


			//FileUtils.writeByteArrayToFile(new File("/home/webuser/pdftest.pdf"),  pdfarray);

		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
