package uk.ac.tees.com2060.kitkat.generalist;


/**
 * Created by q5052694 on 07/03/2017.
 */

public class ListInfo {

    private int ID;
    private String name;
    private String contents;
    private String category;

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

    public ListInfo(String name, String contents, String category) {

        this.name = name;
        this.category = category;
        this.contents = contents;
    }

    public ListInfo(int ID, String name, String contents, String category) {

        this.ID = ID;
        this.name = name;
        this.contents = contents;
        this.category = category;
    }
}