package com.example.mc_week1_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactFragment extends Fragment{
    private ContactList contactList;
    private UseServer useServer;
    private ArrayList<Contact> arrayList;
    private Context context;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactList = new ContactList(getContext());
        ArrayList<Contact> arrayList = contactList.getContactList();
        useServer = new UseServer();
        useServer.putContactToServer(arrayList);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        listView = (ListView) view.findViewById(R.id.listView);


        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.249.19.251:9880").addConverterFactory(GsonConverterFactory.create(gson)).build();
        TestService testService = retrofit.create(TestService.class);
        Call<ArrayList<Contact>> call = testService.getAllContacts();

        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                arrayList = response.body();
                ListViewAdapter listViewAdapter = new ListViewAdapter(context, arrayList);
                listView.setAdapter(listViewAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
            }
        });

        return view;
    }
}