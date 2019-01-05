package com.example.admin.easyhostel;

/**
 * Created by Admin on 9/26/2018.
 */

public class studentDetails {

    private String Name;
    private String RegistarNO;

    private String Gender;
    private String Year;
    private String Fac1;

    private String SendNotification;


    public studentDetails() {
    }

    public studentDetails(String name, String registarNO, String gender, String year, String fac1,String sendNotification) {
        Name = name;
        RegistarNO = registarNO;

        Gender = gender;
        Year = year;
        Fac1= fac1;

        SendNotification=sendNotification;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegistarNO() {
        return RegistarNO;
    }

    public void setRegistarNO(String registarNO) {
        RegistarNO = registarNO;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }


    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getFac1() {
        return Fac1;
    }

    public void setFac1(String fac1) {
        Fac1 = fac1;
    }


    public String getSendNotification() {
        return SendNotification;
    }

    public void setSendNotification(String sendNotification) {
        SendNotification = getSendNotification();
    }




}
