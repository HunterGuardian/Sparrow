package com.hijackerstudios.sparrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by cody on 23/02/15.
 */
public class GridAdapter extends BaseAdapter{

    boolean empty = true;

    Context context;
    private ArrayList<String> songs;
    private ArrayList<String> image;

    public GridAdapter(Context context, ArrayList<String> songs, ArrayList<String> image) {
        this.songs = songs;
        this.image = image;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_row, null);
        }

        String a = (String) getItem(position);
        String b = (String) getItem(position);

        TextView tv1 = (TextView) v.findViewById(R.id.album_name);
        ImageView tv2 = (ImageView) v.findViewById(R.id.albumz_art);

       //
       //
       // tv1.setMovementMethod(new ScrollingMovementMethod());

        //int check = R.drawable.cassette_example;


        //String s= Integer.toString(check);



        tv1.setText(songs.get(position));
        //Log.e("song", " " + songs);

//                tv2.setImageBitmap(BitmapFactory.decodeFile(image.get(position)));

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
}
