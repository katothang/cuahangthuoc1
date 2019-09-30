package com.example.tudienthuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.tudienthuoc.adapter.CustomAdapter;
import com.example.tudienthuoc.adapter.DatePickerFragment;
import com.example.tudienthuoc.model.LichKham;
import com.example.tudienthuoc.model.Thuoc;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.angmarch.views.NiceSpinner;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DatLichKhamActivity extends AppCompatActivity{
    NiceSpinner niceSpinner;
    TextView edtHoTen,edtEmail,tvThoiGian,edtDiaChi;
    Button btnDatLichKham;
    ImageView imghoadonthuoc;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef =  storage.getReferenceFromUrl("gs://tudienthuoc-d16ab.appspot.com");
    StorageReference mountainsRef = storageRef.child(SystemClock.currentThreadTimeMillis()+"consen.jpg");
    CustomAdapter customAdaper;
    static DialogFragment datePickerFragment;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseAuth.AuthStateListener mAuthListner;
    private SmartMaterialSpinner spProvince;
    private SmartMaterialSpinner spEmptyItem;
    final Calendar myCalendar = Calendar.getInstance();
    ArrayList<String> arrLoaiBenh = new ArrayList<>();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            tvThoiGian.setText(sdf.format(myCalendar.getTime()));
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich_kham);
        btnDatLichKham = findViewById(R.id.btndatlich);
        tvThoiGian = findViewById(R.id.tvdate);
        imghoadonthuoc = findViewById(R.id.imghoadonthuoc);
        edtEmail = findViewById(R.id.edtemail);
        edtHoTen = findViewById(R.id.edthoten);
        edtDiaChi = findViewById(R.id.edtdiachi);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        edtEmail.setText(mUser.getEmail());

        loadLoaiBenh();


        btnDatLichKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imghoadonthuoc.setDrawingCacheEnabled(true);
                imghoadonthuoc.buildDrawingCache();
                Bitmap bitmap = imghoadonthuoc.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "that bai", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        LichKham lichKham = new LichKham(edtHoTen.getText().toString(),edtEmail.getText().toString(),edtDiaChi.getText().toString(),tvThoiGian.getText().toString(),niceSpinner.getSelectedItem().toString(),downloadUrl.toString());
                        themLichKham(lichKham);
                    }
                });
            }
        });

        tvThoiGian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                openDatePickerDialog(v);
            }
        });


        imghoadonthuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,22);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void loadLoaiBenh() {
        arrLoaiBenh.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LoaiThuoc/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot item:dataSnapshot.getChildren())
                {
                    Thuoc thuoc = new Thuoc();
                    thuoc = item.getValue(Thuoc.class);
                    thuoc.setId(item.getKey());
                    arrLoaiBenh.add(thuoc.getTenThuoc());

                }

                niceSpinner.attachDataSource(arrLoaiBenh);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void openDatePickerDialog(final View v) {
        Calendar mcurrentDate = Calendar.getInstance();

        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mYear  = mcurrentDate.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(DatLichKhamActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                tvThoiGian.setText(date);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null)
        {
            Uri uri = data.getData();
            imghoadonthuoc.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void themLichKham(LichKham lichKham)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LichKham/"+ SystemClock.currentThreadTimeMillis());
        myRef.setValue(lichKham);
        Toast.makeText(this, "Thêm thành công lịch khám", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
