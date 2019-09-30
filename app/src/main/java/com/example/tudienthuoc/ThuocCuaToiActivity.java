package com.example.tudienthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.tudienthuoc.adapter.CustomAdapter;
import com.example.tudienthuoc.database.DBManager;
import com.example.tudienthuoc.database.ThuocDB;
import com.example.tudienthuoc.model.Thuoc;

import java.util.ArrayList;
import java.util.List;

public class ThuocCuaToiActivity extends AppCompatActivity {
    private ListView lvThuocCuaToi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuoc_cua_toi);
        lvThuocCuaToi = (ListView) findViewById(R.id.lv);
        ArrayList<Thuoc> arrThuocs = new ArrayList<>();
        DBManager thuocDB = new DBManager(getApplicationContext());
        arrThuocs = thuocDB.getAll();

        CustomAdapter customAdaper = new CustomAdapter(this, R.layout.row_listview, arrThuocs);
        lvThuocCuaToi.setAdapter(customAdaper);
    }
}