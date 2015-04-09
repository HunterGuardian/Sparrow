package com.hijackerstudios.sparrow;

import android.media.MediaPlayer;

/**
 * Created by cody on 07/03/15.
 */
public class MediaPlayerSat extends MediaPlayer{

    static MediaPlayerSat mySelf;
    public static MediaPlayerSat getInstance(){
        if(mySelf == null){
            mySelf = new MediaPlayerSat();
        }
        return mySelf;
    }
}
