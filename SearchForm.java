package com.example;

import org.apache.struts.action.ActionForm;

public class SearchForm extends ActionForm {
    private String city;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
