package com.hijackerstudios.sparrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cody on 29/03/15.
 */
public class SongAdapter extends BaseAdapter {

    //song list and layout
    private ArrayList<Songz> songs;
    private LayoutInflater songInf;

    //constructor
    public SongAdapter(Context c, ArrayList<Songz> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        RelativeLayout songLay = (RelativeLayout)songInf.inflate
                (R.layout.rowlayout, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.songz);
        TextView artistView = (TextView)songLay.findViewById(R.id.artist_name);
        //get song using position
        Songz currSong = songs.get(position);
        //get title and artist strings
        songView.setText(currSong.gettitle());
        artistView.setText(currSong.getartist());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }

}
