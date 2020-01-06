package com.example.mc_week1_final;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageItem implements Parcelable {
    private String displayName;
    private String idColum;
    private int item_id;

    public ImageItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(idColum);
    }

    // 객체 받았을 때 직렬화 풀어줌
    public ImageItem(Parcel in) {
        this.displayName = in.readString();
        this.idColum = in.readString();
    }

    // Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }

    };

    public void setDisplayName(String displayName){this.displayName=displayName;}
    public void setIdColum(String idColum){this.idColum=idColum;}

    public String getDisplayName(){return this.displayName;}
    public String getIdColum(){return this.idColum;}

    public void setItem_id(int Item_id) {
        this.item_id = Item_id;
    }

}
