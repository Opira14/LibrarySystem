package org.piraven;

public class Book extends Item {
    private String ISBN;
    private String author;
    private Genre genre;

    public enum Genre {
        SCIENCEFICTION, MYSTERY, LITERARYFICTION, HORROR, HISTORICALFICTION, THRILLER,
        ROMANCE, HISTORY, FANTASY, BIOGRAPHY, WESTERN, BILDUNGSROMAN,
        DYSTOPIAN, MAGICALREALISM, REALISTLITERATURE
    }
}
