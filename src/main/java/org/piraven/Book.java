package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Book extends Item {
    private String ISBN;
    private String author;
    private Genre genre;

    public Book(String title, Status status, String ISBN, Genre genre, String author) {
        super(title, status);
        if (!Validation.isValidISBN(ISBN)) {
            throw new IllegalArgumentException("Invalid ISBN");
        }

        this.ISBN = ISBN;
        this.genre = genre;
        this.author = author;
    }

    public enum Genre {
        SCIENCEFICTION, MYSTERY, HORROR, THRILLER, ROMANCE, HISTORY, FANTASY, DYSTOPIAN
    }
}
