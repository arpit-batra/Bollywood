package com.example.bollywood;

import java.util.List;

public class Room {
    private String roomName;
    private List<String> roomMembers;

    public Room() {
    }

    public Room(String roomName, List<String> roomMembers) {
        this.roomName = roomName;
        this.roomMembers = roomMembers;
    }

    public String getRoomName() {
        return roomName;
    }

    public List<String> getRoomMembers() {
        return roomMembers;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomMembers(List<String> roomMembers) {
        this.roomMembers = roomMembers;
    }
}
