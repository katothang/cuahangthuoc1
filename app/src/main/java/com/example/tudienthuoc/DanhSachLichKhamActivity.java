package com.example.tudienthuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tudienthuoc.adapter.CustomAdapter;
import com.example.tudienthuoc.model.LichKham;
import com.example.tudienthuoc.model.Thuoc;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhSachLichKhamActivity extends AppCompatActivity {
    private ListView lvLichKham;
    ArrayList<Thuoc> arrLichKham;
    ArrayList<LichKham> chiTietLichKham;
    CustomAdapter customAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lich_kham);
        lvLichKham = (ListView) findViewById(R.id.lv);

        arrLichKham = new ArrayList<>();

        customAdaper = new CustomAdapter(this, R.layout.row_listview, arrLichKham);
        lvLichKham.setAdapter(customAdaper);
        loadLichKham();
        lvLichKham.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showConfirm("Xác nhận xóa lịch khám", "Bạn có muốn xóa lịch khám không.?",  i);
                return true;
            }
        });

        lvLichKham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("chitietkhambenh", chiTietLichKham.get(i));
                Intent intents = new Intent(getApplicationContext(), ChiTietKhamBenhActivity.class);
                intents.putExtra("chitietkhambenh", bundle);
                startActivity(intents);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadLichKham() {
        arrLichKham.clear();
        chiTietLichKham = new ArrayList<>();
        customAdaper = new CustomAdapter(this, R.layout.row_listview, arrLichKham);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LichKham/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
                for (DataSnapshot item:dataSnapshot.getChildren())
                {

                    LichKham lichKham = new LichKham();
                    lichKham = item.getValue(LichKham.class);


                    if(lichKham.getEmail().equals(users.getEmail())){
                        Thuoc thuoc = new Thuoc();
                        thuoc.setHinhAnh(lichKham.getUrlSoKham());
                        thuoc.setTenThuoc("địa chỉ: "+lichKham.getDiaChi());
                        thuoc.setMoTa("Thời gian: "+ lichKham.getThoiGian());
                        thuoc.setId(item.getKey());
                        arrLichKham.add(thuoc);
                        chiTietLichKham.add(lichKham);
                    }


                }
                customAdaper.notifyDataSetChanged();
                lvLichKham.setAdapter(customAdaper);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void showConfirm(String title, String message, int i){

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
                        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("LichKham").child(arrLichKham.get(i).getId());
                        DeleteReference.removeValue();
                        Toast.makeText(DanhSachLichKhamActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadLichKham();
                        dialog.dismiss();

                    }
                });

        alertDialog.show();
    }
}
