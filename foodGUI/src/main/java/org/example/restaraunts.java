package org.example;

public class restaraunts
{
    private int restaraunt_id;
    private String address;
    private String name;

    public restaraunts(int restaraunt_id, String address, String name) {
        this.restaraunt_id = restaraunt_id;
        this.address = address;
        this.name = name;
    }

    public int getRestaraunt_id() {
        return restaraunt_id;
    }

    public void setRestaraunt_id(int restaraunt_id) {
        this.restaraunt_id = restaraunt_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
