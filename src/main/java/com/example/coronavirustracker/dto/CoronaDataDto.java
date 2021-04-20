package com.example.coronavirustracker.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Configuration;

@ApiModel(description = "Details about the Total cases and new cases registered for each country")
@Configuration
public class CoronaDataDto {
    //private String State;
    @ApiModelProperty(notes = "Name of the country")
    private String Country;
    @ApiModelProperty(notes = "Total number of cases")
    private int latestTotalCases;
    @ApiModelProperty(notes = "Number of newly registered cases")
    private int differenceFromYesterday;

    public CoronaDataDto() {
    }

    public CoronaDataDto(/*String state,*/ String country, int latestTotalCases, int differenceFromYesterday) {
        //State = state;
        Country = country;
        this.latestTotalCases = latestTotalCases;
        this.differenceFromYesterday = differenceFromYesterday;
    }

    /*public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }*/

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDifferenceFromYesterday() {
        return differenceFromYesterday;
    }

    public void setDifferenceFromYesterday(int differenceFromYesterday) {
        this.differenceFromYesterday = differenceFromYesterday;
    }

    @Override
    public String toString() {
        return "CoronaDataModel{" +
                /*"State='" + State + '\'' +*/
                ", Country='" + Country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", differenceFromYesterday=" + differenceFromYesterday +
                '}';
    }
}
