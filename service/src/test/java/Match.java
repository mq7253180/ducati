
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Match {

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		readPdf("/Users/maqiang/match20250312/pdf");
		matchExcel("/Users/maqiang/match20250312/src", "/Users/maqiang/match20250312/dst");
		System.out.println("Duration========="+(System.currentTimeMillis()-start));
	}

//	private final static List<String> list = new ArrayList<>(240000);//230071
	private final static Map<String, String> map = new HashMap<>(262144);//183119

	private static void matchExcel(String src, String dst) throws IOException {
		File srcDir = new File(src);
		File[] excels = srcDir.listFiles();
		for(File excel:excels) {
			if(excel.getName().equals(".DS_Store"))
				continue;
			InputStream in = null;
			OutputStream out = null;
			XSSFWorkbook wb = null;
			try {
				in = new FileInputStream(excel);
				out = new FileOutputStream(dst+"/"+excel.getName());
				wb = new XSSFWorkbook(in);
	            XSSFFont font = wb.createFont();
	            font.setColor(IndexedColors.RED.getIndex());
	            XSSFCellStyle style = wb.createCellStyle();
	            style.setFont(font);
				int sheets = wb.getNumberOfSheets();
				for(int i=0;i<sheets;i++) {
					XSSFSheet sheet = wb.getSheetAt(0);
					int index = 0;
					String code = null;
					while(true) {
						XSSFRow row = sheet.getRow(index);
						if(row==null)
							break;
						XSSFCell cell1 = row.getCell(1);
						if(cell1==null)
							break;
						code = cell1.getStringCellValue().trim();
						/*if(code.length()>0) {
							for(String toMathCode:list) {
								if(code.equalsIgnoreCase(toMathCode)) {
									XSSFCell cell2 = row.getCell(2);
									if(cell2==null)
										cell2 = row.createCell(2);
									cell2.setCellValue("已出库");
									cell2.setCellStyle(style);
									break;
								}
							}
						}*/
						if(code.length()>0&&map.get(code)!=null) {
							XSSFCell cell2 = row.getCell(2);
							if(cell2==null)
								cell2 = row.createCell(2);
							cell2.setCellValue("已出库");
							cell2.setCellStyle(style);
						}
						index++;
					}
				}
				wb.write(out);
				out.flush();
			} finally {
				if(wb!=null)
					wb.close();
				if(in!=null)
					in.close();
				if(out!=null)
					out.close();
			}
		}
	}

	private static void readPdf(String dirPath) throws IOException {
        File dir = new File(dirPath);
        File[] pdfs = dir.listFiles();
        for(File pdf:pdfs) {
        	if(pdf.getName().equals(".DS_Store"))
        		continue;
        	PDDocument document = null;
            try {
    			document = PDDocument.load(pdf);
    			PDFTextStripper pdfStripper = new PDFTextStripper();
    	        String text = pdfStripper.getText(document);
    	        String[] codes = text.split("\n");
    	        for(String _s:codes) {
    	        	String s = _s.trim();
    	        	if(s.length()>0) {
//    	        		list.add(s);
    	        		map.put(s, s);
    	        	}
    	        }
    		} catch(IOException e) {
    			System.out.println("================="+pdf.getName());
    			throw e;
    		} finally {
    			if(document!=null)
    				document.close();
    		}
        }
//        System.out.println(list.size()+"-"+map.size()+"="+(list.size()-map.size()));
        System.out.println(map.size());
	}
}
