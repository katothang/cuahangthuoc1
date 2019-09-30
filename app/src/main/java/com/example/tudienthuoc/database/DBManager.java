package com.example.tudienthuoc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tudienthuoc.model.Thuoc;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private final Context context;

    private static final String TABLE_THUOC_CUA_TOI = "thuoccuatoi";
    private static final String THUOC_CUA_TOI_ID = "ID";
    private static final String THUOC_CUA_TOI_TEN_THUOC = "thuoccuatoi_tenthuoc";
    private static final String THUOC_CUA_TOI_MO_TA = "thuoccuatoi_mota";
    private static final String THUOC_CUA_TOI_HINH_ANH = "thuoccuatoi_hinhanh";




    public DBManager(Context context) {
        super(context, "tudienthuoc",null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String sqlQuery = "CREATE TABLE "+TABLE_THUOC_CUA_TOI +" (" +
                    THUOC_CUA_TOI_ID +" integer primary key, "+
                    THUOC_CUA_TOI_TEN_THUOC + " TEXT, "+
                    THUOC_CUA_TOI_MO_TA +" TEXT, "+
                    THUOC_CUA_TOI_HINH_ANH +" TEXT)";


                db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addThuoc(Thuoc thuoc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(THUOC_CUA_TOI_TEN_THUOC, thuoc.getTenThuoc());
        values.put(THUOC_CUA_TOI_MO_TA, thuoc.getMoTa());
        values.put(THUOC_CUA_TOI_HINH_ANH, thuoc.getHinhAnh());
        db.insert(TABLE_THUOC_CUA_TOI,null,values);
        db.close();
    }


    public ArrayList<Thuoc> getAll() {
        ArrayList<Thuoc> listNews = new ArrayList<Thuoc>();
        // Select All Query
        String selectQuery = "SELECT  *  FROM " + TABLE_THUOC_CUA_TOI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Thuoc thuoc = new Thuoc();
                thuoc.setTenThuoc(cursor.getString(1));
                thuoc.setMoTa(cursor.getString(2));
                thuoc.setHinhAnh(cursor.getString(3));
                listNews.add(thuoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listNews;
    }
}
