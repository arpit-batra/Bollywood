package com.example.bollywood;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Room {
    private String roomName;
    private int host;
    private ArrayList<Player> players;
    private String currentMovieName;

    public Room() {
    }

    public Room(String roomName, ArrayList<Player>players,String currentMovieName,int host) {
        this.roomName = roomName;
        this.players = players;
        this.currentMovieName = currentMovieName;
        this.host = host;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public String getCurrentMovieName() {
        return currentMovieName;
    }

    public String getRoomName() {
        return roomName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setCurrentMovieName(String currentMovieName) {
        this.currentMovieName = currentMovieName;
    }
}
