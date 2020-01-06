package com.example.mc_week1_final;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Contact {
    private String name;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumeber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

class ContactList {

    private Context context;

    public ContactList(Context context) {
        this.context = context;
    }

    public ArrayList<Contact> getContactList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
        ArrayList<Contact> contactList = new ArrayList<>();

        if(contactCursor.moveToFirst()){
            do {
                String phoneNumber = contactCursor.getString(0).replaceAll("-", "");

                phoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7);

                Contact contact = new Contact();
                contact.setPhoneNumber(phoneNumber);
                contact.setName(contactCursor.getString(1));
                contactList.add(contact);
            }while (contactCursor.moveToNext());
        }
        return contactList;
    }
}