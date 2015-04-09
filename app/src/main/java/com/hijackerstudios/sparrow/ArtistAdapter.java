package com.hijackerstudios.sparrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cody on 29/03/15.
 */
public class ArtistAdapter extends BaseAdapter {

    //song list and layout
    private ArrayList<Artist> songs;
    private LayoutInflater songInf;

    //constructor
    public ArtistAdapter(Context c, ArrayList<Artist> theArtists){
        songs=theArtists;
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
                (R.layout.artistlayout, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.artistz);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_count);
        //get song using position
        Artist currArtist = songs.get(position);
        //get title and artist strings
        songView.setText(currArtist.getartist());
        artistView.setText(currArtist.getAlbum() +" / "+ currArtist.getSongart());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}
