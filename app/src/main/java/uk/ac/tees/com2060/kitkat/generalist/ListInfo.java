package uk.ac.tees.com2060.kitkat.generalist;


import android.os.Parcelable;

/**
 * Created by q5052694 on 07/03/2017.
 */

public class ListInfo {

    private int ID;
    private String name;
    private String contents;
    private String category;
    private int active;
    private long epochDate;

    public long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(long epochDate) {
        this.epochDate = epochDate;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String toString(){
        return  contents;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public ListInfo(String name, String contents, String category, int active, long date) {

        this.name = name;
        this.category = category;
        this.contents = contents;
        this.active = active;
        this.epochDate = date;
    }

    public ListInfo(int ID, String name, String contents, String category, int active, long date) {

        this.ID = ID;
        this.name = name;
        this.contents = contents;
        this.category = category;
        this.active = active;
        this.epochDate = date;
    }
}