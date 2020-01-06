package com.example.mc_week1_final;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;
    private EditText editText;
    private Button button;
    private ArrayList<Place> arrayList;
    private boolean arrayok = false;
    private TestService testService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("악1", "oncreate");
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.249.19.251:9880").addConverterFactory(GsonConverterFactory.create(gson)).build();
        testService = retrofit.create(TestService.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("악2", "oncreateview");
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        button = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("악6", "onclick");
                String p = editText.getText().toString();
                if(!p.equals("")){
                    Log.d("악7", "null");
                    Call<ArrayList<Place>> call = testService.getPlace(p);
                    new NetworkCall().execute(call);
                }

                if(arrayok == true && googleMap != null){
                    if(arrayList != null){
                        Log.d("악8", "진짜" + arrayList.get(0).getLatitude());
                        LatLng place = new LatLng(Double.parseDouble(arrayList.get(0).getLatitude()), Double.parseDouble(arrayList.get(0).getLongitude()));
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(place);
                        googleMap.addMarker(markerOptions);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(4));
                        arrayok = false;
                        arrayList = null;
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("악3", "onviewcreate");
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.mapView);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("악4", "onmapready");
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    private class NetworkCall extends AsyncTask<Call, Void, String> {
        @Override
        protected String doInBackground(Call... calls) {
            Log.d("악5", "doinbackground");
            try{
                Call<ArrayList<Place>> call = calls[0];
                Response<ArrayList<Place>> response = call.execute();
                arrayList = response.body();
                arrayok = true;
                return "good";
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
