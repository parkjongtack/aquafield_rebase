package com.soft.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

@Component("excelDownloadView")
public class ExcelDownload extends AbstractJExcelView {
	// Logger
	private final Log logger = LogFactory.getLog(getClass());
	
    @Override
    protected void buildExcelDocument(Map<String, Object> model,WritableWorkbook workbook, HttpServletRequest request,HttpServletResponse response) throws Exception {
		List header = (List)model.get("header");
		List list = (List)model.get("list");
        String fileName = String.valueOf(model.get("fileName") );
 
        response.setHeader("Content-Disposition", "attachement; filename=\""+ fileName);
 
        makeExcelFile(workbook, header, list);
    }
 
    private void makeExcelFile(WritableWorkbook workbook, List header, List list) throws RowsExceededException, WriteException {
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
		cellFont.setBoldStyle(WritableFont.BOLD);
		jxl.write.WritableCellFormat fmTitle= new WritableCellFormat(cellFont);
		jxl.write.WritableCellFormat fmBodyCenter= new WritableCellFormat();
		jxl.write.WritableCellFormat fmBodyLeft= new WritableCellFormat();
		jxl.write.WritableCellFormat fmInteger= new WritableCellFormat(NumberFormats.INTEGER);
		
		jxl.write.NumberFormat fFloat = new jxl.write.NumberFormat("#####0.0");
		jxl.write.WritableCellFormat fmFloat= new WritableCellFormat(fFloat);
		
		fmTitle.setBackground(jxl.format.Colour.IVORY );
		fmTitle.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN );
		fmTitle.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN );
		fmTitle.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.THIN );
		fmTitle.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.THIN );
		fmTitle.setAlignment(jxl.format.Alignment.CENTRE);
		
		
		fmBodyCenter.setBackground(jxl.format.Colour.WHITE );
		//fmBodyCenter.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.DOTTED );
		fmBodyCenter.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN );
		fmBodyCenter.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN );
		fmBodyCenter.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.THIN );
		fmBodyCenter.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.THIN );
		fmBodyCenter.setAlignment(jxl.format.Alignment.CENTRE);
		
		
		
		fmBodyLeft.setBackground(jxl.format.Colour.WHITE );
		//fmBodyLeft.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
		fmBodyLeft.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN );
		fmBodyLeft.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN );
		fmBodyLeft.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.THIN );
		fmBodyLeft.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.THIN );
		fmBodyLeft.setAlignment(jxl.format.Alignment.LEFT);
		
		
		fmInteger.setBackground(jxl.format.Colour.WHITE );
		//fmInteger.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
		fmInteger.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN );
		fmInteger.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN );
		fmInteger.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.THIN );
		fmInteger.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.THIN );
		
		fmFloat.setBackground(jxl.format.Colour.WHITE );
		//fmFloat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
		fmFloat.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN );
		fmFloat.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN );
		fmFloat.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.THIN );
		fmFloat.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.THIN );
		
		
		jxl.write.Number number = null;
		jxl.write.Label label =null;
		jxl.write.Blank blank=null;		
		
        WritableSheet sheet = null;
    	// 시트 생성( 시트명, 인덱스 )
        WritableSheet ws = workbook.createSheet("Sheet1", 0);
        sheet = workbook.getSheet(0);
        
        int headerCount = header.size();
        
        for(int i = 0; i < headerCount; i++){
        	header.get(i);
    		label = new jxl.write.Label(i,0,String.valueOf(header.get(i) ),fmTitle);
    		sheet.setColumnView(i,20);
    		sheet.addCell(label);
        }
        
        int listCount = list.size() + 1;

        for (int i=1; i < listCount; i++) {
            List<Object> data = (List<Object>)list.get(i-1);

            int count = data.size();

            for (int j=0; j<count; j++) {
                Object cell = data.get(j);
                String cellStr;

                if (cell instanceof Date) {
                    cellStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US).format(cell);
                } else {
                    cellStr = String.valueOf(cell);
                }
                label = new jxl.write.Label(j,i,cellStr,fmBodyLeft);
    			sheet.addCell(label);
            }
        }

    }
	
}
