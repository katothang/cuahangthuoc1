package com.example.tudienthuoc.common;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tudienthuoc.R;
import com.example.tudienthuoc.ThuocActivity;
import com.example.tudienthuoc.adapter.CustomAdapter;
import com.example.tudienthuoc.database.DBManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmUtils {
    private static Boolean isCheckConfirm = false;

    public static Boolean getCheckConfirm() {
        return isCheckConfirm;
    }

    public static void setCheckConfirm(Boolean checkConfirm) {
        isCheckConfirm = checkConfirm;
    }

    public static void showConfirm(Context context, String title, String message){

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hủy",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        setCheckConfirm(false);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Đồng ý",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setCheckConfirm(true);
                    }
                });

        alertDialog.show();
    }
    }
