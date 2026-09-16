package com.smarthome;
public record RoomLocation(String floor, String roomName) {
    public RoomLocation {
        if (floor == null || roomName == null) {
            throw new IllegalArgumentException("Floor and roomName must not be null");
        }
    }
}
