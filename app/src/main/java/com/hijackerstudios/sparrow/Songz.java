package com.hijackerstudios.sparrow;

/**
 * Created by cody on 29/03/15.
 */
public class Songz {

    private long id;
    private String title;
    private String artist;

    public Songz(long songID, String songTitle, String songArtist){
        id=songID;
        title=songTitle;
        artist=songArtist;
    }

    public long getID(){return id;}
    public String gettitle(){return title;}
    public String getartist(){return artist;}
}
