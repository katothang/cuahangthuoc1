package com.example.tudienthuoc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tudienthuoc.R;
import com.example.tudienthuoc.model.Thuoc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Thuoc> {

    private Context context;
    private int resource;
    private List<Thuoc> arrThuocs;

    public CustomAdapter(Context context, int resource, ArrayList<Thuoc> arrThuoc) {
        super(context, resource, arrThuoc);
        this.context = context;
        this.resource = resource;
        this.arrThuocs = arrThuoc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_danh_sach, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageNews = (ImageView) convertView.findViewById(R.id.image_news);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvCategory = (TextView) convertView.findViewById(R.id.category);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Thuoc thuoc = arrThuocs.get(position);
        try{
            Picasso.get().load(thuoc.getHinhAnh()).into(viewHolder.imageNews);
        }
        catch (Exception ex){
            Log.d("LOIANH", "getView: ");
        }
        viewHolder.tvTitle.setText(thuoc.getTenThuoc());
        viewHolder.tvCategory.setText(thuoc.getMoTa());
        return convertView;
    }

    public class ViewHolder {
        ImageView imageNews;
        TextView tvTitle,tvCategory;

    }
}
