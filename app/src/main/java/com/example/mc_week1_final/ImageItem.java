package com.example.mc_week1_final;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageItem implements Parcelable {
    private String title; //displayName
    private String image; //uri
    private Integer item_id;

    public ImageItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
    }

    // 객체 받았을 때 직렬화 풀어줌
    public ImageItem(Parcel in) {
        this.title = in.readString();
        this.image = in.readString();
    }

    // Creator
    public static final Creator CREATOR = new Creator() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }

    };

    public void setTitle(String displayName){this.title=displayName;}
    public void setImage(String uri){this.image=uri;}
    public void setItem_id(int Item_id) {this.item_id = Item_id;}

    public String getTitle(){return this.title;}
    public String getImage(){return this.image;}
    public int getItem_id(){return this.item_id;};
}
