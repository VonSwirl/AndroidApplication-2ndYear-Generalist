package uk.ac.tees.com2060.kitkat.generalist;


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
    private int checked;

    public long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(long epochDate) {
        this.epochDate = epochDate;
    }

    public int getChecked() { return checked; }

    public void setChecked(int checked) { this.checked = checked; }

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

    public String toString() {
        return name;
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

    //Used for saving/updating all
    public ListInfo(String name, String contents, String category, int active, long date, int checked) {

        this.name = name;
        this.category = category;
        this.contents = contents;
        this.active = active;
        this.epochDate = date;
        this.checked = checked;
    }

    //Used for saving/updating by a single ID
    public ListInfo(int ID, String name, String contents, String category, int active, long date, int checked) {

        this.ID = ID;
        this.name = name;
        this.contents = contents;
        this.category = category;
        this.active = active;
        this.epochDate = date;
        this.checked = checked;
    }
}