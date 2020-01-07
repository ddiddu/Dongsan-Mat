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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    private ArrayList<Marker> markerArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.249.19.251:9880").addConverterFactory(GsonConverterFactory.create(gson)).build();
        testService = retrofit.create(TestService.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        button = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(markerArrayList != null && markerArrayList.size() != 0 && googleMap != null){
                    for(int i = 0; i < markerArrayList.size(); i++){
                        markerArrayList.get(i).remove();
                    }
                }
                markerArrayList =  new ArrayList<>();
                String p = editText.getText().toString();
                if(!p.equals("")){
                    Call<ArrayList<Place>> call = testService.getPlace(p);
                    new NetworkCall().execute(call);
                }

                if(arrayok == true && googleMap != null){
                    if(arrayList != null){
                        LatLng[] places = new LatLng[arrayList.size()];
;                        for(int i = 0; i < arrayList.size(); i++){
                            places[i] = new LatLng(Double.parseDouble(arrayList.get(i).getLatitude()), Double.parseDouble(arrayList.get(i).getLongitude()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(places[i]).title(p);
                            markerArrayList.add(googleMap.addMarker(markerOptions));
                        }
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(places[0]));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(2));
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
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.mapView);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap1) {
        this.googleMap = googleMap1;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(33.396572, 104.538323)));
        this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(4));
        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
                googleMap.moveCamera(zoom);
                return false;
            }
        });
    }



    private class NetworkCall extends AsyncTask<Call, Void, String> {
        @Override
        protected String doInBackground(Call... calls) {
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
