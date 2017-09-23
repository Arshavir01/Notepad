package com.example.user.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.notepad.realm.RealmHelper;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by user on 23.09.2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private Realm realm;
    private RealmHelper helper;

    private ArrayList<String> titleList;
    private ArrayList<String> textList;
    private ArrayList<String> dateTimeList;

    public CustomAdapter(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;
    }


    @Override
    public int getCount() {
        helper = new RealmHelper(realm);
        return helper.retrieveText().size() ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.custom_layout, null);

        helper = new RealmHelper(realm);

        TextView title = (TextView)convertView.findViewById(R.id.textTitleId);
        TextView text = (TextView)convertView.findViewById(R.id.textId);
        TextView date = (TextView)convertView.findViewById(R.id.dateId);


        titleList = helper.retrieveTitle();
        textList = helper.retrieveText();
        dateTimeList = helper.retrieveDateTime();

        String[] dateStringArray = dateTimeList.toArray(new String[dateTimeList.size()]);
        date.setText(dateStringArray[position]);


        String[] titleStringArray = titleList.toArray(new String[titleList.size()]);
        title.setText(titleStringArray[position]);

        String[] textStringArray = textList.toArray(new String[textList.size()]);
        text.setText(textStringArray[position]);

        return convertView;
    }
}
