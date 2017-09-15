package com.company;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PoslanoSporocilo {
    private String vsebina;
    private Boolean global;
    private String prejemnik;


    private PoslanoSporocilo() {
    }

    public PoslanoSporocilo(String prejemnik, String vsebina) {
        this.global = false;
        this.prejemnik = prejemnik;
        this.vsebina = vsebina;
    }


    public PoslanoSporocilo(String vsebina) {
        this.global = true;
        this.vsebina = vsebina;
    }


    public String toString() {
        return "PoslanoSporocilo [" +
                "vsebina='" + vsebina + '\'' +
                ", global=" + global +
                ", prejemnik='" + prejemnik + '\'' +
                ']';
    }


    @JsonProperty("text")
    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    @JsonProperty("global")
    public Boolean getGlobal() {
        return global;
    }


    public void setGlobal(Boolean global) {
        this.global = global;
    }

    @JsonProperty("recipient")
    public String getPrejemnik() {
        return prejemnik;
    }


    public void setPrejemnik(String prejemnik) {
        this.prejemnik = prejemnik;
    }

}
