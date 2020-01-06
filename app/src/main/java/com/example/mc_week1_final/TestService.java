package com.example.mc_week1_final;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TestService {
////////////////////////////CONTACT
    @GET("/contacts")
    Call<ArrayList<Contact>> getAllContacts();

    @POST("/contacts")
    Call<ArrayList<Contact>> postOneContact(@Body Contact contact);

////////////////////////////PLACE
    @GET("/places/place/{place}")
    Call<ArrayList<Place>> getPlace(@Path("place") String place);
}
