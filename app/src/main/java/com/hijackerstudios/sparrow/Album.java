package com.hijackerstudios.sparrow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by cody on 07/02/15.
 */
public class Album {

    private long mID;
    private String mArtist;
    private String mName;
    private String mCover;

    public Album (long ID, String name, String cover) {
        mID = ID;
        mName = name;
        mCover = cover;
    }

    public long getID () {
        return mID;
    }

    public String getAlbum () {
        return mName;
    }

    public Bitmap getCover () {
        return BitmapFactory.decodeFile(mCover);
    }
}
