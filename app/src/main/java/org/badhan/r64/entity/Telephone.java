package org.badhan.r64.entity;

public class Telephone {
    private String countryCode = "88";
    private String number;

    public Telephone(String number, String countryCode){
        this.countryCode = countryCode;
        this.number = number;
    }

    public String getNumberWithCountryCode(){
        return countryCode + number;
    }
}
