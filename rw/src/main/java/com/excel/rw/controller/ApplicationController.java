package com.excel.rw.controller;

import com.excel.rw.model.ExcelModel;
import com.excel.rw.util.ExcelTOBEANUTILS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/excel/*")
public class ApplicationController {

    @Autowired
    public ExcelTOBEANUTILS excelTOBEANUTILS;

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public List<ExcelModel> list(@RequestParam("files")MultipartFile file){
     return excelTOBEANUTILS.getBeanList(file);
    }
}
