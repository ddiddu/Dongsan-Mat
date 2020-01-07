package com.example.mc_week1_final;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<ImageItem> ImageDataList;
    private ArrayList<ImageItem> filteredList;

    public ViewPagerAdapter(Context context, ArrayList<ImageItem> list) {
        this.context=context;
        this.ImageDataList=list;
        this.filteredList=list;
    }

    public int getCount() {
        return filteredList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
         return view==o;
     }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_layout,null);

        SubsamplingScaleImageView imageView=(SubsamplingScaleImageView)view.findViewById(R.id.myImageView);

        ImageItem item=filteredList.get(position);

        Bitmap image = StringToBitmap(item.getImage());
        if(image != null) {
            imageView.setImage(ImageSource.bitmap(image));
        }
        else {    // 이미지 없을 경우
            imageView.setImage(ImageSource.resource(R.drawable.no_album_img));
        }

        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(view);
        return view;
    }

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
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager=(ViewPager)container;
        View view=(View)object;
        viewPager.removeView(view);
    }
}
