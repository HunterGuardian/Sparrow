package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cody on 07/02/15.
 */
public class AlbumList extends Fragment{

    ArrayList<String> album = new ArrayList<String>();
    ArrayList<String> art = new ArrayList<String>();
    ArrayList<Album> albumList;


    GridView lv;

    public static TapeList newInstance(String sectionNumber) {
        TapeList fragment = new TapeList();
        Bundle args = new Bundle();
        args.putString("SongList", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumList() {
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

        //AlbumsAdapter adapter = new AlbumsAdapter(MediaStoreHandler.getAlbums(getActivity()));
        //setListAdapter(adapter);

        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.album_list, container, false);
        lv = (GridView) view.findViewById(R.id.listalbums);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        albumList = new ArrayList<Album>();
        //get songs from device
        getAlbumList();
        //sort alphabetically by title

        //create and set adapter
        AlbumAdapter songAdt = new AlbumAdapter(this.getActivity(), albumList);
        lv.setAdapter(songAdt);

       // ((MainActivity)getActivity()).mediaStoreHandler.DisplayAlbums(album, art);
       // AlbumsAdapter adapter = new AlbumsAdapter(getActivity(), album, art);
        //((MainActivity)getActivity()).mediaStoreHandler.getLibraryArtists(artists,ids2);
        //((MainActivity)getActivity()).mediaStoreHandler.getAllSongNames(names,ids);
        //CustomAdapter adapter = new CustomAdapter(getActivity(), names, artists);
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

    public void getAlbumList(){
        //query external audio
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String sortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER + " ASC";
        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, sortOrder);
        //iterate over results if valid
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int albumArt = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String Albumart = musicCursor.getString(albumArt);
                //String numberSongs = musicCursor.getString(numberofSongs) + " Songs";
                albumList.add(new Album(thisId, thisArtist, Albumart));
            }
            while (musicCursor.moveToNext());
        }
    }


 /*   public class AlbumsAdapter extends BaseAdapter {

        Context context;
        private ArrayList<String> album;
        private ArrayList<String> image;

        public AlbumsAdapter(Context context, ArrayList<String> album, ArrayList<String> image) {
            this.album = album;
            this.image = image;
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_item_album, null);
            }

            String a = (String) getItem(position);
            String b = (String) getItem(position);

            final TextView tv1 = (TextView) v.findViewById(R.id.list_album_name_text);
            ImageView tv2 = (ImageView) v.findViewById(R.id.list_album_cover_image);

            tv1.setText(album.get(position));

            if (image.get(position) == null) {

                tv2.setImageResource(R.drawable.ic_audiotrack_black_48dp);
                // Log.e("song", " " + image);
                //AlbumArt.add(imageUri);
                //AlbumArt.add(q.getString(2));
                //AlbumArt.remove(null);
            } else {

                tv2.setImageBitmap(BitmapFactory.decodeFile(image.get(position)));

                //AlbumArt.add(q.getString(2));

            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String AlbumId = album.get(position);
                    String cover = image.get(position);
                    Intent intent = new Intent(AlbumList.this.getActivity(), AlbumtoSongs.class);
                    intent.putExtra("TAG_ID2", AlbumId);
                    intent.putExtra("imagebitmap", cover);
                    Log.e("song", " " + cover);
                    startActivity(intent);

                }
            });




            return v;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return album.size();
        }

        @Override
        public Object getItem(int position) {
            if (position >= album.size())
                return image.get(position);
            return album.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }*/

}
