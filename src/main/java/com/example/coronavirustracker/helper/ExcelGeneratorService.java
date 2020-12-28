package com.example.coronavirustracker.helper;

import com.example.coronavirustracker.dto.CoronaDataDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExcelGeneratorService {


    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<CoronaDataDto> coronaDataDtoList;

    ExcelGeneratorService(){
        //this.coronaDataDtoList=coronaDataDtos;
        workbook= new XSSFWorkbook();
    }

    private void writeHeaderLines(){
        sheet=workbook.createSheet("Country wise COVID-19 Cases");

        Row row = sheet.createRow(0);
        CellStyle style= workbook.createCellStyle();
        XSSFFont font= workbook.createFont();

        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCustomCell(row,0,"Country",style);
        createCustomCell(row, 1, "Total Cases",style);
        createCustomCell(row,2,"Difference From Yesterday",style);

    }

    private void createCustomCell(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell=row.createCell(columnCount);

        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if(value instanceof String){
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(){
        int rowCount=1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (CoronaDataDto coronaDataDto: coronaDataDtoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCustomCell(row, columnCount++, coronaDataDto.getCountry(), style);
            createCustomCell(row, columnCount++, coronaDataDto.getLatestTotalCases(), style);
            createCustomCell(row, columnCount++, coronaDataDto.getDifferenceFromYesterday(), style);
        }
    }

    public void export(HttpServletResponse response, List<CoronaDataDto> coronaDataDtoList) throws IOException{

        //Resource resource= new Resource();
        this.coronaDataDtoList=coronaDataDtoList;
        writeHeaderLines();
        writeDataLines();
        ServletOutputStream outputStream= response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();


    }
}
