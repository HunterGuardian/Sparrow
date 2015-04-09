package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by cody on 15/12/14.
 */
public class SongList extends Fragment {

    ArrayList<Integer> ids = new ArrayList<Integer>();
    ArrayList<Integer> ids2 = new ArrayList<Integer>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> artists = new ArrayList<String>();
    ArrayList<Songz> songList;


    ListView lv;

    private PlayerActivity player;

    //service
    private MediaPservice musicSrv;
    private Intent playIntent;
    //binding
    private boolean musicBound=false;

    //controller
    private MusicController controller;

    //activity and playback pause flags
    private boolean paused=false, playbackPaused=false;


    public static TapeList newInstance(String sectionNumber) {
        TapeList fragment = new TapeList();
        Bundle args = new Bundle();
        args.putString("SongList", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SongList() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.song_list, container, false);
        lv = (ListView) view.findViewById(R.id.listsongs);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        songList = new ArrayList<Songz>();
        //get songs from device
        getSongList();
        //sort alphabetically by title

        //create and set adapter
        SongAdapter songAdt = new SongAdapter(this.getActivity(), songList);
        lv.setAdapter(songAdt);





      //  ((MainActivity)getActivity()).mediaStoreHandler.getLibraryArtists(artists,ids2);
     //   ((MainActivity)getActivity()).mediaStoreHandler.getAllSongNames(names,ids);
   //     CustomAdapter adapter = new CustomAdapter(getActivity(), names, artists, ids);
       // lv.setAdapter(adapter);
    }



    @Override
    public void onResume() {
        super.onResume();

       /* ((MainActivity)getActivity()).mediaStoreHandler.getLibraryArtists(artists,ids2);
        ((MainActivity)getActivity()).mediaStoreHandler.getAllSongNames(names,ids);
        CustomAdapter adapter = new CustomAdapter(getActivity(), names, artists);
        lv.setAdapter(adapter);*/

    }





    public void getSongList(){
        //query external audio
        ContentResolver musicResolver = getActivity().getContentResolver();
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";
        Cursor musicCursor = musicResolver.query(musicUri, null, selection, null, sortOrder);
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
                songList.add(new Songz(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }
    }


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

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Songz currSong = songs.get(position);
                    //songList.get(position);
                   // songPicked(view);

                    int Song1 = Integer.parseInt(view.getTag().toString());

                    //songList.get(position);



                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    //intent.putExtra("playListId", -1);
                   // intent.putExtra("type",  2);
                    //intent.putExtra("TAG_SONG", SongId);
                    intent.putExtra("TAG_PATH", Song1);
                    startActivity(intent);

                }
            });

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

   /* //user song select
    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }*/

   /* public class CustomAdapter extends BaseAdapter{
        Context context;
        private ArrayList<String> songs;
        private ArrayList<String> artist;
        private ArrayList<Integer> id_s;

        public CustomAdapter(Context context, ArrayList<String> songs, ArrayList<String> artist, ArrayList<Integer> id_s) {
            this.songs = songs;
            this.artist = artist;
            this.id_s = id_s;
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.rowlayout, null);
            }

            String a = (String) getItem(position);
            String b = (String) getItem(position);

            TextView tv1 = (TextView) v.findViewById(R.id.songz);
            TextView tv2 = (TextView) v.findViewById(R.id.artist_name);

            tv1.setText(songs.get(position));
            tv2.setText(artist.get(position));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //int songIndex = position;
                   // Log.e("song", " " + songIndex);



                    Log.e("song", " " + ids.get(position));

                    String SongId = songs.get(position);
                    String ArtistId = artist.get(position);
                   // String TitleId = names.get(position);
                    int song_ID = id_s.get(position);
                    Intent intent = new Intent(getActivity(), AltPlayAct.class);
                    //intent.putExtra("playListId", -1);
                    intent.putExtra("type",  1);
                    intent.putExtra("TAG_SONG", SongId);
                    intent.putExtra("TAG_Artist", ArtistId);
                    intent.putExtra("TAG_PATH", song_ID);
                   // onActivityResult(null, 100, intent);
                    //getActivity().startActivityForResult(intent, 100);
                    //Intent i = new Intent(getActivity(), PlayerActivity.class);
                    startActivity(intent);



                }
            });

            return v;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return songs.size();
        }

        @Override
        public Object getItem(int position) {
            if (position >= songs.size())
                return artist.get(position);
            return songs.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }*/

  }
