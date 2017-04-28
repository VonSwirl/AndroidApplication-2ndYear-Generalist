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
    private int year;
    private int month;
    private int day;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public void setCategory(String category) {

        this.category = category;
    }

    public String getContents() {

        return contents;
    }

    public void setContents(String contents) {

        this.contents = contents;
    }

    public ListInfo(String name, String contents, String category, int active, int year, int month, int day) {

        this.name = name;
        this.category = category;
        this.contents = contents;
        this.active = active;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public ListInfo(int ID, String name, String contents, String category, int active, int year, int month, int day) {

        this.ID = ID;
        this.name = name;
        this.contents = contents;
        this.category = category;
        this.active = active;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}