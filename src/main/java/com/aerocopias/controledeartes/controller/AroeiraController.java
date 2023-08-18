package com.aerocopias.controledeartes.controller;

public class AroeiraController {
    String id;
    String artes;
    String link;
    String data;
    String status;

    public int getId() {
        var meuID = Integer.parseInt(id);
        return meuID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtes() {
        return artes;
    }

    public void setArtes(String artes) {
        this.artes = artes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
