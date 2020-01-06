package com.example.mc_week1_final;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UseServer {
    private ArrayList<Contact> arrayList;
    private Gson gson;
    private Retrofit retrofit;
    private TestService testService;
    private Boolean ok = false;

    public UseServer() {
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl("http://192.249.19.251:9880").addConverterFactory(GsonConverterFactory.create(gson)).build();
        testService = retrofit.create(TestService.class);
    }

    public ArrayList<Contact> getContactFromServer(){

        Call<ArrayList<Contact>> call = testService.getAllContacts();

        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                arrayList = response.body();
                ok = true;
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
            }
        });

        return arrayList;
    }

    public void putContactToServer(ArrayList<Contact> fromContact){
        int size = fromContact.size();
        for(int i = 0; i < size; i++){
            Call<ArrayList<Contact>> call = testService.postOneContact(fromContact.get(i));
            call.enqueue(new Callback<ArrayList<Contact>>() {
                @Override
                public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                }

                @Override
                public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                }
            });
        }
    }

    public boolean getOK(){
        return ok;
    }

    public ArrayList<Contact> getArrayList(){
        return arrayList;
    }
}
