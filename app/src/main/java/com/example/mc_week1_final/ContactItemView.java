package com.example.mc_week1_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ContactItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;

    public ContactItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.contact_item,this,true);

        textView1 = findViewById(R.id.contact_name);
        textView2 = findViewById(R.id.contact_phone);

    }

    public  void setName(String name){
        textView1.setText(name);
    }

    public  void setPhone(String phone){
        textView2.setText(phone);
    }
}