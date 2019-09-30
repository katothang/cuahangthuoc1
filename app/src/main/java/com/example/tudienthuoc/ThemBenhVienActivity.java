package com.example.tudienthuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tudienthuoc.model.Thuoc;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ThemBenhVienActivity extends AppCompatActivity {
    private ImageView imgAnhThuoc;
    private EditText edtTenThuoc;
    private EditText edtMoTa;
    private Button btnThem;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef =  storage.getReferenceFromUrl("gs://tudienthuoc-d16ab.appspot.com");
    StorageReference mountainsRef = storageRef.child(SystemClock.currentThreadTimeMillis()+"consen.jpg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_benh_vien);
        imgAnhThuoc = findViewById(R.id.imgthuoc);
        edtTenThuoc = findViewById(R.id.edttenthuoc);
        edtMoTa = findViewById(R.id.edtmota);
        btnThem = findViewById(R.id.btn_them);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        imgAnhThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,22);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data from an ImageView as bytes
                imgAnhThuoc.setDrawingCacheEnabled(true);
                imgAnhThuoc.buildDrawingCache();
                Bitmap bitmap = imgAnhThuoc.getDrawingCache();
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
                        Thuoc thuoc = new Thuoc();
                        thuoc.setTenThuoc(edtTenThuoc.getText().toString());
                        thuoc.setMoTa(edtMoTa.getText().toString());
                        thuoc.setHinhAnh(downloadUrl.toString());
                        themThuoc(thuoc);


                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null)
        {
            Uri uri = data.getData();
            imgAnhThuoc.setImageURI(uri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void themThuoc(Thuoc thuoc)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("BenhVien/"+ SystemClock.currentThreadTimeMillis());
        myRef.setValue(thuoc);
        Toast.makeText(this, "Thêm thành công bệnh viện", Toast.LENGTH_SHORT).show();
    }
}
