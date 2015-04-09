package com.hijackerstudios.sparrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by cody on 27/02/15.
 */
public class AtoSadapter extends BaseAdapter {

    Context context;

    private ArrayList<Integer> song_ID;
    private ArrayList<String> songs;
    private ArrayList<Long> image;
    String album_id;

    public AtoSadapter(Context context, String album_id, ArrayList<String> songs, ArrayList<Long> image, ArrayList<Integer> song_ID) {
        this.songs = songs;
        this.image = image;
        this.song_ID = song_ID;
        this.album_id = album_id;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.atosrow, null);
        }

        String a = (String) getItem(position);
        String b = (String) getItem(position);

        TextView tv1 = (TextView) v.findViewById(R.id.AtoStitle);
        TextView tv2 = (TextView) v.findViewById(R.id.duration);


        //
        //
        // tv1.setMovementMethod(new ScrollingMovementMethod());

        //int check = R.drawable.cassette_example;


        //String s= Integer.toString(check);

        //String SongDurat = image.get(position);





        tv1.setText(songs.get(position));
        //Log.e("song", " " + songs);

        for (int i = 0; i < image.size(); i++) {
            //image.set(i, "D");
            image.get(position);
            //String test = image.get(position);
            //DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            //String formatted = df.format(image);
            //timeDifference(image.get(position));
            tv2.setText(timeDifference(image.get(position)));
            Log.e("song", " " + timeDifference(image.get(position)));

        }





        //Log.e("song", " " + SongDurat);

                //tv2.setText(image.get(position));

        //String check = image.get(i);

        /*Iterator<String> itr = image.iterator();
        while(itr.hasNext()){
            String element = itr.next();
            Log.e("song", " " + element);

            if(element == null){
                //itr.remove();
                tv2.setImageResource(R.drawable.cassette_example);

            }else{
                tv2.setImageBitmap(BitmapFactory.decodeFile(image.get(position)));
            }
        }*/



//        if (image.get(position) == null) {
            // Log.e("song", " " + image);
            //AlbumArt.add(imageUri);
            //AlbumArt.add(q.getString(2));
            //AlbumArt.remove(null);
  //      } else {

    //        tv2.setImageBitmap(BitmapFactory.decodeFile(image.get(position)));

            //AlbumArt.add(q.getString(2));

//        }



        // Log.e("song", " " + image);

        // Log.e("song", " " + image);

        //tv2.setImageResource(R.drawable.cassette_example);



        //tv2.setImageBitmap(BitmapFactory.decodeFile(image.get(position)));



        //tv2.setImageResource(R.drawable.cassette_example);

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
            return image.get(position);
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public static String timeDifference(long timeDifference1) {
        long timeDifference = timeDifference1 / 1000;
        int h = (int) (timeDifference / (3600));
        int m = (int) ((timeDifference - (h * 3600)) / 60);
        int s = (int) (timeDifference - (h * 3600) - m * 60);

        return String.format("%02d:%02d", m, s);
    }
}
