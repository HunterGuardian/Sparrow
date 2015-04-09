package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by cody on 19/03/15.
 */
public class AltPlayAct extends Activity{

    private final Handler handler = new Handler();
    private Intent intent;
    private Resources res;
    private String palbumkey="";
    boolean donotrefresh=false;
    int playing=2;
    public ArrayList<String> songList;
    private ListView songView;
    String[] sarkilarlistesi;
    //service
    public MediaPservice musicSrv;
    private Intent playIntent;
    private Bitmap albumart;
    //binding
    private boolean musicBound=false;
    ImageView imgvStop;
    int PlayType, test;
    MediaStoreHandler mediaStoreHandler = MediaStoreHandler.getInstance((Activity) this);
    ArrayList<Integer> songsList;
    int songIndex;
    private ImageView btnPlay,btnNext,btnPrevious;

    //controller


    //activity and playback pause flags
    private boolean paused=false, playbackPaused=false;
    Handler updateViewHandler = new Handler();
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPservice.MusicBinder binder = (MediaPservice.MusicBinder)service;
            Log.e("AltPlayAct", " " + "serviceconnected");
            //get service
            musicSrv = binder.getService();

            musicBound = true;
            updateViewFromService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Log.e("AltPlayAct", " " + "onStart");
        res=getResources();
        if(playIntent==null){
            playIntent = new Intent(getApplicationContext(), MediaPservice.class);
            getApplicationContext().bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            getApplicationContext().startService(playIntent);
            // setBarColor(0xFF666666);
        }

    }


    @Override
    public void onPause(){
        super.onPause();
        donotrefresh=true;


    }
    @Override
    public void onResume(){
        super.onResume();
        donotrefresh=false;
        if(musicBound) {
            updateViewFromService();
        }
        // recycleAlbumArt();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        donotrefresh=true;

    }

    @Override
    public void onStop(){
        super.onStop();
        donotrefresh=true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("AltPlayAct", " " + "onCreate");
        setContentView(R.layout.player_activity_page);
        songsList = new ArrayList<Integer>();
        ArrayList<String> Library = new ArrayList<String>();
        mediaStoreHandler.getAllSongNames(Library, songsList);
        test = getIntent().getIntExtra("TAG_PATH", 0);
        songIndex = songsList.indexOf(test);
        Log.e("song", " " + songIndex);

        PlayType = getIntent().getIntExtra("type", 0);
        test = getIntent().getIntExtra("TAG_PATH", 0);
        Log.e("song", " " + test);

        Intent servicestart = new Intent(this, MediaPservice.class);
        servicestart.putExtra("typez", PlayType);
        servicestart.putExtra("TAG_PATHZ", test);
        startService(servicestart);

        btnPlay = (ImageView) findViewById(R.id.play_id);
        btnNext = (ImageView) findViewById(R.id.skip_next_id);
        btnPrevious = (ImageView) findViewById(R.id.skip_prev_id);


      /*  btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                //mp.reset();
                // mp.release();
                if(!musicSrv.isPlaying()){
                    Log.e("song", " wtf is going on ");
                }

                if (musicSrv.isPlaying()) {
                    //btnPlay.setImageResource(R.drawable.ic_play_arrow_grey600_36dp);
                    if (musicSrv != null) {
                        musicSrv.pause();
                        musicSrv.release();
//                         musicSrv.pausePlayer();
                        //mp.stop();
                        //musicSrv.pausePlayer();
                        // Changing button image to play button
                        // btnPlay.setImageResource(R.drawable.);
                        btnPlay.setImageResource(R.drawable.ic_play_arrow_grey600_36dp);
                    }
                } else {
                    // Resume song
                    if (musicSrv != null) {
                        musicSrv.start();
//                         musicSrv.isPng();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.ic_pause_grey600_36dp);
                    }

                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check if next song is there or not

                Log.e("next", " wtf is going on ");

                musicSrv.Next();

            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                musicSrv.Prev();

            }
        });*/


    }

Runnable run = new Runnable() {

    @Override
    public void run() {
        updateViewFromService();
    }
};

    public void updateViewFromService(){




    }


  /*  public void onControlClicked(View v) {

        String command = v.getTag().toString();
        if(command.equals("play_id")){
            if(musicSrv.isPlaying()) {
                musicSrv.pause();
                imgvStop.setImageResource(R.drawable.ic_play_arrow_grey600_36dp);
                playing=0;
            }
            else{
                musicSrv.start();
                if(musicSrv.isPlaying()) {
                    imgvStop.setImageResource(R.drawable.ic_pause_grey600_36dp);
                    playing = 1;
                }
            }
        }
        else if(command.equals("rewind")){
            if(musicSrv.isPrepared()) {
                musicSrv.rewind();
            }

        }
        else if(command.equals("forward")){
            if(musicSrv.isPrepared()) {
                musicSrv.forward();
            }
        }
        else if(command.equals("skip_next")){
            if(musicSrv.isPrepared()) {
                musicSrv.Next();
            }
        }
        else if(command.equals("skip_prev_id")){
            if(musicSrv.isPrepared()) {
                musicSrv.Prev();
            }
        }


    }*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

private Drawable.Callback drawableCallback = new Drawable.Callback() {
    @Override
    public void invalidateDrawable(Drawable who) {

    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        handler.postAtTime(what, when);
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        handler.removeCallbacks(what);
    }
};
}
