package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cody on 01/02/15.
 */
public class ArtistList extends Fragment {

    ArrayList<Integer> ids = new ArrayList<Integer>();
    ArrayList<Integer> ids2 = new ArrayList<Integer>();
    ArrayList<String> count = new ArrayList<String>();
    ArrayList<String> artists = new ArrayList<String>();
    ArrayList<Artist> artistList;

    String TAG_ID;

    int artistid;


    ListView lv;

    public static TapeList newInstance(String sectionNumber) {
        TapeList fragment = new TapeList();
        Bundle args = new Bundle();
        args.putString("ArtistList", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistList() {
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

        View view = inflater.inflate(R.layout.artist_list, container, false);
        lv = (ListView) view.findViewById(R.id.artistsongs);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        artistList = new ArrayList<Artist>();
        //get songs from device
        getArtistList();
        //sort alphabetically by title

        //create and set adapter
        ArtistAdapter songAdt = new ArtistAdapter(this.getActivity(), artistList);
        lv.setAdapter(songAdt);




       // ((MainActivity)getActivity()).mediaStoreHandler.getArtistcounts(artists,ids2);
        //((MainActivity)getActivity()).mediaStoreHandler.getArtistNames(count, ids);
       // ArtistAdapter adapter = new ArtistAdapter(getActivity(), artists, count);
        //lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    /*  ((MainActivity)getActivity()).mediaStoreHandler.getArtistcounts(artists,ids2);
        ((MainActivity)getActivity()).mediaStoreHandler.getArtistNames(count, ids);
        ArtistAdapter adapter = new ArtistAdapter(getActivity(), artists, count);
        lv.setAdapter(adapter); */
       // displayList();
    }

    public void displayList(){
        ids = new ArrayList<Integer>();
        artists = new ArrayList<String>();
        ((MainActivity)getActivity()).mediaStoreHandler.getArtistNames(artists, ids);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.play_list_itemlabel, artists);
        lv.setAdapter(adapter);
    }

    public void getArtistList(){
        //query external audio
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER + " ASC";
        Uri musicUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, sortOrder);
        //iterate over results if valid
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.ARTIST);
            int numberofAlbums = musicCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);

            int numberofSongs = musicCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String numberAlbums = musicCursor.getString(numberofAlbums) + " Albums";
                String numberSongs = musicCursor.getString(numberofSongs) + " Songs";
                artistList.add(new Artist(thisId, thisArtist, numberAlbums, numberSongs));
            }
            while (musicCursor.moveToNext());
        }
    }

/* public class ArtistAdapter extends BaseAdapter {

     Context context;
     private ArrayList<String> count;
     private ArrayList<String> artist;

     public ArtistAdapter(Context context, ArrayList<String> count, ArrayList<String> artist) {
         this.count = count;
         this.artist = artist;
         this.context = context;
     }

     public View getView(int position, View convertView, ViewGroup parent) {

         View v = convertView;

         if (v == null) {
             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             v = inflater.inflate(R.layout.artistlayout, null);
         }

         String a = (String) getItem(position);
         String b = (String) getItem(position);

         final TextView tv1 = (TextView) v.findViewById(R.id.artistz);
         TextView tv2 = (TextView) v.findViewById(R.id.song_count);

         tv1.setText(artist.get(position));
         tv2.setText(count.get(position));

         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 String ArtistId = artist.get(position);
                 Intent intent = new Intent(getActivity().getApplicationContext(), ArtistInfoPage.class);
                 intent.putExtra("TAG_ID", ArtistId);
                 startActivity(intent);

             }
         });




         return v;

     }

     @Override
     public int getCount() {
         // TODO Auto-generated method stub
         return artist.size();
     }

     @Override
     public Object getItem(int position) {
         if (position >= artist.size())
             return count.get(position);
         return artist.get(position);
     }

     @Override
     public long getItemId(int position) {
         // TODO Auto-generated method stub
         return position;
     }
 }*/
}
