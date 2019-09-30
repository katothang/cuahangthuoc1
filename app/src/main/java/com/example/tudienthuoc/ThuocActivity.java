package com.example.tudienthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.tudienthuoc.adapter.CustomAdapter;
import com.example.tudienthuoc.model.Thuoc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.example.tudienthuoc.database.DBManager;
import android.content.DialogInterface;

import java.util.ArrayList;

public class ThuocActivity extends AppCompatActivity {
    private ListView lvThuoc;
    private StorageReference mStorageRef;
    ArrayList<Thuoc> arrThuocs = new ArrayList<>();
    CustomAdapter customAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuoc);
        lvThuoc = (ListView) findViewById(R.id.lv);
        arrThuocs = new ArrayList<>();

        customAdaper = new CustomAdapter(this, R.layout.row_listview, arrThuocs);
        lvThuoc.setAdapter(customAdaper);

        loadThuoc();
        customAdaper = new CustomAdapter(this, R.layout.row_listview, arrThuocs);
        lvThuoc.setAdapter(customAdaper);

        lvThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(ThuocActivity.this).create();
                alertDialog.setTitle("Thêm Thuốc Của Tôi");
                alertDialog.setMessage("Bạn có chắc muốn thêm vào danh sách của tôi");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Xóa",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Thuoc").child(arrThuocs.get(position).getId());
                                DeleteReference.removeValue();
                                Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                loadThuoc();
                                customAdaper = new CustomAdapter(getApplicationContext(), R.layout.row_listview, arrThuocs);
                                lvThuoc.setAdapter(customAdaper);
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Thêm",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DBManager thuocDB = new DBManager(getApplicationContext());
                                thuocDB.addThuoc(arrThuocs.get(position));
                                Toast.makeText(ThuocActivity.this, "Thêm Thành Công vào thuốc của tôi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();

            }
        });

    }

    private void loadThuoc() {
        arrThuocs.clear();
        customAdaper = new CustomAdapter(this, R.layout.row_listview, arrThuocs);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Thuoc/");

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
                lvThuoc.setAdapter(customAdaper);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}