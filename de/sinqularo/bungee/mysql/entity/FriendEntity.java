package de.sinqularo.bungee.mysql.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendEntity {

    private String uuid;
    private String name;

    private ArrayList<String> friends;
    private HashMap<String, String> request;
    private ArrayList<String> favorites;
    private ArrayList<String> settings;

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getSettings() {
        return settings;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public HashMap<String, String> getRequest() {
        return request;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequest(HashMap<String, String> request) {
        this.request = request;
    }

    public void setSettings(ArrayList<String> settings) {
        this.settings = settings;
    }

}