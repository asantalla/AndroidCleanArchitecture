package co.develoop.androidcleanarchitecturesample.domain.model.user;

import java.util.Locale;

public class RandomUserName {

    private String title;
    private String first;
    private String last;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "%s %s %s", title, first, last);
    }
}