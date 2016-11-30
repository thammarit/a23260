package com.egco428.a23260;

/**
 * Created by Thammarit on 18/11/2559.
 */

public class Map {

    private long id;
    private String username;
    private String password;
    private String latitude;
    private String longtitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password;}

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {this.latitude = latitude;}

    public String getLongtitude() {return longtitude;}

    public void setLongtitude(String longtitude) {this.longtitude = longtitude;}


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return username;
    }

}
