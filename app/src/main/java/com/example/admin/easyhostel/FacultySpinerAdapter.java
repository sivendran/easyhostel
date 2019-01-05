package com.example.admin.easyhostel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 10/14/2018.
 */

public class FacultySpinerAdapter extends ArrayAdapter<FacultySpiner>{
    //input the clas FacultySpiner to ArrayList
    public FacultySpinerAdapter(Context context, ArrayList<FacultySpiner> FacultyList) {
        super(context, 0,FacultyList);

    }

    public FacultySpinerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }
    private View initView(int position, View covertView, ViewGroup parent ){
        if(covertView ==null){
            covertView= LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_faculty, parent,false
            );
        }

        ImageView imageViewfaculty=covertView.findViewById(R.id.imgfac);
        TextView textViewfaculty=covertView.findViewById(R.id.txt_fac_spnr);

        FacultySpiner currentItem=getItem(position);
        if(currentItem!=null) {
            imageViewfaculty.setImageResource((currentItem.getmFacultyimage()));
            textViewfaculty.setText(currentItem.getmFacultyname());
        }
        return covertView;
    }
}
