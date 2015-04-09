package com.hijackerstudios.sparrow;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cocosw.bottomsheet.BottomSheet;
import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by cody on 15/12/14.
 */
public class TapeList extends Fragment {

    Context mContext;

    ArrayList<String> playList;
    ArrayList<Integer> ids;
    ArrayList<Integer> ids2 = new ArrayList<Integer>();
    ArrayList<String> count = new ArrayList<String>();

    public String PlayListName;
    public String selectedFromList;

    ListView lv;
    FloatingActionButton fabz;
    MaterialEditText plName;

    int id;

    public MediaStoreHandler mediaStoreHandler = MediaStoreHandler.getInstance(getActivity());

    int playlistID;


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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tape_list, container, false);
        lv = (ListView) rootView.findViewById(R.id.tape_listview);
        fabz = (FloatingActionButton) rootView.findViewById(R.id.fab);
        plName = (MaterialEditText) rootView.findViewById(R.id.playlistname);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fabz.attachToListView(lv);


        displayList();

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                selectedFromList = (lv.getItemAtPosition(position).toString());

              //  Intent in = new Intent(getActivity().getApplicationContext(), CustomPlaylist.class);
               // in.putExtra("playlistID", ids.get(position));
//			System.out.println("my position is "+ ids.get(position));
//			startActivity(in);
              //  getActivity().startActivityForResult(in,101);
