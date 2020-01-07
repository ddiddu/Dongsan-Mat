package com.example.mc_week1_final;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter implements Filterable {

    private Context mContext;

    private ArrayList<ImageItem> ImageDataList;
    private ArrayList<ImageItem> filteredList;

    public ImageAdapter(Context mContext, ArrayList<ImageItem> list) {
        this.mContext = mContext;
        this.filteredList=list;
        this.ImageDataList=list;
    }

    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);

        ImageItem item=filteredList.get(position);

        //uri 로부터 사진 불러오기 (albumart)
        Intent intent=new Intent();

        Bitmap image = StringToBitmap(item.getImage());
        if(image != null) {
            imageView.setImageBitmap(image);
        }
        else {    // 이미지 없을 경우
            imageView.setImageResource(R.drawable.no_album_img);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(340, 350));
        return imageView;
    }

    //string형을 bitmap으로 변환시켜주는 함수
    private Bitmap StringToBitmap(String encodedString) {
        try{
            byte[] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
            return bitmap;
        } catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {       // performFiltering, publishResults 필수
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();      // 입력받은 값 string으로 변경
                if (charString.isEmpty()) {
                    filteredList = ImageDataList;
                }             // 검색값 없으면, 전체 연락처
                else {
                    ArrayList<ImageItem> filteringList = new ArrayList<>();   // 필터링 중, 검색된 이미지 저장할 변수
                    for (ImageItem name : ImageDataList) {                    // 반복문으로 전체 필터 체크
                        if (name.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    filteredList = filteringList;       // 검색된 리스트ㄱ
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<ImageItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}


