package com.example.tudienthuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tudienthuoc.adapter.CustomAdapter;
import com.example.tudienthuoc.model.Thuoc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BenhVienActivity extends AppCompatActivity {
    private ListView lvBenhVien;
    ArrayList<Thuoc> arrThuocs;
    CustomAdapter customAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benh_vien);
        lvBenhVien = (ListView) findViewById(R.id.lv);
        arrThuocs = new ArrayList<>();

        customAdaper = new CustomAdapter(this, R.layout.row_listview, arrThuocs);
        lvBenhVien.setAdapter(customAdaper);
        loadThuoc();
        lvBenhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("BenhVien").child(arrThuocs.get(i).getId());
                DeleteReference.removeValue();
                Toast.makeText(BenhVienActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                loadThuoc();
                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadThuoc() {
        arrThuocs.clear();
        customAdaper = new CustomAdapter(this, R.layout.row_listview, arrThuocs);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("BenhVien/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot item:dataSnapshot.getChildren())
                {
                    Thuoc thuoc = new Thuoc();
                    thuoc = item.getValue(Thuoc.class);
                    thuoc.setId(item.getKey());
                    arrThuocs.add(thuoc);
                }
                customAdaper.notifyDataSetChanged();
                lvBenhVien.setAdapter(customAdaper);

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
}
