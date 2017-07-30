package io.validly;

import java.util.List;

public class Address {

    private String country;
    private String state;
    private String postCode;
    private String moveInDate;
    private List<String> addressLines;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMoveInDate() {
        return moveInDate;
    }

    public List<String> getAddressLines() {
        return addressLines;
    }

    public String getState() {
        return state;
    }
}
