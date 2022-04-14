package com.excel.rw.util;

import com.excel.rw.model.ExcelModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExcelTOBEANUTILS {


    public  List<ExcelModel> getBeanList(MultipartFile files){
        String fileName= files.getOriginalFilename();
        Workbook workbook=null;
        try {
            if (files instanceof File) {
                FileInputStream excelInputStream = new FileInputStream((File) files);
                 if(fileName.contains(".xlsx"))
                     workbook= new XSSFWorkbook(excelInputStream);
                 else if (fileName.contains(".xls"))
                     workbook=new HSSFWorkbook(excelInputStream);
                 else {
                     excelInputStream.close();
                     throw new RuntimeException("The file you have requested for must be in .xlsx or .xls format. ");
                 }
            }
            if(files instanceof MultipartFile){
                System.out.printf(" instance of multipart");
                byte[] fileByteArray=((MultipartFile) files).getBytes();
                InputStream finStream= new ByteArrayInputStream(fileByteArray);
                if(fileName.contains(".xlsx"))
                    workbook= new XSSFWorkbook(finStream);
                else if(fileName.contains(".xls"))
                    workbook= new HSSFWorkbook(finStream);
                else {
                    finStream.close();
                    throw new RuntimeException("The file you have requested for must be in .xlsx or .xls format. ");
                   }

                ExcelModel excelModel= new ExcelModel();
                List<ExcelModel> list= new ArrayList<>();
                List<ExcelModel> shortedList= new ArrayList<>();
                Sheet sheet= workbook.getSheetAt(0);

                FormulaEvaluator evaluator=workbook.getCreationHelper().createFormulaEvaluator();
                for(int i=1; i<sheet.getPhysicalNumberOfRows();i++){
                    excelModel= new ExcelModel();
                    Row row=sheet.getRow(i);
                    //if row is not empty
                    if(row!=null) {

                        Cell c0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (c0 != null) {
                            DataFormatter formatter = new DataFormatter();
                            String msn = formatter.formatCellValue(c0, evaluator).toString();
                            excelModel.setMsn(msn);
                        }
                        Cell c1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (c1 != null) {
                            DataFormatter formatter = new DataFormatter();
                            String taxonomyCode = formatter.formatCellValue(c1, evaluator).toString();
                            excelModel.setTaxonomyCode(taxonomyCode);
                        }
                        Cell c2 = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (c2 != null) {
                            DataFormatter formatter = new DataFormatter();
                            String productNamme = formatter.formatCellValue(c2, evaluator).toString();
                            excelModel.setProductName(productNamme);
                        }
                        list.add(excelModel);
                    }
                }
                //short the list basead on the categoyCode
                getshortList(list,shortedList);
                return shortedList;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void getshortList(List<ExcelModel> list,List<ExcelModel> shortedList) {
       shortedList.addAll(  list.stream().sorted(Comparator.comparing(ExcelModel::getTaxonomyCode)).collect(Collectors.toList()));
    }
}
