package com.example.tudienthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ThemDuLieuActivity extends AppCompatActivity {
    LinearLayout lnThemLoaiThuoc,lnThemPhongKham, lnThemCongTyDuoc,lnThemBenhVien, lnThemNhaThuoc, lnThemThuocCuaToi,lnThemThuoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_du_lieu);
        initView();
        lnThemLoaiThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(ThemDuLieuActivity.this,ThemLoaiThuocActivity.class);
                startActivity(intent);
            }
        });
        lnThemPhongKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(ThemDuLieuActivity.this,ThemPhongKhamActivity.class);
                startActivity(intent);
            }
        });

        lnThemCongTyDuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(ThemDuLieuActivity.this,ThemCongTyDuocActivity.class);
                startActivity(intent);
            }
        });

        lnThemBenhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(ThemDuLieuActivity.this,ThemBenhVienActivity.class);
                startActivity(intent);
            }
        });

        lnThemNhaThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(ThemDuLieuActivity.this,ThemNhaThuocActivity.class);
                startActivity(intent);
            }
        });

        lnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(ThemDuLieuActivity.this,ThemThuocActivity.class);
                startActivity(intent);
            }
        });




    }
    private void initView() {
        lnThemLoaiThuoc = (LinearLayout) findViewById(R.id.ln_themloaithuoc);
        lnThemPhongKham = (LinearLayout) findViewById(R.id.ln_themphongkham);
        lnThemCongTyDuoc = (LinearLayout) findViewById(R.id.ln_themcongtyduoc);
        lnThemBenhVien = (LinearLayout) findViewById(R.id.ln_thembenhvien);
        lnThemNhaThuoc = (LinearLayout) findViewById(R.id.ln_themnhathuoc);
        lnThemThuoc = (LinearLayout) findViewById(R.id.ln_themthuoc);
    }
}
