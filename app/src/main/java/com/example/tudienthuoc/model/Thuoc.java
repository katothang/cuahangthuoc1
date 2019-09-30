package com.example.tudienthuoc.model;

public class Thuoc {
    private String id;
    private String tenThuoc;
    private String hinhAnh;
    private String moTa;


    public String getTenThuoc() {
        return tenThuoc;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Thuoc(String tenThuoc, String hinhAnh, String moTa) {
        this.tenThuoc = tenThuoc;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
    }


    public Thuoc() {
    }
}
