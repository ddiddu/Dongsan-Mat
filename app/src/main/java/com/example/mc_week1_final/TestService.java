package com.example.mc_week1_final;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TestService {

    @GET("/contacts")
    Call<ArrayList<Contact>> getAllContacts();

    @POST("/contacts")
    Call<ArrayList<Contact>> postOneContact(@Body Contact contact);
}
