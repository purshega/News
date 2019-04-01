package com.dtek.portal.models.business_trips;

public class BusinessTripTestModel {

    private String string_1;
    private String string_2;
    private String string_3;

    public BusinessTripTestModel() {
    }

    public BusinessTripTestModel(String string_1, String string_2, String string_3) {
        this.string_1 = string_1;
        this.string_2 = string_2;
        this.string_3 = string_3;
    }

    public String getString_1() {
        return string_1;
    }

    public String getString_2() {
        return string_2;
    }

    public String getString_3() {
        return string_3;
    }

    public void setString_1(String string_1) {
        this.string_1 = string_1;
    }

    public void setString_2(String string_2) {
        this.string_2 = string_2;
    }

    public void setString_3(String string_3) {
        this.string_3 = string_3;
    }
}
