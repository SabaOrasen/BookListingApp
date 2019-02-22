package com.example.saba.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Books> {


    public BookAdapter(@NonNull Context context, @NonNull List<Books> objects) {
        super(context, 0, objects);
        Log.v("BookAdapter","BookAdapter is created");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }
        Books currentAndroidBooks = getItem(position);

        TextView title_textview = (TextView) listItemView.findViewById(R.id.title);
        title_textview.setText(currentAndroidBooks.getTitle());
        TextView subtitle_textview = (TextView) listItemView.findViewById(R.id.subtitle);
        subtitle_textview.setText(currentAndroidBooks.getSubtitle());
        TextView author_textview = (TextView) listItemView.findViewById(R.id.author);
        author_textview.setText(currentAndroidBooks.getAuthor());
        TextView year_textview = (TextView) listItemView.findViewById(R.id.year);
        year_textview.setText(formatDate(currentAndroidBooks.getYear()));
        return listItemView;
    }

    private String formatDate(String date){

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (date.length()== 4){
                dateFormat = new SimpleDateFormat("yyyy");
            }else if (date.length()==7){
                dateFormat = new SimpleDateFormat("yyyy-MM");
            }
            Date t = dateFormat.parse(date);
            SimpleDateFormat dateFormatString = new SimpleDateFormat("yyyy");
            String ts = dateFormatString.format(t);
            return ts;
        }catch (Exception e){
            Log.e("FormateDate","Formating Date not possible");
        }

        return "";
    }
}
