package com.example.tudienthuoc;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tudienthuoc.model.LichKham;
import com.squareup.picasso.Picasso;

public class ChiTietKhamBenhActivity extends AppCompatActivity {

    TextView tvChiTiet;
    ImageView imgChiTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_kham_benh);
        tvChiTiet = findViewById(R.id.tvchitiet);
        imgChiTiet = findViewById(R.id.imganh);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("chitietkhambenh")) {
            Bundle bundle = intent.getBundleExtra("chitietkhambenh");
            if (bundle != null && bundle.containsKey("chitietkhambenh")) {
                LichKham lichKham = bundle.getParcelable("chitietkhambenh");
                Picasso.get().load(lichKham.getUrlSoKham()).into(imgChiTiet);
                tvChiTiet.setText(Html.fromHtml(String.format("<b>Tên Bác Sĩ: <i> %s </i>  <br><br> Địa Chỉ: <i> %s </i> <br><br> Thời Gian Tái Khám: <i> %s </i>  <br><br> Loại Bệnh: <i> %s </i></b>",lichKham.getHoTen(),lichKham.getDiaChi(),lichKham.getThoiGian(),lichKham.getLoaiBenh())));
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
