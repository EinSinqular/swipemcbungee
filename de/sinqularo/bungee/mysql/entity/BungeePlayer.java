package de.sinqularo.bungee.mysql.entity;

public class BungeePlayer {

    private String uuid;
    private String name;
    private String ipAdress;
    private int globalPoints;
    private int joinCount;


    public String getUuid() {
        return uuid;
    }

    public int getGlobalPoints() {
        return globalPoints;
    }

    public String getName() {
        return name;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setGlobalPoints(int globalPoints) {
        this.globalPoints = globalPoints;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }
}

