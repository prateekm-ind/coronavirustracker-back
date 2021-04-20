package com.example.coronavirustracker.controller;

import com.example.coronavirustracker.dto.CoronaDataDto;
import com.example.coronavirustracker.service.CoronaDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CoronaDataController {

    @Autowired
    private CoronaDataService coronaDataService;

    /*
    * This end point shows the data of each country
    * AUTHORITY= ROLES_USER
    * */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/data")
    @ApiOperation(  value = "Find the total no of cases for each country",
                    notes = "Provides the details about the new cases and total no of cases",
                    response = CoronaDataController.class)
    public List<CoronaDataDto> read(){
        return (List<CoronaDataDto>) coronaDataService.getAllData();
    }

    /*
     * This end point shows the total number of cases in the world
     * AUTHORITY= ROLES_ADMIN
     * */
    @GetMapping("/totalcases")
    @ApiOperation(  value = "Find the total no of cases in the World",
            notes = "Provides the details about the final figure of total number of cases",
            response = Integer.class)
    public int totalCases(){
        return coronaDataService.getTotalCasesinWorld();
    }

    /*
    Creates and excel file for the data
    AUTHORITY=EVERYONE
     */
    @GetMapping("/export/excel")
    @ApiOperation(  value = "Exports the data as an EXCEL file",
            notes = "Provides the details about the new cases and total no of cases for each country")
    public void export(HttpServletResponse response){
        coronaDataService.exportDataAsExcel(response);
    }
}
