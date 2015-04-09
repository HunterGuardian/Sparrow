package com.hijackerstudios.sparrow;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by cody on 26/12/14.
 */
public class CustomPlaylist extends ListActivity {

    int playlistID;
    public MediaStoreHandler mediaStoreHandler = MediaStoreHandler.getInstance(this);
    SQLiteOpenHelper helper;

    ArrayList<Integer> ids;
    ArrayList<String> names;

    public void onCreate(Bundle savedInstanceState) {
        Log.w("Custom", "Made");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        playlistID = getIntent().getExtras().getInt("playlistID");
        displayList(playlistID);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        if (ids.get(position) == -1) {
            Intent in = new Intent(getApplicationContext(), SelectSong.class);
            in.putExtra("playlistID", playlistID);
            in.putExtra("Action", 0);
            startActivity(in);
//			startActivityForResult(in,101);
//			setResult(100,in);
//			finish();
        } else if (ids.get(position) == -2) {
            Intent in = new Intent(getApplicationContext(), SelectSong.class);
            in.putExtra("playlistID", playlistID);
            in.putExtra("Action", 1);
            startActivity(in);
//			startActivityForResult(in,101);
//			setResult(100,in);
//			finish();
        } else if (ids.get(position) == -3) {
            mediaStoreHandler.removePlaylist(playlistID);


//			Intent in = new Intent(getApplicationContext(), PlayListActivity.class);
//			startActivity(in);
//			startActivityForResult(in,101);
//			setResult(100,in);
            finish();
        }/*else {
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            in.putExtra("playListId",  playlistID);
            in.putExtra("type", 0);
            in.putExtra("songId", ids.get(position));
//			startActivity(in);
            setResult(101,in);
            finish();
        }*/
    }

    public void displayList(int playlistID) {
        ids = new ArrayList<Integer>();
        names = new ArrayList<String>();
        mediaStoreHandler.getPlayListSongs(playlistID, names, ids);
        if (playlistID == -25){

        }
        else{
            names.add(0, "+ Add a new Song");
            ids.add(0,-1);
            names.add(0, "- Remove an Existing Song");
            ids.add(0,-2);
            names.add(0, "- Delete This Playlist");
            ids.add(0,-3);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.playlist_item, names);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayList(playlistID);
    }
}
