package com.example.tudienthuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    LinearLayout lnLoaiThuoc,lnPhongKham, lnCongTyDuoc,lnBenhVien, lnNhaThuoc, lnThuocCuaToi,lnThemThuoc;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseAuth.AuthStateListener mAuthListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);


        //startActivity( new Intent(HomeActivity.this,TestActivity.class));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        initView();

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        else if(mUser.getEmail().equals("haivancnpm@gmail.com"))
        {
            lnThemThuoc.setVisibility(View.VISIBLE);
        }
        //Intent intent = new Intent(HomeActivity.this,ThemThuocActivity.class);
        //startActivity(intent);

        lnLoaiThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(HomeActivity.this,LoaiThuocActivity.class);
                startActivity(intent);
            }
        });
        lnPhongKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(HomeActivity.this,PhongKhamActivity.class);
                startActivity(intent);
            }
        });

        lnCongTyDuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(HomeActivity.this,CongTyDuocActivity.class);
                startActivity(intent);
            }
        });

        lnBenhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(HomeActivity.this,BenhVienActivity.class);
                startActivity(intent);
            }
        });

        lnNhaThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(HomeActivity.this,DatLichKhamActivity.class);
                startActivity(intent);
            }
        });

        lnThuocCuaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(HomeActivity.this,DanhSachLichKhamActivity.class);
                startActivity(intent);
            }
        });

        lnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XỬ CHUYỂN MÀN HÌNH
                Intent intent = new Intent(HomeActivity.this,ThemDuLieuActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        lnLoaiThuoc = (LinearLayout) findViewById(R.id.ln_thuoc);
        lnPhongKham = (LinearLayout) findViewById(R.id.ln_phongkham);
        lnCongTyDuoc = (LinearLayout) findViewById(R.id.ln_congtyduoc);
        lnBenhVien = (LinearLayout) findViewById(R.id.ln_benhvien);
        lnNhaThuoc = (LinearLayout) findViewById(R.id.ln_nhathuoc);
        lnThuocCuaToi = (LinearLayout) findViewById(R.id.ln_thuoccuatoi);
        lnThemThuoc = (LinearLayout) findViewById(R.id.ln_themdl);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        showConfirm("Xác nhận Đăng Xuất", "Bạn có muốn đăng xuất");
        return super.onOptionsItemSelected(item);
    }

    public void showConfirm(String title, String message){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hủy",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Đồng ý",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        dialog.dismiss();

                    }
                });

        alertDialog.show();
    }

}
