package com.example.acrigooo.Model;

public class Modelplanteadded {
    String nameplante,description,search,photo ,uid,QR;
            Integer maxtemp,mintemp,maxhum,minhum;
    public Modelplanteadded() {

    }

    public Modelplanteadded(String nameplante, String description, String search, String photo, String uid,Integer maxtemp,Integer mintemp,Integer maxhum,Integer minhum,String QR) {
        this.nameplante = nameplante;
        this.description = description;
        this.search = search;
        this.photo = photo;
        this.uid = uid;
        this.maxtemp=maxtemp;
        this.mintemp=mintemp; this.maxhum=maxhum; this.minhum=minhum;
        this.QR = QR;

    }

    public String getQR() {
        return QR;
    }

    public void setQR(String QR) {
        this.QR = QR;
    }

    public Integer getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(Integer maxtemp) {
        this.maxtemp = maxtemp;
    }

    public Integer getMintemp() {
        return mintemp;
    }

    public void setMintemp(Integer mintemp) {
        this.mintemp = mintemp;
    }

    public Integer getMaxhum() {
        return maxhum;
    }

    public void setMaxhum(Integer maxhum) {
        this.maxhum = maxhum;
    }

    public Integer getMinhum() {
        return minhum;
    }

    public void setMinhum(Integer minhum) {
        this.minhum = minhum;
    }

    public String getNameplante() {
        return nameplante;
    }

    public void setNameplante(String nameplante) {
        this.nameplante = nameplante;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
