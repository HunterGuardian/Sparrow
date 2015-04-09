package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cody on 18/02/15.
 */
public class ArtistInfoPage extends Activity{

    //TextView testing;
    GridView info_list;

    ArrayList<String> testList2 = new ArrayList<>();
    ArrayList<String> imageArt = new ArrayList<>();

    String Ainfo;
    //String Tag_AID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_info_page);

      //  testing = (TextView) this.findViewById(R.id.test);
        info_list = (GridView) this.findViewById(R.id.new_albumList);

        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        Ainfo = getIntent().getStringExtra("TAG_ID");
        MediaStoreHandler  cls2= new MediaStoreHandler(this);
        cls2.InfoArtist(Ainfo, testList2, imageArt);
        //testing.setText(Ainfo);



        Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar2);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
        toolbar.setTitle(Ainfo);
        toolbar.setTitleTextColor(Color.parseColor("#FAFAFA"));
        toolbar.setBackgroundColor(randomAndroidColor);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArtistInfoPage.this.finish();
            }
        });
        //this.setSupportActionBar(toolbar);

        //Intent info = getIntent();


        //testList2 = MediaStoreHandler.testList;

      // Log.e("song", " " + testList2);

        GridAdapter adapter = new GridAdapter(this, testList2, imageArt);
        info_list.setAdapter(adapter);

        info_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String ArtistId = testList2.get(position);
               String image= imageArt.get(position);
               Intent intent = new Intent(ArtistInfoPage.this, AlbumtoSongs.class);
               intent.putExtra("TAG_ID2", ArtistId);
               intent.putExtra("imagebitmap", image);
               Log.e("song", " " + image);
               startActivity(intent);

           }
       });
    }
}
