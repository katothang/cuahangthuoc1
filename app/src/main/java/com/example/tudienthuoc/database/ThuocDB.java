package com.example.tudienthuoc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tudienthuoc.model.Thuoc;

import java.util.ArrayList;
import java.util.List;


public class ThuocDB extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "TuDienThuoc";


    // Tên bảng: Note.
    private static final String TABLE_LOAI_THUOC = "LoaiThuoc";
    private static final String LOAI_THUOC_ID = "loaithuoc_Id";
    private static final String LOAI_THUOC_TITLE = "loaithuoc_Title";
    private static final String LOAI_THUOC_MO_TA = "loaithuoc_mota";

    private static final String TABLE_THUOC = "Thuoc";
    private static final String THUOC_ID = "thuoc_Id";
    private static final String THUOC_TITLE = "thuoc_Title";
    private static final String THUOC_MO_TA = "thuoc_mota";

    private static final String TABLE_BENH_VIEN = "benhvien";
    private static final String BENH_VIEN_ID = "benhvien_Id";
    private static final String BENH_VIEN_TITLE = "benhvien_Title";
    private static final String BENH_VIEN_MO_TA = "benhvien_mota";

    private static final String TABLE_CONG_TY_DUOC = "congtyduoc";
    private static final String CONG_TY_DUOC_ID = "congtyduoc_Id";
    private static final String CONG_TY_DUOC_TITLE = "congtyduoc_Title";
    private static final String CONG_TY_DUOC_MO_TA = "congtyduoc_mota";

    private static final String TABLE_NHA_THUOC = "nhathuoc";
    private static final String NHA_THUOC_ID = "nhathuoc_Id";
    private static final String NHA_THUOC_TITLE = "nhathuoc_Title";
    private static final String NHA_THUOC_MO_TA = "nhathuoc_mota";

    private static final String TABLE_PHONG_KHAM = "phongkham";
    private static final String PHONG_KHAM_ID = "phongkham_Id";
    private static final String PHONG_KHAM_TITLE = "phongkham_Title";
    private static final String PHONG_KHAM_MO_TA = "phongkham_mota";

    private static final String TABLE_THUOC_CUA_TOI = "thuoccuatoi";
    private static final String THUOC_CUA_TOI_ID = "thuoccuatoi_Id";
    private static final String THUOC_CUA_TOI_TEN_THUOC = "thuoccuatoi_tenthuoc";
    private static final String THUOC_CUA_TOI_MO_TA = "thuoccuatoi_mota";
    private static final String THUOC_CUA_TOI_HINH_ANH = "thuoccuatoi_hinhanh";


    public ThuocDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_THUOC_CUA_TOI + "("
                + THUOC_CUA_TOI_ID + " INTEGER PRIMARY KEY,"
                + THUOC_CUA_TOI_TEN_THUOC + " TEXT, "
                + THUOC_CUA_TOI_MO_TA + " TEXT" + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUOC_CUA_TOI);


        // Và tạo lại.
        onCreate(db);
    }
    public void addThuocCuaToi(Thuoc thuoc) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(THUOC_CUA_TOI_TEN_THUOC, thuoc.getTenThuoc());
        values.put(THUOC_CUA_TOI_MO_TA, thuoc.getMoTa());
        values.put(THUOC_CUA_TOI_HINH_ANH, thuoc.getHinhAnh());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_THUOC_CUA_TOI, null, values);


        // Đóng kết nối database.
        db.close();
    }
    public List<Thuoc> getAllThuocCuaToi() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<Thuoc> thuocList = new ArrayList<Thuoc>();
        // Select All Query
        String selectQuery = "SELECT  * FROM  " + TABLE_THUOC_CUA_TOI  ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Thuoc thuoc = new Thuoc();
                thuoc.setTenThuoc(cursor.getString(1));
                thuoc.setMoTa(cursor.getString(2));
                thuoc.setHinhAnh(cursor.getString(3));


                // Thêm vào danh sách.
                thuocList.add(thuoc);
            } while (cursor.moveToNext());
        }
        return thuocList;
    }
}