//			getActivity().finish();

              //  moreFunc();

                new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog_)
                        //.title("Edit Playlist")
                        // .darkTheme()
                        .sheet(R.menu.bottom_sheet)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case R.id.delete_playlist:
                                        //mediaStoreHandler.removePlaylist(playlistID);
                                        // String selectedFromList =(lv.getItemAtPosition(position).toString());
                                        // Toast toast = Toast.makeText(getActivity(),selectedFromList + " Deleted", Toast.LENGTH_SHORT);
                                        // toast.show();
                                        deletePlaylist(selectedFromList);
                                        displayList();
                                        break;

                                    case R.id.remove_song:
                                        Intent out = new Intent(getActivity().getApplicationContext(), SelectSong.class);
                                        out.putExtra("playlistID", ids.get(position));
                                        out.putExtra("Action", 1);
                                        startActivity(out);
                                        break;

                                    case R.id.add_song:
                                        Intent in = new Intent(getActivity().getApplicationContext(), SelectSong.class);
                                        in.putExtra("playlistID", ids.get(position));
                                        in.putExtra("Action", 0);
                                        startActivity(in);

                                }
                            }
                        }).show();


                return true;
            }
        });

        fabz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreatePlayList();

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        displayList();


    }

    public void CreatePlayList() {

        final MaterialEditText input = new MaterialEditText(getActivity());
        input.setTextColor(Color.parseColor("#000000"));
        input.setHint("Name for tape");
        input.setHintTextColor(Color.parseColor("#BDBDBD"));
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.requestFocus();
        // InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


        new MaterialDialog.Builder(getActivity())
                .title("Create Playlist")
                .customView(input)
                .positiveText("Save")
                .negativeText("Cancel")
                .positiveColor(Color.parseColor("#03a9f4"))
                .callback(new MaterialDialog.FullCallback() {
                    @Override
                    public void onNeutral(MaterialDialog materialDialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog materialDialog) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        materialDialog.dismiss();

                    }

                    @Override
                    public void onPositive(MaterialDialog materialDialog) {

                        PlayListName = input.getText().toString();

                        if (playList.contains(PlayListName)) {

                            Toast.makeText(getActivity(), "Playlist already exist!", Toast.LENGTH_SHORT).show();

                        } else {

                            id = ((MainActivity) getActivity()).mediaStoreHandler.addPlayList(PlayListName);
                            ids.add(id);
                            playList.add(PlayListName);

                        }

                    }

                })
                .build()
                .show();
    }

    public void displayList() {

        ids = new ArrayList<Integer>();
        playList = new ArrayList<String>();

        ((MainActivity) getActivity()).mediaStoreHandler.getPlayListNames(playList, ids);
        // ((MainActivity)getActivity()).mediaStoreHandler.getPlayListSize(id);
        TapeAdapter Tadapter = new TapeAdapter(getActivity(), playList);
        lv.setAdapter(Tadapter);

       /* ids = new ArrayList<Integer>();
        playList = new ArrayList<String>();
        ((MainActivity)getActivity()).mediaStoreHandler.getPlayListNames(playList, ids);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.play_list_itemlabel, playList);
        lv.setAdapter(adapter);*/
    }

    private void deletePlaylist(String selectedplaylist) {
// // Log.i(TAG, "deletePlaylist");
        String playlistid = getPlayListId(selectedplaylist);
        ContentResolver resolver = getActivity().getContentResolver();
        String where = MediaStore.Audio.Playlists._ID + "=?";
        String[] whereVal = {playlistid};
        resolver.delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, where, whereVal);
        Toast toast = Toast.makeText(getActivity(), selectedplaylist + " Deleted", Toast.LENGTH_SHORT);
        toast.show();
        return;
    }

    public String getPlayListId(String playlist) {
        //  read this record and get playlistid
        Uri newuri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        final String playlistid = MediaStore.Audio.Playlists._ID;
        final String playlistname = MediaStore.Audio.Playlists.NAME;
        String where = MediaStore.Audio.Playlists.NAME + "=?";
        String[] whereVal = {playlist};
        String[] projection = {playlistid, playlistname};
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor record = resolver.query(newuri, projection, where, whereVal, null);
        int recordcount = record.getCount();
        String foundplaylistid = "";
        if (recordcount > 0) {
            record.moveToFirst();
            int idColumn = record.getColumnIndex(playlistid);
            foundplaylistid = record.getString(idColumn);
            record.close();
        }
        return foundplaylistid;
    }

  /*  public void moreFunc() {

        new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog_)
                //.title("Edit Playlist")
                // .darkTheme()
                .sheet(R.menu.bottom_sheet)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.delete_playlist:
                                //mediaStoreHandler.removePlaylist(playlistID);
                                // String selectedFromList =(lv.getItemAtPosition(position).toString());
                                // Toast toast = Toast.makeText(getActivity(),selectedFromList + " Deleted", Toast.LENGTH_SHORT);
                                // toast.show();
                                deletePlaylist(selectedFromList);
                                displayList();
                                break;

                            case R.id.remove_song:
                                Intent out = new Intent(getActivity().getApplicationContext(), SelectSong.class);
                                out.putExtra("playlistID", ids.get(position));
                                out.putExtra("Action", 1);
                                startActivity(out);
                                break;

                            case R.id.add_song:
                                Intent in = new Intent(getActivity().getApplicationContext(), SelectSong.class);
                                in.putExtra("playlistID", ids.get(position));
                                in.putExtra("Action", 0);
                                startActivity(in);

                        }
                    }
                }).show();

    }*/

    public class TapeAdapter extends BaseAdapter {

        Context context;
        private ArrayList<String> tape;
        //private ArrayList<String> counts;

        public TapeAdapter(Context context, ArrayList<String> tape) {
            this.tape = tape;
            //this.counts = counts;
            this.context = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.tape_row, null);
            }

            String a = (String) getItem(position);
            //String b = (String) getItem(position);

            TextView tv1 = (TextView) v.findViewById(R.id.tapez);
            //TextView tv2 = (TextView) v.findViewById(R.id.songs);

            tv1.setText(tape.get(position));
            // tv2.setText(counts.get(position));

            final ImageView moreButton = (ImageView) v.findViewById(R.id.more_button);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu popUp = new PopupMenu(getActivity(), moreButton);

                    popUp.getMenuInflater().inflate(R.menu.bottom_sheet, popUp.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {


                            switch (item.getItemId()) {
                                case R.id.delete_playlist:
                                    selectedFromList = (lv.getItemAtPosition(position).toString());
                                    //mediaStoreHandler.removePlaylist(playlistID);
                                    // String selectedFromList =(lv.getItemAtPosition(position).toString());
                                    // Toast toast = Toast.makeText(getActivity(),selectedFromList + " Deleted", Toast.LENGTH_SHORT);
                                    // toast.show();
                                    deletePlaylist(selectedFromList);
                                    displayList();
                                    break;

                                case R.id.remove_song:
                                    Intent out = new Intent(getActivity().getApplicationContext(), SelectSong.class);
                                    out.putExtra("playlistID", ids.get(position));
                                    out.putExtra("Action", 1);
                                    startActivity(out);
                                    break;

                                case R.id.add_song:
                                    Intent in = new Intent(getActivity().getApplicationContext(), SelectSong.class);
                                    in.putExtra("playlistID", ids.get(position));
                                    in.putExtra("Action", 0);
                                    startActivity(in);
                                    Toast.makeText(getActivity(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                            }
                            return true;
                        }
                    });

                    popUp.show();//showing popup menu

                }
            });




            return v;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return tape.size();
        }

        @Override
        public Object getItem(int position) {
            //  if (position >= tape.size())
            //     return counts.get(position);
            return tape.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

    }
}





