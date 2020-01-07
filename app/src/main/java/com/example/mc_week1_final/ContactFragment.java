package com.example.mc_week1_final;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

public class ContactFragment extends Fragment{
    private ContactList contactList;
    private UseServer useServer;
    private ArrayList<Contact> arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactList = new ContactList(getContext());
        ArrayList<Contact> arrayList = contactList.getContactList();
        useServer = new UseServer();
        useServer.putContactToServer(arrayList);
        arrayList = useServer.getContactFromServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);

        if(useServer.getOK() == true){
            ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), useServer.getArrayList());
            listView.setAdapter(listViewAdapter);
        }

        return view;
    }
}