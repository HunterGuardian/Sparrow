package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by cody on 15/12/14.
 */
public class TapeList extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<Song> songList;

    private int TapePage;

    ListView lv;


    public static TapeList newInstance(String sectionNumber) {
        TapeList fragment = new TapeList();
        Bundle args = new Bundle();
        args.putString("TapeList", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TapeList() {
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songList = new ArrayList<Song>();

    //    getSongList();
        //sort alphabetically by title
        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        //create and set adapter




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tape_list, container, false);
        lv = (ListView) rootView.findViewById(R.id.tapelist);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SongAdapter songAdt = new SongAdapter(getActivity(), songList);
        lv.setAdapter(songAdt);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

 /*   public void getSongList(){
        //query external audio
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        //iterate over results if valid
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }
    }*/
}
