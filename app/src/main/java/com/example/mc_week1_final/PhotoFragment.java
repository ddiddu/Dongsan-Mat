package com.example.mc_week1_final;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment implements TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GridView gridView;
    ImageAdapter imageAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Retrofit retrofit;
    TestService testService;

    private Button BtUpload;
    private static final int IMG_REQUEST=777;

    private ArrayList<ImageItem> imageList;
    private ArrayList<ImageItem> getImageList;
    private Gson gson;
    private Boolean ok=false;

    public PhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder().baseUrl("http://192.249.19.251:9880/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        testService=retrofit.create(TestService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        gridView = (GridView) view.findViewById(R.id.grid_view);

        //getImageFromServer();

        BtUpload = (Button) view.findViewById(R.id.upload_button);

        BtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        imageAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST){
            Uri uri=data.getData();
            ImageItem myImage = new ImageItem();
            myImage.setImage(uri.toString());

            String sortOrder = MediaStore.Images.Media._ID  + " COLLATE LOCALIZED ASC";
            Cursor cursor = getContext().getContentResolver().query(uri,null,null,null,sortOrder);
            if(cursor.moveToFirst()){
                myImage.setItem_id((int)(Math.random()*100));
                System.out.println("getItemId"+myImage.getItem_id());
                myImage.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            };

            imageList=new ArrayList<ImageItem>();
            imageList.add(myImage);
            putImageToServer(imageList);
        }
    }

    public ArrayList<ImageItem> getImageFromServer(){
        Call<ArrayList<ImageItem>> call=testService.getAllImage();

        call.enqueue(new Callback<ArrayList<ImageItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ImageItem>> call, Response<ArrayList<ImageItem>> response) {
                getImageList=new ArrayList<ImageItem>();
                imageList=response.body();
                Log.d("tag", ""+response.body().size());

                for(int i=0;i<imageList.size();i++){
                    getImageList.add(imageList.get(i));
                }

                for (int j=0;j<getImageList.size();j++){
                    getImageList.get(j).setItem_id(j);
                }

                imageAdapter = new ImageAdapter(getContext(), getImageList);

                gridView.setAdapter(imageAdapter); //context를 이 activity에서 가져오는 것

                final ArrayList<ImageItem> myImages = getImageList;

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), FullScreenActivity3.class);
                        intent.putExtra("id", position).putExtra("myImages", myImages);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<ImageItem>> call, Throwable t) {
                Log.d("asd","get failed " + t.getMessage());
            }
        });
        return imageList;
    }

    public void putImageToServer(ArrayList<ImageItem> imageItemArrayList){
        Call<ArrayList<ImageItem>> call=testService.uploadOneImage(imageItemArrayList.get(0));

        call.enqueue(new Callback<ArrayList<ImageItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ImageItem>> call, Response<ArrayList<ImageItem>> response) {
                System.out.println("connection success");
            }

            @Override
            public void onFailure(Call<ArrayList<ImageItem>> call, Throwable t) {
                System.out.println("connection failed" + t.getMessage());
            }
        });
    }
}
