package com.soft.web.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	/*
	    if(filePath.toUpperCase().endsWith(".XLS")) {
            readXls();
        }
        else if(filePath.toUpperCase().endsWith(".XLSX")) {
        	readXlsx();
        }

	 */
	
    private HSSFWorkbook xWorkbook;
    private XSSFWorkbook mWorkbook;
    private int mStartRow = 0;
    private int mStartCol = 0;
    private int mWidth = 5000;


    public List<Map<String, String>> readXls(String filePath) {
		Map<String, String> map = new HashMap<String, String>();
	    List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	    String cellName = "";
		try {
			FileInputStream fis = new FileInputStream(filePath);   
			POIFSFileSystem fs = new POIFSFileSystem(fis);   
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
	        HSSFSheet sheet = wb.getSheetAt(0);
	        HSSFCell cell = null;
	        
	        mStartRow = 1;
	        
	        int numOfRows = 0;
	        short numOfCells = 0;
	        
	        numOfRows = sheet.getLastRowNum();         
	      
	        for (int rowIndex = mStartRow; rowIndex <= numOfRows; rowIndex++) {      
	            HSSFRow row = sheet.getRow(rowIndex);      
	       
	            if (row == null) continue;
	            numOfCells = row.getLastCellNum();
	            
	            map = new HashMap<String, String>();
	       
	            for (short cellIndex=0 ; cellIndex < numOfCells ; cellIndex++) {
	            	cell = row.getCell(cellIndex);
	            	cellName = String.valueOf(cellIndex);
	       
	            	if(cell != null){
	       
		            	switch (cell.getCellType()) {         
		                	case HSSFCell.CELL_TYPE_BOOLEAN:
		                		map.put(cellName, String.valueOf(cell.getBooleanCellValue() ) );
		                		break;
		                	case HSSFCell.CELL_TYPE_NUMERIC:
		                		map.put(cellName, String.valueOf(cell.getBooleanCellValue() ) );
		                		break;    
		                	case HSSFCell.CELL_TYPE_STRING:
		                		map.put(cellName, cell.getStringCellValue() );
		                		break;
							case HSSFCell.CELL_TYPE_ERROR:
								map.put(cellName, String.valueOf(cell.getErrorCellValue() ) );
								break;
		                	case HSSFCell.CELL_TYPE_FORMULA:
		                		map.put(cellName, cell.getCellFormula() );
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								map.put(cellName, "" );
								break;
		                	default:         
		                		continue;         
		            	}      
	                }
	                else{
	                	map.put(cellName, "" );
	                }
	            }
	            result.add(map);
	        }
	        fis.close();
	        fis = null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

    
    
    public List<Map<String, String>> readXlsx(String filePath) {
        Map<String, String> map = new HashMap<String, String>();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        XSSFSheet sheetX  = null; //시트 가져오기
        String cellName = "";
        XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook( new FileInputStream(filePath) );
	        sheetX    = wb.getSheetAt(0);      // 시트 가져오기
	        
	        
	        mStartRow = 1;
	        
	        int numOfRows = 0;
	        int numOfCells = 0;
	        
	        numOfRows = sheetX.getPhysicalNumberOfRows();
	        
	        Row row = null;
	        Cell cell = null;
	
	        
	        /*
	         * Row만큼 반복을 한다.
	         */
	        for(int rowIndex = mStartRow; rowIndex < numOfRows; rowIndex++) {
	            row = sheetX.getRow(rowIndex);
	            
	            if(row != null) {
	                numOfCells = row.getPhysicalNumberOfCells();
	                
	                map = new HashMap<String, String>();
	                
	                for(int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
	                    cell = row.getCell(cellIndex);
	                    cellName = String.valueOf(cellIndex);
	                    
	                    if(cell != null){
							switch(cell.getCellType()){
		
								case 0:  //Cell.CELL_TYPE_NUMERIC :
			
									if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
										map.put(cellName, cell.getDateCellValue().toString() );
			
									}
									else{
										map.put(cellName, Integer.toString((int) cell.getNumericCellValue() ) );
									}	
									break;
			
								case 1: //Cell.CELL_TYPE_STRING :
									map.put(cellName, cell.getRichStringCellValue().getString() );
									break;
			
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN :
									map.put(cellName, cell.getBooleanCellValue()+"" );
									break;
			
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA :								
										map.put(cellName, cell.toString() );
			
									break;
							}
	                    }
	                    else{
	                    	map.put(cellName, "" );
	                    }
	                }
	                result.add(map);
	            }
	            
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}

        return result;
        
    }
    
    /**
     * 사용안됨
     * @param mHeader
     * @param mData
     * @return
     */
    public byte[] makeXls(List<Object> mHeader, List<List<Object>> mData) {

        IndexedColors mHeaderColor =  IndexedColors.LIGHT_CORNFLOWER_BLUE;
        IndexedColors mDataColor =  IndexedColors.WHITE;

        String mSheetName = "sheet1";
        
        InputStream mReadFile;

        xWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = xWorkbook.createSheet(mSheetName);
        HSSFRow headerRow = sheet.createRow((short)0);

        int headerCount = mHeader.size();

        for (short i=0; i<headerCount+mStartCol; i++) {
            HSSFCell headerCell = headerRow.createCell(i, HSSFCell.CELL_TYPE_STRING);
            setCell(headerCell, String.valueOf(mHeader.get(i - mStartCol)), mHeaderColor.getIndex());
            sheet.setColumnWidth(i, mWidth);
        }

        int dataCount = mData.size();

        for (short i=0+1; i<dataCount+0+1; i++) {
        	HSSFRow dataRow = sheet.createRow(i);

            List<Object> data = mData.get(i - (mStartRow + 1));

            int count = data.size();

            for (int j=mStartCol; j<count+mStartCol; j++) {
                HSSFCell dataCell = dataRow.createCell(j, HSSFCell.CELL_TYPE_STRING);

                Object cell = data.get(j - mStartCol);
                String cellStr;

                if (cell instanceof Date) {
                    cellStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US).format(cell);
                } else {
                    cellStr = String.valueOf(cell);
                }

                setCell(dataCell, cellStr, mDataColor.getIndex());
                sheet.setColumnWidth(i, mWidth);
            }
        }

        byte[] bytes = new byte[0];

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            xWorkbook.write(out);
            bytes = out.toByteArray();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString() );
        }

        return bytes;
    }

    
    public byte[] makeXlsx(List<Object> mHeader, List<List<Object>> mData) {

        IndexedColors mHeaderColor =  IndexedColors.LIGHT_CORNFLOWER_BLUE;
        IndexedColors mDataColor =  IndexedColors.WHITE;

        String mSheetName = "sheet1";
        
        InputStream mReadFile;

        mWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = mWorkbook.createSheet(mSheetName);
        XSSFRow headerRow = sheet.createRow(mStartRow);

        int headerCount = mHeader.size();

        for (int i=mStartCol; i<headerCount+mStartCol; i++) {
            XSSFCell headerCell = headerRow.createCell(i, XSSFCell.CELL_TYPE_STRING);
            setCell(headerCell, String.valueOf(mHeader.get(i - mStartCol)), mHeaderColor.getIndex());
            sheet.setColumnWidth(i, mWidth);
        }

        int dataCount = mData.size();

        for (int i=mStartRow+1; i<dataCount+mStartRow+1; i++) {
            XSSFRow dataRow = sheet.createRow(i);

            List<Object> data = mData.get(i - (mStartRow + 1));

            int count = data.size();

            for (int j=mStartCol; j<count+mStartCol; j++) {
                XSSFCell dataCell = dataRow.createCell(j, XSSFCell.CELL_TYPE_STRING);

                Object cell = data.get(j - mStartCol);
                String cellStr;

                if (cell instanceof Date) {
                    cellStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US).format(cell);
                } else {
                    cellStr = String.valueOf(cell);
                }

                setCell(dataCell, cellStr, mDataColor.getIndex());
                sheet.setColumnWidth(i, mWidth);
            }
        }

        byte[] bytes = new byte[0];

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            mWorkbook.write(out);
            bytes = out.toByteArray();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString() );
        }

        return bytes;
    }

    private void setCell(HSSFCell headerCell, String data, short index) {
        headerCell.setCellValue(data);

        HSSFCellStyle cellStyle = xWorkbook.createCellStyle();

        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(index);

        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        headerCell.setCellStyle(cellStyle);
    }

    private void setCell(XSSFCell headerCell, String data, short index) {
        headerCell.setCellValue(data);

        XSSFCellStyle cellStyle = mWorkbook.createCellStyle();

        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(index);

        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        headerCell.setCellStyle(cellStyle);
    }
}
