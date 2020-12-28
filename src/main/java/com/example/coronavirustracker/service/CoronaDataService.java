package com.example.coronavirustracker.service;

import com.example.coronavirustracker.dto.CoronaDataDto;
import com.example.coronavirustracker.exception.CoronaAppGenException;
import com.example.coronavirustracker.helper.ExcelGeneratorService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CoronaDataService {

    public static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String CONTENTTYPE = "application/octet-stream";

    @Autowired
    List<CoronaDataDto> allData = new ArrayList<>();

    @Autowired
    ExcelGeneratorService excelGeneratorService;

    public int getTotalCasesinWorld() {
        return totalCasesinWorld;
    }

    private int totalCasesinWorld=0;

    public List<CoronaDataDto> getAllData() {
        return allData;
    }

    @PostConstruct
    public void fetchVirusData() throws IOException, InterruptedException {

        List<CoronaDataDto> tempData= new ArrayList<>();

        //creating a new HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        //creating a new HTTPRequest
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();

        //creating a new HTTPResponse
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());

        //parsing the response body into StringReader format
        StringReader stringReader= new StringReader(response.body());

        Iterable<CSVRecord> records= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

       /* //temporary variable that holds data
        String tempCountry= "";
        int totalCasesInCountry = 0;*/


        //iterating all the records
        for(CSVRecord record: records){
            CoronaDataDto coronaDataModel= new CoronaDataDto();

            //Get the data of total cases in a country
            if(record.get("Province/State").equalsIgnoreCase("")) {
                coronaDataModel.setCountry(record.get("Country/Region"));


                //coronaDataModel.setState(record.get("Province/State"));

                //used for getting the data in Integer Format
                //record.get() returns a String
                int latestCases= Integer.parseInt(record.get(record.size()-1));
                int prevDayCases=Integer.parseInt(record.get(record.size()-2));

                coronaDataModel.setLatestTotalCases(latestCases);
                coronaDataModel.setDifferenceFromYesterday(latestCases-prevDayCases);


                //adding the data to the arraylist
                tempData.add(coronaDataModel);

                //Total cases reported
                totalCasesinWorld+=latestCases;
            }

        }

        this.allData=tempData;

        //System.out.println(allData);

        /*
        //Used for fetching the data uniquely from CSV file such that it shows the count only
        for(CSVRecord record: records){

            if(tempCountry==record.get("Country/Region")){
                totalCasesInCountry = totalCasesInCountry + Integer.parseInt(record.get(record.size() - 1));
            }
            else {
                totalCasesInCountry=0;
                totalCasesInCountry= Integer.parseInt(record.get(record.size()-1));
            }

        }*/

    }

    public void exportDataAsExcel(HttpServletResponse response){
        String contentType=CONTENTTYPE;
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        response.setContentType(contentType);
        String headerKey = "Content-Disposition";

        String headerValue= "attachment; filename=coronavirusdata_"+currentDateTime+".xlsx";

        response.setHeader(headerKey, headerValue);

        try {
            excelGeneratorService.export(response, this.allData);

        } catch (IOException e) {
            throw new CoronaAppGenException("Error while creating the excel file");
        }
    }



}
