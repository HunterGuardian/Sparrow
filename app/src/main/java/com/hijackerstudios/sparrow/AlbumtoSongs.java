package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cody on 27/02/15.
 */
public class AlbumtoSongs extends Activity {

    ListView AlbumSongs;

    ArrayList<String> songList2 = new ArrayList<>();
    ArrayList<Long> songDur = new ArrayList<>();
    ArrayList<Integer> songID = new ArrayList<>();

    String AtoSinfo;
    String Artimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albumtosong);

        AlbumSongs = (ListView) this.findViewById(R.id.albumsongs);
        RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.artback);

        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        AtoSinfo = getIntent().getStringExtra("TAG_ID2");
        Artimage = getIntent().getStringExtra("imagebitmap");
        MediaStoreHandler  cls2= new MediaStoreHandler(this);
        cls2.InfoAlbums(AtoSinfo, songList2, songDur, songID);
        Drawable d = new BitmapDrawable(BitmapFactory.decodeFile(Artimage));
        rLayout.setBackground(d);
        //testing.setText(Ainfo);11



        Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar3);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
        toolbar.setTitle(AtoSinfo);
        toolbar.setTitleTextColor(Color.parseColor("#FAFAFA"));
        toolbar.setBackgroundColor(randomAndroidColor);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlbumtoSongs.this.finish();
            }
        });

        AtoSadapter ASadapter = new AtoSadapter(this,AtoSinfo, songList2, songDur, songID);
        AlbumSongs.setAdapter(ASadapter);

        AlbumSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songID.get(position);
                Log.e("song", " " + songID);
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                //intent.putExtra("playListId", -1);
                intent.putExtra("type",  2);
                //intent.putExtra("TAG_SONG", SongId);
                intent.putExtra("TAG_Album", AtoSinfo);
                intent.putExtra("TAG_PATH", songID.get(position));
                startActivity(intent);
            }
        });

    }
}
