package com.hijackerstudios.sparrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cody on 29/03/15.
 */
public class AlbumAdapter extends BaseAdapter {


    //song list and layout
    private ArrayList<Album> songs;
    private LayoutInflater songInf;

    //constructor
    public AlbumAdapter(Context c, ArrayList<Album> theAlbums){
        songs=theAlbums;
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
                (R.layout.list_item_album, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.list_album_name_text);
        ImageView CoverView = (ImageView)songLay.findViewById(R.id.list_album_cover_image);
        //get song using position
        Album currArtist = songs.get(position);
        //get title and artist strings
        songView.setText(currArtist.getAlbum());
        CoverView.setImageBitmap(currArtist.getCover());
        //artistView.setText(currArtist.getAlbum() +" / "+ currArtist.getSongart());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}
