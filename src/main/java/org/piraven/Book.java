package org.piraven;

public class Book extends Item {
    private String ISBN;
    private String author;
    private Genre genre;

    public enum Genre {
        SCIENCEFICTION, MYSTERY, HORROR, THRILLER, ROMANCE, HISTORY, FANTASY, DYSTOPIAN
    }
}
