package com.example.admin.easyhostel;

/**
 * Created by Admin on 10/21/2018.
 */

public class Set_Notification {

    String spinnerFaculty;
    String  spinnerGender;
    String spinnerYear;

    public Set_Notification() {
    }

    public Set_Notification(String spinnerFaculty, String spinnerGender, String spinnerYear) {
        this.spinnerFaculty = spinnerFaculty;
        this.spinnerGender = spinnerGender;
        this.spinnerYear = spinnerYear;
    }

    public String getSpinnerFaculty() {
        return spinnerFaculty;
    }

    public void setSpinnerFaculty(String spinnerFaculty) {
        this.spinnerFaculty = spinnerFaculty;
    }

    public String getSpinnerGender() {
        return spinnerGender;
    }

    public void setSpinnerGender(String spinnerGender) {
        this.spinnerGender = spinnerGender;
    }

    public String getSpinnerYear() {
        return spinnerYear;
    }

    public void setSpinnerYear(String spinnerYear) {
        this.spinnerYear = spinnerYear;
    }
}
