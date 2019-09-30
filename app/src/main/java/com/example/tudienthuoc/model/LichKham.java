package com.example.tudienthuoc.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LichKham implements Parcelable {
    private String hoTen;
    private String email;
    private String diaChi;
    private String thoiGian;
    private String loaiBenh;
    private String urlSoKham;

    protected LichKham(Parcel in) {
        hoTen = in.readString();
        email = in.readString();
        diaChi = in.readString();
        thoiGian = in.readString();
        loaiBenh = in.readString();
        urlSoKham = in.readString();
    }

    public static final Creator<LichKham> CREATOR = new Creator<LichKham>() {
        @Override
        public LichKham createFromParcel(Parcel in) {
            return new LichKham(in);
        }

        @Override
        public LichKham[] newArray(int size) {
            return new LichKham[size];
        }
    };

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getUrlSoKham() {
        return urlSoKham;
    }

    public void setUrlSoKham(String urlSoKham) {
        this.urlSoKham = urlSoKham;
    }

    public String getLoaiBenh() {
        return loaiBenh;
    }

    public void setLoaiBenh(String loaiBenh) {
        this.loaiBenh = loaiBenh;
    }

    public LichKham() {
    }

    public LichKham(String hoTen, String email, String diaChi, String thoiGian, String loaiBenh, String urlSoKham) {
        this.hoTen = hoTen;
        this.email = email;
        this.diaChi = diaChi;
        this.thoiGian = thoiGian;
        this.loaiBenh = loaiBenh;
        this.urlSoKham = urlSoKham;
    }

    public LichKham(String hoTen, String email, String diaChi, String thoiGian) {
        this.hoTen = hoTen;
        this.email = email;
        this.diaChi = diaChi;
        this.thoiGian = thoiGian;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hoTen);
        parcel.writeString(email);
        parcel.writeString(diaChi);
        parcel.writeString(thoiGian);
        parcel.writeString(loaiBenh);
        parcel.writeString(urlSoKham);
    }
}
