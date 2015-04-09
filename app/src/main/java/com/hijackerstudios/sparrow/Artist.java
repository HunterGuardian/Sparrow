package com.hijackerstudios.sparrow;

/**
 * Created by cody on 29/03/15.
 */
public class Artist {

    private long id;
    private String title;
    private String artist;
    private String Album;
    private String songart;

    public Artist(long songID, String songArtist, String aritistAlbums, String aritistSongs){
        id=songID;
        artist=songArtist;
        Album=aritistAlbums;
        songart=aritistSongs;
    }

    public long getID(){return id;}
    public String getartist(){return artist;}
    public String getAlbum(){return Album;}
    public String getSongart(){return songart;}

}
