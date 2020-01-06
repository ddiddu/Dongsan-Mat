package com.example.mc_week1_final;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Contact> contactModelArrayList;

    public ListViewAdapter(Context context, ArrayList<Contact> contactModelArrayList){
        this.context = context;
        this.contactModelArrayList = contactModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return contactModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        ContactItemView view = new ContactItemView(context);
        Contact item  = contactModelArrayList.get(position);
        view.setName(item.getName());
        view.setPhone(item.getPhoneNumeber());
        return view;
    }
}
