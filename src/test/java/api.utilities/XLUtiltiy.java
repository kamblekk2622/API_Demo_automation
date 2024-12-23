package api.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class XLUtiltiy {
    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;
    public XLUtiltiy(String path){
        this.path=path;
    }
    public int getRowCount(String sheetName) throws IOException {
        fi=new FileInputStream(path);
        workbook=new XSSFWorkbook(fi);
        sheet=workbook.getSheet(sheetName);
        int rowCount=sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return rowCount;
    }
    public int getCellCount(String sheetName,int rownum) throws IOException {
        fi=new FileInputStream(path);
        workbook=new XSSFWorkbook(fi);
        sheet=workbook.getSheet(sheetName);
        row=sheet.getRow(rownum);
        int CellCount=row.getLastCellNum();
        workbook.close();
        fi.close();
        return CellCount;
    }

    public  String getCellData(String sheetName, int rownum,int colnum) throws IOException {
        fi=new FileInputStream(path);
        workbook=new XSSFWorkbook(fi);
        sheet=workbook.getSheet(sheetName);
        row=sheet.getRow(rownum);
        cell=row.getCell(colnum);
        DataFormatter formatter=new DataFormatter();
        String data;
        try{
            data=formatter.formatCellValue(cell);
        }
        catch (Exception e){
            data="";
        }
        workbook.close();
        fi.close();
        return data;
    }

    public void setCellData(String sheetName,int rownum,int colum,String data) throws IOException {
      File xlfile=new File(path);
      if(!xlfile.exists()){
          workbook=new XSSFWorkbook();
          fo=new FileOutputStream(path);
          workbook.write(fo);
      }
      fi=new FileInputStream(path);
      workbook=new XSSFWorkbook(fi);

      if(workbook.getSheetIndex(sheetName)==1)
          workbook.createSheet(sheetName);
      sheet=workbook.getSheet(sheetName);

      if(sheet.getRow(rownum)==null)
          sheet.createRow(rownum);
      row=sheet.getRow(rownum);
      cell=row.createCell(colum);
      cell.setCellValue(data);
      fo=new FileOutputStream(path);
      workbook.write(fo);
      workbook.close();
      fi.close();
      fo.close();
    }
}
