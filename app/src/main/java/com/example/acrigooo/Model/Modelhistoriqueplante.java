package com.example.acrigooo.Model;

public class Modelhistoriqueplante {
    String hum,temp;

    public Modelhistoriqueplante() {

    }

    public Modelhistoriqueplante(String hum, String temp) {
        this.hum = hum;
        this.temp = temp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
